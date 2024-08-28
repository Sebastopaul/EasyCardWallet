package com.isitechproject.barcodescanner

import android.content.pm.PackageManager
import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.isitechproject.businesscardscanner.BusinessCardScannerApp
import com.isitechproject.easycardwallet.AUTH_PORT
import com.isitechproject.easycardwallet.FIRESTORE_PORT
import com.isitechproject.easycardwallet.LOCALHOST
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class BarcodeScannerActivity : AppCompatActivity() {
    lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureFirebaseServices()

        cameraExecutor = Executors.newSingleThreadExecutor()

        checkCameraPermission()
    }

    private fun configureFirebaseServices() {
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator(
                LOCALHOST,
                AUTH_PORT
            )
            Firebase.firestore.useEmulator(
                LOCALHOST,
                FIRESTORE_PORT
            )
        }
    }

    private fun checkCameraPermission() {
        try {
            val requiredPermissions = arrayOf(Manifest.permission.CAMERA)
            ActivityCompat.requestPermissions(this, requiredPermissions, 0)
        } catch (e: IllegalArgumentException) {
            checkIfCameraPermissionIsGranted()
        }
    }

    private fun checkIfCameraPermissionIsGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted: start the preview
            setContent {
                EasyCardWalletTheme {
                    BarcodeScannerApp()
                }
            }
        } else {
            // Permission denied
            MaterialAlertDialogBuilder(this)
                .setTitle("Permission required")
                .setMessage("This application needs to access the camera to process barcodes")
                .setPositiveButton("Ok") { _, _ ->
                    // Keep asking for permission until granted
                    checkCameraPermission()
                }
                .setCancelable(true)
                .setOnCancelListener {
                    setContent {
                        BarcodeScannerApp(EASY_CARD_WALLET_MAIN_SCREEN)
                    }
                }
                .create()
                .apply {
                    setCanceledOnTouchOutside(true)
                    show()
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ActivityResultContracts.RequestPermission()
        checkIfCameraPermissionIsGranted()
    }

    fun shutdownCamera() {
        if (!cameraExecutor.isShutdown && !cameraExecutor.isTerminated) {
            cameraExecutor.shutdown()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        shutdownCamera()
    }
}
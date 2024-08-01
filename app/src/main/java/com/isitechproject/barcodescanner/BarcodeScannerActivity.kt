package com.isitechproject.barcodescanner

import android.content.pm.PackageManager
import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.isitechproject.easycardwallet.AUTH_PORT
import com.isitechproject.easycardwallet.FIRESTORE_PORT
import com.isitechproject.easycardwallet.LOCALHOST
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.databinding.BarcodeScannerBinding
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class BarcodeScannerActivity : AppCompatActivity() {
    private val viewModel = BarcodeScannerViewModel(AccountServiceImpl())

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureFirebaseServices()

        cameraExecutor = Executors.newSingleThreadExecutor()

        setContent {
            EasyCardWalletTheme {
                BarcodeScannerApp(this)
            }
        }

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

    fun checkCameraPermission() {
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
            startCamera()
        } else {
            // Permission denied
            MaterialAlertDialogBuilder(this)
                .setTitle("Permission required")
                .setMessage("This application needs to access the camera to process barcodes")
                .setPositiveButton("Ok") { _, _ ->
                    // Keep asking for permission until granted
                    checkCameraPermission()
                }
                .setCancelable(false)
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
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

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            viewModel.cameraListener(
                cameraExecutor,
                cameraProviderFuture,
                findViewById(R.id.barcode_scanner),
                this,
            )
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()

        cameraExecutor.shutdown()
    }
}
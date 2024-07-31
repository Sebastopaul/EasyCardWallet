package com.isitechproject.barcodescanner

import android.content.pm.PackageManager
import android.Manifest
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
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
import com.isitechproject.easycardwallet.databinding.BarcodeScannerBinding
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.LoyaltyCardServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.UserServiceImpl
import com.isitechproject.easycardwallet.utils.BarcodeBoxView
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class BarcodeScannerActivity(
    private val createCard: () -> Unit
) : AppCompatActivity() {
    private val accountService = AccountServiceImpl()
    private val viewModel = BarcodeScannerViewModel(
        accountService,
    )

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var barcodeBoxView: BarcodeBoxView
    private lateinit var binding: BarcodeScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureFirebaseServices()

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding = BarcodeScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barcodeBoxView = BarcodeBoxView(this)
        addContentView(barcodeBoxView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

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
                binding,
                barcodeBoxView,
                this,
                createCard,
            )
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()

        cameraExecutor.shutdown()
    }
}
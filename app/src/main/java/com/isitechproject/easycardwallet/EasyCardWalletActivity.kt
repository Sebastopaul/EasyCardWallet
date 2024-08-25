package com.isitechproject.easycardwallet

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.isitechproject.barcodescanner.BarcodeScannerApp
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EasyCardWalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureFirebaseServices()

        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        try {
            val requiredPermissions: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                buildPermissionArrayForUDCApi()
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                buildPermissionArrayForTApi()
            } else {
               buildPermissionArrayForAnteriorApi()
            }
            ActivityCompat.requestPermissions(this, requiredPermissions, 0)
        } catch (e: IllegalArgumentException) {
            checkIfStoragePermissionIsGranted()
        }
    }

    private fun checkSinglePermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun arePermissionsGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            checkSinglePermission(Manifest.permission.READ_MEDIA_IMAGES) && checkSinglePermission(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkSinglePermission(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            checkSinglePermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun checkIfStoragePermissionIsGranted() {
        if (arePermissionsGranted()) {
            // Permission granted: start the views
            setContent {
                EasyCardWalletTheme {
                    EasyCardWalletApp()
                }
            }
        } else {
            // Permission denied
            MaterialAlertDialogBuilder(this)
                .setTitle("Permission required")
                .setMessage("This application needs to access the storage to process barcodes")
                .setPositiveButton("Ok") { _, _ ->
                    // Keep asking for permission until granted
                    checkStoragePermission()
                }
                .setCancelable(false)
                .create()
                .apply {
                    setCanceledOnTouchOutside(false)
                    show()
                }
        }
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

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun buildPermissionArrayForUDCApi(): Array<String> {
        return arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun buildPermissionArrayForTApi(): Array<String> {
        return arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
        )
    }

    private fun buildPermissionArrayForAnteriorApi(): Array<String> {
        return arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}
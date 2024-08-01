package com.isitechproject.easycardwallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EasyCardWalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureFirebaseServices()

        setContent {
            EasyCardWalletTheme {
                EasyCardWalletApp()
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
}
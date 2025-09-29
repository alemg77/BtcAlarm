package com.alarm

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.alarm.ui.cryptoScreen.CryptoPriceScreen
import com.alarm.ui.cryptoScreen.CryptoViewModel
import com.alarm.ui.theme.BtcAlarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BtcAlarmTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CryptoPriceScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel =  CryptoViewModel(this.application),
                        onEnterPip = { enterPictureInPicture() }
                    )
                }
            }
        }
    }

    private fun enterPictureInPicture() {
        val aspectRatio = Rational(1, 1) // Cuadrado
        val pipBuilder = PictureInPictureParams.Builder()
            .setAspectRatio(aspectRatio)
            .build()

        enterPictureInPictureMode(pipBuilder)
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        if (isInPictureInPictureMode) {
            // Ocultar UI no esencial
            Log.v("TAGGG", "Esta en modo PIP")
        } else {
            // Mostrar UI completa
            Log.v("TAGGG", "Modo normal")
        }
    }
}
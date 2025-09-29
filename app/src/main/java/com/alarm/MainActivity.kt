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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.alarm.ui.cryptoScreen.CryptoPriceScreen
import com.alarm.ui.cryptoScreen.CryptoViewModel
import com.alarm.ui.theme.BtcAlarmTheme

class MainActivity : ComponentActivity() {

    private val isInPip = mutableStateOf(false)

    private lateinit var viewModel: CryptoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = CryptoViewModel(this.application)
        enableEdgeToEdge()
        setContent {
            BtcAlarmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CryptoPriceScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel =  viewModel,
                        onEnterPip = { enterPictureInPicture() },
                        isInPip = isInPip,
                    )
                }
            }
        }
    }

    private fun enterPictureInPicture() {
        val aspectRatio = Rational(3, 2)
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

        isInPip.value = isInPictureInPictureMode

        if (isInPictureInPictureMode) {
            // Ocultar UI no esencial
            Log.v("TAGGG", "Esta en modo PIP")
        } else {
            // Mostrar UI completa
            Log.v("TAGGG", "Modo normal")
        }
    }
}
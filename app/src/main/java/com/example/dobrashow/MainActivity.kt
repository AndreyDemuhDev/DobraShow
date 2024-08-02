package com.example.dobrashow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.dobrashow.screens.DetailShowScreen
import com.example.dobrashow.ui.theme.DobraShowTheme
import com.example.network.KtorClient
import com.example.network.models.domain.DomainShowEntity
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            DobraShowTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DetailShowScreen(
                        ktorClient = ktorClient,
                        showId = 1,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
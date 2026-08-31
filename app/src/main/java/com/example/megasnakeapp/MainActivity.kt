package com.example.megasnakeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.megasnakeapp.ui.theme.MEGASNAKEAPPTheme

// Texto do popup "Sobre a equipe" - preencha aqui quando quiser
private const val ABOUT_TEAM_TEXT = ""

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MEGASNAKEAPPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppFlow(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppFlow(modifier: Modifier = Modifier) {
    var showTerms by remember { mutableStateOf(true) }
    var connected by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            connected -> CameraScreen(onBack = { connected = false })
            else -> ConnectScreen(onReady = { connected = true })
        }
    }

    if (showTerms) {
        AlertDialog(
            onDismissRequest = { /* precisa aceitar pra fechar */ },
            title = { Text("Termos de Uso") },
            text = {
                Text("Termos de uso(Não tem ainda kkk)")
            },
            confirmButton = {
                TextButton(onClick = { showTerms = false }) {
                    Text("Aceitar")
                }
            }
        )
    }
}

@Composable
fun ConnectScreen(onReady: () -> Unit) {
    var showAboutTeam by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.logo_mega_snakes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f
        )

        Text(
            text = "Conecte-se ao robô\n         Pelo WiFi",
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = "Saiba Mais",
            color = Color(0xFF64B5F6),
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
                .clickable { showAboutTeam = true }
        )

        Button(
            onClick = onReady,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Text("TUDO PRONTO")
        }
    }

    if (showAboutTeam) {
        AlertDialog(
            onDismissRequest = { showAboutTeam = false },
            title = { Text("🐍 Sobre a equipe") },
            text = { Text(ABOUT_TEAM_TEXT) },
            confirmButton = {
                TextButton(onClick = { showAboutTeam = false }) {
                    Text("Fechar")
                }
            }
        )
    }
}
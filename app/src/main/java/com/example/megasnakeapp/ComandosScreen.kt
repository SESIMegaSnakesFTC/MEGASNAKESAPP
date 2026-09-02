package com.example.megasnakeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val MegaGreen = Color(0xFF1E5631)
private val ButtonGreen = Color(0xFF66BB6A)

@Composable
fun ComandosScreen(onBack: () -> Unit, onNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MegaGreen)
            .padding(24.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp)
        ) {
            Text("←", color = Color.White, fontSize = 40.sp)
        }

        Image(
            painter = painterResource(id = R.drawable.logo_transparente),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(56.dp)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "COMANDOS",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.hand),
                contentDescription = "Comandos de gestos",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }

        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text("Próximo", color = Color.White)
        }
    }
}
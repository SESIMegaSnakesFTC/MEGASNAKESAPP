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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
private const val ABOUT_TEAM_TEXT =
        "Quem Somos \n\n" +
        "Nós somos o SESI MEGASNAKES, uma equipe que carrega no nome a identidade de Boituva - “Grandes Cobras”, em Tupi-Guarani - um símbolo que nos inspira a seguir com força, respeito e resiliência\n" +
        "Tudo começou em 2013. Tivemos diversos integrantes durante todo esse período, todos foram muito especiais, como nossa equipe atual!\n" +
        "A cada temporada buscamos ter novas experiências, aprendizados, emoções e conquistas, sempre fazendo o nosso melhor.\n" +
        "Dentro de todas as temporadas alguns integrantes tiveram a oportunidade de disputar o TORNEIOS MUNDIAS! Assim, acumulamos 3 idas para torneios internacionais de FLL.\n" +
        "\n" +
        "O SESI MEGASNAKES teve início em 2013, quando um grupo de estudantes do SESI CE332 - Boituva decidiu unir suas paixões por tecnologia e inovação para participar das competições da FIRST. Desde então, a equipe tem crescido e evoluído a cada temporada.\n" +
        "Nos primeiros anos, a equipe aprendeu os fundamentos da robótica e da programação, enfrentando os desafios com dedicação e criatividade. Com o tempo, fomos conquistando resultados expressivos nos campeonatos regionais e nacionais.\n" +
        "\n" +
        "Além das competições, sempre priorizamos o impacto na comunidade, desenvolvendo projetos que levam a educação STEAM para mais pessoas. Acreditamos que a robótica é uma ferramenta poderosa de transformação social.\n" +
        "Hoje, somos uma equipe consolidada, com membros comprometidos, mentores experientes e patrocinadores que acreditam na nossa missão. Cada geração traz novas ideias e continua escrevendo esta história.\n\n" +
        "Nossa Missão" +
        "\n" +
        "Promover o aprendizado por meio da robótica, desenvolvendo habilidades técnicas e humanas nos nossos membros para que se tornem cidadãos capazes de transformar o mundo." +
        "\n" +
        "Nossa Visão" +
        "\n" +
        "Ser referência em robótica educacional na região, inspirando novas gerações a se interessarem por ciência e tecnologia, e impactando positivamente a comunidade.\n" +
        "Nossos Valores" +
        "\n" +
        "Colaboração, respeito, criatividade, ética, responsabilidade social e paixão pelo aprendizado contínuo."

private const val Termos =
        "Aplicativo de Controle Robótico – Equipe Mega Snakes Tech Challenge\n" +
        "\n" +
        "Bem-vindo(a) ao aplicativo da equipe SESI MEGASNAKES FTC! Este aplicativo foi desenvolvido com fins exclusivamente educacionais para conectar você ao nosso robô/carrinho construído com Arduino, estimulando o aprendizado prático de tecnologia e programação.\n" +
        "\n" +
        "Ao clicar em \"Aceitar\", você concorda com as condições de uso descritas abaixo:\n" +
        "\n" +
        "1. CONECTIVIDADE WI-FI\n" +
        "Para controlar o carrinho, o aplicativo precisa se comunicar com a placa Arduino através de uma conexão Wi-Fi. Certifique-se de estar conectado à rede correta do robô para permitir o envio dos comandos.\n" +
        "\n" +
        "2. USO DA CÂMERA E RECONHECIMENTO DE IMAGEM\n" +
        "Para permitir que o carrinho seja controlado por gestos de mão, este aplicativo utiliza a câmera do seu celular:\n" +
        "\n" +
        "Processamento de Imagem: O aplicativo utiliza a câmera para identificar a imagem da mão da criança em tempo real e transformá-la em comandos para o robô.\n" +
        "\n" +
        "Privacidade e Proteção de Dados: Nenhuma foto, imagem ou vídeo da criança é gravado, armazenado ou enviado para a internet/servidores. O reconhecimento visual ocorre 100% de forma local no próprio celular e a imagem é descartada instantaneamente após a leitura do comando.\n" +
        "\n" +
        "3. COMANDOS DE MOVIMENTO\n" +
        "Os comandos reconhecidos pelo aplicativo através da câmera são:\n" +
        "\n" +
        "✋ Mão Aberta (Palma): AVANÇAR\n" +
        "\n" +
        "✊ Mão Fechada (Punho): PARAR\n" +
        "\n" +
        "\uD83D\uDC4D Polegar para a Direita: DIREITA\n" +
        "\n" +
        "\uD83D\uDC48 Polegar para a Esquerda: ESQUERDA\n" +
        "\n" +
        "4. RECOMENDAÇÕES DE SEGURANÇA\n" +
        "Recomendamos que o uso por crianças seja acompanhado por um responsável.\n" +
        "\n" +
        "Opere o robô em superfícies planas e livres de obstáculos para evitar acidentes ou quedas do robô.\n" +
        "\n" +
        "5. FINALIDADE EDUCACIONAL\n" +
        "Este projeto é um protótipo de robótica educacional sem fins lucrativos, desenvolvido para engajar e inspirar jovens nas áreas de ciência, tecnologia, engenharia e matemática (STEM)."


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MEGASNAKEAPPTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppFlow(modifier = Modifier.padding(innerPadding))
                }
                }
            }
        }
    }

enum class Screen { CONNECT, COMMANDS, CAMERA }

@Composable
fun AppFlow(modifier: Modifier = Modifier) {
    var showTerms by remember { mutableStateOf(true) }
    var screen by remember { mutableStateOf(Screen.CONNECT) }

    Box(modifier = modifier.fillMaxSize()) {
        when (screen) {
            Screen.CONNECT -> ConnectScreen(onReady = { screen = Screen.COMMANDS })
            Screen.COMMANDS -> ComandosScreen(
                onBack = { screen = Screen.CONNECT },
                onNext = { screen = Screen.CAMERA }
            )
            Screen.CAMERA -> CameraScreen(onBack = { screen = Screen.CONNECT })
        }
    }

    if (showTerms) {
        AlertDialog(
            onDismissRequest = { /* precisa aceitar pra fechar */ },
            title = { Text("Termos de Uso") },
            text = { Text( text= Termos, modifier = Modifier.verticalScroll(rememberScrollState())) },

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
            painter = painterResource(id = R.drawable.logo_transparente),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(76.dp)

        )

        Image(
            painter = painterResource(id = R.drawable.fundo_verde),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f
        )

        Text(
            text = "Conecte-se ao robô\n Pelo WiFi/Bluetooth",
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
            text = {
                Text(
                    text = ABOUT_TEAM_TEXT,
                    // É esta linha que faz a mágica acontecer:
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            },
            confirmButton = {
                TextButton(onClick = { showAboutTeam = false }) {
                    Text("Fechar")
                }
            }
        )
    }
}
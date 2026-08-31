package com.example.megasnakeapp

import android.util.Log
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult

private const val WRIST = 0
private const val MIDDLE_MCP = 9 // base do dedo médio, usado como referência de orientação
private const val THUMB_TIP = 4
private const val INDEX_TIP = 8
private const val INDEX_PIP = 6
private const val MIDDLE_TIP = 12
private const val MIDDLE_PIP = 10
private const val RING_TIP = 16
private const val RING_PIP = 14
private const val PINKY_TIP = 20
private const val PINKY_PIP = 18

private const val CONFIRMATION_MS = 2000L
private const val THUMB_SIDE_THRESHOLD = 0.2f // ajuste se precisar (0.3 a 0.7)

private var allDownSince: Long = -1L
private var pendingGesture: String = "NONE"
private var pendingSince: Long = 0L

private fun rawGesture(result: HandLandmarkerResult): String {
    if (result.landmarks().isEmpty()) {
        allDownSince = -1L
        return "NONE"
    }

    val lm = result.landmarks()[0]
    val wrist = lm[WRIST]

    fun distSq(idx: Int): Float {
        val dx = lm[idx].x() - wrist.x()
        val dy = lm[idx].y() - wrist.y()
        return dx * dx + dy * dy
    }

    val indexUp = distSq(INDEX_TIP) > distSq(INDEX_PIP)
    val middleUp = distSq(MIDDLE_TIP) > distSq(MIDDLE_PIP)
    val ringUp = distSq(RING_TIP) > distSq(RING_PIP)
    val pinkyUp = distSq(PINKY_TIP) > distSq(PINKY_PIP)
    val fingersUp = listOf(indexUp, middleUp, ringUp, pinkyUp).count { it }

    // Eixo "pra frente da mão": do pulso até a base do dedo médio
    val forwardX = lm[MIDDLE_MCP].x() - wrist.x()
    val forwardY = lm[MIDDLE_MCP].y() - wrist.y()
    val handScale = kotlin.math.sqrt(forwardX * forwardX + forwardY * forwardY)
        .coerceAtLeast(0.0001f)

    // Eixo "lado da mão": perpendicular ao "pra frente" (gira 90°)
    val rightX = -forwardY
    val rightY = forwardX

    val thumbVecX = lm[THUMB_TIP].x() - wrist.x()
    val thumbVecY = lm[THUMB_TIP].y() - wrist.y()

    val sideValue = (thumbVecX * rightX + thumbVecY * rightY) / handScale
    val thumbExtended = kotlin.math.abs(sideValue) > THUMB_SIDE_THRESHOLD

    val gesture = when {
        fingersUp == 4 && thumbExtended -> {
            allDownSince = -1L
            "PARA_FRENTE"
        }
        fingersUp == 0 && thumbExtended -> {
            allDownSince = -1L
            // Se sair invertido no seu teste, troque o sinal aqui (> por <)
            if (sideValue > 0) "ESQUERDA" else "DIREITA"
        }
        fingersUp == 0 && !thumbExtended -> {
            val now = System.currentTimeMillis()
            if (allDownSince == -1L) allDownSince = now
            if (now - allDownSince > 2000) "PARA_TRAS" else "PARAR"
        }
        else -> {
            allDownSince = -1L
            "NONE"
        }
    }

    Log.d("GESTO", "fingersUp=$fingersUp sideValue=$sideValue thumbExtended=$thumbExtended -> $gesture")
    return gesture
}

fun interpretGesture(result: HandLandmarkerResult): String {
    val raw = rawGesture(result)

    if (raw == "PARAR") {
        pendingGesture = "NONE"
        return "PARAR"
    }

    val now = System.currentTimeMillis()
    if (raw != pendingGesture) {
        pendingGesture = raw
        pendingSince = now
        return "NONE"
    }

    return if (now - pendingSince >= CONFIRMATION_MS) raw else "NONE"
}

val gestureToCode: Map<String, Int> = mapOf(
    "PARAR" to 0,
    "PARA_FRENTE" to 1,
    "DIREITA" to 2,
    "ESQUERDA" to 3,
    "PARA_TRAS" to 4,
    "NONE" to -1
)
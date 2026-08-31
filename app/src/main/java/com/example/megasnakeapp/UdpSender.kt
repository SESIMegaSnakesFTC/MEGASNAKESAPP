package com.example.megasnakeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UdpSender(
    private val ip: String = "192.168.4.1", // IP padrão do ESP8266
    private val port: Int = 4210
) {
    private val socket = DatagramSocket()

    suspend fun send(code: Int) = withContext(Dispatchers.IO) {
        try {
            val address = InetAddress.getByName(ip)
            val data = byteArrayOf(code.toByte())
            val packet = DatagramPacket(data, data.size, address, port)
            socket.send(packet)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
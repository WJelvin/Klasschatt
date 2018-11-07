package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatSender {

    MulticastSocket msocket;
    InetAddress ip;
    int port;

    public ChatSender(MulticastSocket msocket, InetAddress ip, int port) throws IOException {
        this.msocket = msocket;
        this.ip = ip;
        this.port = port;
    }

    public void sendMessage(String message) throws IOException {
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        msocket.send(packet);
    }

    public void disconnectFromChat() throws IOException {
        msocket.leaveGroup(ip);
    }
}

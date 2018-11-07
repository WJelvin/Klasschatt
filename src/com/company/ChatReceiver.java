package com.company;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatReceiver implements Runnable {
    Thread thread = new Thread(this);
    JTextArea textArea;


    private MulticastSocket msocket;

    public ChatReceiver(MulticastSocket msocket, InetAddress ip, int port, JTextArea textArea) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.out.println(ip.getHostAddress() + " " + port);
        this.msocket = msocket;
        this.textArea = textArea;
        thread.start();
        System.out.println("Thread started");
    }

    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[256];
            DatagramPacket receivedPacket = new DatagramPacket(data, data.length);
            try {
                msocket.receive(receivedPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String latestMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            textArea.append(latestMessage + "\n");
        }
    }
}

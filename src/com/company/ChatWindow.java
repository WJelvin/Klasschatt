package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class ChatWindow extends JFrame {

    private int port = 12540;
    private MulticastSocket msocket = new MulticastSocket(port);
    private InetAddress ip = InetAddress.getByName("234.235.236.237");

    //Komponenter
    JButton disconnectButton = new JButton("Disconnect");
    JTextArea chatTextArea = new JTextArea();
    JTextField inputTextField = new JTextField();
    JPanel panel = new JPanel();

    //Sender & receiver
    ChatSender sender = new ChatSender(msocket, ip, port);
    ChatReceiver receiver = new ChatReceiver(msocket, ip, port, chatTextArea);

    //Konstruktor

    public ChatWindow() throws IOException {

        msocket.joinGroup(ip);

        //Grundinställningar
        setSize(400, 400);
        setLocation(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Lägg till komponenter
        panel.setLayout(new BorderLayout());
        panel.add(disconnectButton, BorderLayout.PAGE_START);
        panel.add(chatTextArea, BorderLayout.CENTER);
        panel.add(inputTextField, BorderLayout.PAGE_END);

        //Lägg till eventlisteners
        disconnectButton.addActionListener(this::actionPerformed);
        inputTextField.addActionListener(this::actionPerformed);


        add(panel);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == disconnectButton) {
            try {
                sender.disconnectFromChat();
                System.out.println("Disconnected.");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == inputTextField) {
            String message = "William: " + inputTextField.getText();
            try {
                sender.sendMessage(message);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            inputTextField.setText("");
        }
    }


}

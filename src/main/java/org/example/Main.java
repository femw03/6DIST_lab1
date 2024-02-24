package org.example;

import TCP.TCPclient;
import TCP.TCPserver;
import UDP.UDPclient;
import UDP.UDPserver;

public class Main {
    public static void main(String[] args) {
        // Start TCP server in a separate thread
        Thread tcpServerThread = new Thread(() -> {
            try {
                TCPserver.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tcpServerThread.start();

        // Start UDP server in a separate thread
        Thread udpServerThread = new Thread(() -> {
            try {
                UDPserver.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        udpServerThread.start();

        // Sleep for a short time to allow the servers to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Start multiple TCP clients in separate threads
        for (int i = 0; i < 3; i++) {
            final int clientId = i;
            Thread tcpClientThread = new Thread(() -> {
                try {
                    TCPclient.main(new String[]{Integer.toString(clientId)});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            tcpClientThread.start();
        }

        // Start multiple UDP clients in separate threads
        for (int i = 0; i < 3; i++) {
            final int clientId = i;
            Thread udpClientThread = new Thread(() -> {
                try {
                    UDPclient.main(new String[]{Integer.toString(clientId)});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            udpClientThread.start();
        }
    }
}
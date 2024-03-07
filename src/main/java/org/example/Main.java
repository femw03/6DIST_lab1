package org.example;

import java.util.*;
import TCP.TCPclient;
import TCP.TCPserver;
import UDP.UDPclient;
import UDP.UDPserver;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");

        System.out.println("TCP(1) or UDP(2)?   ");
        Scanner scanner1 = new Scanner(System.in);
        int protocol = Integer.parseInt(scanner1.nextLine());

        System.out.println("Server(1) or client(2)?   ");
        Scanner scanner2 = new Scanner(System.in);
        int mode = Integer.parseInt(scanner2.nextLine());

        System.out.println("IP address of server:   ");
        Scanner scanner3 = new Scanner(System.in);
        String ip = scanner3.nextLine();

        System.out.println("Port number of server:   ");
        Scanner scanner4 = new Scanner(System.in);
        String port = scanner4.nextLine();

        if (protocol == 1) {
            if (mode == 1) {
                // Start TCP server in a separate thread
                Thread tcpServerThread = new Thread(() -> {
                    try {
                        TCPserver.main(new String[] {ip,port});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                tcpServerThread.start();

                // Sleep for a short time to allow the servers to start
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else if (mode == 2) {
                // Start multiple TCP clients in separate threads
                for (int i = 0; i < 2; i++) {
                    final int clientId = i;
                    Thread tcpClientThread = new Thread(() -> {
                        try {
                            TCPclient.main(new String[]{Integer.toString(clientId),ip,port});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    tcpClientThread.start();
                }
            }

        } else if (protocol == 2) {
            if (mode == 1) {
                // Start UDP server in a separate thread
                Thread udpServerThread = new Thread(() -> {
                    try {
                        UDPserver.main(new String[] {ip,port});
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

            } else if (mode == 2) {
                // Start multiple UDP clients in separate threads
                for (int i = 0; i < 2; i++) {
                    final int clientId = i;
                    Thread udpClientThread = new Thread(() -> {
                        try {
                            UDPclient.main(new String[]{Integer.toString(clientId),ip,port});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    udpClientThread.start();
                }
            }
        }
    }
}
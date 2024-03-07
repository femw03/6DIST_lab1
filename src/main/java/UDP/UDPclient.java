package UDP;

import java.io.*;
import java.net.*;

public class UDPclient {
    public static void main(String[] args) {
        int clientId = Integer.parseInt(args[0]);
        String ip = args[1];
        int port = Integer.parseInt(args[2]);
        DatagramSocket clientSocket = null;


        try {
            clientSocket = new DatagramSocket();

            // Send a file request to the server
            String fileName = "example.txt";
            byte[] sendData = fileName.getBytes();
            InetAddress serverAddress = InetAddress.getByName(ip);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
            clientSocket.send(sendPacket);
            System.out.println("Client "+clientId+" sends request to the server");

            // Receive the file content from the server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);
            System.out.println("Client "+clientId+" received packet");
            String receivedLine = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("UDPclient: " + receivedLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
    }
}
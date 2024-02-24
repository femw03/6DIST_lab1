package UDP;

import java.io.*;
import java.net.*;

public class UDPclient {
    public static void main(String[] args) {
        DatagramSocket clientSocket = null;

        try {
            clientSocket = new DatagramSocket();

            // Send a file request to the server
            String fileName = "example.txt";
            byte[] sendData = fileName.getBytes();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);
            clientSocket.send(sendPacket);

            // Receive the file content from the server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            while (true) {
                clientSocket.receive(receivePacket);

                String receivedLine = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (receivedLine.equals("EOF")) {
                    break; // End of file
                }

                System.out.println("UDPclient: " + receivedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
    }
}
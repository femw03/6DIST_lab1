package UDP;

import java.io.*;
import java.net.*;

public class UDPserver {
    public static void main(String[] args) {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("UDP server started on port " + port);

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                System.out.println("New client request incoming");

                String fileName = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Process the file request and send the file content
                sendFileContent(fileName, receivePacket.getAddress(), receivePacket.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }

    private static void sendFileContent(String fileName, InetAddress clientAddress, int clientPort) {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                System.err.println("File not found: (udp) " + fileName);
                return;
            }

            // Open the file
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            String line;
            DatagramSocket socket = new DatagramSocket();

            // Read and send the file content line by line
            while ((line = fileReader.readLine()) != null) {
                byte[] sendData = line.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
            System.out.println("Requested file sent to client");


            // Close the file reader and socket
            fileReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
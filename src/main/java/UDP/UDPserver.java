package UDP;

import java.io.*;
import java.net.*;

public class UDPserver {
    public static void main(String[] args) {
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(9876);

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

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

            // Build the relative file path
            String relativeFilePath = "6DIST_lab1/src/main/java/example.txt";
            String absoluteFilePath = new File(relativeFilePath).getAbsolutePath();

            File file = new File(absoluteFilePath);

            if (!file.exists()) {
                System.err.println("File not found: (udp) " + absoluteFilePath);
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

            // Close the file reader and socket
            fileReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
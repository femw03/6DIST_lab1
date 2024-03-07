package TCP;

import java.io.*;
import java.net.*;

public class TCPserver {
    public static void main(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("TCP server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress());

            // Create a new thread to handle the client
            Thread clientHandlerThread = new Thread(() -> handleClient(clientSocket));
            clientHandlerThread.start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            // Create input and output streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read file request from the client
            String fileName = in.readLine();

            // Process the file request and send the file content
            sendFileContent(fileName, out);
            System.out.println("Requested file sent to client");

            // Close the connection
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFileContent(String fileName, PrintWriter out) {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                System.err.println("File not found: " + fileName);
                out.println("File not found: " + fileName);
                return;
            }

            // Open the file
            BufferedReader fileReader = new BufferedReader(new FileReader(file));

            // Read and send the file content line by line
            String line;
            while ((line = fileReader.readLine()) != null) {
                out.println(line);
            }

            // Close the file reader and output stream
            fileReader.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
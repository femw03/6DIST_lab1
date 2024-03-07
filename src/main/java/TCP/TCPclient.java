package TCP;

import java.io.*;
import java.net.*;

public class TCPclient {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: java TCP.TCPclient <clientId>, should have 3!!!");
            System.exit(1);
        }

        int clientId = Integer.parseInt(args[0]);
        String ip = args[1];
        int port = Integer.parseInt(args[2]);

        // Connect to the server
        Socket socket = new Socket(ip, port);
        System.out.println("Client "+clientId+" connected to the server");

        // Create input and output streams for communication
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        FileOutputStream fileOutputStream = new FileOutputStream("received_file_client_"+clientId+".txt");

        // Send a file request to the server
        // Get the current working directory
        String baseDirectory = System.getProperty("user.dir");

        // Specify the file name with respect to the base directory
        String relativePath = "example.txt";
        System.out.println("Requested file: "+relativePath);

        // Concatenate the base directory and relative path to get the complete relative part
        String filename = baseDirectory + "/" + relativePath;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(filename);

        // Receive and print the file content
        receiveAndPrintFileContent(in);

        // Close the connection
        socket.close();
    }

    private static void receiveAndPrintFileContent(BufferedReader in) {
        try {
            // Print the received file content
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("TCPclient: " + line);
            }

            // Close the input stream
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
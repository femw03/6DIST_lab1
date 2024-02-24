package TCP;

import java.io.*;
import java.net.*;

public class TCPclient {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java TCP.TCPclient <clientId>");
            System.exit(1);
        }

        int clientId = Integer.parseInt(args[0]);

        // Connect to the server
        Socket socket = new Socket("localhost", 12345);

        // Create input and output streams for communication
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Build the relative file path
        String relativeFilePath = "src/main/java/example.txt";
        String absoluteFilePath = new File(relativeFilePath).getAbsolutePath();

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(absoluteFilePath);

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
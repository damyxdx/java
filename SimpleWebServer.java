import java.io.*;
import java.net.*;
import java.nio.file.*;

public class SimpleWebServer {

    // Define the port number where the server will listen
    private static final int PORT = 8080;
    // Define the directory where HTML files are stored
    private static final String WEB_ROOT = "./webroot";
    // Define the default file to be served
    private static final String DEFAULT_FILE = "index.html";
    // Define the 404 file
    private static final String FILE_NOT_FOUND = "404.html";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                try {
                    // Accept incoming client connections
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    // Handle the client request in a new thread
                    new Thread(() -> handleClientRequest(clientSocket)).start();

                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server could not start: " + e.getMessage());
        }
    }

    // Method to handle client requests
    private static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            // Read the request line (e.g., "GET /index.html HTTP/1.1")
            String requestLine = in.readLine();
            if (requestLine == null) return;

            System.out.println("Request: " + requestLine);

            // Parse the requested file from the request line
            String[] tokens = requestLine.split(" ");
            String requestedFile = tokens[1].equals("/") ? DEFAULT_FILE : tokens[1].substring(1);

            // Build the path to the requested file
            Path filePath = Paths.get(WEB_ROOT, requestedFile);

            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                // Serve the file
                byte[] fileBytes = Files.readAllBytes(filePath);
                String contentType = Files.probeContentType(filePath);

                // Send HTTP response headers
                out.write(("HTTP/1.1 200 OK\r\n").getBytes());
                out.write(("Content-Type: " + contentType + "\r\n").getBytes());
                out.write("\r\n".getBytes());

                // Send the file content
                out.write(fileBytes);
            } else {
                // Serve the 404 file
                File notFoundFile = new File(WEB_ROOT, FILE_NOT_FOUND);
                byte[] fileBytes = Files.readAllBytes(notFoundFile.toPath());
                String contentType = Files.probeContentType(notFoundFile.toPath());

                // Send HTTP response headers
                out.write(("HTTP/1.1 404 Not Found\r\n").getBytes());
                out.write(("Content-Type: " + contentType + "\r\n").getBytes());
                out.write("\r\n".getBytes());

                // Send the 404 content
                out.write(fileBytes);
            }

            out.flush();
        } catch (IOException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}

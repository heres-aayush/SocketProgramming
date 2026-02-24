package TCP_Chat;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started on port 5000");
            System.out.println("Waiting for clients...");

            // Server broadcast thread
            Thread serverWrite = new Thread(() -> {
                try {
                    BufferedReader serverInput =
                            new BufferedReader(new InputStreamReader(System.in));

                    String msg;
                    while (true) {
                        msg = serverInput.readLine();
                        broadcast("Server: " + msg);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            });

            serverWrite.start();

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected from Port: " + socket.getPort());

                ClientHandler ch = new ClientHandler(socket);
                clients.add(ch);
                ch.start();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Server → all clients
    public static void broadcast(String msg) {
        for (ClientHandler ch : clients) {
            ch.sendMessage(msg);
        }
    }
}


/* ================= CLIENT HANDLER ================= */

class ClientHandler extends Thread {

    Socket socket;
    BufferedReader input;
    PrintWriter output;
    int clientPort;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.clientPort = socket.getPort();

        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            output = new PrintWriter(
                    socket.getOutputStream(), true);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessage(String msg) {
        output.println(msg);
    }

    public void run() {

        try {
            String msg;

            while (true) {

                msg = input.readLine();

                if (msg == null || msg.equalsIgnoreCase("exit")) {
                    System.out.println("Client " + clientPort + " disconnected");
                    socket.close();
                    ChatServer.clients.remove(this);
                    break;
                }

                // SHOW ONLY ON SERVER
                System.out.println("Client[" + clientPort + "]: " + msg);

                // IMPORTANT: NO BROADCAST HERE
            }

        } catch (Exception e) {
            System.out.println("Client " + clientPort + " left unexpectedly");
            ChatServer.clients.remove(this);
        }
    }
}
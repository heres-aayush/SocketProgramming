package TCP_Chat;
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server");

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(
                    new InputStreamReader(System.in));

            // Receive thread
            Thread readThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = input.readLine()) != null) {
                        System.out.println("Server: " + msg);
                    }
                } catch (Exception e) {
                    System.out.println("Disconnected from server");
                }
            });

            // Send thread
            Thread writeThread = new Thread(() -> {
                try {
                    String msg;
                    while (true) {
                        msg = userInput.readLine();
                        output.println(msg);

                        if (msg.equalsIgnoreCase("exit")) {
                            socket.close();
                            System.out.println("You left the chat");
                            System.exit(0);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            });

            readThread.start();
            writeThread.start();

        } catch (Exception e) {
            System.out.println("Server not running");
        }
    }
}
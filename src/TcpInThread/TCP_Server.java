package TcpInThread ;
import java.io.*;
import java.net.*;
import java.util.Date;

// Writing thread (sends time)
class WriteThread extends Thread {
    Socket socket;

    WriteThread(Socket s) {
        socket = s;
    }

    public void run() {
        try {
            DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());

            while(true) {
                String time = new Date().toString();
                out.writeUTF("Server Time: " + time);
                sleep(5000);   // send every 5 seconds
            }
        }
        catch(Exception e) {
            System.out.println("Write Thread Closed");
        }
    }
}

// Reading thread
class ReadThread extends Thread {
    Socket socket;

    ReadThread(Socket s) {
        socket = s;
    }

    public void run() {
        try {
            DataInputStream in =
                    new DataInputStream(socket.getInputStream());

            while(true) {
                String msg = in.readUTF();
                System.out.println("Client says: " + msg);
            }
        }
        catch(Exception e) {
            System.out.println("Read Thread Closed");
        }
    }
}

public class TCP_Server {

    public static void main(String args[]) throws Exception {
        ServerSocket server = new ServerSocket(6790);
        System.out.println("Server Started...");

        Socket client = server.accept();

        System.out.println("Client connected from port: " + client.getPort());

        // two separate threads
        ReadThread r = new ReadThread(client);
        WriteThread w = new WriteThread(client);

        r.start();
        w.start();
    }
}
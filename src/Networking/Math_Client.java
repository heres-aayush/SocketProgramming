package Networking;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Math_Client {
    public static void main(String[] args) {
        try {
            Socket clientSck = new Socket("localhost", 6000);
            System.out.println("Server Connected at: " + clientSck.getRemoteSocketAddress());

            DataInputStream in = new DataInputStream(clientSck.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSck.getOutputStream());

            Scanner sc = new Scanner(System.in);

            while (true) {
                String menu = in.readUTF();
                System.out.println("Server:\n" + menu);

                int choice = sc.nextInt();
                out.writeInt(choice);
                out.flush();

                if (choice == 4) {
                    System.out.println("Disconnecting...");
                    break;
                }

                String msg = in.readUTF();
                System.out.println("Server: " + msg);

                int a = sc.nextInt();
                int b = sc.nextInt();

                out.writeInt(a);
                out.writeInt(b);
                out.flush();

                String result = in.readUTF();
                System.out.println("Output from server: " + result);
            }

            sc.close();
            clientSck.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package UDP;

import java.net.*;
import java.io.*;

public class UDP_Server {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(6778);

        byte[] receive = new byte[1024];
        byte[] send;

        System.out.println("UDP Math Server running...");

        while(true)
        {
            DatagramPacket request = new DatagramPacket(receive, receive.length);
            socket.receive(request);

            InetAddress clientAddr = request.getAddress();
            int clientPort = request.getPort();

            String menu = "Select 1 to add, 2 to subtract, 3 to multiply, 4 to exit";

            send = menu.getBytes();
            DatagramPacket reply = new DatagramPacket(send, send.length, clientAddr, clientPort);
            socket.send(reply);

            DatagramPacket choicePacket = new DatagramPacket(receive, receive.length);
            socket.receive(choicePacket);

            String choiceStr = new String(choicePacket.getData(),0,choicePacket.getLength());
            int choice = Integer.parseInt(choiceStr);

            if(choice == 4)
            {
                System.out.println("Client exited");
                continue;
            }

            String msg = "Enter operands";

            send = msg.getBytes();
            reply = new DatagramPacket(send, send.length, clientAddr, clientPort);
            socket.send(reply);

            DatagramPacket operandPacket = new DatagramPacket(receive, receive.length);
            socket.receive(operandPacket);

            String operands = new String(operandPacket.getData(),0,operandPacket.getLength());
            String parts[] = operands.split(" ");

            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);

            int result = 0;

            if(choice == 1) result = a + b;
            if(choice == 2) result = a - b;
            if(choice == 3) result = a * b;

            String res = "Result = " + result;

            send = res.getBytes();
            reply = new DatagramPacket(send, send.length, clientAddr, clientPort);
            socket.send(reply);
        }
    }
}
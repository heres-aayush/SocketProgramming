package UDP;

import java.net.*;
import java.util.Scanner;

public class UDP_Client {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");

        Scanner sc = new Scanner(System.in);

        byte[] send;
        byte[] receive = new byte[1024];

        while(true)
        {
            send = "start".getBytes();

            DatagramPacket request = new DatagramPacket(send, send.length, address, 6778);
            socket.send(request);

            DatagramPacket reply = new DatagramPacket(receive, receive.length);
            socket.receive(reply);

            String menu = new String(reply.getData(),0,reply.getLength());
            System.out.println(menu);

            int choice = sc.nextInt();

            send = String.valueOf(choice).getBytes();
            request = new DatagramPacket(send, send.length, address, 6778);
            socket.send(request);

            if(choice > 4)
            {
                System.out.println("invalid") ;
                continue ;
            }
            if(choice == 4)
            {
                System.out.println("Exiting client...");
                break;
            }

            socket.receive(reply);
            String msg = new String(reply.getData(),0,reply.getLength());
            System.out.println(msg);

            int a = sc.nextInt();
            int b = sc.nextInt();

            String operands = a + " " + b;

            send = operands.getBytes();
            request = new DatagramPacket(send, send.length, address, 6778);
            socket.send(request);

            socket.receive(reply);

            String result = new String(reply.getData(),0,reply.getLength());
            System.out.println(result);
        }

        socket.close();
        sc.close();
    }
}
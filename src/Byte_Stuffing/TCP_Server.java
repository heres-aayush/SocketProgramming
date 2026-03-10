package Byte_Stuffing;

import java.util.* ;
import java.io.* ;
import java.net.* ;

public class TCP_Server {
    public static void main(String[] args) throws IOException {
        ServerSocket sck = new ServerSocket(6778) ;
        System.out.println("Looking for clients : ") ;

        while(true)
        {
            Socket skt = sck.accept() ;
            System.out.println("Client is connected at : " +  skt.getRemoteSocketAddress() );
            DataInputStream in = new DataInputStream(skt.getInputStream()) ;
            DataOutputStream out = new DataOutputStream(skt.getOutputStream());

            String pull = in.readUTF() ;
            System.out.print("The destuffed message is : ");
            System.out.println(pull) ;
            String push = "F" ;

            for(int i = 0 ; i < pull.length() ; i++)
            {
                if( pull.charAt(i) == 'F' || pull.charAt(i) == 'E') {
                    push = push + 'E' ;
                }
                push = push + pull.charAt(i) ;
            }
            push = push + "F";
            System.out.println("Sending the stuffed message back : " + push);
            out.writeUTF(push);
            skt.close();
        }
    }
}
package Byte_Stuffing;

import java.io.* ;
import java.net.* ;
import java.util.Scanner ;

public class TCP_Client {
    public static void main(String[] args) throws IOException {
        Socket sck = new Socket("localhost", 6778);
        System.out.println("Server is connected at : " + sck.getRemoteSocketAddress());

        DataInputStream in = new DataInputStream(sck.getInputStream());
        DataOutputStream out = new DataOutputStream(sck.getOutputStream());
        System.out.print("Enter dataframe ('D' for data, 'E' for Esc , 'F' for flag : " );

        Scanner sc = new Scanner(System.in);
        String send = sc.next(); // for a single word can have nextLine but need to have String[] on server side

        System.out.println("Sending destuffed message " + send);

        out.writeUTF(send);

        String stuff = in.readUTF();
        System.out.println("The stuffed message is :  " + stuff) ;

        sck.close() ;
    }
}

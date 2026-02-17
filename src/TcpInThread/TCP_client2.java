package TcpInThread;

import java.io.*  ;
import java.util.*  ;
import java.net.* ;

public class TCP_client2 {
    public static void main(String[] args) throws IOException {
        try
        {
            Socket sck  = new Socket("localhost" , 6000 ) ;
            System.out.println("The system is connected at " + sck.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(sck.getInputStream()) ;
            DataOutputStream out = new DataOutputStream((sck.getOutputStream())) ;

            String msg = in.readUTF() ;
            System.out.println("Server: " + msg );
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

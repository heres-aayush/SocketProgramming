//accepting new client connections in a new thread +
// printing port number of client +
//sending system time to client

package TcpInThread;

import java.io.* ;
import java.net.*  ;
import java.util.Date;

class connectionThread extends Thread {
    private Socket sock = new Socket() ;

    public connectionThread(Socket sck){
        this.sock = sck ;
    }

    public void run (){
        try{
            DataInputStream in  = new DataInputStream(sock.getInputStream());
            DataOutputStream out = new DataOutputStream (sock.getOutputStream()) ;
            //printing clients socket address
            System.out.println("Connected to client at " + sock.getRemoteSocketAddress());
            // sending the local server date and tim e
            Date date = new Date() ;
            out.writeUTF("Date and time : " + date );
            out.flush() ;

        }
        catch (Exception e){
            System.out.println(e.getMessage() );
        }

    }
}


public class TCP_Server2 {
    public static void main(String[] args) throws IOException {
        //create a socket
        ServerSocket skt = new ServerSocket(6000) ;
        while(true){
            try{
                // create the socket
                Socket sck = skt.accept()  ;
                // do the rest of the work in thread ;
                connectionThread socket1 = new connectionThread(sck);
                socket1.start() ;
            } catch (Exception e) {
                System.out.println(e.getMessage() );
            }
        }
    }
}

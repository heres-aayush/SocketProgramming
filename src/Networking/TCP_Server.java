package Networking;
import java.io.* ;
import java.net.* ;

class GreetingServer extends Thread{
    private ServerSocket serverSocket ;

    public GreetingServer (int port) throws IOException {
        //setting the Server socket port
        serverSocket = new ServerSocket(port) ;
        serverSocket.setSoTimeout(10000) ;
    }

    public void run() {
        while(true)
        {
            try{
                System.out.println("Waiting for Client on port" + serverSocket.getLocalPort());
                // creating a Socket object which waits for client to connect
                Socket server = serverSocket.accept() ;
                System.out.println("Client connected to " + server.getRemoteSocketAddress());

                DataInputStream in = new DataInputStream(server.getInputStream()) ;
                System.out.println(in.readUTF());

                DataOutputStream out = new DataOutputStream(server.getOutputStream()) ;
                out.writeUTF("Hi this is the server responding from " + serverSocket.getLocalSocketAddress() + "Thankyou") ;

                server.close() ;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
public class TCP_Server {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]) ;
        try {
            Thread t = new GreetingServer(port) ;
            t.start() ;
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}

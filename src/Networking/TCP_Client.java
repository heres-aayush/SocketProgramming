package Networking;
import java.io.* ; // BufferedReader / writer and stuffs
import java.net.* ; // networking components (Socket, ServerSocket, url , InetAddress )

public class TCP_Client {
    public static void main(String[] args) {
        //taking server name and ports from cmd
        String Servername = args[0];
        int ServerPort = Integer.parseInt(args[1]) ;
        try {
            System.out.println("Connecting to " + Servername + " on port : " + ServerPort);
            Socket clientSocket = new Socket (Servername , ServerPort);
            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress() );
            OutputStream outToServer = clientSocket.getOutputStream() ;
            DataOutputStream out = new DataOutputStream(outToServer) ;
            out.writeUTF("Hello from " + clientSocket.getLocalSocketAddress()) ;
            InputStream inFromServer = clientSocket.getInputStream() ;
            DataInputStream in = new DataInputStream(inFromServer) ;

            System.out.println("server says : " + in.readUTF() );
            clientSocket.close() ;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package Networking;
import com.sun.security.ntlm.Client;
import com.sun.xml.internal.ws.addressing.WsaActionUtil;

import java.io.* ;
import java.net.* ;

//make a class to make a server socket object and connect to client and accept response
// make a function which operates and responds back , then according to response , reply back to the client

class serverSocket extends Thread {
    private ServerSocket skt ;
    serverSocket (int port) throws IOException {
        skt = new ServerSocket(port) ;
    }
    //function to accept client connection / responses and respond back
    public void run() {
        while(true)
        {
            try {
                System.out.println("Waiting for Client on port number : " + skt.getLocalPort());
                Socket socket = skt.accept() ;
                System.out.println("Client connected to : " + socket.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream((socket.getOutputStream())) ;

                boolean running = true ;
                while(running) {
                    out.writeUTF ("Menu : \n 1. Add, 2. Subtract, 3. Multiply, 4.Exit ") ;
                    out.flush() ;

                    int choice = in.readInt() ;
                    if(choice == 4) {
                        running = false ;
                        System.out.println("Client Disconnecting ... ");
                        break ;
                    }

                    out.writeUTF("Enter operands");
                    out.flush() ;
                    int a = in.readInt() ; // readInt() function
                    int b = in.readInt() ;


                    int result = 0 ;

                    switch(choice)
                    {
                        case 1 :
                            result = a+b ;
                            break ;
                        case 2 :
                            result = a-b ;
                            break ;
                        case 3 :
                            result = a*b ;
                            break ;
                        default :
                            out.writeUTF("invalid input.. try again! ");
                            out.flush() ;
                            continue ;
                    }
                    out.writeUTF("Result : " + result) ;
                    out.flush() ;
                }
                socket.close() ;
            }
            catch (Exception e){
                System.out.println(e.getMessage()) ;
            }
        }
    }
}
public class Math_Server {
    public static void main(String[] args) {
        try {
            serverSocket skt = new serverSocket(6000) ;
            skt.start();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


    }
}

package TcpInThread ;
import java.io.*;
import java.net.*;

public class TCP__Client {

    public static void main(String args[]) throws Exception {

        Socket socket = new Socket("localhost", 6790);
        System.out.println("Connected to server");

        DataInputStream in =
                new DataInputStream(socket.getInputStream());

        DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            // read time from server
            if(in.available() > 0) {
                String msg = in.readUTF();
                System.out.println(msg);
            }

            // send message to server
            if(br.ready()) {
                String send = br.readLine();
                out.writeUTF(send);
            }
        }
    }
}
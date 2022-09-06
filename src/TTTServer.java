import java.io.*;
import java.net.*;

public class TTTServer {
    public Socket socket;

    public TTTServer(int portNumber){
        try{
            InetAddress inetAddress = InetAddress.getLocalHost();
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("[Server] Opened at " + inetAddress.getHostAddress());
            System.out.println("[Server] Waiting for Client connection...");
            socket = serverSocket.accept();
            System.out.println("[Server] Connection established with Client.");
        }
        
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

        // finally{
        //     socket.close();
        // }
        
    }
}

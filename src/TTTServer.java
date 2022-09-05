import java.io.*;
import java.net.*;

public class TTTServer extends P2P{
    public TTTServer(int portNumber){
        super();
        try{
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("[Server] Waiting for Client connection...");
            Socket socket = serverSocket.accept();
            System.out.println("[Server] Connection established with Client.");
            createStreams(socket);
        }
        catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public void printReceive() throws IOException {
        System.out.println("Client: " + receive()); 
    }
}

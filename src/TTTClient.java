import java.io.*;
import java.net.*;
// can create the sockets in p2p class then createStreams in the constructor
public class TTTClient extends P2P{
    public TTTClient(String hostName, int portNumber) throws IOException, InterruptedException {
        super();
        try{
            Thread.sleep(1000);
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("[Client] Connected to Server at " + hostName + ".");
            createStreams(socket);
        }
        catch (UnknownHostException e){
            System.err.println("Could not resolve hostname: " + hostName);
            System.exit(1);
        }
        catch (IOException e){
            System.err.println("Could not get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    public void printReceive() throws IOException {
        System.out.println("Host: " + receive()); 
    }
}

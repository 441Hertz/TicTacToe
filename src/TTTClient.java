import java.io.*;
import java.net.*;
public class TTTClient{
    public Socket socket;

    public TTTClient(String hostName, int portNumber) throws IOException, InterruptedException {
        try{
            Thread.sleep(500);
            socket = new Socket(hostName, portNumber);
            System.out.println("[Client] Connected to server at " + hostName + ".");
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
}

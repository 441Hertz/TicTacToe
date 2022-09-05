import java.io.*;
import java.net.*;

public class P2P {
    private PrintWriter sender; 
    private BufferedReader receiver;
    private BufferedReader stdIn;
    public P2P(){

    }
    public void createStreams(Socket socket) throws IOException {
        this.sender = new PrintWriter(socket.getOutputStream(), true);
        this.receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    
    }

    public void send(String msg){
        this.sender.println(msg);
    }

    public void send(int[][] board){
        this.sender.println(board);
    }

    public void send(int msg){
        this.sender.println(msg);
    }

    public String receive() throws IOException {
        return this.receiver.readLine();
    }

    public int receiveMove() throws IOException {
        return Integer.parseInt(this.receiver.readLine());
    }

    // Maybe move stdIn to Main class since it is not socket related?
    public void printInput() throws IOException {
        System.out.println(this.stdIn.readLine());
    }

    public String readInput() throws IOException {
        return this.stdIn.readLine();
    }

    public int readInputMove() throws IOException {
        return Integer.parseInt(this.stdIn.readLine());
    }

    public void sendInput() throws IOException {
        send(this.stdIn.readLine());
    }
}

import java.io.IOException;
import java.util.Scanner;
// TODO rename server
public class Main {
    public static TTTServer server = null;
    public static TTTClient client = null;
    public static Game game;
    public static String name;
    public static void main(String[] args) throws IOException, InterruptedException{
        init(args);
    }

    public static void init(String[] args) throws IOException, InterruptedException {
        if (args.length == 1){
            host(Integer.parseInt(args[0]));  
        }
        else if (args.length == 2){
            connect(args[0], Integer.parseInt(args[1]));
        }
        else{
            Scanner scanner = null;
            try{
                scanner = new Scanner(System.in);
                while (true){
                    System.out.print("Enter 'h' to host or 'c' to connect: ");
                    String response = scanner.next();
                    if (response.toLowerCase().equals("h") || response.toLowerCase().equals("host")){
                        System.out.print("Enter port number: ");
                        int portNumber = scanner.nextInt();
                        host(portNumber);
                        break;
                    }
                    else if (response.toLowerCase().equals("c") || response.toLowerCase().equals("connect")){
                        System.out.print("Enter host name: ");
                        String hostName = scanner.next();
                        System.out.print("Enter port number: ");
                        int portNumber = scanner.nextInt();
                        connect(hostName, portNumber);
                        break;
                    }
                    else{
                        System.out.println("Invalid response.");
                    }
                }
            }
            finally{
                if (scanner != null){
                    scanner.close();
                }
            }
            playGame();
        }
    }

    // game code repeats. same method to handle? could have a variable hold the server or client. 
    // could have print statements that print depending on client or server (via fields or argument)
    // or have wrapper method
    public static void host(int portNumber) throws IOException {
        server = new TTTServer(portNumber);
        
        name = "[Host]";
        game = new Game('H');

        playGame();
    }

    public static void connect(String hostName, int portNumber) throws IOException, InterruptedException {
        client = new TTTClient(hostName, portNumber);

        name = "[Client]";
        game = new Game('C');

        playGame();
    }

    public static void pickMarker(){
        // bufferedreader to get input
    }
    
    public static void playGame() throws IOException{
        int input;
        int response;
        game.rules();
        game.printBoard();

        System.out.format("You are %s.%n", name);

        if (isHost()){
            System.out.format("Your move: ");
            input = server.readInputMove();
            game.playYourTurn(input);
            game.printBoard();
            server.send(input);
        }
        System.out.format("Waiting for %s to move...%n", name);

        while (!game.isOver()){
            if (isHost()){
                response = server.receiveMove();
            }
            else{
                response = client.receiveMove();
            }

            game.playTheirTurn(response);
            game.printBoard();

            if (game.isWon()){
                System.out.println("you lost");
                break;
            }
            else if (game.isDraw()){
                break;
            }

            System.out.print("Your move: ");
            if (isHost()){
                input = server.readInputMove();
                server.send(input);
            }
            else{
                input = client.readInputMove();
                client.send(input);
            }

            game.playYourTurn(input);
            game.printBoard();

            if (game.isWon()){
                System.out.println("you won");
                break;
            }
            else if (game.isDraw()){

            }

            System.out.format("Waiting for %s to move...%n", name);
        }
    }


    public static void quit(){
        // TODO 
        // Go down a level of prompts (changing from connecting to host)
        // Stay in loop forever despite exceptions
    }

    public static boolean isHost(){
        return server != null;
    }
}

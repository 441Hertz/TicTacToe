import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static P2P p2p = null;
    public static Game game;
    public static String[] name;
    public static final String divider = "=========================================";

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

    public static void host(int portNumber) throws IOException {
        game = new Game('H');
        name = new String[] {"[Host]", "[Client]"};
        
        try{
            p2p = new P2P(portNumber);
            pickMarker();
            playGame();
        } finally {
            p2p.closeStreams();
        }

    }

    public static void connect(String hostName, int portNumber) throws IOException, InterruptedException {
        game = new Game('C');
        name = new String[] {"[Client]", "[Host]"};
        
        try{
            p2p = new P2P(hostName, portNumber);
            pickMarker();
            playGame();
        } finally {
            p2p.closeStreams();
        }
}

    public static void playGame() throws IOException{
        String input = null;
        int response;

        System.out.format("You are %s.%n", name[0]);
        System.out.println(divider);

        game.printBoard();

        if (isHost()){
            System.out.println(divider);
            System.out.print("Your move: ");
            do{
                input = p2p.readInput();
            } while(!game.isValidMove(input));
            
            game.playYourTurn(input);
            game.printBoard();
            p2p.send(input);
        }

        System.out.println(divider);
        System.out.format("Waiting for %s to move...%n", name[1]);

        while (!game.isOver()){
            response = p2p.receiveMove(); 

            game.playTheirTurn(response);
            game.printBoard();

            if (game.isWon()){
                System.out.println("How do you even lose in tic tac toe LOL.");
                System.out.println(divider);
                break;

            }
            else if (game.isDraw()){
                System.out.println("Stalemate.");
                System.out.println(divider);
                break;

            }

            System.out.println(divider);

            System.out.print("Your move: ");
            
            do{
                input = p2p.readInput();
            } while(!game.isValidMove(input));
            
            p2p.send(input);

            game.playYourTurn(input);
            game.printBoard();

            if (game.isWon()){
                System.out.println("Winner Winner Chicken Dinner!");
                System.out.println(divider);
                break;
            }
            else if (game.isDraw()){
                System.out.println("Stalemate.");
                System.out.println(divider);
                break;
            }
            
            System.out.println(divider);
            System.out.format("Waiting for %s to move...%n", name[0]);
        }
    }

    public static boolean isHost(){
        return name[0].equals("[Host]");
    }

    public static void rematch(){
        
    }

    public static void pickMarker() throws IOException{
        String input;
        String output; 
        System.out.println("Choose a char to be your marker (symbol).");

        if (isHost()){
            System.out.print("Your marker: ");
            input = p2p.readInput();

            while(!game.isChar(input)){
                System.out.print("Enter char marker: ");
                input = p2p.readInput();
            }

            game.setYourMarker(input);
            p2p.send(input);

            System.out.format("%s picking marker...%n", name[1]); // same
            output = p2p.receive();
            game.setTheirMarker(output);
            System.out.format("%1$s picked '%2$s'.%n", name[1], output); // same

        } else {
            System.out.format("%s picking marker...%n", name[1]); // same
            output = p2p.receive();
            game.setTheirMarker(output);
            System.out.format("%1$s picked '%2$s', choose your marker: ", name[1], output); // same

            input = p2p.readInput();
            
            while (true){
                if (!game.isChar(input)){
                    System.out.print("Enter char marker: ");
                    input = p2p.readInput();
                }
                else if (!game.isValidMarker(input)){
                    System.out.print("Enter unique marker: ");
                    input = p2p.readInput();
                }
                else{
                    break;
                }
            }

            game.setYourMarker(input);
            p2p.send(input);
        }
    }
    
    public static void quit(){
        // TODO
        // Go down a level of prompts (changing from connecting to host)
        // Stay in loop forever despite exceptions
    }
}

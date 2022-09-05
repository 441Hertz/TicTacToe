import java.util.Arrays;

public class Game {
    private static int id = 1;
    private static char marker = 'X';
    public static int[][] board;
    // should be final - client always is 1, server is 2
    // tests
    public static void main(String[] args) throws Exception {
        Game main = new Game();
        main.newBoard();
        main.printBoard();
        main.printBoard();
    }
    
    public Game(){
        this(marker);
    }
    
    public Game(char symbol){
        marker = symbol;
        this.newBoard();
    }
    
    public void newBoard(){
        board = new int[3][3];
    }

    public boolean isWon(){
        int rowSum = 0;
        int colSum = 0;
        int diag1Sum = 0;
        int diag2Sum = 0;


        for (int[] row : board){
            for (int value : row){
                rowSum += value;
            }
            if (rowSum == 3 || rowSum == -3){
                return true;
            }
            else{
                rowSum = 0;
            }
        }

        for (int y = 0; y < 3; y++){
            for (int[] row : board){
                colSum += row[y];
            }
            if (colSum == 3 || colSum == -3){
                return true;
            }
            else{
                colSum = 0;
            }
        }

        for (int i = 0; i < 3; i++){
            diag1Sum += board[i][i];
            diag2Sum += board[2 - i][i];
        }
        if (diag1Sum == 3 ||diag1Sum == -3){
            return true;
        }
        if (diag2Sum == 3 ||diag2Sum == -3){
            return true;
        }
        return false;
    }

    public boolean isDraw(){
        return !isWon() && isFull();
    }

    public boolean isFull(){
        for (int[] row : board){
            for (int value : row){
                if (value == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOver(){
        return isWon() || isFull();
    }

    public void playYourTurn(String pos){
        playYourTurn(Integer.parseInt(pos));
    }
    
    public void playYourTurn(int pos){
        if (board[Math.floorDiv(pos, 3)][pos % 3] == 0){
            board[Math.floorDiv(pos, 3)][pos % 3] = id;
        } 
    }

    public void playTheirTurn(String pos){
        playTheirTurn(Integer.parseInt(pos));
    }

    public void playTheirTurn(int pos){
        if (board[Math.floorDiv(pos, 3)][pos % 3] == 0){
            board[Math.floorDiv(pos, 3)][pos % 3] = -1;
        } 
    }

    public void printBoard(){
        printBoard(board);
    }

    public void printBoard(int[][] board){
        int count = 1;
        System.out.println("");
        for (int[] row : board){
            for (int value : row){
                if (value == 1){
                    System.out.print(marker);
                }
                else if (value == -1){
                    System.out.print("O");
                }
                else{
                    System.out.print("_");
                }
                if (count % 3 == 0){
                    System.out.println("");
                }
                count++;
            }
        }
        System.out.println("");
    }

    public void printRulesBoard(){
        int[][] rulesBoard = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int count = 1;
        System.out.println("");
        for (int[] row : rulesBoard){
            for (int value : row){
                System.out.print(value);
                if (count % 3 == 0){
                    System.out.println("");
                }
                count++;
            }
        }
        System.out.println("");
    }
    public void rules(){
        System.out.println("-----------");
        System.out.println("TIC TAC TOE");
        System.out.println("-----------");
        System.out.println("RULES");
        System.out.println("1. HOST MOVES FIRST");
        System.out.println("2. ENTER INT TO MOVE (SEE BOARD BELOW)");
        this.printRulesBoard();
    }
    
    public boolean isValidMove(String move){
        if (!isInteger(move)){
            System.out.print("Enter an integer: ");
            return false;
        }
        try{
            int pos = Integer.parseInt(move);
            if (board[Math.floorDiv(pos, 3)][pos % 3] == 0){
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e){
        } 
        System.out.print("Enter valid move: ");
        return false;

    }

    public static boolean isInteger(String numStr){
        if (numStr == null){
            return false;
        }
        try{
            int i = Integer.parseInt(numStr);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}

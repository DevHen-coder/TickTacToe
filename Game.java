import java.util.Scanner;

public class Game{
    private boolean isRunning;
    private Board board;

    private Scanner keys;

    private boolean isXTurn;
    private String playerSymbol;

    private int turnCounter = 0;

    public Game(){
        init();
    }
    
    private void init(){
        System.out.println("   ===| Tick Tac Toe |===");
        System.out.println("Select a square by entering the number position on the board");
        board = Board.getInstance();
        keys = new Scanner(System.in);

        toggleTurn();

        setIsRunning(true);
    }

    public void displayTurn(){
        if(isXTurn)
            System.out.println("X's turn ");
        else
            System.out.println("O's turn ");
    }

    public void getUserInput(){
        boolean valid = false;
        do{
            System.out.print("> ");
            try{
                int position = keys.nextInt();
                if(checkInput(position) && checkEmptySpace(position) ){
                    valid = true;
                    markBoard(position);

                }
                else{
                    valid = false;
                    System.out.println("INFO | Invalid input");
                }
                
            }
            catch(Exception ex){
                ex.printStackTrace();
                System.out.println("INFO | Invalid input");
                keys.next();
            }
        }while(!valid);
    }

    public void toggleTurn(){
        isXTurn = !isXTurn;
        if(isXTurn)
            playerSymbol = "X";
        else
            playerSymbol = "O";
    }

    public void markBoard(int pos){
        board.markBoard(pos, playerSymbol);
    }

    public boolean checkInput(int pos){
        return (pos >= 0 && pos < 9);
    }

    public boolean checkEmptySpace(int pos){
        if(board.getPositions()[pos].equals("X") || board.getPositions()[pos].equals("O") ){
            System.out.println("INFO | space " + pos + " is not empty");
            return false;
        }
        return true;
    }

    public void checkWinner(){
        int[][] lines = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
        };

        for( int i = 0; i < lines.length; i++ ){
            if( board.getPositions()[lines[i][0]] == playerSymbol &&
                board.getPositions()[lines[i][1]] == playerSymbol &&
                board.getPositions()[lines[i][2]] == playerSymbol
            ){
                System.out.println("\tWinner is " + playerSymbol );
                board.showBoard();
                isRunning = false;
            }
        }

        // draw
        if(turnCounter >= 7){
            System.out.println("\tDraw");
            isRunning = false;
        }
    }

    public void start(){
        displayTurn();
        board.showBoard();
        getUserInput();
        checkWinner();
        turnCounter++;
        toggleTurn();
    }

    public void cleanUp(){
        board = null;
        keys.close();
        //System.out.println("Clean up executed");
    }

    public boolean getIsRunning(){ return isRunning; }
    public void setIsRunning(boolean value){ isRunning = value; }

}

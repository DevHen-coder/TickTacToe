import java.util.Scanner;

/**
 * Main class to start tick-tack-toe game
 */
public class Game{
    private boolean isRunning;
    private Board board;

    private Scanner keys;

    private boolean isXTurn;
    private String playerSymbol;

    private int turnCounter;

    // use a method to initalize, not the constructor
    public Game(){ init(); }
    
    private void init(){
        // title
        System.out.println("   ===| Tick Tac Toe |===");
        // quick instructions message
        System.out.println("Select a square by entering the number position on the board");
        board = Board.getInstance();
        keys = new Scanner(System.in);

        // ensure X is first
        toggleTurn();

        setIsRunning(true);
    }

    private void displayTurn(){
        if(isXTurn)
            System.out.println("X's turn ");
        else
            System.out.println("O's turn ");
    }

    /*
     * Main user input handler.
     */
    private void getUserInput(){
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

    /*
     * Flip turns and change the playerSymbol to match
     */
    private void toggleTurn(){
        isXTurn = !isXTurn;
        if(isXTurn)
            playerSymbol = "X";
        else
            playerSymbol = "O";
    }

    private void markBoard(int pos){
        board.markBoard(pos, playerSymbol);
    }

    private boolean checkInput(int pos){
        return (pos >= 0 && pos < 9);
    }

    private boolean checkEmptySpace(int pos){
        if(board.getPositions()[pos].equals("X") || board.getPositions()[pos].equals("O") ){
            System.out.println("INFO | space " + pos + " is not empty");
            return false;
        }
        return true;
    }

    private void checkWinner(){
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
                setIsRunning(false);
            }
        }

        // after the 7th turn and no winner is assigned, its a DRAW
        if(turnCounter >= 7){
            System.out.println("\tDraw");
            setIsRunning(false);
        }
    }

    /*
     * main loop
     */
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

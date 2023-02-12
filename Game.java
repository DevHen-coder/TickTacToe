import java.util.Scanner;

/**
 * Main class to start tick-tack-toe game
 */
public class Game{
    private boolean isRunning;
    private Board board;
    private States gameState;

    private Scanner keys;

    private boolean isXTurn;
    private String playerSymbol;
    private String[] symbolOptions;

    private int turnCounter;

    // use a method to initalize, not the constructor
    public Game(){ init(); }
    
    private void init(){
        // title
        System.out.println("   ===| Tick Tac Toe |===");
        // quick instructions message
        board = Board.getInstance();
        keys = new Scanner(System.in);

        gameState = States.MAINMENU;
        symbolOptions = new String[]{"X", "O"};
        // ensure X is first
        toggleTurn();

        setIsRunning(true);
    }
    
    private void displayMainMenu(){
        System.out.println("| Main Menu |");
        System.out.println("1. Start Game");
        System.out.println("2. Rules");
        System.out.println("3. Change Symbols (" +
                symbolOptions[0] + " vs " + symbolOptions[1] + ")");
        System.out.println("0. Exit");
    }

    /*
     * main menu related methods
     */
    private void runMainMenu(){
        int mmSelection; 
        do{
            displayMainMenu();
            System.out.print("> ");
            mmSelection = keys.nextInt();
            switch(mmSelection){
                case 1:
                    board.buildBoard();
                    turnCounter = 0;
                    isXTurn = true;
                    System.out.println("\n| Game |");
                    setGameState(States.GAME);
                    break;
                case 2:
                    displayRules();
                    break;
                case 3:
                    setGameState(States.CHANGE);
                    break;
                case 0:
                    setGameState(States.EXIT);
                    setIsRunning(false);
                    break;
                default:
                    System.out.println("Invalid entry");
                    break;
            }
        }while(gameState == States.MAINMENU);
    }

    private void displayTurn(){
        //if(isXTurn) System.out.println("X's turn ");
        //else System.out.println("O's turn ");
        if(isXTurn) System.out.println(symbolOptions[0] + "'s turn ");
        else System.out.println(symbolOptions[1] + "'s turn ");
    }

    private void displayRules(){
        System.out.println(
                "\n| Rules |\n" +
                "Select a square by entering the number position" + 
                "on the board.\nYour symbol must occupy three squares " +
                "to win the game.\nIt can be diagonal, horizontal or " +
                "vertical.\n");
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
            playerSymbol = symbolOptions[0];
        else
            playerSymbol = symbolOptions[1];
    }

    private void markBoard(int pos){
        board.markBoard(pos, playerSymbol);
    }

    private boolean checkInput(int pos){
        return (pos >= 0 && pos < 9);
    }

    private boolean checkEmptySpace(int pos){
        if(board.getPositions()[pos].equals(symbolOptions[0]) 
                || 
                board.getPositions()[pos].equals(symbolOptions[1]) ){
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
                System.out.println(" ");
                setGameState(States.MAINMENU);
                //setIsRunning(false);
            }
        }

        // after the 7th turn and no winner is assigned, its a DRAW
        if(turnCounter >= 7){
            System.out.println("\tDraw");
            setGameState(States.MAINMENU);
            //setIsRunning(false);
        }
    }


    private void runGameLoop(){
        displayTurn();
        board.showBoard();
        getUserInput();
        checkWinner();
        turnCounter++;
        toggleTurn();
    }

    private boolean checkSymbol(String symbol){
        // I want the result to be false
        int tmp;
        try{
            tmp = Integer.parseInt(symbol);
            // if it worked then the input was a number
            return true;
        }
        catch(NumberFormatException e){
            //System.out.println("INFO | Number not allowed. ");
        }
        return false;
    }

    private void runChangeSymbols(){

        boolean isValid = true;
        String p1, p2;
        do{
            System.out.print("Enter player 1's symbol (numbers not allowed): ");
            p1 = keys.next();
            isValid = checkSymbol( p1.substring(0, 1) );
        }while( isValid );
        //System.out.println("p1: " + p1.substring(0, 1) );

        isValid = true;

        do{
            System.out.print("Enter player 2's symbol (numbers not allowed): ");
            p2 = keys.next();
            isValid = checkSymbol(p2.substring(0, 1));
        }while( isValid );
        //System.out.println("p2: " + p2);
        
        // set the new symbols
        symbolOptions[0] = p1.substring(0, 1);
        symbolOptions[1] = p2.substring(0, 1);

        // playerSymbol needs to be "updated".
        // use the new symbols provided.
        playerSymbol = symbolOptions[0];

        System.out.println(" ");
        setGameState(States.MAINMENU);
    }

    /*
     * main loop
     */
    public void start(){
        switch(gameState){
            case MAINMENU:
                runMainMenu();
                break;
            case GAME:
                runGameLoop();
                break;
            case CHANGE:
                System.out.println("\n| Change Symbols |");
                runChangeSymbols();
                break;
            default: break;
        }
    }

    public void cleanUp(){
        board = null;
        keys.close();
        //System.out.println("Clean up executed");
    }

    public boolean getIsRunning(){ return isRunning; }
    public void setIsRunning(boolean value){ isRunning = value; }

    public void setGameState(States newState){ gameState = newState; }

}

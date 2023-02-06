
/**
 * class to display the main board, using the singelton pattern
 */
public class Board{
    
    private String[] positions;

    private static Board instance;
    private Board(){ init(); }
    public static Board getInstance(){
        if(instance == null)
            instance = new Board();
        return instance;
    }

    private void init(){
        buildBoard();
    }

    private void buildBoard(){
        positions = new String[9];
        for(int i = 0; i < positions.length; i++){
            positions[i] = String.valueOf(i);
        }
    }

    public void showBoard(){
        System.out.print("\t+---+---+---+\n");
        System.out.print("\t| ");
        for(int r0 = 0; r0 < 3; r0++){
            System.out.print(positions[r0] + " | ");
        }
        //System.out.println();
        System.out.print("\n\t+---+---+---+\n");
        System.out.print("\t| ");
        for(int r0 = 3; r0 < 6; r0++){
            System.out.print(positions[r0] + " | ");
        }
        System.out.print("\n\t+---+---+---+\n");
        //System.out.println();
        System.out.print("\t| ");
        for(int r0 = 6; r0 < 9; r0++){
        System.out.print(positions[r0] + " | ");
        }
        System.out.print("\n\t+---+---+---+\n");
    }

    public void markBoard(int position, String mark){
        positions[position] = mark;
    }

    public String[] getPositions(){
        return positions;
    }
}

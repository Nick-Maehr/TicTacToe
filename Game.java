import java.util.Scanner;

public class Game {

  private Board board;


  public Game() {
    board = new Board();
  }


  public Game(Board board) {
    this.board = board;
  }


  /**
  * gameMode: 0 = PvC | 1 = CvP | 2 = PvP | 3 = CvC
  */
  public void play(int gameMode) {
    boolean toMove = true; 
    int winner = 0;

    Scanner scanner = new Scanner(System.in);

    board.prettyPrint();
    for(int i=0; i < 9; i++)
    {

      if (i % 2 == gameMode || gameMode == 2)
      {
        winner = PTurn(toMove ? -1 : 1, scanner);
      }
      else
      {
        winner = CTurn(toMove ? -1 : 1);
      }
      
      
      board.prettyPrint();

      // Print the winner if the game is over
      if (winner != 0)
      {
        System.out.println("The winner is: " + winner);
        break;
      }

      toMove = !toMove;
    }

    if (winner == 0)
    {
      System.out.println("The winner is: " + winner);
    }

    scanner.close();
  }


  /** 
   * Ask the player-to-move for input and make their move
   * */ 
  private int PTurn(int toMove, Scanner scanner) {

    // Get user input
    System.out.println("Pick a valid spot (ex: top_right = 0|2)");
    String input = scanner.nextLine();

    int row = Integer.parseInt(input.substring(0, 1));
    int col = Integer.parseInt(input.substring(2, 3));

    // Try to make the user's move
    if (board.addMove(row, col, toMove)) {
      // If the move is valid, end the turn
      return won();
    }

    // Repeat if the input was invalid
    return PTurn(toMove, scanner);
  }


  private int CTurn(int toMove) {
    int[] move = Minimax.move(board, toMove);
    int row = move[0];
    int col = move[1];

    // Try to make the AI's move
    if (board.addMove(row, col, toMove)) {
      // If the move is valid, end the turn
      return won();
    }
    else {
      // Handle if the input was invalid
      System.out.println("The AI made an illegal move.");
      System.exit(0);
      return 0;
    }
  }


  private int won() {
    return Board.winState(board.getBoard());
  }
}

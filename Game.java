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
  public void play() {
    int winner = 0;
    int gameMode;

    Scanner scanner = new Scanner(System.in);

    System.out.println("What game mode do you want to play?\n0 = PvC | 1 = CvP | 2 = PvP | 3 = CvC");
    String choice = scanner.nextLine();
    gameMode = Integer.parseInt(choice);

    boolean toMove = gameMode != 1; 

    board.prettyPrint();
    for(int i=0; i < 9; i++)
    {

      long startTime = System.nanoTime();
      
      if (i % 2 == gameMode || gameMode == 2)
      {
        winner = PTurn(toMove ? -1 : 1, scanner);
      }
      else
      {
        winner = CTurn(toMove ? -1 : 1);
      }
      double runTime = (System.nanoTime() - startTime) / 1E9;
      
      System.out.println("Process ran for: " + runTime + " seconds");
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
    int row;
    int col;

    try {
      row = Integer.parseInt(input.substring(0, 1));
      col = Integer.parseInt(input.substring(2, 3));
    }
    catch(NumberFormatException e)
    {
      System.out.println("Your responce must be int the format: Y|X. Y and X must be integers 0, 1, or 2.");
      return PTurn(toMove, scanner);
    }

    // Try to make the user's move
    if (board.addMove(row, col, toMove)) {
      // If the move is valid, end the turn
      return won();
    }

    // Repeat if the input was invalid
    System.out.println("Your responce must be int the format: Y|X. Y and X must be integers 0, 1, or 2.");
    return PTurn(toMove, scanner);
  }


  private int CTurn(int toMove) {
    int[] move = Minimax.move(board, toMove, toMove);
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

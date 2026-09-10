import java.util.Arrays;

public class Board {
  
  private int[][] board;

  public Board() {
    board = new int[3][3];
  }

  public int[][] getBoard() {
    return board;
  }

  public boolean addMove(int r, int c, int team) {
    if (board[r][c] != 0) {
      return false;
    }
    board[r][c] = team;
    return true;
  }

  public void prettyPrint() {
    prettyPrint(board);
  }

  public static void prettyPrint(int[][] board) {
    String[][] b = new String[3][3];
    for (int r=0; r < 3; r++) {
      for(int c=0; c < 3; c++) {
        int x = board[r][c];
        if (x == -1) {b[r][c] = "-1";}
        else {b[r][c] = " " + x;}
      }
    }

    System.out.println("┌──┬──┬──┐\n│" + b[0][0] + "│" + b[0][1] + "│" + b[0][2] + "│");
    System.out.println("├──┼──┼──┤\n│" + b[1][0] + "│" + b[1][1] + "│" + b[1][2] + "│");
    System.out.println("├──┼──┼──┤\n│" + b[2][0] + "│" + b[2][1] + "│" + b[2][2] + "│");
    System.out.println("└──┴──┴──┘");
  }

  /** 
   * Determines the winner of a board state. Teams denoted by 1 and -1. <br>
   * Inputs: int[][]<br>
   * Outputs: int<br>
   * Interpretation key: -1=A_Team | 0=blanks/draws | 1=B_Team
  */
  public static int winState(int[][] b) {
    // Check horizontal wins
    int score = 0;
    for (int[] row : b) {
      score = sumArray(row) / 3;
      // Return the winner if there is one
      if (score != 0) {
        return score;
      }
    }

    // Check verticle wins
    for (int c=0; c < 3; c++) {
      for (int r=0; r < 3; r++) {
        score += b[r][c];
      }
      score /= 3;
      // Return the winner if there is one
      if (score != 0) {
        return score;
      }
    }

    // Check diagonals
    score += b[0][0];
    score += b[1][1];
    score += b[2][2];
    score /= 3;
    if (score != 0) {
      return score;
    }

    score += b[2][0];
    score += b[1][1];
    score += b[0][2];
    score /= 3;

    return score;
  }


  public static int sumArray(int[] array) {
    return Arrays.stream(array).sum();
  }
}

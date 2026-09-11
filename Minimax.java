import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.Map;

public class Minimax {

  public static int[] move(Board board, int toMove) {

    int[] bestMove = new int[2];
    int[] moveResult = minimax(board.getBoard(), 8, toMove);
    
    bestMove[0] = moveResult[1];
    bestMove[1] = moveResult[2];

    System.out.println(bestMove[0] + "|" + bestMove[1] + "  Score: " + moveResult[0]);
    return bestMove;
  }

  private static int[] minimax(int[][] board, int depth, int toMove) {

    if (depth == 0 || Board.winState(board) != 0)
    {
      int score = Board.winState(board);

      if (score == 1) {
        score = 100 + depth;
      }
      else if (score == -1) {
        score = -100 + depth;
      }

      return new int[] {score, -1, -1};
    }

    boolean maximizingPlayer = toMove == 1;
    ArrayList<int[]> children = possibleMoves(board);
    int[] bestMove = new int[2];

    if (maximizingPlayer)
    {
      int maxEval = Integer.MIN_VALUE;

      for(int[] child : children)
      {
        int eval = minimax(simulateBoard(child[0], child[1], board, toMove), depth - 1, -1)[0];
        if (eval > maxEval)
        {
          maxEval = eval;
          bestMove = child;
        }
      }
      return new int[] {maxEval, bestMove[0], bestMove[1]};
    }

    else
    {
      int minEval = Integer.MAX_VALUE;

      for(int[] child : children)
      {
        int eval = minimax(simulateBoard(child[0], child[1], board, toMove), depth - 1, 1)[0];
        if (eval < minEval)
        {
          minEval = eval;
          bestMove = child;
        }
      }
      return new int[] {minEval, bestMove[0], bestMove[1]};
    }
  
  }

  private static ArrayList<int[]> possibleMoves(int[][] board) {
    ArrayList<int[]> result = new ArrayList<>();
    for(int r=0; r < 3; r++) {
      for (int c=0; c < 3; c++) {
        if (board[r][c] == 0) {
          result.add(new int[] {r, c});
        }
      }
    }
    return result;
  }

  // private static Map<int[], Integer> availableMoves(int[][] board, int toMove) {
  //   Map<int[], Integer> dictionary = new HashMap<>();
  //   for(int r=0; r < 3; r++) {
  //     for (int c=0; c < 3; c++) {
  //       if (board[r][c] == 0) {
  //         int score = score(simulateBoard(r, c, board, toMove), toMove);
  //         dictionary.put(new int[] {r, c}, score);
  //       }
  //     }
  //   }
  //   return dictionary;
  // }

  // private static int score(int[][] board, int toMove) {
  //   int score = Board.winState(board);
  //   score *= toMove;
  //   return score;
  // }

  private static int[][] simulateBoard(int r, int c, int[][] board, int toMove)
  {
    int[][] result = {board[0].clone(), board[1].clone(), board[2].clone()};
    result[r][c] = toMove;
    return result;
  }
}
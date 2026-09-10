import java.util.HashMap;
import java.util.Map;

public class Minimax {


  public static int[] move(Board board, int toMove) {
    Map<int[], Integer> options = availableMoves(board.getBoard(), toMove);
    
    int[] bestMove = null;
    int highestScore = Integer.MIN_VALUE;

    for (Map.Entry<int[], Integer> entry : options.entrySet()) {
      if (entry.getValue() > highestScore) {
        bestMove = entry.getKey();
        highestScore = entry.getValue();
      }
    }

    System.out.println(bestMove[0] + "|" + bestMove[1] + "  Score: " + options.get(bestMove));
    return bestMove;

  }

  private static Map<int[], Integer> availableMoves(int[][] board, int toMove) {
    Map<int[], Integer> dictionary = new HashMap<>();
    for(int r=0; r < 3; r++) {
      for (int c=0; c < 3; c++) {
        if (board[r][c] == 0) {
          int score = score(simulateBoard(r, c, board, toMove), toMove);
          dictionary.put(new int[] {r, c}, score);
        }
      }
    }
    return dictionary;
  }

  private static int score(int[][] board, int toMove) {
    int score = Board.winState(board);
    score *= toMove;
    return score;
  }

  private static int[][] simulateBoard(int r, int c, int[][] board, int toMove)
  {
    int[][] result = {board[0].clone(), board[1].clone(), board[2].clone()};
    result[r][c] = toMove;
    return result;
  }
}
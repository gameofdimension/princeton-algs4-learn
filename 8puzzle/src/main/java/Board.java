import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-08-29.
 */
public class Board {

  private int[][] tiles;
  private int n;

  private int nr;
  private int nc;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    this.n = tiles.length;
    this.tiles = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] == 0) {
          nr = i;
          nc = j;
        }
        this.tiles[i][j] = tiles[i][j];
      }
    }
  }

  // string representation of this board
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    int sum = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] != i * n + j + 1) {
          sum += 1;
        }
      }
    }
    if (tiles[n - 1][n - 1] != 0) {
      sum -= 1;
    }
    return sum;
  }

  private int computeMht(int val, int i, int j) {
    int ei = (val - 1) / n;
    int ej = (val - 1) % n;
    return Math.abs(ei - i) + Math.abs(ej - j);
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int sum = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] == 0) {
          continue;
        }
        int dist = computeMht(tiles[i][j], i, j);
        sum += dist;
      }
    }
    return sum;
  }

  // is this board the goal board?
  public boolean isGoal() {
    int base = n * n;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int act = tiles[i][j];
        int exp = i * n + j + 1;
        if (act % base != exp % base) {
          return false;
        }
      }
    }
    return true;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (this == y) {
      return true;
    }
    if (y == null || getClass() != y.getClass()) {
      return false;
    }
    Board board = (Board) y;
    if (this.n != board.n) {
      return false;
    }

    for (int i = 0; i < n; i++) {
      if (!Arrays.equals(tiles[i], board.tiles[i])) {
        return false;
      }
    }
    return true;
  }

  private boolean isLegal(int r, int c) {
    if (r < 0) {
      return false;
    }
    if (r >= n) {
      return false;
    }
    if (c < 0) {
      return false;
    }
    if (c >= n) {
      return false;
    }
    return true;
  }

  private int[][] createAndSwap(int candr, int candc, int nr, int nc) {
    int[][] arr = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        arr[i][j] = tiles[i][j];
      }
    }
    arr[nr][nc] = tiles[candr][candc];
    arr[candr][candc] = tiles[nr][nc];
    return arr;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    int candr = nr - 1;
    int candc = nc;

    Stack<Board> stack = new Stack<Board>();
    if (isLegal(candr, candc)) {
      int[][] arr = createAndSwap(candr, candc, nr, nc);
      stack.push(new Board(arr));
    }

    candr = nr + 1;
    candc = nc;
    if (isLegal(candr, candc)) {
      int[][] arr = createAndSwap(candr, candc, nr, nc);
      stack.push(new Board(arr));
    }

    candr = nr;
    candc = nc + 1;
    if (isLegal(candr, candc)) {
      int[][] arr = createAndSwap(candr, candc, nr, nc);
      stack.push(new Board(arr));
    }

    candr = nr;
    candc = nc - 1;
    if (isLegal(candr, candc)) {
      int[][] arr = createAndSwap(candr, candc, nr, nc);
      stack.push(new Board(arr));
    }
    return stack;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    int r1 = 0, r2 = 0;
    int c1 = 0, c2 = 0;
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] != 0) {
          count += 1;
        }
        if (count == 1) {
          r1 = i;
          c1 = j;
        } else if (count == 2) {
          r2 = i;
          c2 = j;
          break;
        }
      }
      if (count == 2) {
        break;
      }
    }
    int[][] arr = createAndSwap(r1, c1, r2, c2);
    return new Board(arr);
  }

  // unit testing (not graded)
  public static void main(String[] args) {

    int[][] arr = {{1, 3, 2}, {4, 0, 7}, {6, 8, 5}};
    Board board = new Board(arr);
    StdOut.println(board.dimension());
    StdOut.println(board);

    int[][] arr1 = {{1, 3, 2}, {6, 0, 7}, {8, 4, 5}};

    Board board1 = new Board(arr1);
    StdOut.println(board.equals(board1));
    StdOut.println(board.equals(board));

    StdOut.println(board.manhattan());
    StdOut.println(board1.manhattan());

    StdOut.println(board.hamming());
    StdOut.println(board1.hamming());

    StdOut.println();
    {
      int[][] sample = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
      Board sb = new Board(sample);
      StdOut.println(sb.hamming());
      StdOut.println(sb.manhattan());
      StdOut.println(sb.isGoal());

      StdOut.println("---------------");
      StdOut.println(sb.twin());
      StdOut.println(sb);
      StdOut.println("---------------");
      for (Board neighbor : sb.neighbors()) {
        StdOut.println(neighbor);
      }
      StdOut.println("---------------");
    }

    StdOut.println();
    int[][] garr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    Board goal = new Board(garr);
    StdOut.println(goal.isGoal());

  }

}

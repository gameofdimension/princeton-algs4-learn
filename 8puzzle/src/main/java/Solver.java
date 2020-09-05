import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-08-29.
 */
public class Solver {

  private static class SearchNode {

    private final Board board;
    private final SearchNode previous;
    private final int moves;
    private final int hm;
    private final int mh;

    public SearchNode(Board board, SearchNode previous, int moves) {
      this.board = board;
      this.previous = previous;
      this.moves = moves;
      this.hm = board.hamming();
      this.mh = board.manhattan();
    }
  }

  private boolean solvable = false;
  private SearchNode goal;

  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException("null initial");
    }

    Comparator<SearchNode> comparator = new Comparator<SearchNode>() {
      public int compare(SearchNode o1, SearchNode o2) {
        // return (o1.moves + o1.hm) - (o2.moves + o2.hm);
        return (o1.moves + o1.mh) - (o2.moves + o2.mh);
      }
    };

    MinPQ<SearchNode> pq = new MinPQ<SearchNode>(comparator);
    pq.insert(new SearchNode(initial, null, 0));

    MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(comparator);
    twinPq.insert(new SearchNode(initial.twin(), null, 0));

    solve(pq, twinPq);
  }

  private static SearchNode explore(MinPQ<SearchNode> mq) {
    SearchNode node = mq.delMin();
    if (node.board.isGoal()) {
      return node;
    }
    for (Board next : node.board.neighbors()) {
      if (node.previous != null && next.equals(node.previous.board)) {
        continue;
      }
      mq.insert(new SearchNode(next, node, node.moves + 1));
    }
    return null;
  }

  private void solve(MinPQ<SearchNode> pq, MinPQ<SearchNode> twinPq) {
    while (true) {
      SearchNode res = explore(pq);
      if (res != null) {
        solvable = true;
        goal = res;
        break;
      }

      res = explore(twinPq);
      if (res != null) {
        solvable = false;
        break;
      }
    }
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return solvable;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (!isSolvable()) {
      return -1;
    }
    return goal.moves;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!isSolvable()) {
      return null;
    }
    Stack<Board> result = new Stack<Board>();
    SearchNode pos = goal;
    while (pos != null) {
      result.push(pos.board);
      pos = pos.previous;
    }
    return result;
  }

  // test client (see below)
  public static void main(String[] args) {
    StdOut.println(args);
  }
}

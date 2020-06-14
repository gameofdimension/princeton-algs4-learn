import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-06-14.
 */
public class Percolation {

  private byte[][] sites;
  private final int n;
  private int openNum;

  private final int upNode;
  private final int downNode;

  private final WeightedQuickUnionUF uf;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("wrong n");
    }
    this.n = n;
    this.openNum = 0;
    this.sites = new byte[n + 1][n + 1];
    this.upNode = n * n;
    this.downNode = n * n + 1;
    this.uf = new WeightedQuickUnionUF(n * n + 2);
  }

  private boolean legalSite(int row, int col) {
    if (row < 1 || row > n) {
      return false;
    }
    if (col < 1 || col > n) {
      return false;
    }
    return true;
  }

  private int index(int row, int col) {
    return (row - 1) * n + col - 1;
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    if (!legalSite(row, col)) {
      throw new IllegalArgumentException("wrong row or col");
    }
    if (isOpen(row, col)) {
      return;
    }
    sites[row][col] = 1;
    openNum += 1;

    connectNeighbor(row, col);
  }

  private void connectNeighbor(int row, int col) {
    if (row == 1) {
      uf.union(upNode, index(row, col));
    }

    if (row == n) {
      uf.union(downNode, index(row, col));
    }

    int nrow = row - 1;
    int ncol = col;

    if (legalSite(nrow, ncol) && isOpen(nrow, ncol)) {
      uf.union(index(row, col), index(nrow, ncol));
    }

    nrow = row;
    ncol = col - 1;
    if (legalSite(nrow, ncol) && isOpen(nrow, ncol)) {
      uf.union(index(row, col), index(nrow, ncol));
    }

    nrow = row;
    ncol = col + 1;
    if (legalSite(nrow, ncol) && isOpen(nrow, ncol)) {
      uf.union(index(row, col), index(nrow, ncol));
    }

    nrow = row + 1;
    ncol = col;
    if (legalSite(nrow, ncol) && isOpen(nrow, ncol)) {
      uf.union(index(row, col), index(nrow, ncol));
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    if (!legalSite(row, col)) {
      throw new IllegalArgumentException("wrong row or col");
    }
    return sites[row][col] != 0;
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    if (!legalSite(row, col)) {
      throw new IllegalArgumentException("wrong row or col");
    }

    return uf.connected(index(row, col), upNode);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return openNum;
  }

  // does the system percolate?
  public boolean percolates() {
    return uf.connected(upNode, downNode);
  }

  // test client (optional)
  public static void main(String[] args) {
    while (true) {
      int n = StdIn.readInt();
      Percolation percolation = new Percolation(n);
      while (true) {
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;

        percolation.open(row, col);

        if (percolation.percolates()) {
          int openNum = percolation.numberOfOpenSites();
          int grids = n * n;
          StdOut.printf("percolates at %d/%d=%.4f\n", openNum, grids, openNum * 1.0 / grids);
          break;
        }
      }
    }
  }
}

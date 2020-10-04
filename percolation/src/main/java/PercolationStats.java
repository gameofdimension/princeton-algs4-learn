import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-06-14.
 */
public class PercolationStats {

  private static final double CONFIDENCE_95 = 1.96;
  private final int n;
  private final int trials;

  private double[] thresholds;

  // private final double mean;
  // private final double stddev;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0) {
      throw new IllegalArgumentException("wrong n");
    }
    if (trials <= 0) {
      throw new IllegalArgumentException("wrong trials");
    }
    this.n = n;
    this.trials = trials;
    thresholds = new double[trials];
    doTrails();
    // mean = computeMean();
    // stddev = computeStddev(mean);
  }

  // private double computeStddev(double avg) {
  //   double var = 0;
  //   for (int i = 0; i < trials; i++) {
  //     double delta = thresholds[i] - avg;
  //     var += delta * delta;
  //   }
  //   return Math.sqrt(var / (trials - 1 + 1e-20));
  // }
  //
  // private double computeMean() {
  //   double sum = 0.0;
  //   for (int i = 0; i < trials; i++) {
  //     sum += thresholds[i];
  //   }
  //   return sum / trials;
  // }


  private double oneTrail() {
    Percolation percolation = new Percolation(n);
    while (true) {
      int row = StdRandom.uniform(n) + 1;
      int col = StdRandom.uniform(n) + 1;
      percolation.open(row, col);
      if (percolation.percolates()) {
        return percolation.numberOfOpenSites() * 1.0 / (n * n);
      }
    }
  }

  private void doTrails() {
    for (int i = 0; i < trials; i++) {
      thresholds[i] = oneTrail();
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(thresholds);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(thresholds);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
  }

  // test client (see below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats stats = new PercolationStats(n, trials);

    StdOut.printf("%30s = %f\n", "mean", stats.mean());
    StdOut.printf("%30s = %f\n", "stddev", stats.stddev());
    StdOut.printf("%30s = [%f, %f]\n", "95% confidence interval", stats.confidenceLo(),
        stats.confidenceHi());
  }
}

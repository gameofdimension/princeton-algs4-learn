import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-06-14.
 */
public class Demo {

  public static void main(String[] args) {

    for (int i = 0; i < 100; i++) {
      double rd = StdRandom.uniform(-3., 5.);
      StdOut.printf("%.2f\n", rd);
    }

    Stopwatch stopwatch;
  }

}

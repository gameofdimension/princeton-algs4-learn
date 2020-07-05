import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-06-20.
 */
public class Permutation {

  public static void main(String[] args) {

    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      rq.enqueue(item);
      // StdOut.printf("size: %d\n", rq.size());
    }

    for (int i = 0; i < k; i++) {
      StdOut.printf("%s\n", rq.dequeue());
    }
  }
}

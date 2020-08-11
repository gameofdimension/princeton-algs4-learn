import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-08-09.
 */
public class BruteCollinearPoints {

  private Point[] points;
  private List<LineSegment> result;

  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException("null points");
    }
    for (Point point : points) {
      if (point == null) {
        throw new IllegalArgumentException("null point");
      }
    }

    this.points = points;

    if (hasDuplicate(this.points)) {
      throw new IllegalArgumentException("duplicate");
    }
    result = computeSegments();
  }   // finds all line segments containing 4 points

  private List<LineSegment> computeSegments() {
    List<LineSegment> tmp = new ArrayList<LineSegment>();
    if (points.length < 4) {
      return tmp;
    }

    int size = points.length;
    for (int i = 0; i < size; i++) {
      Point p = points[i];
      for (int j = i + 1; j < size; j++) {
        Point q = points[j];
        for (int k = j + 1; k < size; k++) {
          Point r = points[k];
          for (int l = k + 1; l < size; l++) {
            Point s = points[l];
            double slope1 = p.slopeTo(q);
            double slope2 = p.slopeTo(r);
            double slope3 = p.slopeTo(s);
            if (Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0) {
              Point[] segs = {p, q, r, s};
              // System.out.println(p);
              // System.out.println(q);
              // System.out.println(r);
              // System.out.println(s);
              // System.out.println();
              Arrays.sort(segs);
              tmp.add(new LineSegment(segs[0], segs[3]));
            }
          }
        }
      }
    }
    return tmp;
  }

  public int numberOfSegments() {
    return result.size();
  }    // the number of line segments

  public LineSegment[] segments() {
    return result.toArray(new LineSegment[0]);
  }            // the line segments

  private static boolean hasDuplicate(Point[] points) {
    Arrays.sort(points);
    int size = points.length;
    if (size <= 1) {
      return false;
    }
    for (int i = 1; i < size; i++) {
      if (points[i - 1].compareTo(points[i]) == 0) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}

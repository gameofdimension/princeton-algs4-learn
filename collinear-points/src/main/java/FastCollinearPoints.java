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
public class FastCollinearPoints {

  private static class MySegment implements Comparable<MySegment> {

    private Point p;
    private Point q;

    public MySegment(Point p, Point q) {
      this.p = p;
      this.q = q;
    }

    public int compareTo(MySegment o) {
      if (p.compareTo(o.p) == 0) {
        return q.compareTo(o.q);
      }
      return p.compareTo(o.p);
    }
  }

  private Point[] points;
  private List<LineSegment> result;

  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException("null points");
    }
    for (Point point : points) {
      if (point == null) {
        throw new IllegalArgumentException("null point");
      }
    }

    this.points = points;

    if (BruteCollinearPoints.hasDuplicate(points)) {
      throw new IllegalArgumentException("duplicate");
    }
    result = computeSegments();
  }   // finds all line segments containing 4 or more points

  private List<LineSegment> computeSegments() {
    List<LineSegment> tmp = new ArrayList<LineSegment>();
    if (points.length < 4) {
      return tmp;
    }

    List<MySegment> myList = new ArrayList<MySegment>();
    for (int i = 0; i < points.length; i++) {
      Point[] tmpArr = points.clone();
      Point origin = points[i];
      Arrays.sort(tmpArr, origin.slopeOrder());
      int begin = 1;
      double slope1 = origin.slopeTo(tmpArr[begin]);
      int end = 1;
      while (end < tmpArr.length) {
        double slope2 = origin.slopeTo(tmpArr[end]);
        if (Double.compare(slope1, slope2) == 0) {
          end += 1;
        } else {
          int len = end - begin + 1;
          if (len >= 3) {
            tmpArr[begin - 1] = origin;
            Arrays.sort(tmpArr, begin - 1, end + 1);
            myList.add(new MySegment(tmpArr[begin - 1], tmpArr[end]));
          }
          begin = end;
          slope1 = origin.slopeTo(tmpArr[begin]);
        }
      }
    }
    MySegment[] mySegments = myList.toArray(new MySegment[0]);
    Arrays.sort(mySegments);

    MySegment previous = mySegments[0];
    tmp.add(new LineSegment(previous.p, previous.q));
    for (int i = 1; i < mySegments.length; i++) {
      if (mySegments[i].compareTo(previous) == 0) {
        continue;
      }
      previous = mySegments[i];
      tmp.add(new LineSegment(previous.p, previous.q));
    }
    return tmp;
  }

  public int numberOfSegments() {
    return result.size();
  }     // the number of line segments

  public LineSegment[] segments() {
    return result.toArray(new LineSegment[0]);
  }          // the line segments

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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}

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

  /**
   * 按照 spec 上的思路，有一个很重要的问题是去重。k 点共线的直线可能会被加入 k 次。 普遍的去重思路有两种： 一是在结果集新增过程中去重，即发现是重复的则不加入。 二是最后整体sort
   * and unique 去重。 但是根据 faq 中的最后部分，n 个点的集合大于 4 个点共线的 unique 直线数至少有 O(n^2) 规模， 于是思路二内存使用会超标。思路一简单的
   * for loop 查重，单次耗时是线性 O(n^2) 的，肯定时间性能会超标。 如果使用高效的平衡树存储新增直线，单次查重以及插入的性能是 O(log(n^2))
   * ,看起来是满足要求的，但是这类 高级数据结构相对于本节课显然是超纲了的。
   *
   * Coursera 课程 forum 上有人给出了非常有洞见的解决方法。核心是观察到在 k 点共线时，以不同的点作为 origin ， 虽然会得到 k 个相同的结果，这是这 k
   * 种结果是可以区分的，区分的方法则是观察作为 origin 的点在直线上的相对 位置，是第一个点还是其他位置。这种位置关系可以用排序的方法确定。
   *
   * @return list
   */
  private List<LineSegment> computeSegments() {
    List<LineSegment> tmp = new ArrayList<LineSegment>();
    if (points.length < 4) {
      return tmp;
    }

    for (int i = 0; i < points.length; i++) {
      Point[] tmpArr = new Point[points.length];
      System.arraycopy(points, 0, tmpArr, 0, points.length);
      final Point origin = points[i];

      // System.out.println("before sort");
      // for (Point pp : tmpArr) {
      //   System.out.println(pp);
      // }
      Arrays.sort(tmpArr, origin.slopeOrder());
      // System.out.println("after sort");
      // for (Point pp : tmpArr) {
      //   System.out.println(pp);
      // }
      int begin = 1;
      double slope1 = origin.slopeTo(tmpArr[begin]);
      int end = 1;
      while (end < tmpArr.length) {
        // System.out.println(end);
        double slope2 = origin.slopeTo(tmpArr[end]);
        // System.out.println(slope1 + ":" + slope2);
        if (Double.compare(slope1, slope2) == 0) {
          end += 1;
        } else {
          // System.out.println(begin + " vs " + end);
          int len = end - begin;
          // System.out.println(111);
          if (len >= 3) {
            Point[] again = new Point[len+1];
            // System.out.println(111);
            System.arraycopy(tmpArr, begin, again, 0, len);
            // System.out.println(2222);
            again[len] = origin;

            // System.out.println(3333);
            Arrays.sort(again);
            // System.out.println(4444);
            // System.out.println("origin:" + origin);
            // System.out.println(again[0]);

            // System.out.println();
            // for (Point pp : again) {
            //   System.out.println("000:" + pp);
            // }
            if (origin.compareTo(again[0]) == 0) {
              // System.out.println(5555);
              tmp.add(new LineSegment(again[0], again[len]));
            }
            // System.out.println(6666);
          }
          begin = end;
          slope1 = origin.slopeTo(tmpArr[begin]);
          end += 1;
        }
      }
    }
    // System.out.println(7777);
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

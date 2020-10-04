import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-10-04.
 */
public class PointSET {

  private final SET<Point2D> point2DSET;

  public PointSET() {                               // construct an empty set of points
    this.point2DSET = new SET<Point2D>();
  }

  public boolean isEmpty() {                      // is the set empty?
    return point2DSET.isEmpty();
  }

  public int size() {                        // number of points in the set
    return point2DSET.size();
  }

  public void insert(
      Point2D p) {        // add the point to the set (if it is not already in the set)
    if (p == null) {
      throw new IllegalArgumentException("null args");
    }
    point2DSET.add(p);
  }

  public boolean contains(Point2D p) {           // does the set contain point p?
    if (p == null) {
      throw new IllegalArgumentException("null args");
    }
    return point2DSET.contains(p);
  }

  public void draw() {                      // draw all points to standard draw
    for (Point2D point2D : point2DSET) {
      point2D.draw();
    }
  }

  public Iterable<Point2D> range(
      RectHV rect) {         // all points that are inside the rectangle (or on the boundary)
    if (rect == null) {
      throw new IllegalArgumentException("null args");
    }
    List<Point2D> list = new ArrayList<Point2D>();
    for (Point2D point2D : point2DSET) {
      if (rect.contains(point2D)) {
        list.add(point2D);
      }
    }
    return list;
  }

  public Point2D nearest(
      Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
    if (p == null) {
      throw new IllegalArgumentException("null args");
    }
    double dist = Double.POSITIVE_INFINITY;
    Point2D res = null;
    for (Point2D point2D : point2DSET) {
      if (point2D.distanceSquaredTo(p) < dist) {
        res = point2D;
        dist = point2D.distanceSquaredTo(p);
      }
    }
    return res;
  }

  public static void main(String[] args) {               // unit testing of the methods (optional)
  }

}

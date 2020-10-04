import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-10-04.
 */
public class KdTree {

  private static class Node {

    private final boolean xdim;
    private int size;
    private final Point2D self;
    private Node left;
    private Node right;
    private RectHV rectHV;

    public Node(RectHV rectHV, boolean xdim, Point2D self) {
      this.xdim = xdim;
      this.self = self;
      this.size = 1;
      this.rectHV = rectHV;
      this.left = null;
      this.right = null;
    }
  }

  private Node root;

  public KdTree() {                               // construct an empty set of points
  }

  public boolean isEmpty() {                      // is the set empty?
    return root == null;
  }

  public int size() {                        // number of points in the set
    if (root == null) {
      return 0;
    }
    return root.size;
  }

  private boolean goLeft(Node node, Point2D p) {
    if (node.xdim) {
      return p.x() <= node.self.x();
    }
    return p.y() <= node.self.y();
  }

  private boolean contains(Node node, Point2D p) {
    if (p.equals(node.self)) {
      return true;
    }

    if (goLeft(node, p)) {
      if (node.left == null) {
        return false;
      }
      return contains(node.left, p);
    } else {
      if (node.right == null) {
        return false;
      }
      return contains(node.right, p);
    }
  }

  private void insert(Node node, Point2D p) {
    if (p.equals(node.self)) {
      throw new IllegalStateException("should not equal");
    }
    node.size += 1;

    if (goLeft(node, p)) {
      if (node.left == null) {
        node.left = new Node(leftRectHV(node), !node.xdim, p);
      } else {
        insert(node.left, p);
      }
    } else {
      if (node.right == null) {
        node.right = new Node(rightRectHV(node), !node.xdim, p);
      } else {
        insert(node.right, p);
      }
    }
  }

  public void insert(
      Point2D p) {        // add the point to the set (if it is not already in the set)

    if (p == null) {
      throw new IllegalArgumentException("null args");
    }
    if (contains(p)) {
      return;
    }
    if (root == null) {
      root = new Node(new RectHV(0, 0, 1, 1), true, p);
      return;
    }
    insert(root, p);
  }

  public boolean contains(Point2D p) {           // does the set contain point p?
    if (p == null) {
      throw new IllegalArgumentException("null args");
    }
    if (root == null) {
      return false;
    }
    return contains(root, p);
  }

  private void draw(Node node) {
    if (node != null) {
      node.self.draw();
      draw(node.left);
      draw(node.right);
    }
  }

  public void draw() {                      // draw all points to standard draw
    if (root != null) {
      draw(root);
    }
  }

  private RectHV leftRectHV(Node node) {
    if (node.xdim) {
      return new RectHV(node.rectHV.xmin(), node.rectHV.ymin(), node.self.x(), node.rectHV.ymax());
    } else {
      return new RectHV(node.rectHV.xmin(), node.rectHV.ymin(), node.rectHV.xmax(), node.self.y());
    }
  }

  private RectHV rightRectHV(Node node) {
    if (node.xdim) {
      return new RectHV(node.self.x(), node.rectHV.ymin(), node.rectHV.xmax(), node.rectHV.ymax());
    } else {
      return new RectHV(node.rectHV.xmin(), node.self.y(), node.rectHV.xmax(), node.rectHV.ymax());
    }
  }

  private void range(List<Point2D> result, Node node, RectHV rectHV) {
    // if (node == null) {
    //   return result;
    // }
    if (rectHV.contains(node.self)) {
      result.add(node.self);
    }
    if (node.left != null) {
      if (rectHV.intersects(node.left.rectHV)) {
        range(result, node.left, rectHV);
      }
    }
    if (node.right != null) {
      if (rectHV.intersects(node.right.rectHV)) {
        range(result, node.right, rectHV);
      }
    }
  }

  public Iterable<Point2D> range(
      RectHV rect) {         // all points that are inside the rectangle (or on the boundary)
    if (rect == null) {
      throw new IllegalArgumentException("null args");
    }
    List<Point2D> result = new ArrayList<Point2D>();
    if (root == null) {
      return result;
    }
    range(result, root, rect);
    return result;
  }

  private Point2D nearest(Point2D closest, Node node, Point2D point2D) {

    if (node.self.distanceTo(point2D) < closest.distanceTo(point2D)) {
      closest = node.self;
    }
    if (node.left != null) {
      if (node.left.rectHV.distanceTo(point2D) < closest.distanceTo(point2D)) {
        closest = nearest(closest, node.left, point2D);
      }
    }
    if (node.right != null) {
      if (node.right.rectHV.distanceTo(point2D) < closest.distanceTo(point2D)) {
        closest = nearest(closest, node.right, point2D);
      }
    }
    return closest;
  }


  public Point2D nearest(
      Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
    if (p == null) {
      throw new IllegalArgumentException("null args");
    }

    if (root == null) {
      return null;
    }

    return nearest(root.self, root, p);
  }

  public static void main(String[] args) {              // unit testing of the methods (optional)
  }

}

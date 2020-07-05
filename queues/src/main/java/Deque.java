import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-06-20.
 */
public class Deque<Item> implements Iterable<Item> {

  private final Node first;
  private final Node last;

  private int n;

  private class Node {

    Item item;
    Node prev;
    Node next;
  }

  // construct an empty deque
  public Deque() {
    this.first = new Node();
    this.last = new Node();
    this.first.next = last;
    this.last.prev = first;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size() == 0;
  }

  // return the number of items on the deque
  public int size() {
    return n;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("null param");
    }

    Node afterFirst = first.next;

    Node newNode = new Node();
    newNode.item = item;
    newNode.next = afterFirst;
    first.next = newNode;

    afterFirst.prev = newNode;
    newNode.prev = first;
    n += 1;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("null param");
    }

    Node beforeLast = last.prev;

    Node newNode = new Node();
    newNode.item = item;

    newNode.next = last;
    beforeLast.next = newNode;

    last.prev = newNode;
    newNode.prev = beforeLast;
    n += 1;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException("empty");
    }

    Node afterFirst = first.next;
    first.next = afterFirst.next;
    first.next.prev = first;
    n -= 1;
    return afterFirst.item;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException("empty");
    }

    Node beforeLast = last.prev;
    last.prev = beforeLast.prev;
    last.prev.next = last;
    n -= 1;
    return beforeLast.item;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {

    Node current = first.next;

    public boolean hasNext() {
      return !(current.next == null);
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException("hasNext");
      }
      Node tmp = current;
      current = current.next;
      return tmp.item;
    }

    public void remove() {
      throw new UnsupportedOperationException("remove");
    }
  }

  // unit testing (required)
  public static void main(String[] args) {

    Deque<Integer> deque = new Deque<Integer>();
    deque.addFirst(1);
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(2);
    StdOut.printf("size: %s\n", deque.size());
    deque.removeFirst();
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(3);
    StdOut.printf("size: %s\n", deque.size());
    deque.addLast(6);
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(4);
    StdOut.printf("size: %s\n", deque.size());
    deque.removeLast();
    StdOut.printf("size: %s\n", deque.size());
    deque.addLast(7);
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(5);
    StdOut.printf("size: %s\n", deque.size());
    deque.removeLast();
    StdOut.printf("size: %s\n", deque.size());
    deque.removeFirst();
    StdOut.printf("size: %s\n", deque.size());
    StdOut.printf("is empty : %b\n", deque.isEmpty());

    for (Integer item : deque) {
      StdOut.printf("item: %s\n", item);
    }

    deque.removeLast();
    deque.removeFirst();
    deque.removeFirst();
    StdOut.printf("is empty : %b\n", deque.isEmpty());

    deque.addFirst(1);
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(2);
    StdOut.printf("size: %s\n", deque.size());
    deque.removeFirst();
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(3);
    StdOut.printf("size: %s\n", deque.size());
    deque.addLast(6);
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(4);
    StdOut.printf("size: %s\n", deque.size());
    deque.removeLast();
    StdOut.printf("size: %s\n", deque.size());
    deque.addLast(7);
    StdOut.printf("size: %s\n", deque.size());
    deque.addFirst(5);
    StdOut.printf("size: %s\n", deque.size());
    deque.removeLast();
    StdOut.printf("size: %s\n", deque.size());
    deque.removeFirst();
    StdOut.printf("size: %s\n", deque.size());
    StdOut.printf("is empty : %b\n", deque.isEmpty());

    for (Integer item : deque) {
      StdOut.printf("item: %s\n", item);
    }

    deque.removeLast();
    deque.removeFirst();
    deque.removeFirst();
    StdOut.printf("is empty : %b\n", deque.isEmpty());
  }

}
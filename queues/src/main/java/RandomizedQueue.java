import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author yzq, yzq@leyantech.com
 * @date 2020-06-20.
 */

// copy from https://algs4.cs.princeton.edu/13stacks/ResizingArrayStack.java.html
public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] arr;
  private int pos;

  // construct an empty randomized queue
  public RandomizedQueue() {
    this.arr = (Item[]) new Object[1];
    this.pos = 0;
  }

  // resize the underlying array holding the elements
  private void resize(int capacity) {
    assert capacity >= pos;

    // textbook implementation
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < pos; i++) {
      copy[i] = arr[i];
    }
    arr = copy;

    // alternative implementation
    // a = java.util.Arrays.copyOf(a, capacity);
  }

  private void exch(int i, int j) {
    Item temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return pos == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return pos;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("null");
    }
    if (pos == arr.length) {
      resize(2 * arr.length);
    }
    arr[pos] = item;
    pos += 1;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("dequeue empty");
    }
    int idx = StdRandom.uniform(pos);
    exch(idx, pos - 1);
    Item item = arr[pos - 1];
    arr[pos - 1] = null;
    pos -= 1;
    if (pos > 0 && pos == arr.length / 4) {
      resize(arr.length / 2);
    }
    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException("sample empty");
    }
    int idx = StdRandom.uniform(pos);
    return arr[idx];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {

    int current = 0;

    public boolean hasNext() {
      return current < pos;
    }

    public Item next() {
      if (current >= pos) {
        throw new NoSuchElementException("next");
      }
      Item item = arr[current];
      current += 1;
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException("remove");
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

    rq.enqueue(9);
    rq.enqueue(10);
    rq.enqueue(11);
    rq.enqueue(12);
    StdOut.printf("size: %d, empty: %s\n", rq.size(), rq.isEmpty());
    rq.enqueue(13);
    StdOut.printf("size: %d, empty: %s\n", rq.size(), rq.isEmpty());
    StdOut.printf("deque: %d, size: %d, empty: %s\n", rq.dequeue(), rq.size(), rq.isEmpty());
    StdOut.printf("deque: %d, size: %d, empty: %s\n", rq.dequeue(), rq.size(), rq.isEmpty());
    rq.enqueue(14);
    StdOut.printf("size: %d, empty: %s\n", rq.size(), rq.isEmpty());
    StdOut.printf("sample: %d, size: %d, empty: %s\n", rq.sample(), rq.size(), rq.isEmpty());
    rq.enqueue(15);
    StdOut.printf("size: %d, empty: %s\n", rq.size(), rq.isEmpty());

    for (int a : rq) {
      StdOut.printf("traverse: %d\n", a);
    }
  }

}

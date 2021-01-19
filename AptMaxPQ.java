
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class AptMaxPQ<Apt> implements Iterable<Apt> {
    private Apt[] pq;                    // store items at indices 1 to n
    private int n;                       // number of items on priority queue
    private Comparator<Apt> comparator;  // optional comparator
	private Comparator<Apt> comparatorApt;


    public AptMaxPQ(int initCapacity) {
        pq = (Apt[]) new Object[initCapacity + 1];
        n = 0;
    }

    public AptMaxPQ() {
        this(1);
    }


    public AptMaxPQ(int initCapacity, Comparator<Apt> comparator, Comparator<Apt> comparator2) {
        this.comparator = comparator;
		this.comparatorApt = comparator2;
        pq = (Apt[]) new Object[initCapacity + 1];
        n = 0;
    }
	
    public AptMaxPQ(int initCapacity, Comparator<Apt> comparator) {
        this.comparator = comparator;
        pq = (Apt[]) new Object[initCapacity + 1];
        n = 0;
    }

    public AptMaxPQ(Comparator<Apt> comparator, Comparator<Apt> comparator2) {
        this(1, comparator, comparator2);
    }
	
    public AptMaxPQ(Comparator<Apt> comparator) {
        this(1, comparator);
    }


    public AptMaxPQ(Apt[] apts) {
        n = apts.length;
        pq = (Apt[]) new Object[apts.length + 1];
        for (int i = 0; i < n; i++)
            pq[i+1] = apts[i];
        for (int k = n/2; k >= 1; k--)
            sink(k);
        assert isMaxHeap();
    }
      

    public boolean isEmpty() {
        return n == 0;
    }


    public int size() {
        return n;
    }


    public Apt max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // helper function to double the size of the heap array
    private void resize(int capacity) {
        assert capacity > n;
        Apt[] temp = (Apt[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }



    public void insert(Apt x) {

        // double size of array if necessary
        if (n == pq.length - 1) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert isMaxHeap();
    }


    public Apt delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Apt max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null;     // to avoid loiterig and help with garbage collection
        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
        assert isMaxHeap();
        return max;
    }
	
	public void delApt(Apt del){
		int del_index = aptIndex(del);
		exch(del_index, 1);
		exch(1, n--);
		sink(1);
		//swim(del_index);
		pq[n + 1] = null;
		if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
	}

   /***************************************************************************
    * Helper functions to restore the heap invariant.
    ***************************************************************************/

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

   /***************************************************************************
    * Helper functions for compares and swaps.
    ***************************************************************************/

    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Apt>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }


    private void exch(int i, int j) {
        Apt swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..N] a max heap?
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    // is subtree of pq[1..n] rooted at k a max heap?
    private boolean isMaxHeap(int k) {
        if (k > n) return true;
        int left = 2*k;
        int right = 2*k + 1;
        if (left  <= n && less(k, left))  return false;
        if (right <= n && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }


   /***************************************************************************
    * Iterator.
    ***************************************************************************/


    public Iterator<Apt> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Apt> {

        // create a new pq
        private AptMaxPQ<Apt> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new AptMaxPQ<Apt>(size());
            else                    copy = new AptMaxPQ<Apt>(size(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Apt next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }
	
	//------------------------------------------------------------------------------------------------
	
	public Apt aptOf(int i){
		return pq[i];
	}
	
	public int aptIndex(Apt apts){
		
		int p = 1;
		while(p < n){
			if(comparatorApt.compare(pq[p], apts) == 0){
				break;
			}
			p++;
		}
		
		return p;
	}
	
	public void updateApt(int index, Apt a){
		pq[index] = a;
		swim(index);
	}
	
	//------------------------------------------------------------------------------------------------


    public static void main(String[] args) {
        AptMaxPQ<String> pq = new AptMaxPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }

}


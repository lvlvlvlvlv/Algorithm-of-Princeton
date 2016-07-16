import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int N;
    // construct an empty randomized queue
   public RandomizedQueue() {
       items = (Item[]) new Object[2];
       N = 0;
   }
    // is the queue empty?
   public boolean isEmpty() 
   {
       return N == 0;
   }
    // return the number of items on the queue
   public int size()
   { 
       return N;
   }
   
   private void resize(int n)
   {
       Item[] copy = (Item[]) new Object[n];
       for (int i = 0; i < N; i++)
           copy[i] = items[i];
       items = copy;
   }
       // add the item
   public void enqueue(Item item)  
   {
       if (item == null)
       {
           throw new java.lang.NullPointerException(); 
       }
       if (N == items.length) 
           resize(2 * items.length);
       items[N++] = item;
       
   }
        // remove and return a random item
   public Item dequeue() 
   {
       if (isEmpty())
       {
           throw new java.util.NoSuchElementException();
       }
       int n = StdRandom.uniform(N);
       Item item = items[n];
       
       items[n] = items[--N];
       items[N] = null;
       
       if (N > 0 && N == items.length / 4)
       {
           resize(items.length/2);
       }
       return item;
   }
       // return (but do not remove) a random item
   public Item sample()
   {
       if (isEmpty())
           throw new java.util.NoSuchElementException();
       return items[StdRandom.uniform(N)];
   }
       // return an independent iterator over items in random order
   public Iterator<Item> iterator()
   {
       return new ListIterator();
   }
   
   private class ListIterator implements Iterator<Item>
   {
       
       private int i = 0;
       private Item[] array;
       public ListIterator()
       {
           
           //StdRandom.shuffle(items, 0, N-1);
           array = (Item[]) new Object[N];
            for (int j = 0; j < N; j++)
                array[j] = items[j];
            for (int j = 0; j < N; j++) {
                int r = StdRandom.uniform(j+1);
                Item tmp = array[j];
                array[j] = array[r];
                array[r] = tmp;
            }
       }
       public boolean hasNext()
       {
           return i < N;
       }
       public void remove()
       {
           throw new java.lang.UnsupportedOperationException();

       }
       public Item next()
       {
           if (i >= N)
               throw new java.util.NoSuchElementException();
           //return items[i++];
           return array[i++];
       }
   }
       // unit testing
   public static void main(String[] args)  
   {
       RandomizedQueue<Integer> z = new RandomizedQueue<Integer>();
        z.enqueue(1);
        z.enqueue(4);
        z.enqueue(3);
        z.enqueue(6);
        z.enqueue(7);
        z.enqueue(8);
        z.enqueue(41);
        System.out.println(z.dequeue());
        System.out.println(z.dequeue());
//        for (int i : z) {
//           System.out.printf("outer i = %d\n", i);
//        }
//        for (int j : z) {
//           System.out.printf(" %d ", j);
//            }
       // System.out.printf("size of queue: %d\n", z.size());
        
       for (int i : z) {
           System.out.printf("outer i = %d\n", i);
            for (int j : z) {
                System.out.printf(" %d ", j);
            }
            System.out.println("\n");
        }
        }
   
}

   

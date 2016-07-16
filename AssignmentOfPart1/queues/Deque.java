import java.util.Iterator;
public class Deque<Item> implements Iterable<Item>
{
    private Node first;
    private Node last;
    private int N;
    private class Node
    {
        private Item item;
        private Node left;
        private Node right;
    }
    public Deque()
    {
        //construct an empty deque
        first = null;
        last = null;
        N = 0;
    }
    public boolean isEmpty()
    {
        return N == 0;
    }
    public int size()
    {
        return N;
    }
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new java.lang.NullPointerException("can't add a null item");
        }
        if (N == 0)
        {
            Node newNode = new Node();
            newNode.item = item;
            first = newNode;
            last = newNode;
            newNode.left = null;
            newNode.right = null;
            N++;
        }
        else
        {
            Node newNode = new Node();
            newNode.item = item;
            newNode.left = first;
            newNode.right = null;
            first.right = newNode;
            first = newNode;
            N++;
        }
        
        
        
    }
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new java.lang.NullPointerException("can't add a null item");
        }
        if (N == 0)
        {
            Node newNode = new Node();
            newNode.item = item;
            first = newNode;
            last = newNode;
            newNode.left = null;
            newNode.right = null;
            N++;
        }
        else
        {
            Node newNode = new Node();
            newNode.item = item;
            newNode.left = null;
            newNode.right = last;
            last.left = newNode;
            last = newNode;
            N++;
        }
        
    }
    public Item removeFirst()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque empty");
        if (N == 1)
        {
            Item item = first.item;
            first = null;
            last = null;
            N--;
            return item;
        }
        else
        {
            Item item = first.item;
            Node tmp = first.left;
            first.left.right = null;
            first.left = null;
            first = tmp;
            N--;
            return item;
        }
    }
    public Item removeLast()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque empty");
        
        if (N == 1)
        {
            Item item = last.item;
            first = null;
            last = null;
            N--;
            return item;
        }
        else
        {
            Item item = last.item;
            Node tmp = last.right;
            last.right.left = null;
            last.right = null;
            last = tmp;
            N--;
            return item;
        }
        
    }
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current;
        private Item item;
        public ListIterator()
        {
            current = first;
        }
        public boolean hasNext()
        {
            return current != null;
        }
        public Item next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException();  
            else
            {
                
                item = current.item;
                current = current.left;
                return item;
            }
        }
        public void remove()
        {
            throw new UnsupportedOperationException();  
        }
    }
    public static void main(String[] args)
    {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.addFirst(5);
        dq.addFirst(2);
        dq.addFirst(100);
        dq.addFirst(86);
        dq.addLast(46);
        dq.addLast(22);
        dq.addLast(6);
        
        
        System.out.printf("size of Deque: %d\n", dq.size());
        for (int i : dq) {
           System.out.printf("outer i = %d\n", i);
            for (int j : dq) {
                System.out.printf(" %d ", j);
            }
            System.out.println("\n");
        }
    }
}
import edu.princeton.cs.algs4.*;

public class SAP
{
   private final Digraph G;
   
    // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
       this.G = new Digraph(G);
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
       
       if (v > G.V() - 1 || v < 0 || w > G.V() - 1 || w < 0)
           throw new java.lang.IndexOutOfBoundsException();
       int length = Integer.MAX_VALUE;
       int tmp = 0;
       boolean flag = false;
       BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(this.G,v);
       BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(this.G,w);
       for (int i = 0; i < G.V(); i++)
       {
           if (bv.hasPathTo(i) && bw.hasPathTo(i))
           {
               flag = true;
               tmp = bv.distTo(i) + bw.distTo(i);
               if (tmp < length)
               {
                   length = tmp;
                   
               }
           }
           
       }
       if (!flag)
           return -1;
       else
           return length;
       
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
       
       if (v > G.V() - 1 || v < 0 || w > G.V() - 1 || w < 0)
           throw new java.lang.IndexOutOfBoundsException();
       int length = Integer.MAX_VALUE;
       int ancestor = -1;
       int tmp = 0;
       BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(this.G,v);
       BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(this.G,w);
       for (int i = 0; i < G.V(); i++)
       {
           if (bv.hasPathTo(i) && bw.hasPathTo(i))
           {
               tmp = bv.distTo(i) + bw.distTo(i);
               if (tmp < length)
               {
                   length = tmp;
                   ancestor = i;
                   
               }
           }
           
       }
       return ancestor;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       if (v == null || w == null)
           throw new java.lang.NullPointerException();
       for (int i : v)
       {
           if (i > G.V() - 1 || i < 0)
           {
               throw new java.lang.IndexOutOfBoundsException();
           }
       }
       for (int j : w)
       {
           if (j > G.V() - 1 || j < 0)
           {
               throw new java.lang.IndexOutOfBoundsException();
           }
       }
       int length = Integer.MAX_VALUE;
       int tmp = 0;
       boolean flag = false;
       BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(this.G,v);
       BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(this.G,w);
       for (int i = 0; i < G.V(); i++)
       {
           if (bv.hasPathTo(i) && bw.hasPathTo(i))
           {
               flag = true;
               tmp = bv.distTo(i) + bw.distTo(i);
               if (tmp < length)
               {
                   length = tmp;
                   
               }
           }
       }
       if (!flag)
           return -1;
       else
           return length;
       
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       if (v == null || w == null)
           throw new java.lang.NullPointerException();
       for (int i : v)
       {
           if (i > G.V() - 1 || i < 0)
           {
               throw new java.lang.IndexOutOfBoundsException();
           }
       }
       for (int j : w)
       {
           if (j > G.V() - 1 || j < 0)
           {
               throw new java.lang.IndexOutOfBoundsException();
           }
       }
       int length = Integer.MAX_VALUE;
       int ancestor = -1;
       int tmp = 0;
       BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(this.G,v);
       BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(this.G,w);
       for (int i = 0; i < G.V(); i++)
       {
           if (bv.hasPathTo(i) && bw.hasPathTo(i))
           {
               tmp = bv.distTo(i) + bw.distTo(i);
               if (tmp < length)
               {
                   length = tmp;
                   ancestor = i;
                   
               }
           }
           
       }
       return ancestor;
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       /*Stack<Integer> a = new Stack<Integer>();
       Stack<Integer> b = new Stack<Integer>();
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int k = StdIn.readInt();
           int m = StdIn.readInt();
           
           int v1 = StdIn.readInt();
           int v2 = StdIn.readInt();
           int v3 = StdIn.readInt();
           int v4 = StdIn.readInt();
           int v5 = StdIn.readInt();
           a.push(v);
           a.push(w);
           a.push(k);
           a.push(m);
           
           b.push(v1);
           b.push(v2);
           b.push(v3);
           b.push(v4);
           b.push(v5);
           //int length   = sap.length(v, w);
           //int ancestor = sap.ancestor(v, w);
           int length   = sap.length(a, b);
           int ancestor = sap.ancestor(a, b);
           a.pop();
           a.pop();
           a.pop();
           a.pop();
           
           b.pop();
           b.pop();
           b.pop();
           b.pop();
           b.pop();
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }*/
       
       
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int length   = sap.length(v, w);
           int ancestor = sap.ancestor(v, w);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
   }
}
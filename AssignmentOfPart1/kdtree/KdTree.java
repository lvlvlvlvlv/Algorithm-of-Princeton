import edu.princeton.cs.algs4.*;
import java.util.LinkedList;
public class KdTree 
{
    private Node root;
    private int N;
    
    private static class Node 
    {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;      // the left/bottom subtree
        private Node right;     // the right/top subtree
        
        public Node(Point2D p,RectHV rect)
        {
            this.p = p;
            if (rect == null)
                this.rect = new RectHV(0.0,0.0,1.0,1.0);
            else
                this.rect = rect;
        }
    }
    // construct an empty set of points 
   public KdTree()
   {
       this.root = null;
       this.N = 0;
   }          
    // is the set empty? 
   public boolean isEmpty()
   {
       return N == 0;
   }
   // number of points in the set 
   public int size()  
   {
       return N;
   }
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p)       
   {
      root = insert(root,p,null,true);
   }
   private Node insert(Node nd,Point2D p,RectHV rt,boolean vertical)
   {
       if (nd == null)
       {
           N++;
           return new Node(p,rt);
       }
       // this condition is easy to neglect.
       if (nd.p.equals(p))  return nd;
           
       if (vertical)
       {
           if (p.x() < nd.p.x())
           {
               rt = new RectHV(nd.rect.xmin(),nd.rect.ymin(),nd.p.x(),nd.rect.ymax());
               nd.left = insert(nd.left,p,rt,!vertical);
           }
           else if (p.x() >= nd.p.x())
           {
               rt = new RectHV(nd.p.x(),nd.rect.ymin(),nd.rect.xmax(),nd.rect.ymax());
               nd.right = insert(nd.right,p,rt,!vertical);
           }
       }
       else
       {
           if (p.y() < nd.p.y())
           {
               rt = new RectHV(nd.rect.xmin(),nd.rect.ymin(),nd.rect.xmax(),nd.p.y());
               nd.left = insert(nd.left,p,rt,!vertical);
           }
           else if (p.y() >= nd.p.y())
           {
               rt = new RectHV(nd.rect.xmin(),nd.p.y(),nd.rect.xmax(),nd.rect.ymax());
               nd.right = insert(nd.right,p,rt,!vertical);
           }
           
       }
       
       return nd;
           
           
   }
   // does the set contain point p? 
   public boolean contains(Point2D p) 
   {
       return contains(root,p,true);
   }
   private boolean contains(Node nd,Point2D p,boolean vertical)
   {
       boolean result = false;
       if (nd == null)
           return false;
       if (nd.p.equals(p))
           return true;
       if (vertical)
       {
           if (p.x() < nd.p.x())
           {
               result = contains(nd.left,p,!vertical);
               
           }
           else if (p.x() >= nd.p.x())
           {
               result = contains(nd.right,p,!vertical);
           }
       }
       else
       {
           if (p.y() < nd.p.y())
           {
               result = contains(nd.left,p,!vertical);
           }
           else if (p.y() >= nd.p.y())
           {
               result = contains(nd.right,p,!vertical);
           }
       }
       return result;
          
   }
   // draw all points to standard draw 
   public void draw()      
   {
       draw(root,true);
   }
   private void draw(Node nd,boolean vertical)
   {
       if (nd.left != null) 
           draw(nd.left,!vertical);
       if (nd.right != null) 
           draw(nd.right,!vertical);
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(.01);
       StdDraw.setScale(0.0,1.0);
       StdDraw.point(nd.p.x(),nd.p.y());
       
       if (vertical)
       {
           StdDraw.setPenColor(StdDraw.RED);
           StdDraw.setPenRadius(.001);
           StdDraw.line(nd.p.x(),nd.rect.ymin(),nd.p.x(),nd.rect.ymax());
       }
       else
       {
           StdDraw.setPenColor(StdDraw.BLUE);
           StdDraw.setPenRadius(.001);
           StdDraw.line(nd.rect.xmin(),nd.p.y(),nd.rect.xmax(),nd.p.y());
       }
       
   }
   // all points that are inside the rectangle 
   public Iterable<Point2D> range(RectHV rect)   
   {
        LinkedList<Point2D> lt = new LinkedList<Point2D>();
        range(root,rect,lt);
        return lt;
        
   }
   private void range(Node nd,RectHV rect,LinkedList<Point2D> lt)
   {
       if (nd == null)
           return;
       if (rect.contains(nd.p))
       {
           lt.add(nd.p);
       }
       if (nd.left != null && nd.left.rect.intersects(rect))
       {
           range(nd.left,rect,lt);
       }
       if (nd.right != null && nd.right.rect.intersects(rect))
       {
           range(nd.right,rect,lt);
       }
   }
   // a nearest neighbor in the set to point p; null if the set is empty 
   public Point2D nearest(Point2D p)   
   {
       if (isEmpty())
           return null;
       else
           
           return nearest(root,p,root.p,true);
       
   }
   private Point2D nearest(Node nd,Point2D p,Point2D fp,boolean vertical)
   {
       Point2D minp = fp;
       
       if (nd == null)
       {
           return minp;
       }
       
       if (p.distanceSquaredTo(minp) > p.distanceSquaredTo(nd.p))
       {
           minp = nd.p;
       }
       if (vertical)
       {
           if (p.x() < nd.p.x())
           {
               minp = nearest(nd.left,p,minp,!vertical);
               if (nd.right != null && p.distanceSquaredTo(minp) >= nd.right.rect.distanceSquaredTo(p))
               {
                   minp = nearest(nd.right,p,minp,!vertical);
               }
           }
           else
           {
               minp = nearest(nd.right,p,minp,!vertical);
               if (nd.left != null && p.distanceSquaredTo(minp) >= nd.left.rect.distanceSquaredTo(p))
               {
                   minp = nearest(nd.left,p,minp,!vertical);
               }
           }
       }
       else
       {
           if (p.y() < nd.p.y())
           {
               minp = nearest(nd.left,p,minp,!vertical);
               if (nd.right != null && p.distanceSquaredTo(minp) >= nd.right.rect.distanceSquaredTo(p))
               {
                   minp = nearest(nd.right,p,minp,!vertical);
               }
           }
           else
           {
               minp = nearest(nd.right,p,minp,!vertical);
               if (nd.left != null && p.distanceSquaredTo(minp) >= nd.left.rect.distanceSquaredTo(p))
               {
                   minp = nearest(nd.left,p,minp,!vertical);
               }
           }
       }
       
       return minp;
          
   }
       

   // unit testing of the methods (optional) 
   public static void main(String[] args)   
   {
       KdTree kt = new KdTree();
       StdOut.println(kt.isEmpty());
       StdOut.println(kt.size());
       kt.insert(new Point2D(0.3,0.5));
       kt.insert(new Point2D(0.4,0.6));
       kt.insert(new Point2D(0.7,0.8));
       kt.insert(new Point2D(0.7,0.8));
       StdOut.println(kt.size());
       StdOut.println(kt.contains(new Point2D(0.3,0.4)));   
       kt.draw();
       StdOut.println(kt.range(new RectHV(0.5,0.5,1,1)));
       Point2D q = new Point2D(0.3,0.4);
       Point2D ntP = kt.nearest(q);
       StdOut.println(ntP.x() + " " + ntP.y());
       StdDraw.setPenColor(StdDraw.GREEN);
       StdDraw.setPenRadius(.01);
       StdDraw.point(ntP.x(),ntP.y());
       
       StdDraw.setPenColor(StdDraw.RED);
       StdDraw.point(q.x(),q.y());
   }
}
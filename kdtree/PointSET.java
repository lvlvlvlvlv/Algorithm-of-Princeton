import java.util.TreeSet;
import edu.princeton.cs.algs4.*;
import java.util.LinkedList;


public class PointSET
{
    
    private TreeSet<Point2D> pointSet;
    public PointSET()
    {
        
        pointSet = new TreeSet<Point2D>();
        
    }
    public boolean isEmpty()
    {
        return pointSet.isEmpty();
    }
    public int size()
    {
        return pointSet.size();
    }
    public void insert(Point2D p)
    {
        pointSet.add(p);
    }
    public boolean contains(Point2D p)
    {
        return pointSet.contains(p);
    }
    public void draw()
    {
        StdDraw.setScale(0.0,1.0);
        for (Point2D point : pointSet)
        {
            StdDraw.point(point.x(),point.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect)
    {
        LinkedList<Point2D> rg = new LinkedList<Point2D>();
        for (Point2D point : pointSet)
        {
            if (rect.contains(point))
            {
                rg.add(point);
            }
                
        }
        return rg;
        
    }
    public Point2D nearest(Point2D p)
    {
        if (isEmpty())
            return null;
        Point2D pt = null;
        double temp,minDis = Double.MAX_VALUE;
        
        
        for (Point2D point : pointSet)
        {
            temp = p.distanceSquaredTo(point);
            if (temp < minDis)
            {
                minDis = temp;
                pt = point;
            }
        }
        return pt;
    }
    
    
    public static void main(String[] args)
    {
        StdDraw.setPenColor(StdDraw.RED);
        PointSET pset = new PointSET();
        StdOut.println("" + pset.size());
        pset.insert(new Point2D(0.3,0.5));
        StdOut.println(pset.isEmpty());
        pset.insert(new Point2D(0.1,0.2));
        pset.insert(new Point2D(0.8,0.4));
        pset.insert(new Point2D(0.6,0.6));
        StdOut.println(pset.isEmpty());
        pset.draw();
        StdOut.println("" + pset.nearest(new Point2D(0.8,0.5)).x() + "->" + pset.nearest(new Point2D(0.8,0.5)).y());
    }
}
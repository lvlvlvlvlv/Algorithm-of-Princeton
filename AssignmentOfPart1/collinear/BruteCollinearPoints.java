import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.*;
public class BruteCollinearPoints
{
    private ArrayList<LineSegment> ls;
    private int N;
    
    public BruteCollinearPoints(Point[] points)
    {
        
        if (points == null) 
        {
            throw new java.lang.NullPointerException();
        }
        for (int i = 0; i < N; i++)
        {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < N; j++)
            {
                if (points[j] == null)
                    throw new java.lang.NullPointerException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
            
        }
        Arrays.sort(points);
        ls = new ArrayList<LineSegment>();
        N = 0;
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i + 1; j < points.length; j++)
            {
                for (int k = j + 1; k < points.length; k++)
                {
                    for (int h = k + 1; h < points.length; h++)
                    {
                        if (points[i].slopeOrder().compare(points[j],points[k]) == 0 && points[i].slopeOrder().compare(points[j],points[h]) == 0)
                        {
                            
                            ls.add(new LineSegment(points[i],points[h]));
                            N++;
                        }
                        
                        
                    }
                    
                }
            }
        }
    }
    public int numberOfSegment()
    {
        return N;
    }
    public LineSegment[] segments()
    {
        LineSegment[] LS = new LineSegment[ls.size()];
        ls.toArray(LS);
        return LS;
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++)
        {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x,y);
            
        }
        
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment ls : collinear.segments())
        {
            StdOut.println(ls);
            ls.draw();
        }
    }
    
}

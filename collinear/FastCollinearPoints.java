import edu.princeton.cs.algs4.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints
{
    private ArrayList<LineSegment> ls;
    public FastCollinearPoints(Point[] points)
    {
        if (points == null)
            throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length; i++)
        {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++)
            {
                if (points[j] == null)
                    throw new java.lang.NullPointerException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        
        }
        ls = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++)
        {
            //Arrays.sort(points);
            Arrays.sort(points,points[i].slopeOrder());
            for (int p = 0, first = 1, end = 2; end < points.length; end++)
            {
                while (end < points.length && Double.compare(points[p].slopeTo(points[first]),points[first].slopeTo(points[end])) == 0)
                {
                    end++;
                }
                if (end - first >= 3 && points[p].compareTo(points[first]) < 0)
                {
                    ls.add(new LineSegment(points[p],points[end - 1]));
                }
                
                first = end;
                
                
            }
                
            
        }
                
    }
    public int numberOfSegments()
    {
        return ls.size();
        
    }
    public LineSegment[] segments()
    {
        LineSegment[] LS = new LineSegment[ls.size()];
        ls.toArray(LS);
        return LS;
        
    }
    public static void main(String args[]) 
    {
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) 
        {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) 
        {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment ls : collinear.segments()) 
        {
            StdOut.println(ls);
            ls.draw();
        }
    }
}
    


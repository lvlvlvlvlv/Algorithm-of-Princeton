import java.lang.Math;
import java.util.Iterator;
import edu.princeton.cs.algs4.*;


public class SeamCarver
{
    private Picture picture;
   
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        if (picture == null)
            throw new java.lang.NullPointerException();
        this.picture = picture;
    }
    // current picture
    public Picture picture()
    {
        return this.picture;
    }
    // width of current picture
    public int width()
    {
        
        return this.picture.width();
    }
    // height of current picture
    public int height()
    {
        
        return this.picture.height();
    }
    // energy of pixel at column x and row y
    public double energy(int x,int y)
    {
        int W = width();
        int H = height();
        if ((x < 0 || x > W - 1) || (y < 0 || y > H - 1))
            throw new java.lang.IndexOutOfBoundsException();
        if ((x == 0 || x == W - 1) || (y == 0 || y == H - 1)) 
            return 1000.0;
        
        double deltaX2 = 0.0;
        double deltaY2 = 0.0;
        double energy = 0.0;
        int rX = this.picture.get(x + 1,y).getRed() - this.picture.get(x - 1,y).getRed();
        int gX = this.picture.get(x + 1,y).getGreen() - this.picture.get(x - 1,y).getGreen();
        int bX = this.picture.get(x + 1,y).getBlue() - this.picture.get(x - 1,y).getBlue();
        
        int rY = this.picture.get(x,y + 1).getRed() - this.picture.get(x,y - 1).getRed();
        int gY = this.picture.get(x,y + 1).getGreen() - this.picture.get(x,y - 1).getGreen();
        int bY = this.picture.get(x,y + 1).getBlue() - this.picture.get(x,y - 1).getBlue();
        
        deltaX2 = rX * rX + gX * gX + bX * bX;
        deltaY2 = rY * rY + gY * gY + bY * bY;
        
        energy = Math.sqrt(deltaX2 + deltaY2);
        return energy;
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()
    {
         
        this.picture = transpose();
        int[] a = findVerticalSeam();
        int[] b = new int[a.length];
        for (int m = 0; m < a.length; m++)
        {
            b[m] = a[a.length - 1 - m];
        }
        this.picture = transposeBack();
        return b;
    }
    private Picture transpose()
    {
        int H = height();
        int W = width();
        Picture t = new Picture(H,W);
        for (int i = 0; i < H; i++)
        {
            for (int j = 0; j < W; j++)
            {
                t.set(i,j,this.picture.get(W-1-j,i));
            }
        }
        
        return t;
    }
    private Picture transposeBack()
    {
        int H = height();
        int W = width();
        Picture t = new Picture(H,W);
        for (int i = 0; i < H; i++)
        {
            for (int j = 0; j < W; j++)
            {
                t.set(i,j,this.picture.get(j,H-1-i));
            }
        }
        return t;
    }
    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {
        int H = height();
        int W = width();
        int V = H * W;
        
        int[] a = new int[H];
        double minValue = Double.MAX_VALUE;
        int minIndex = -1;
        double[][] energy = new double[H][W];
        for (int i = 0; i < H; i++)
        {
            for (int j = 0; j < W; j++)
            {
                energy[i][j] = energy(j,i);
            }
        }
        double[]   distTo = new double[V];
        int[]      pointTo = new int[V];
        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(V);
        for (int v = 0; v < V; v++)
        {
            distTo[v] = Double.POSITIVE_INFINITY;
            
        }
        for (int i = 0; i < W; i++)
        {
            pointTo[i] = i;
            distTo[i] = 0.0;
            pq.insert(i,1000.0);
        }
        
        
        
        for (int v = 0; v < V; v++)
        {
            for (int k : adj(v))
            {
                int x = k % W;
                int y = k / W;
                if (distTo[k] > distTo[v] + energy[y][x])
                {
                    distTo[k] = distTo[v] + energy[y][x];
                    pointTo[k] = v;
                    if (pq.contains(k)) 
                        pq.decreaseKey(k,distTo[k]);
                    else
                        pq.insert(k,distTo[k]);
                }
            }
                
            
            
        }
        for (int v = V - W; v < V; v++)
        {
            double tmp = pq.keyOf(v);
            if (tmp < minValue)
            {
                minValue = tmp;
                minIndex = v;
            }
            
        }
        
        for (int i = 0; i < H; i++)
        {
            a[H-i-1] = (minIndex % W);
            minIndex = pointTo[minIndex];
        }
        
        return a;
        
    }
    private Iterable<Integer> adj(int v)
    {
        int H = height();
        int W = width();
        int V = H * W;
        Queue<Integer> q = new Queue<Integer>();
        if (v >= V - W)
        {
            return new Queue<Integer>();
        }
        
        if (W > 1)
        {
            if (v % W == 0)
            {
                q.enqueue(v + W    );
                q.enqueue(v + W + 1);
            }
            
            else if ((v + 1) % W == 0)
            {
                q.enqueue(v + W);
                q.enqueue(v + W - 1);
            }
            
            else
            {
                q.enqueue(v + W - 1);
                q.enqueue(v + W    );
                q.enqueue(v + W + 1);
            }
        }
        else
        {
            q.enqueue(v + W    );
        }
         
        
        
        return q;
    }
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        int H = height();
        int W = width();
        if (seam == null)
            throw new java.lang.NullPointerException();
        if (seam.length != W)
            throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < seam.length; i++)
        {
            if (seam[i] < 0 || seam[i] > H - 1)      
                throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++)
        {
            if (Math.abs(seam[i] - seam[i+1]) > 1)          
                throw new java.lang.IllegalArgumentException(); 
        }
                     
        
        if (H <= 1)
            throw new java.lang.IllegalArgumentException();
        
        int[] seamB = new int[seam.length];
        for (int m = 0; m < seam.length; m++)
        {
            seamB[m] = seam[seam.length - 1 - m];
        }
        this.picture = transpose();
        removeVerticalSeam(seamB);
        this.picture = transposeBack();
        
    }
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)   
    {
        int H = height();
        int W = width();
        if (seam == null)
            throw new java.lang.NullPointerException();
        if (seam.length != H)
            throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < seam.length; i++)
        {
            if (seam[i] < 0 || seam[i] > W - 1)
                throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++)
        {
            if (Math.abs(seam[i] - seam[i+1]) > 1)
                throw new java.lang.IllegalArgumentException();
        }
            

        if (W <= 1)
            throw new java.lang.IllegalArgumentException();
        
        Picture nPicture = new Picture(W - 1,H);
        for (int i = 0; i < H; i++)
        {
            for (int j = 0; j < W - 1; j++)
            {
                if (j < seam[i])
                {
                    nPicture.set(j,i,this.picture.get(j,i));
                }
                else
                {
                    nPicture.set(j,i,this.picture.get(j+1,i));
                }
            }
        }
        
        this.picture = nPicture;
        
    }
    
    public static void main(String[] args)
    {
        Picture picture = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(picture);
        //StdOut.println(sc.adj(1));
       
       /* for (int i : sc.findVerticalSeam())    
            StdOut.println(i);*/
        
        //sc.transposeBack().show();
        int[] seams = { -1 };
        sc.removeHorizontalSeam(seams);
    }
    
    
}
import edu.princeton.cs.algs4.*;
import java.util.LinkedList;
public class Board
{
    private final int[][] blocks;
    private final int N;
    private int blank_i,blank_j;
    public Board(int[][] blocks)
    {
        N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (blocks[i][j] == 0)
                {
                    blank_i = i;
                    blank_j = j;
                }
                this.blocks[i][j] = blocks[i][j];
            }
        }
            
            
    }
    public int dimension()
    {
        return this.N;
    }
    public int hamming()
    {
        int count = 0;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (this.blocks[i][j] == 0)
                    continue;
                if (this.blocks[i][j] != (N * i + j + 1))
                {
                    count++;
                }
            }
        }
        
        return count;
    }
    public int manhattan()
    {
        int distance = 0;
        int inix;
        int iniy;
        int tmp;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                tmp = this.blocks[i][j];
                if (tmp == 0)
                    continue;
                if (tmp != (N * i + j + 1))
                {
                    inix = (tmp - 1) / N;
                    iniy = (tmp - 1) % N;
                    distance = distance + Math.abs(i - inix) + Math.abs(j - iniy);
                    
                }
            }
        }
        return distance;
    }
    public boolean isGoal()
    {
        boolean flag = true;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (this.blocks[i][j] == 0)
                {
                    continue;
                }
                if (this.blocks[i][j] != i * N + (j + 1))
                {
                    
                    flag = false;
                    break;
                }
            }
        }
        return flag;
            
    }
    public Board twin()
    {
        int[][] twinBlocks = new int[N][N];
        int tmp;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                twinBlocks[i][j] = this.blocks[i][j];
            }
        }
        if (twinBlocks[0][0] != 0 && twinBlocks[0][1] != 0)
        {
            tmp = twinBlocks[0][0];
            twinBlocks[0][0] = twinBlocks[0][1];
            twinBlocks[0][1] = tmp;    
                
        }
        else
        {
            tmp = twinBlocks[1][0];
            twinBlocks[1][0] = twinBlocks[1][1];
            twinBlocks[1][1] = tmp;
        }
            
            
        return new Board(twinBlocks);
        
    }
    public boolean equals(Object y)
    {
        if (y == this) return true;
        
        if (y == null) return false;
        
        if (y.getClass() != this.getClass())
            return false;
        
        Board thatBoard = (Board)y;
        if (this.N != thatBoard.N)
            return false;
        
        int[][]  thatBlocks = thatBoard.blocks;
        for (int i = 0; i < N; i++){  
            for (int j = 0; j < N; j++){  
                if (blocks[i][j] != thatBlocks[i][j]){  
                    return false;  
                }  
            }  
        }  
        return true;  

        
    }
    public Iterable<Board> neighbors()
    {
        int[][] temp = Copy();
        
        LinkedList<Board> mpq = new LinkedList<Board>();
        
        if (blank_i - 1 >= 0)
        {
            
            temp[blank_i][blank_j] = temp[blank_i - 1][blank_j];
            temp[blank_i - 1][blank_j] = 0;
            mpq.add(new Board(temp));
            
            
            temp[blank_i - 1][blank_j] = temp[blank_i][blank_j];
            temp[blank_i][blank_j] = 0;
            
        }
        if (blank_i + 1 <= N - 1)
        {
            
            temp[blank_i][blank_j] = temp[blank_i + 1][blank_j];
            temp[blank_i + 1][blank_j] = 0;
            mpq.add(new Board(temp));
            
            temp[blank_i + 1][blank_j] = temp[blank_i][blank_j];
            temp[blank_i][blank_j] = 0;
        }
        if (blank_j - 1 >= 0)
        {
            
            temp[blank_i][blank_j] = temp[blank_i][blank_j - 1];
            temp[blank_i][blank_j - 1] = 0;
            mpq.add(new Board(temp));
            
            temp[blank_i][blank_j - 1] = temp[blank_i][blank_j];
            temp[blank_i][blank_j] = 0;
            
        }
        if (blank_j + 1 <= N - 1)
        {
            
            temp[blank_i][blank_j] = temp[blank_i][blank_j + 1];
            temp[blank_i][blank_j + 1] = 0;
            mpq.add(new Board(temp));
            
            temp[blank_i][blank_j + 1] = temp[blank_i][blank_j];
            temp[blank_i][blank_j] = 0;
        }
       return mpq;
    }
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < N; j++) 
            {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    private int[][] Copy(){  
        int[][] result = new int[N][N];  
        for (int i = 0; i < N; i++){  
            for (int j = 0; j < N; j++)
            {
                result[i][j] = blocks[i][j];  
            }
            
        }  
        return result;  
    } 
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);  
        int N = in.readInt();  
        int[][] blocks = new int[N][N];  
        for (int i = 0; i < N; i++)  
            for (int j = 0; j < N; j++)  
                blocks[i][j] = in.readInt();  
        Board initial = new Board(blocks);  
        StdOut.println(initial.isGoal());  
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.twin().toString());
        for (Board board : initial.neighbors())
        {
            StdOut.println(board.toString());
        }
    }
}
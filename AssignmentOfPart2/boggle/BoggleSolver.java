import edu.princeton.cs.algs4.*;
//import java.util.List;
//import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
public class BoggleSolver
{
    private TrieST<Integer> dic;
    private boolean marked[][];
//    private static final int R = 26;
//    private static class Node {
//        private Object val;
//        private Node[] next = new Node[R];
//    }
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
       
       int index = 0;
       dic = new TrieST<Integer>();
       for (int i = 0; i < dictionary.length; i++)
       {
           dic.put(dictionary[i],index);
           index++;       
       }
    }
    
    

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        Set<String> set = new TreeSet<String>();
        int rows = board.rows();
        int columns = board.cols();
        marked = new boolean[rows][columns];
        
        int[][] boardInt = new int[rows][columns];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                boardInt[i][j] = board.getLetter(i,j) - "A".charAt(0);
                
            }
        }
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                TrieST.Node topTempNode = this.dic.root.next[boardInt[i][j]];
                if (!marked[i][j] && topTempNode != null)
                    dfs(i,j,"",boardInt,set,topTempNode);
            }
        }
//        String s = "";
//        marked = new boolean[rows][columns];
//        dfs(3,3,s,board,l,this.dic.root);
        
        return set;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
        if (this.dic.contains(word))
        {
            int wordLong = word.length();
            if (wordLong >= 3 && wordLong <= 4)
            {
                return 1;
            }
            else if (wordLong == 5)
            {
                return 2;
            }
            else if (wordLong == 6)
            {
                return 3;
            }
            else if (wordLong == 7)
            {
                return 5;
            }
            else if (wordLong >= 8)
            {
                return 11;
            }
            else
            {
                return 0;
            }
        }
        
        return 0;
    }
    
    private void dfs(int x, int y, String s, int[][] boardInt, Set<String> set, TrieST.Node nd)
    {
        int letterInt = boardInt[x][y];
        if (letterInt == 16)
            nd = nd.next[20];
        if (nd == null) return;
        StringBuilder sb = new StringBuilder(s);
        sb.append((char)(65 + letterInt));
        if (letterInt == 16) sb.append((char)85);
        marked[x][y] = true;
//        marked[x][y] = true;
//        if (nd == null) return;
//        StringBuilder sb = new StringBuilder(s);
//        
//        if (boardInt[x][y] == 16)
//        {
//            sb.append("QU");
//            nd = nd.next[20];
//        }
//        else
//        {
//            sb.append((char)(65+boardInt[x][y]));
//        }
        if (nd.val != null && sb.length() > 2)
            set.add(sb.toString());
        
        for (int[] a : adj(x,y,boardInt))
        {
            TrieST.Node tempNode = nd.next[boardInt[a[0]][a[1]]];
            if (!marked[a[0]][a[1]] && tempNode != null)
                dfs(a[0],a[1],sb.toString(),boardInt,set,tempNode);
        }
        
        
        marked[x][y] = false;
        sb = sb.deleteCharAt(sb.length()-1);
        if (sb.length() > 0 && sb.charAt(sb.length()-1) == "Q".charAt(0)) 
            sb.deleteCharAt(sb.length()-1);
        return;
    }
    private Iterable<int[]> adj(int x, int y, int[][] boardInt)
    {
        Queue<int[]> q = new Queue<int[]>();
        boolean a = false,b = false,c = false,d = false;
        int R = boardInt.length;
        int C = boardInt[0].length;
        if (x < 0 || x > R - 1 || y < 0 || y > C - 1)
            throw new java.lang.IndexOutOfBoundsException();
        if (x - 1 >= 0){ a = true; q.enqueue(new int[]{x-1,y}); }
        
        if (x + 1 <= R - 1){ b = true; q.enqueue(new int[]{x+1,y});}
  
        if (y - 1 >= 0){ c = true; q.enqueue(new int[]{x,y-1});}

        if (y + 1 <= C - 1){ d = true; q.enqueue(new int[]{x,y+1});}


        
        if (a && c) { q.enqueue(new int[]{x-1,y-1});}

        if (c && b) { q.enqueue(new int[]{x+1,y-1});}

        if (a && d) { q.enqueue(new int[]{x-1,y+1});}

        if (d && b) { q.enqueue(new int[]{x+1,y+1});}

//        StdOut.print(q.size());
//        StdOut.print(a);
//        StdOut.print(b);
//        StdOut.print(c);
//        StdOut.print(d);
        return q;
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        
//        for (int i = 0; i < 100; i++)
//        {
//            BoggleBoard board = new BoggleBoard();
//            int score = 0;
//            try
//            {
//                for (String word : solver.getAllValidWords(board))
//                {
//                    //StdOut.println(word);
//                    score += solver.scoreOf(word);
//                }
//                StdOut.println("Score = " + score);
//            }
//            catch(NullPointerException e)
//            {
//                StdOut.println(e);
//                StdOut.println(board);
//            }
//            
//        }
        
       
        
    }
}
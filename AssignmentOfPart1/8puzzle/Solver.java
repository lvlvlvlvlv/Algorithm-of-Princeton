import edu.princeton.cs.algs4.*;
import java.util.Comparator;

public class Solver
{
    private boolean isSolvable = false;
    private int moves = -1;
    private class SearchNode implements Comparable<SearchNode>
    {
        private final Board board;
        private final int moves;
        private final int priority;
        private final SearchNode previousNode;
        private final boolean isTwin;
        
        public SearchNode(Board board,int moves,SearchNode previousNode,boolean isTwin)
        {
            this.board = board;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
            this.previousNode = previousNode;
            this.isTwin = isTwin;
            
        }
        public int compareTo(SearchNode that)
        {
            if (this.priority == that.priority)
                return 0;
            if (this.priority < that.priority)
                return -1;
            else return 1;
          
                
        }
    }
        
        private MinPQ<SearchNode> mpq = new MinPQ<SearchNode>();
        private Stack<Board> solutionQueue = new Stack<Board>();
        
        public Solver(Board initial)
        {
            Board initialTwin = initial.twin();
            SearchNode initialTwinSearchNode = new SearchNode(initialTwin,0,null,true);
            SearchNode initialSearchNode = new SearchNode(initial,0,null,false);
            mpq.insert(initialSearchNode);
            mpq.insert(initialTwinSearchNode);
            
            while(!mpq.isEmpty())
            {
                SearchNode searchNode = mpq.delMin();
                if (searchNode.board.isGoal())
                {
                    if (searchNode.isTwin)
                    {
                        this.isSolvable = false;
                        this.moves = -1;
                    }
                    else
                    {
                        this.isSolvable = true;
                        this.moves = searchNode.moves;
                        this.solutionQueue.push(searchNode.board);
                        while(searchNode.previousNode != null)
                        {
                            searchNode = searchNode.previousNode;
                            this.solutionQueue.push(searchNode.board);
                        }
                    }
                    
                
                break;
                }
                else
                {
                
                    for (Board neighborBoard : searchNode.board.neighbors())
                    {
                        SearchNode neighborNode = new SearchNode(neighborBoard,searchNode.moves + 1,searchNode,searchNode.isTwin);
//                        if ((searchNode.previousNode == null) || (!searchNode.previousNode.board.equals(neighborNode.board)))
//                        {
//                            mpq.insert(neighborNode);
//                        }
//                        else
//                        {
//                            continue;
//                        }
                        if (searchNode.previousNode == null)
                        {
                            mpq.insert(neighborNode);
                        }
                        else if (!searchNode.previousNode.board.equals(neighborNode.board))
                        {
                            mpq.insert(neighborNode);
                        }
                        
                    }
                }
                
            }
        }
        
        public boolean isSolvable()
        {
            return this.isSolvable;
        }
        
        public int moves()
        {
            return this.moves;
            
        }
        
        public Iterable<Board> solution()
        {
            if (this.isSolvable)
                return this.solutionQueue;
            else 
                return null;
        }
        
        public static void main(String[] args)
        {
            // create initial board from file
            In in = new In(args[0]);
            int N = in.readInt();
            int[][] blocks = new int[N][N];
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    blocks[i][j] = in.readInt();
                }
            }
                
            Board initial = new Board(blocks);
            
            // solve the puzzle
            Solver solver = new Solver(initial);
            
            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
        }
        
        
    }
    
    


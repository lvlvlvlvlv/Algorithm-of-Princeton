import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.Stopwatch;
public class Percolation{
    private WeightedQuickUnionUF grid = null;
    private WeightedQuickUnionUF auxGrid = null;
    private boolean[] state = null;
    private int N;
    
    public Percolation(int N){
        this.N = N;
        if(this.N > 0){
            int size = this.N * this.N ;
            grid = new WeightedQuickUnionUF(size+2);
            auxGrid = new WeightedQuickUnionUF(size+1);
            state = new boolean[size+2];
            for (int index = 0;index < size;index ++){
                state[index] = false;
            }
            state[0] = true;
            state[size+1] = true;
         }
        else{
            throw new IllegalArgumentException();
        }
        
    }
    
   
    
    private int xyTo1D(int i,int j){
        if(i <= 0 || i > N){
            throw new IllegalArgumentException("Out of Bounds");
        }
        if(j <= 0 || j > N){
            throw new IllegalArgumentException("Out of Bounds");
        }
        return (i-1)*N+j;
    }
    
   
    public void open(int i,int j){
        int lable = xyTo1D(i,j);
        state[lable] = true;
        
        
        if(j!= 1&& isOpen(i,j-1)){
            grid.union(lable,xyTo1D(i,j-1));
            auxGrid.union(lable,xyTo1D(i,j-1));
         }
        if(j!= N && isOpen(i,j+1)){
                grid.union(lable,xyTo1D(i,j+1));
                auxGrid.union(lable,xyTo1D(i,j+1));
            }
            if(i!= 1 && isOpen(i-1,j)){
                grid.union(lable,xyTo1D(i-1,j));
                auxGrid.union(lable,xyTo1D(i-1,j));
            }
            if(i!= N && isOpen(i+1,j)){
                grid.union(lable,xyTo1D(i+1,j));
                auxGrid.union(lable,xyTo1D(i+1,j));
            }
            if(lable <= N){
                grid.union(0,lable);
                auxGrid.union(0,lable);
            }
            if(lable >= (N-1)*N+1){
                grid.union(state.length-1,lable);
            }
       
        
    }
    public boolean isOpen(int i,int j){
        int lable = xyTo1D(i,j);
        return state[lable] == true;
        
        
    }
    public boolean isFull(int i,int j){
        int lable = xyTo1D(i,j);
        return grid.connected(0,lable) && auxGrid.connected(0,lable);
        
    }
    public boolean percolates(){
        return grid.connected(0,state.length-1);
    }
    
    public static void main(String[] args){
       Stopwatch sp = new Stopwatch();
       int N = Integer.parseInt(args[0]);
       Percolation ex = new Percolation(N);
       do{
           int i = StdRandom.uniform(1,N+1);
           int j = StdRandom.uniform(1,N+1);
           if(ex.isOpen(i,j)){
                    continue;
                }
                else{
                    ex.open(i,j);
                    
                }
       }while(!ex.percolates());
       System.out.println("time:"+sp.elapsedTime()+"s");
        
    }
}
    
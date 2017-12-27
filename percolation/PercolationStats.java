import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] threshold; 
    public PercolationStats(int N,int T){
        if( N <= 0 || T <= 0 ){
            throw new IllegalArgumentException();
        }
        threshold = new double[T];
        for(int index=0;index<T;index++){
            int count = 0;
            Percolation per = new Percolation(N);
            do{
                int row = StdRandom.uniform(1,N+1);
                int column = StdRandom.uniform(1,N+1);
                if(per.isOpen(row,column)){
                    continue;
                }
                else{
                    per.open(row,column);
                    count ++;
                }
                  
            }
            while(!per.percolates());
            threshold[index] = count*1.0/(N*N);
        }
    }
    public double mean(){
        return StdStats.mean(this.threshold);
    }
    public double stddev(){
        return StdStats.stddev(this.threshold);
    }
    public double confidenceLo(){
        return mean()-1.96*stddev()/Math.sqrt(this.threshold.length);
    }
    public double confidenceHi(){
        return mean()+1.96*stddev()/Math.sqrt(this.threshold.length);
    }
    
    public static void main(String[] args){
        Stopwatch sp = new Stopwatch();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats perstat = new PercolationStats(N,T);
        System.out.println("mean:"+perstat.mean());
        System.out.println("stddev:"+perstat.stddev());
        System.out.println("confidence interval:"+perstat.confidenceLo()+","+perstat.confidenceHi());
        System.out.println("time:"+sp.elapsedTime()+"s");
        
        
    }
}
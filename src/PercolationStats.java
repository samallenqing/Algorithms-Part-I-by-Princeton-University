package week1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    /// holds each experiment's percolation threshold result
    private double[] thresholdResults;
    private int t;
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int m) 
    {
        /// The constructor should throw a java.lang.IllegalArgumentException if either N <= 0 or T >= 0.
        if (n < 1 || m < 1)
        {
            throw new IllegalArgumentException("both arguments n and m must be greater than 1");
        }
        
        t = m;
        thresholdResults = new double[t];
        for (int k = 0; k < t; k++)
        {
             Percolation percolation = new Percolation(n);
             double openSites = 0;
             while (!percolation.percolates())
             {
                 int i = StdRandom.uniform(1, n+1);
                 int j = StdRandom.uniform(1, n+1);
            
                 if (!percolation.isOpen(i, j))
                 {
                     percolation.open(i, j);
                     openSites += 1;
                 }
             }
             double threshold = openSites/(n*n);
            thresholdResults[k] = threshold;
        }
    }
    // sample mean of percolation threshold
    public double mean() 
    {
        return StdStats.mean(thresholdResults);
    }
    // sample standard deviation of percolation threshold
    public double stddev()  
    {
        return StdStats.stddev(thresholdResults);
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo()  
    {
        return mean() - (1.96*stddev()/Math.sqrt(t));
    }
    // returns upper bound of the 95% confidence interval
    public double confidenceHi()             
        {
        return mean() + (1.96*stddev()/Math.sqrt(t));
    }
    /*
     * Also, include a main() method that takes two command-line arguments N and T,
     * performs T independent computational experiments (discussed above)
     * on an N-by-N grid, and prints out the mean, standard deviation, 
     * and the 95% confidence interval for the percolation threshold.
     */
    
    public static void main(String[] args)
    {
//        int n = Integer.parseInt(args[0]);
//        int t = Integer.parseInt(args[1]);
     int n = 2;
     int t = 1000000;
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean = "+ stats.mean());
        StdOut.println("standard deviation = "+ stats.stddev());
        StdOut.println("95% confidence interval = "+ stats.confidenceLo() + " , " + stats.confidenceHi());
    }
        
        
      
 }
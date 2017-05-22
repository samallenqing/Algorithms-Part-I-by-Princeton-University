 package week1;
 import edu.duke.*;
 import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
 private boolean[] opened;
    private int bottom;
    private int size, count, top;
    private WeightedQuickUnionUF qf, pf;
    
    /**
     * Creates N-by-N grid, with all sites blocked.
     */
    public Percolation(int n) {
     if (n < 1) {
      throw new IllegalArgumentException("Given n <= 0");
     }
        size = n;
        bottom = n * n + 1;
        qf = new WeightedQuickUnionUF(n*n + 2);
        pf = new WeightedQuickUnionUF(n*n + 2);
        opened = new boolean[n*n + 2];
        count = 0;
        top = 0;
        opened[0] = true;
        opened[n*n + 1] = true;
    }
    
    /**
     * Opens site (row i, column j) if it is not already.
     */
    public void open(int i, int j) {
     if (i < 1 || i > size || j < 1 || j > size) {
      throw new IndexOutOfBoundsException();
     }
     if (isOpen(i, j)) {
      return;
     }
     count++;
     int tag = size * (i - 1) + j;
        opened[size*(i-1) + j] = true;
        if (i == 1) {
            qf.union(tag, top);
            pf.union(tag, top);
        }
        if (i == size) {
            qf.union(tag, bottom);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            qf.union(tag, tag - 1);
            pf.union(tag, tag - 1);
        }
        if (j < size && isOpen(i, j + 1)) {
            qf.union(tag, tag + 1);
            pf.union(tag, tag + 1);
        }
        if (i > 1 && isOpen(i - 1, j)) {
            qf.union(tag, tag - size);
            pf.union(tag, tag - size);
        }
        if (i < size && isOpen(i + 1, j)) {
            qf.union(tag, tag + size);
            pf.union(tag, tag + size);
        }
    }

    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
     if (i < 1 || i > size || j < 1 || j > size) {
           throw new IndexOutOfBoundsException();
          }
        return opened[size*(i-1) + j];
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
        throw new IndexOutOfBoundsException();
        } 
        return pf.connected(top, size * (i - 1) + j) && isOpen(i, j);
        
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return qf.connected(top, bottom);
    }
    
    public int numberOfOpenSites() {
     return count;
    }

    public static void  main(String[] args)
    {
       FileResource fr = new FileResource("/Users/qinqing/Desktop/percolation/input10.txt");
            String s = fr.asString();
        String[] a = s.split("\\W+");
        int[] k = new int[a.length]; 
        for(int i = 0; i< a.length; i ++){
         k[i]= Integer.parseInt(a[i]);
        }
        Percolation percolation = new Percolation((k[0]));
        for(int i=1; i < k.length-1 ; i+= 2){   
         System.out.println("The block will be opend is: "+k[i] + " " +k[i+1]);
            StdOut.println(percolation.percolates());
            if(percolation.percolates()){
             break;
            }
            percolation.open((k[i]),(k[i+1]));  
            }
       }
}
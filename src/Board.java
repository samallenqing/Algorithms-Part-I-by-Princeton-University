import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    
    private final int n;
    private final int[][] data;
    
    public Board(int[][] blocks) {
        this(blocks, 0);
    }
    
    private Board(int[][] blocks, int moves) {
        this.n = blocks.length;
        this.data = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.data[i][j] = blocks[i][j];
            }
        }
    }
        
    // board dimension N
    public int dimension() {
        return n;
    }
    
    // number of blocks out of place
    public int hamming() {
        int num, hammingValue = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                num = i * n + j + 1;
                if (i == n-1 && j == n-1) break;
                if (data[i][j] != num) hammingValue++;
                num++;
            }
        }
        return hammingValue;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanValue = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (data[i][j] == 0) continue;
                int iTmp = (data[i][j]-1) / n;
                int jTmp = (data[i][j]-1) % n;
                manhattanValue += (Math.abs(iTmp - i) + Math.abs(jTmp - j));
            }
        }    
        return manhattanValue;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
      return hamming() == 0;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {      
        Board bb = new Board(data);
        
        if (bb.data[0][0] == 0) {
            exch(bb.data, 1, 0, 1, 1);
        } else if (bb.data[0][1] == 0) {
            exch(bb.data, 1, 0, 1, 1);
        } else {
            exch(bb.data, 0, 0, 0, 1);
        }
        return bb;
      
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y; 
        
        if (that.dimension() != this.dimension()) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.data[i][j] != (that.data)[i][j]) return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        
        int row = 0, col = 0;
        int[][] dataTmp = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dataTmp[i][j] = data[i][j];
                if (data[i][j] == 0) {
                    row = i; 
                    col = j;
                }
            }
        }
        
       // shift with left
        if (col > 0) {
            exch(dataTmp, row, col, row, col-1);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row, col-1);
        }
        
       // shift with right
        if (col < n-1) {
            exch(dataTmp, row, col, row, col+1);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row, col+1);
        }
        
        // shift with up
        if (row > 0) {
            exch(dataTmp, row, col, row-1, col);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row-1, col);
        }
        
        // shift with down
        if (row < n-1) {
            exch(dataTmp, row, col, row+1, col);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row+1, col);
        }
        return stack;
    }
    
    private void exch(int[][] matrix, int i, int j, int p, int q) {
        int tmp = matrix[i][j];  
        matrix[i][j] = matrix[p][q]; 
        matrix[p][q] = tmp;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder str = new StringBuilder(dimension() + "\n");     
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str.append(data[i][j]);
                str.append(" ");
            }      
            str.append("\n");
        }     
        return str.toString();
    }
    
    public static void main(String[] args) {
        int[][] input = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board tag = new Board(input);
        StdOut.println(tag);
        StdOut.println(tag.twin());
//        StdOut.println(tag.manhattan());
//        StdOut.println(tag.hamming());
//        StdOut.println(tag.toString());      
//        Iterable<Board> result = tag.neighbors();
//        for (Board b : result) {
//            StdOut.println(b.toString());
//        }
    }
}
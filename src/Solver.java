import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<Node> pq;
    private MinPQ<Node> pqTwin;
    private Node finalNode;
    private boolean solvable;
    
    private class Node {
        private Board board;
        private Node prevNode;
        
        public Node(Board board, Node prevNode) {
            this.board = board;
            this.prevNode = prevNode;
        }
    }
    private Comparator<Node> nodeComparator = new Comparator<Node>() {   
        @Override
        public int compare(Node a, Node b) {
            return a.board.manhattan() + numMoves(a) - b.board.manhattan() - numMoves(b);
        }
    };
    
    public Solver(Board initial) {
        pq = new MinPQ<Node>(nodeComparator);
        pqTwin = new MinPQ<Node>(nodeComparator);
        
        solvable = false;
        
        Node node = new Node(initial, null);
        Node nodeTwin = new Node(initial.twin(), null);
        
        pq.insert(node);
        pqTwin.insert(nodeTwin);
        
        node = pq.delMin();
        nodeTwin = pqTwin.delMin();
        
        while (!node.board.isGoal() && !nodeTwin.board.isGoal()) {

            for (Board b : node.board.neighbors()) { 
                if (node.prevNode == null || !b.equals(node.prevNode.board)) {
                    Node neighbor = new Node(b, node);
                    pq.insert(neighbor);
                }
            }
            
            for (Board bTwin : nodeTwin.board.neighbors()) {
                if (nodeTwin.prevNode == null || !bTwin.equals(nodeTwin.prevNode.board)) {
                    Node neighbor = new Node(bTwin, nodeTwin);
                    pqTwin.insert(neighbor);
                }
            }
            
            node = pq.delMin();
            nodeTwin = pqTwin.delMin();
        }
        pq = new MinPQ<Node>(nodeComparator);
        pqTwin = new MinPQ<Node>(nodeComparator);
        
        if (node.board.isGoal()) {
            solvable = true; 
            finalNode = node;
        }  
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (!solvable) return -1;
        Node current = finalNode;
        int moves = 0;
        
        while (current.prevNode != null) {
            moves++;
            current = current.prevNode;
        }
        return moves;
    }
    
    // to add number of moves to hamming or manhattan distance
    private static int numMoves(Node node) {
        int moves = 0;
        Node current = node;
        
        while (current.prevNode != null) {
            moves++;
            current = current.prevNode;
        }
        return moves;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!solvable) return null;
        Node current = finalNode;
        Stack<Board> boards = new Stack<Board>();
        boards.push(current.board);
        
        while (current.prevNode != null) {
            boards.push(current.prevNode.board);
            current = current.prevNode;
        }
        return boards;
    }
 
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
      int[][] input4 = new int[][]{{3, 7, 1, 0}, {6, 2, 8, 4}, {5, 10, 11, 12}, {9, 13, 14, 15}};
        Board initial = new Board(input4);
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] segments;
    private final Point[] points;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (nullpoints(points)) throw new NullPointerException();
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
        }

        Arrays.sort(this.points);
        Point[] aux = Arrays.copyOf(this.points, this.points.length);
        ArrayList<LineSegment> collector = new ArrayList<>();
        int N = aux.length;

        for (int i = 0; i < N; i++) {
            Point p = this.points[i];
            Arrays.sort(aux);
            Arrays.sort(aux, p.slopeOrder());

            int min = 0;
            while (min < N && p.slopeTo(aux[min]) == Double.NEGATIVE_INFINITY) min++;
            if (min != 1) throw new IllegalArgumentException();// check duplicate points
            int max = min;
            while (min < N) {
                while (max < N && p.slopeTo(aux[max]) == p.slopeTo(aux[min])) max++;
                if (max - min >= 3) {
                    Point pMin = aux[min].compareTo(p) < 0 ? aux[min] : p;
                    Point pMax = aux[max - 1].compareTo(p) > 0 ? aux[max - 1] : p;
                    if (p == pMin) collector.add(new LineSegment(pMin, pMax));
                }
                min = max;
            }
        }
        segments = collector.toArray(new LineSegment[collector.size()]);
    }

    // the number of line segments
    public int numberOfSegments() { return segments.length; }

    // the line segments
    public LineSegment[] segments() { return Arrays.copyOf(segments,segments.length); }

    private boolean nullpoints(Point[] points) {
        if  (points == null)                return true;
        for (Point p: points) if(p == null) return true;
        return false;
    }
    
    public static void main(String[] args) {
    	try {
    		String q = null;
    		ArrayList<String> s = new ArrayList<>();
    		FileReader fr = new FileReader("/Users/qinqing/Desktop/123.txt");
    		BufferedReader br = new BufferedReader(fr);
    		while ((q = br.readLine()) != null) {
    			s.add(q);
    		}
    		
            Point[] points = new Point[s.size()];
            for (int i = 1; i < s.size() -1; i += 2) {
                int x = Integer.valueOf(s.indexOf(i));
                int y = Integer.valueOf(s.indexOf(i+1));
                points[i] = new Point(x, y);
            }

            // draw the points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show();

            // print and draw the line segments
            BruteCollinearPoints collinear = new BruteCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
    	}
    	catch (IOException e) {
    		System.out.println("File does no exist.");
    	}

       
    }

}
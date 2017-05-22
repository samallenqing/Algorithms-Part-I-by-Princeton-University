import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private final Point[] points;
    
    public BruteCollinearPoints(Point[] points) {
        if (nullpoints(points)) throw new NullPointerException();
        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);
        if (duplicates(this.points)) throw new IllegalArgumentException();
        ArrayList<LineSegment> list = new ArrayList<LineSegment>();
        int n = this.points.length;
        for (int p = 0; p < n-3; p++) {
            for (int q = p+1; q < n-2; q++) {
                for (int r = q+1; r < n-1; r++) {
                   double PQ = this.points[p].slopeTo(this.points[q]);
                   double PR = this.points[p].slopeTo(this.points[r]);
                     if (PQ == PR) {
                         for (int s = r+1; s < n; s++) {
                             double PS = this.points[p].slopeTo(this.points[s]);
                             if (PQ == PS) {
                                 list.add(new LineSegment(this.points[p], this.points[s]));
                             }
                    }
                }
            }
        }
        }
        segments = list.toArray(new LineSegment[list.size()]);
    }
    
    private boolean nullpoints(Point[] k) {
        if  (k == null)                return true;
        for (Point p: k) if(p == null) return true;
        return false;
    }

    private boolean duplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) return true;
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
}

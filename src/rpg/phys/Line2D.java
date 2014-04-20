package rpg.phys;

public class Line2D {
    public static boolean linesIntersect(double x1, double y1,
            double x2, double y2,
            double x3, double y3,
            double x4, double y4)
    {
    	return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
    			relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
    			&& (relativeCCW(x3, y3, x4, y4, x1, y1) *
    					relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }
    public static int relativeCCW(double x1, double y1,
            double x2, double y2,
            double px, double py)
    {
    	x2 -= x1;
    	y2 -= y1;
    	px -= x1;
    	py -= y1;
    	double ccw = px * y2 - py * x2;
    	if (ccw == 0.0) {
    		// The point is colinear, classify based on which side of
    		// the segment the point falls on.  We can calculate a
    		// relative value using the projection of px,py onto the
    		// segment - a negative value indicates the point projects
    		// outside of the segment in the direction of the particular
    		// endpoint used as the origin for the projection.
    		ccw = px * x2 + py * y2;
    		if (ccw > 0.0) {
    			// Reverse the projection to be relative to the original x2,y2
    			// x2 and y2 are simply negated.
    			// px and py need to have (x2 - x1) or (y2 - y1) subtracted
    			//    from them (based on the original values)
    			// Since we really want to get a positive answer when the
    			//    point is "beyond (x2,y2)", then we want to calculate
    			//    the inverse anyway - thus we leave x2 & y2 negated.
    			px -= x2;
    			py -= y2;
    			ccw = px * x2 + py * y2;
    			if (ccw < 0.0) {
    				ccw = 0.0;
    			}
    		}
    	}
    	return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }
    
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    
    public int getRealX()
    {
    	return x1;
    }
    
    public int getRealY()
    {
    	return y1;
    }
    public int getX1()
    {
    	return x1;
    }
    public int getX2()
    {
    	return x2;
    }
    public int getY1()
    {
    	return y1;
    }
    public int getY2()
    {
    	return y2;
    }
    
    public Line2D(int x1 , int y1 , int x2 , int y2){
    	this.x1 = x1;
    	this.y1 = y2;
    	this.x2 = x2;
    	this.y2 = y2;
    }

}

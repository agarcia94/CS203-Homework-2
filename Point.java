package hw3;

import java.util.ArrayList;



public class Point extends GeometricObject {
    long id;
    double x, y;
    ArrayList<Segment> edges = new ArrayList<Segment>();
    ArrayList<Point> neighborPoints=new ArrayList<Point>();
    boolean hasVisited= false;

    public Point() { }

    public Point(long id, double x, double y) {
        this.id = id; this.x = x; this.y = y;
    }

    public void drawLine(Point other) {
        // maybe easier to draw a line between other and me...
    }

    double Xr() { return x; }
    double Yr() { return y; }

    double Xs() { 
//    	double oldmax = Boundaries.xmax;
//		double oldmin = Boundaries.xmin;
//		double newmax = 1.0;
//		double newmin = 0.0;
//
//		return this.x = Math.abs(((x - oldmin) * newmax) / oldmax) + newmin;
    	return Math.abs((Xr() - Boundaries.xmin) / (Boundaries.xmax - Boundaries.xmin));
    }

    double Ys() { 
//    	double oldmax = Boundaries.ymax;
//		double oldmin = Boundaries.ymin;
//		double newmax = 1.0;
//		double newmin = 0.0;
//		return this.y = Math.abs(((y - oldmin) * newmax) / oldmax) + newmin;
//		return Math.abs((Yr()- Boundaries.ymin)/(Boundaries.ymax - Boundaries.ymin));
    	return  Math.abs((Yr() - Boundaries.ymin) / (Boundaries.ymax - Boundaries.ymin)); 
    	
    }

    public void rescaleX() {
		// does not show up on map
		
		// this.x /= 1000;

	}

	private void setX(double x2) {
		// TODO Auto-generated method stub
		this.x = x2;
	}

	public void rescaleY() {
		// does not show up on map
		double oldmax = Boundaries.ymax;
		double oldmin = Boundaries.ymin;
		double newmax = 1.0;
		double newmin = 0.0;
		this.y = ((y - oldmin) * newmax) / oldmax + newmin;
		setY(this.y);

		// this.y /= 1000;

	}
    
    
    
    private void setY(double y2) {
		// TODO Auto-generated method stub
		this.y = y2;
	}

	public Point(String line) {
    }

    public Point(String line, boolean isOSM) {
        if (isOSM) {
            this.id = Long.parseLong( OSM.extractStringFromVal(line, "id"));
            this.x =  Double.parseDouble( OSM.extractStringFromVal(line, "lon"));
            this.y =  Double.parseDouble( OSM.extractStringFromVal(line, "lat"));
        }
    }
    
    public boolean isVisited(){ //return whether the point has been visited or not
    	return hasVisited;
    }
    
    public void setIsVisted(boolean visited){
    	hasVisited= visited;
    }
    
    public ArrayList<Segment> getListOfEdges(){ //return the list of segments that intersect with a given point
    	return edges;
    }
    
    public void setListOfEdges(ArrayList<Segment> edges){
    	this.edges=edges;
    }
    
    public ArrayList<Point> getNeighborPoints(){  //return the list of points that neighbors for a given point
    	return neighborPoints;
    }
    
    public void setNeighborPoints(Point p){
    	neighborPoints.add(p);
    }
    
    public double distance(double lon, double lat){ // return the distance between this point and another point defined by lon,lat
    	return Math.sqrt(Math.pow(lat - this.Ys(), 2) + Math.pow(lon - this.Xs(), 2));
    }

    public String toString() {
        return "P " + id + " " + Xs() + " " + Ys();
    }
    
    public void draw() {
        StdDraw.filledRectangle(Xs(), Ys(), 0.01, 0.01);
    }

    public void drawDot() {
        StdDraw.filledCircle(Xs(), Ys(), 0.005);
    }

    public double getArea() {
        return 0;
    }

    public void dump() {
        System.out.printf("p[%ld] = (%f,%f) => (%f,%f)\n", id, 
                Xr(), Yr(), Xs(), Ys());
    }
}

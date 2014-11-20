import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Road {
    ArrayList<Segment> segments = 
            new java.util.ArrayList<Segment>();
    ArrayList<Point> points = 
            new java.util.ArrayList<Point>();
    
    ArrayList<CellNetwork> networks=new ArrayList<CellNetwork>();
    double sigma=0.0;
    int amountOfPoints=0;
    
    String name;
    long id;

    public Road(String line, boolean isOSM) {
        if (isOSM) {
            String idStr = OSM.extractStringFromVal(line, "id");
            id = Long.parseLong(idStr);
        }
    }
    
    public int getTotalPointAmount(){ //returns the total amount of points in this road
    	
    	return amountOfPoints;
    }
    
    public void setPointAmount(){ //while traversing the list of points, increment the variable amountOfPoints
    	for(Point p: points)
    		amountOfPoints++;
    }
    
    /*
    public double getSigma(){
    	return sigma;
    }
    
    public void setSigma(double sigma){
    	this.sigma=sigma;
    }
    */
    
    ArrayList<CellNetwork> getAllCellConfigurations(int N, double radius) {

        /*
         * This method will return an array of cellular networks
         * Each network consist of cell towers
         */
        ArrayList<CellNetwork> cellNetworks = new ArrayList<CellNetwork>();
//        this.setSigma(radius);

        /*
         * obtain points from allPoints hash table
         */
//        ArrayList<Point> points = new ArrayList<Point>(allPoints.values());
        
        /*
         * Use the combinations class to find unique combination of points
         */
        Set<Set<Point>> pointSets = new Combinations<Point>().getCombinationsFor(points, N);
        
        
        for (Set<Point> s : pointSets) {
            CellNetwork cn = new CellNetwork();
            for (Point p : s) {
                CellTower tower = new CellTower(p, radius);
                cn.add(tower);
            }
            cellNetworks.add(cn);
//            networks.add(cn);
        }
        
        return cellNetworks;
    }


    public Road() {

    }
    
    public String toString() {
        String buffer = "R " + id;
        for (Point p : points) {
            buffer += " " + p.id;
        }
        return buffer;
    }

    public void addSegment(Segment segment) {
        segments.add( segment );
    }
    
    public ArrayList<CellNetwork> getNetworks(){
    	return networks;
    }
    
    public void setListOfNetworks(ArrayList<CellNetwork> networks) {
    	this.networks=networks;
    }

    /*
    boolean checkCoverage() {
        boolean status = true;
        for (Segment s : segments) {
        	s.setSigma(this.getSigma());
        	s.setListOfNetworks(this.getNetworks());
            if (s.checkCoverage() == false) 
                return false;
        }
        return status;
    }
    */

    public void build(ArrayList<Point> tempPoints) {
        // create segments
        Point p0 = tempPoints.get(0);
        for (int i = 1; i < tempPoints.size(); i++) {
            Point p1 = tempPoints.get(i);
            Segment segment = new Segment(p0, p1);
            addSegment( segment );
            p0 = p1;
        }
    }

    public void dump() {
        for ( Segment segment : segments ) {
            segment.dump();
        }
    }

    public void draw() {
        for ( Segment segment : segments ) {
            segment.draw();
        }
    }

    public void AppendDiscretizePoints(ArrayList<Point> discretizePoints) {
        for (Segment s : segments) {
            discretizePoints.addAll( s.discretize(100) );
        }

    }

    public ArrayList<Point> getAllPoints() {
        return points;
    }
    
    
}

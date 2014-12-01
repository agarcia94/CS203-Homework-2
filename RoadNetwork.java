package hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class RoadNetwork {

    java.util.ArrayList<Road> roads =  new java.util.ArrayList<Road>();
    java.util.ArrayList<Segment> segments =  new java.util.ArrayList<Segment>();
    HashMap<Long, Point> allPoints = new HashMap<Long, Point>();
    java.util.ArrayList<Long> roadRef = new java.util.ArrayList<Long>();
    java.util.ArrayList<Point> tempPoints = new java.util.ArrayList<Point>();
    HashMap<Long, ArrayList<Segment>> pointNeighbors= new HashMap<Long, ArrayList<Segment>>();
    
    private Scanner scanner;
    
    /*
     * This method returns all possible cell network combinations
     *
     */
    ArrayList<CellNetwork> getAllCellConfigurations(int N, double radius) {

        /*
         * This method will return an array of cellular networks
         * Each network consist of cell towers
         */
        ArrayList<CellNetwork> cellNetworks = new ArrayList<CellNetwork>();

        /*
         * obtain points from allPoints hash table
         */
        ArrayList<Point> points = new ArrayList<Point>(allPoints.values());
        
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
        }
        
        return cellNetworks;
    }
    
    public void searchNeighbors(Point p){  //fill in the arraylist of points that are neighbors of the given point p
    	ArrayList<Segment> neighbors=new ArrayList<Segment>();
//    	ArrayList<Point> neighborPoints = new ArrayList<Point>();
    	
    	for(Road road : roads){
    		for(Segment s : road.segments){
    			if(s.p1.equals(p) || s.p2.equals(p))
    				neighbors.add(s);
    		}
    	}
    
//    	pointNeighbors.put(p.id, neighbors);
//    	p.setListOfEdges(neighbors);
    	
    	for(Segment s : neighbors){
    		if(s.p1.equals(p))
    			p.setNeighborPoints(s.p2);
    		
    		if(s.p2.equals(p))
    			p.setNeighborPoints(s.p1);
    			
    	}
    	
    }
    
    public HashMap<Long, ArrayList<Segment>> getPointNeighbors(){  //might use it, but ignore it for now 
    	return pointNeighbors;
    }
    
    public Point findNearestPoint(double lon, double lat){  //find the nearest point in the road network closest to the point 
    	Point nearestPoint = null;							// given by a lon and lat
    	double distance= Double.MAX_VALUE;
    	ArrayList<Point> points = (ArrayList<Point>) allPoints.values();
    	
    	for(Point p : points){
    		if(p.distance(lon, lat) < distance){
    			distance = p.distance(lon, lat);
    			nearestPoint = p;
    		}
    	}
    
    	return nearestPoint;
    }
    
    public void bfs(Point p1, Point p2){ //breadth-first search from p1 to p2
    	p1.setIsVisted(true);
    	this.searchNeighbors(p1);
    	Queue<Point> queue= new LinkedList<Point>();
    	queue.add(p1);
    	
    	while(!queue.isEmpty()){
    		ArrayList<Point> neighborPoints= p1.getNeighborPoints();
    		for(Point p : neighborPoints){
    			if(p.isVisited() == false){
    				p.setIsVisted(true);
    				queue.add(p);
    			}else
    				queue.remove(p);
    		}
    	}
    	
  
    }

    private void readTextFormat(File file) throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(new File("text.txt")));
    	String line = "";
    	try {
			while((line = br.readLine()) != null){
				
				String space = " ";
				String[] components = line.split(space);
				String[] newc = line.split(space);
				
				if(line.startsWith("boundaries:")){
					double xmi = Double.valueOf(components[1]);
					double ymi = Double.valueOf(components[2]);
					double xma = Double.valueOf(components[3]);
					double yma = Double.valueOf(components[4]);
					Boundaries.xmax = xma;
					Boundaries.ymax = yma;
					Boundaries.xmin = xmi;
					Boundaries.ymin = ymi;
				}
				
				
				if(line.startsWith("P")){
				long id = Long.valueOf(components[1]);
				double x = Double.valueOf(components[2]);
				double y = Double.valueOf(components[3]);
				Point p = new Point(id, x, y);
				allPoints.put(id, p);
				}
				
				if(line.startsWith("R")){
					java.util.ArrayList<Long> pointsID = new java.util.ArrayList<Long>();
					Queue<Long> q = new LinkedList<Long>();
					for(int i = 1; i < components.length; i++){
						q.add(Long.valueOf(components[i]));
						pointsID.add(Long.valueOf(components[i]));
						//System.out.println(q);
						System.out.println(pointsID);
					}
					for(int i = 1; i < pointsID.size(); i++){
						roadRef.add(pointsID.get(0));
						Point p1 = allPoints.get(pointsID.get(i));
						//Point p = allPoints.get(q.poll());
						//System.out.println(p);
						//tempPoints.add(p);
						tempPoints.add(p1);
						System.out.println(tempPoints);
					}
					Road r = new Road();
					r.build(tempPoints);
					System.out.println(r.segments);
					roads.add(r);
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		br.close();
    }

    public RoadNetwork(File file) throws IOException {
        readTextFormat(file);
    }


    private void readOSMFormat(File file) {
        // extract boundaries
        try {
            scanner = new Scanner( file );
            while ( scanner.hasNext() ) {
                String line = scanner.nextLine();
                if (line.startsWith(" <bounds")) {
                    Boundaries.update(line);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;  // not good! plz handle exceptions accordingly
        }

        // extract points
        try {
            scanner = new Scanner( file );
            while ( scanner.hasNext() ) {
                String line = scanner.nextLine().replace("^\\s+", "");
                if (line.startsWith("<node")) {
                    Point p = new Point(line, true);
                    allPoints.put((long)p.id, p);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;  // not good! plz handle exceptions accordingly
        }

        // extract ways: implement your code below
//        try {
//        	scanner = new Scanner(file);
//        }
    }

    public RoadNetwork(File file, boolean isOSM) throws IOException {
        if (isOSM) {
            OSM.extractBoundaries(file);
            allPoints= OSM.extractNodes(file);
            roads.addAll(OSM.extractWays(file, allPoints).values());
        } else {
            readTextFormat(file);
        }
    }

    public void draw() {
        for (Road road : roads ) {
            road.draw();
        }
    }

    public void dump() {
        for (Road road : roads ) {
            road.dump();
        }
    }
    
    public int highestPointAmount(){  
    	ArrayList<Integer> points=new ArrayList<Integer>();
    	for(Road road : roads)
    		points.add(road.getTotalPointAmount());
    	
    	int highest=0;
    	for(int i : points){
    		if( i > highest)
    			highest = i;
    	}
    	
    	return highest;
    }
    
    public Road hasMostPoints(){  //finds the road in the network that has the most points
    	int mostPoints= highestPointAmount();
    	Road road=new Road();
    	for(Road r : roads){
    		if(r.getTotalPointAmount() == mostPoints)
    			road=r;
    	}
    	
    	return road;
    }
    
    public int getPointAmount(){
    	int sum=0;
    	for (Road road: roads){
    		sum += road.getTotalPointAmount();
    	}
    	return sum;
    }
   
//    public ArrayList<CellNetwork> getNetworks(){
//    	return networks;
//    }
   
    /*
    public void setListOfNetworks(ArrayList<CellNetwork> networks){
    	this.networks=networks;
    }
    */
    
    /*
    public boolean checkCoverage(){
    	boolean status=true;
    	for(Road r: roads){
    		if(r.checkCoverage() == false)
    			return false;
    	}
    	return status;
    		
    		
    }
    */
}

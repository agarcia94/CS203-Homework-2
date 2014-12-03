package hw3;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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


	public Point findNearestPoint(double lon, double lat){  //find the nearest point in the road network closest to the point 
		Point nearestPoint = null;							// given by a lon and lat
		double distance= Double.MAX_VALUE;
		ArrayList<Point> points = new ArrayList<Point>();
		points.addAll(allPoints.values());

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
		Queue<Point> queue= new LinkedList<Point>();

		ArrayList<Point> neighborPoints= p1.getNeighbors();
		ArrayList<Segment> edges=p1.getListOfEdges();
		for(Point p : neighborPoints){
			if(p.isVisited() == false){
				p.setIsVisted(true);
				queue.add(p);
			}else
				queue.remove(p);
		}


		while(!queue.isEmpty() && (queue.peek() != p2)){
			edges=queue.peek().getListOfEdges();
			neighborPoints= queue.remove().getNeighbors();      

			for(Point p : neighborPoints){					
				if(p.isVisited() == false){
//					StdDraw.setPenColor(Color.GREEN);
					p.setIsVisted(true);
					queue.add(p);
				}else
					queue.remove(p);


			}
		}


		if(queue.peek() == p2){
//			StdDraw.setPenColor();
			for(Segment segment : edges){  //trying to figure out how to color only the segments in edges, not the entire road network
//				StdDraw.setPenColor(Color.GREEN);
				StdDraw.line(segment.p1.Xs(), segment.p1.Ys(), segment.p2.Xs(), segment.p2.Ys());
//				StdDraw.setPenColor();

			}
			System.out.println("P1: " + p1.toString());
			System.out.println("p2 found: " + p2.toString());
		}
		else
			System.out.println("p2 not found: " + p2.toString());

		for(Point p: neighborPoints)
			p.setIsVisted(false);
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

	}

	public RoadNetwork(File file, boolean isOSM) throws IOException {
		if (isOSM) {
			OSM.extractBoundaries(file);
			allPoints= OSM.extractNodes(file);
			roads.addAll(OSM.extractWays(file, allPoints).values());

			stitchIntersections();
		}
	}

	private void stitchIntersections(){
		for(Road road: roads){
			road.connect();
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

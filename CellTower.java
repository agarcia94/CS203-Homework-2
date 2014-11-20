public class CellTower extends Point {
    double radius;
    int coveredPoints =0;

    boolean isCovered(Point p) {
        double distance = 
                Math.sqrt(Math.pow(Xs()-p.Xs(), 2) - Math.pow(Ys()-p.Ys(), 2));  

        return distance > radius ? false : true;
    }

    public CellTower(int id, double x, double y) {
        super(id, x, y);
    }

    public CellTower(String line) {
        String[] cols = line.split("\\s+"); // regex for white spaces
        this.id = Integer.parseInt(cols[0]);
        this.x = Double.parseDouble(cols[1]);
        this.y = Double.parseDouble(cols[2]);
        this.radius = Double.parseDouble(cols[3]);
    }

    public CellTower(Point p, double radius) {
        super(p.id, p.x, p.y);
        this.radius = radius;
    }

    public void draw() {
        super.draw();
        StdDraw.filledCircle(Xs(), Ys(), radius);
    }
    
    public int pointsCovered(){ //returns the amount of points covered by this cell tower
    	return coveredPoints;
    }
    
    public void setAmountOfCoveredPoints(Road road){  //while traversing the points in the road, increments the variable coveredPoints
    	for(Point p : road.points){					  //based on whether or not this cell tower covers a point on the road
    		if(isCovered(p))
    			coveredPoints++;
    			
    	}
    		
    }
    
    /*
    public boolean hasCoverage(RoadNetwork roadNetwork) {
        boolean assumption = true;
        for (Road road : roadNetwork.roads ) {
            if(!road.checkCoverage())
            	return false;
        }
        
        	return assumption;
    }
	*/
        
}



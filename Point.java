//go ahead and put the appropriate package
class Point extends GeometricObject {
	int id;
	double x, y;

	public Point(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public void drawLine(Point other) {
		StdDraw.line(this.x, this.y, other.x, other.y);
	}

	public Point(String line) {
		String[] cols = line.split("\\s+");
		this.id = Integer.parseInt(cols[0]);
		this.x = Double.parseDouble(cols[1]);
		this.y = Double.parseDouble(cols[2]);
	}

	public Point(String line, boolean b) {
        // write this construct such it takes a string that begins
        // with "<node ... " and extract its x, y values;
		if(b == true){	
			this.id = Integer.parseInt(OSM.extractStringFromVal(line, "id"));  //you needed to update the instance variables
			this.x = Double.parseDouble(OSM.extractStringFromVal(line, "lat")); //you needed to update the instance variables
			this.y = Double.parseDouble(OSM.extractStringFromVal(line, "lon")); //you needed to update the instance variables
			
			//you were using local variables such as double x or double y, which would've never updated the points' x,y, and id
		
		}
    }
	
	
	public void draw() {
		StdDraw.filledRectangle(x / 1000, y / 1000, 0.01, 0.01);
	}

	public void rescaleX() {   //hardcoding the oldmax and oldmin values might not work. Use John's Ys(), Yr() methods 
		//for example, the ones he gave us for lab6. Those methods use the Boundaries class, which also helps when you're 
		//reading from the OSM file. Check out the comments I gave you in the readOSMFormat method.
		
		
		// does not show up on map
		double oldmax = 1000;
		double oldmin = 0.0;
		double newmax = 1.0;
		double newmin = 0.0;

		this.x = ((x - oldmin) * newmax) / oldmax + newmin;
		setX(this.x);
		// this.x /= 1000;

	}

	public void rescaleY() {
		//hardcoding the oldmax and oldmin values might not work. Use John's Ys(), Yr() methods 
		//for example, the ones he gave us for lab6. Those methods use the Boundaries class, which also helps when you're 
		//reading from the OSM file. Check out the comments I gave you in the readOSMFormat method.
		
		
		
		// does not show up on map
		double oldmax = 1000;
		double oldmin = 0.0;
		double newmax = 1.0;
		double newmin = 0.0;
		this.y = ((y - oldmin) * newmax) / oldmax + newmin;
		setY(this.y);

		// this.y /= 1000;

	}

	public void setId(int id) {
		this.id = id;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getArea() {
		return 0;
	}

	public int getId() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String toString() {
		return x + " " + y; //for some reason you had a plus sign before the x
	}
}

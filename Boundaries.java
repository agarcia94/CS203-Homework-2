//you go ahead and put the appropriate package

public class Boundaries 
{
	static double xMin=Double.MAX_VALUE;
	static double yMin=Double.MAX_VALUE;
	static double xmax= -Double.MAX_VALUE;
	static double ymax= -Double.MAX_VALUE;
	
	static double scaleFactor=1.0;
	public static void update(Point p)
	{
		xMin= p.x < xMin ? p.x: xMin;
		yMin= p.y < yMin ? p.y : yMin;
		xmax= Math.max(xmax, p.x);
		ymax=Math.max(ymax,p.y);
	}

	public static void update(String line){   //you should definitely look at this!
		xMin= Double.parseDouble(OSM.extractStringFromVal(line,"minlat"));
		yMin= Double.parseDouble(OSM.extractStringFromVal(line,"minlon"));
		xmax= Double.parseDouble(OSM.extractStringFromVal(line,"maxlat"));
		ymax=Double.parseDouble(OSM.extractStringFromVal(line,"maxlon"));
		
	}
	
	public static void dump(){
		System.out.println("(%f,%f) (%f,%f)\n");
	}
}

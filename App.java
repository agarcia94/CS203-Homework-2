package hw3;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        final String roadFilename = args[0];
        final String imageFilename = args[1];
        final String reportFilename = args[2];
        final double x1= Double.parseDouble(args[3]);
        final double y1= Double.parseDouble(args[4]);
        final double x2=Double.parseDouble(args[5]);
        final double y2=Double.parseDouble(args[6]);
      

        final boolean isOSM = true;
      
        RoadNetwork roadNetwork;
		try {
			File file = new File(roadFilename);
			roadNetwork = new RoadNetwork(file, isOSM);
			
	        Point startPoint= roadNetwork.findNearestPoint(x1, y1);  //find the nearest point to the point defined by x1,y1
			Point endPoint= roadNetwork.findNearestPoint(x2, y2); // find the nearest point to the points defined by x2,y2
			roadNetwork.bfs(startPoint, endPoint);  //try to find the route from start to end, using breadth-first search
//			for(Point points: roadNetwork.allPoints.values()){
//				points.draw();
//			}
			roadNetwork.draw();
			
			
			
			
			/*
			
			try {
				File file2 = new File(reportFilename);
				Report re = new Report(file2);
				re.write(mostPoints, bestCellConfiguration, startTime, finishTime);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        StdDraw.save(imageFilename);
			
    }
}

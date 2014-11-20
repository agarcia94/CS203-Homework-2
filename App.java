import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        final String roadFilename = args[0];
        final String imageFilename = args[1];
        final String reportFilename = args[2];
        final int numCellTower = Integer.parseInt(args[3]);
        final double sigma = Double.parseDouble(args[4]);

        final boolean isOSM = true;

        RoadNetwork roadNetwork = 
                new RoadNetwork(new java.io.File(roadFilename), isOSM);
        roadNetwork.draw();
        
        for(Road road: roadNetwork.roads)
        	road.setPointAmount();
        
        
        Road mostPoints= roadNetwork.hasMostPoints();  //road with the most post points in the road network
        

        /*
         * Get a list of all of the cellular network configurations
         */

        
        ArrayList<CellNetwork> cellNetworkConfigurations =
                mostPoints.getAllCellConfigurations(numCellTower, sigma);
        
        for(CellNetwork networks: cellNetworkConfigurations)
        	networks.setAmountOfCoveredPoints(mostPoints);      //fill in the counters for the numbers of covered points for each
        														//cell network
        
        
        CellNetwork bestCellConfiguration = null;
        
        int bestScore = 0;
      
        for (CellNetwork cn : cellNetworkConfigurations) {
            // do test on cn to see if cn's score is better than bestScore
            // if it is update bestCellConfiguration = cn
        	if(cn.pointsCovered() > bestScore){
        		bestScore = cn.coveredPoints;
        		bestCellConfiguration = cn;
        	}
        		
        }
        
        bestCellConfiguration.draw();
        

        /*
         * Everything is done
         * 
         */

        StdDraw.save(imageFilename);
        Report report = 
                new Report(new java.io.File(reportFilename));
        report.write();
    }
}

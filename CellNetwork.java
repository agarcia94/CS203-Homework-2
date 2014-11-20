import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CellNetwork {
	java.util.ArrayList<CellTower> towers;
	int coveredPoints=0;
	
	public CellNetwork(){}
	
	public CellNetwork(File file) {
		try {
			Scanner scanner = new Scanner( file );
			while ( scanner.hasNext() ) {
				String line = scanner.nextLine();
				CellTower cellTower = new CellTower( line );
				add( cellTower );
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void add(CellTower tower) {
		towers.add(tower);
	}
	
	public void draw() {
		for ( CellTower cellTower : towers ) {
			cellTower.draw();
		}
	}
	
	public ArrayList<CellTower> getTowers(){
		return towers;
	}
	
	public int pointsCovered(){     //returns total amount of covered points throughout this particular cell network
		return coveredPoints;
	}
	
	public void setAmountOfCoveredPoints(Road road){  //adds to the total amount of covered points by adding up the amount of covered
		for(CellTower tower : towers){				  // points from each cell tower, given a road
			tower.setAmountOfCoveredPoints(road);
			coveredPoints += tower.pointsCovered();
		}
	}
	
	/*
	public boolean hasCoverage(RoadNetwork roadNetwork) {
		boolean assumption = true;
		for (CellTower tower : towers) {
			if (! tower.hasCoverage( roadNetwork) ) {
				return false;
			}
		}
		return assumption;
	}
	*/
}

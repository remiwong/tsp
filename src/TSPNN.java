
/**
 *
 * @author Rémi Wong
 * @version 1.0
 * Main Class for Nearest Neighbor TSP without Improvement
 *  
 */
//Please note that this class is the reference only because the algorithm was improved in the form of class TSP
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class TSPNN {

	public static void main(String[] args) {
		try {
			// let the user specify the name of the file containing the training points and
			
			System.out.println(
					"[WELCOME TO THE TRAVELLING SALESMAN PROBLEM]\nEnter the file path and name of the file containing the data set points of the different cities: ");
			System.out.println("file name of training points: tp1.txt, tp2,txt, tp3.txt and final points: fp1.txt, fp2.txt, fp3.txt, fp4.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String filename = bufferedReader.readLine();
			if (filename == null || filename.isEmpty())
				return;
			
			// put the different cities in a list of objects
			List<City> pts = FileParser.loadPointsFromFile(filename);

			System.out.println("All cities: " + pts);
			System.out.println(pts.get(0));
			System.out.println(pts.size());
			
			
			//initialise distance and city variables
			int initialCity;
			double kNearestDistance = 0.0;
			double comparatorDistance = Double.POSITIVE_INFINITY;
			double bestShortestPath = Double.POSITIVE_INFINITY;
			int referenceCity = 0;
			int unvisitedCity = 0;
			int rankCity = 0;

			//Record start time for the TSP algorithm
			long startTime = System.nanoTime();
			long milli_startTime = System.currentTimeMillis();

			
			//repeat the whole algorithm for each city, thus starting from different cities
			for (initialCity = 0; initialCity < pts.size(); initialCity++) {
				int rank = 1;

				for (int i = 0; i < pts.size(); i++) {
					pts.get(i).rank = 0;
					pts.get(i).visited = 0;
				}
				
				//set shortest path for current city A to next possible city B distances
				double shortestPath = 0.0;
				
				//set starting city's rank and status to visited
				pts.get(initialCity).visited = 1;
				pts.get(initialCity).rank = rank;
				referenceCity = initialCity;
				
				////repeat ranking system for all cities by visiting cities only once based on nearest neighboring city, thus recording visited cities
				for (Object pts1 : pts) {
					
					//repeat the nearest neighbor finder for remaining cities unvisited
					for (Object pts2 : pts) {
						
						//set city coordinates to be compared and calculate nearest distances
						CityCoordinates pointA = pts.get(referenceCity);
						if (pts.get(unvisitedCity).visited == 0) {
							CityCoordinates pointB = pts.get(unvisitedCity);
							System.out.println("start Pt A: " + pointA.x + "," + pointA.y + "start Pt B: " + pointB.x
									+ "," + pointB.y);

							EuclideanDistance nB = new EuclideanDistance(pointA, pointB);
							
							
							double compareDistance = nB.distance();
							System.out.println("Compare Distance: " + compareDistance);
							
							//compare current city to next possible city and set the nearest possible city
							if (compareDistance < comparatorDistance) {
								comparatorDistance = compareDistance;
								rankCity = unvisitedCity;
								kNearestDistance = compareDistance;

							}

						}

						unvisitedCity++;

					}
					System.out.println("Nearest Distance from current city " + kNearestDistance);
					
					//add the nearest distance from the current city to the total distance
					//adding the last distance to return back to initial city if all cities are visited
					if (rank <= (pts.size() - 1)) {

						shortestPath += kNearestDistance;
					} else {
						CityCoordinates pointB = pts.get(initialCity);
						CityCoordinates pointA = pts.get(referenceCity);
						System.out.println("Z A: " + pointA.x() + "," + pointA.y());
						System.out.println("Z B: " + pointB.x() + "," + pointB.y());
						EuclideanDistance nB = new EuclideanDistance(pointA, pointB);
						double returnToInitialCityDistance = nB.distance();
						System.out.println("returnToInitialCityDistance " + returnToInitialCityDistance);
						shortestPath += returnToInitialCityDistance;
					}
					
					//set the rank of the nearest city and mark as visited
					rank++;
					if (rank <= pts.size()) {
						pts.get(rankCity).rank = rank;
					}
					pts.get(rankCity).visited = 1;
					
					//reset comparators and set the value of reference city as rank city so that the nearest city becomes the next point A
					comparatorDistance = Double.POSITIVE_INFINITY;
					referenceCity = rankCity;
					unvisitedCity = 0;

					// display updated city rankings
					System.out.println("updates city rankings: " + pts.toString());

					System.out
							.println("------------------------------------------------------------------------------");
				}
				System.out.println("final: " + pts.toString());
				System.out.println(">>>>> Shortest Path: " + shortestPath + "\n");
				
				//alter the shortest path if the initial city chosen for nearest neighbor is better than previous 
				if (shortestPath < bestShortestPath) {
					bestShortestPath = shortestPath;
				}
			}

			//Record End time for the TSP algorithm
			long endTime = System.nanoTime();

			long milli_endTime = System.currentTimeMillis();

			System.out.println("Best path distance is: " + bestShortestPath);

			long eclapsedTime = endTime - startTime;
			
			//display timed elapsed for experiment
			System.out.println("TSP Time Taken nanosecond: " + eclapsedTime);

			System.out.println("TSP Time Taken ms: " + (milli_endTime - milli_startTime));

			System.out.println("TSP Time Taken s: " + eclapsedTime / 1000000000);

			
		//catch except if the algorithm fails
		} catch (Exception e) {
			System.out.println("Fatal Exception: " + e);
			System.out.println("Program aborted.");
		}
	}

}

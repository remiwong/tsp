/**
 *
 * @author Rémi Wong
 * @version 1.0
 * Main Class for TSP problem with a combination of Nearest Neighbor, Mexican Hat, and Simulated Annealing
 *  
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class TSP {

	public static void main(String[] args) {
		try {
			// let the user specify the name of the file containing the training points and put the different cities in a list of objects
			System.out.println("[WELCOME TO THE TRAVELLING SALESMAN PROBLEM]\nEnter the file path and name of the file containing the data set points of the different cities: ");
			System.out.println("file name of training points: tp1.txt, tp2,txt, tp3.txt and final points: fp1.txt, fp2.txt, fp3.txt, fp4.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String filename = bufferedReader.readLine();
			if (filename == null || filename.isEmpty())
				return;
			List<City> pts = FileParser.loadPointsFromFile(filename);

			System.out.println("Allcities: " + pts);
			//System.out.println(pts.size());
			
			
			//initialise distance and city variables
			
			double kNearestDistance = 0.0;
			double comparatorDistance = Double.POSITIVE_INFINITY;
			double bestShortestPath = Double.POSITIVE_INFINITY;
			int initialCity;
			int referenceCity = 0;
			int unvisitedCity = 0;
			int rankCity = 0;

			
			//Record start time for the TSP algorithm
			long startTime = System.nanoTime();
			long milli_startTime = System.currentTimeMillis();
			
			//repeat the whole algorithm for each city, thus starting from different cities
			for (initialCity = 0; initialCity < pts.size(); initialCity++) {
				
				System.out.println("Current Initial City" + initialCity);
				
				

				
				//set the value of the minimum inner weight and the maximum outer weight based on the closest city and farthest city to the initial city
				double minInnerWeight = 0.0, maxOuterWeight = 0.0;
				minInnerWeight = initialiseInnerWeight(pts, initialCity);
				maxOuterWeight = initialiseOuterWeight(pts, initialCity);
				System.out.println("Inner Min: " + minInnerWeight);
				System.out.println("Outer Min: " + maxOuterWeight);
				
				//define the learning rate of the TSP based on the value of the divider ranging from 1 >> infinity 
				double learningRate = (maxOuterWeight - minInnerWeight)/8;
				System.out.println("The learning is: " + learningRate);
				
				//find mean distances form initial city to all cities to find the initial weights to be used for the mexican hat categories
				double meanDistance = calculateMeanDistance(pts, initialCity);
				
				System.out.println("The mean is: " + meanDistance);
				
				
				
				
				//set the initial weights as the mean distance between the initial city to all other cities 
				double innerWeight = meanDistance, outerWeight = meanDistance;

	
				
				//Repeat the algorithm using different weights until the weights exceed any of the closest city or the farthest city from the initial city being used
				//the weights are adjusted each time the loop is executed
				while (minInnerWeight < innerWeight || maxOuterWeight > outerWeight) {
					
					int rank = 1;
					for (int i = 0; i < pts.size(); i++) {
						pts.get(i).rank = 0;
						pts.get(i).visited = 0;
					}
					
					System.out.println("Inner W: " + innerWeight);
					System.out.println("Outer W: " + outerWeight);
					int countA = 0;
					int countB = 0;
					int countC = 0;
					
					//classify all the cities in the mexican hat using A, B, or C based on the value of the inner and outer weight
					//if distance < inner weight => A
					//if inner < distance < outer => B
					//if distance > outer => C
					for (int j = 0; j < pts.size(); j++) {
						CityCoordinates pointA = (CityCoordinates) pts.get(initialCity);
						CityCoordinates pointB = (CityCoordinates) pts.get(j);
						EuclideanDistance nAB = new EuclideanDistance(pointA, pointB);
						double classifyWeightDistance = nAB.distance();
						if (classifyWeightDistance <= innerWeight) {
							pts.get(j).category = "A";
							countA++;
						} else if (classifyWeightDistance > innerWeight && classifyWeightDistance < outerWeight) {
							pts.get(j).category = "B";
							countB++;
						} else {
							pts.get(j).category = "C";
							countC++;
						}
					}

					//show classified cities
					System.out.println("Classified cities: " + pts);

					System.out.println("|||||||||||||||||||||||||||||||||||||||||");
					
					
					//set shortest path for current city A to next possible city B distances
					double shortestPath = 0.0;
					
					//set starting city's rank and status to visited				
					pts.get(initialCity).visited = 1;
					pts.get(initialCity).rank = rank;
					referenceCity = initialCity;

					//sets the catehory directly to B or C if the previous category doesn't have any city
					int countee = countA + countB + countC;
					String cityCategory = "A";
					if (countA < 2) {
						cityCategory = "B";
						if (countB == 0) {
							cityCategory = "C";
						}
					}
					
					//Based on the number of city classified in either A, B, or C of the mexican hat, the algorithm changes the category based
					//on the number of cities in the same category until all cities have been ranked
					for (int counter = 1; counter < (countee); counter++) {
						
						//show number of cities in each category and the current city category being ranked
						System.out.println("current category " + cityCategory + " count A " + countA + " count B "
								+ countB + " count C " + countC);
						///////////////////////////
						
						
						//find nearest city starting from the reference city reached by the salesman to all other cities but based on conditions
						//conditions: same category and unvisited
						for (Object pts2 : pts) {

							CityCoordinates pointA = pts.get(referenceCity);
							
							
							//consider next closest city if it is unvisited and in the current category being ranked 
							if (pts.get(unvisitedCity).visited == 0 && pts.get(unvisitedCity).category == cityCategory) {
								
								//set city coordinates to calculate distance
								CityCoordinates pointB = pts.get(unvisitedCity);
								System.out.println("start Pt A: " + pointA.x + "," + pointA.y + "start Pt B: " + pointB.x + "," + pointB.y);
								
								
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
						/////////////////////////////

						System.out.println("Nearest Distance from current city " + kNearestDistance);
						
						//add the nearest distance from the current city to the total distance
						//adding the last distance to return back to initial city if all cities are visited
						if (counter < (countee - 1)) {							
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
							shortestPath += kNearestDistance;
						}
						System.out.println("current shortest" + shortestPath);
						
						//set the rank of the nearest city and mark as visited
						if (rank < pts.size() && pts.get(rankCity).rank == 0) {
							rank++;
							pts.get(rankCity).rank = rank;
							pts.get(rankCity).visited = 1;

						}
						
						//reset comparators and set the value of reference city as rank city so that the nearest city becomes the next point A
						comparatorDistance = Double.POSITIVE_INFINITY;
						referenceCity = rankCity;
						unvisitedCity = 0;
						
						//display updated city rankings
						System.out.println(pts.toString());
						
						System.out.println(
								"------------------------------------------------------------------------------");
						
						//alter the category to be considered for the mexican hat after the previous category has been accounted for
						if (counter == (countA-1) && cityCategory == "A") {
							cityCategory = "B";
							if (countB == 0) {
								cityCategory = "C";
							}
						}

						if (counter == (countA + countB - 1) && cityCategory == "B") {
							cityCategory = "C";
						}
					}

					System.out.println("final route: " + pts.toString());
					System.out.println(">>>>> Shortest Path: " + shortestPath + "\n");
					
					//change the best path distance if the initial city selected and weights produced better results than previous
					if (shortestPath < bestShortestPath) {
						bestShortestPath = shortestPath;
					}
					
					//adjust the inner and outer weights to test the outcome of different weight distances of the hat  
					innerWeight -= learningRate;
					outerWeight += learningRate;
					
					System.out.println("New Inner and Outer Weight\n");
					
					
				}
			}
			
			
			//Record start time for the TSP algorithm
			long endTime = System.nanoTime();
			long milli_endTime = System.currentTimeMillis();
			
			
			
			System.out.println("************ VERDICT ***********");
			
			
			System.out.println("\nBest path distance is: " + bestShortestPath);

			long eclapsedTime = endTime - startTime;
			
			//display timed elapsed for experiment
			System.out.println("TSP Time Taken nanosecond: " + eclapsedTime);
			System.out.println("TSP Time Taken ms: " + (milli_endTime - milli_startTime));
			System.out.println("TSP Time Taken s: " + eclapsedTime / 1000000000);
			
			
			System.out.println("************ END ***********");
			
		//catch except if the algorithm fails
		} catch (Exception e) {
			System.out.println("Fatal Exception: " + e);
			System.out.println("Program aborted.");
		}
	}
	
	
	//method to find the nearest city to the initial city
	public static double initialiseInnerWeight(List iW, int iC) {
		int testCity = 0;
		double smallestDistance = Double.POSITIVE_INFINITY;
		for (Object all : iW) {
			if (testCity != iC) {
				CityCoordinates pointA = (CityCoordinates) iW.get(iC);
				CityCoordinates pointB = (CityCoordinates) iW.get(testCity);
				EuclideanDistance nB = new EuclideanDistance(pointA, pointB);
				double compareWeightDistance = nB.distance();
				if (compareWeightDistance < smallestDistance) {
					smallestDistance = compareWeightDistance;
				}
			}
			testCity++;
		}
		return smallestDistance;
	}
	
	//method to find the farthest city to the initial city
	public static double initialiseOuterWeight(List iW, int iC) {
		int testCity = 0;
		double largestDistance = 0.0;
		for (Object all : iW) {
			if (testCity != iC) {
				CityCoordinates pointA = (CityCoordinates) iW.get(iC);
				CityCoordinates pointB = (CityCoordinates) iW.get(testCity);
				EuclideanDistance nB = new EuclideanDistance(pointA, pointB);
				double compareWeightDistance = nB.distance();
				if (compareWeightDistance > largestDistance) {
					largestDistance = compareWeightDistance;
				}
			}
			testCity++;

		}
		return largestDistance;
	}
	
	//method to find the mean distance between the initial city and other cities based on simulated annealing concept
	public static double calculateMeanDistance(List iW, int iC) {
		double totalDistance = 0.0;
		double meanTotal = 0.0;
		int chosenCity = 0;
		for (Object all : iW) {
			if (chosenCity != iC) {
				CityCoordinates pointA = (CityCoordinates) iW.get(iC);
				CityCoordinates pointB = (CityCoordinates) iW.get(chosenCity);
				EuclideanDistance nB = new EuclideanDistance(pointA, pointB);
				double cityDistance = nB.distance();
				totalDistance += cityDistance;
			}
			chosenCity++;
		}
		System.out.println("the total distance is : " + totalDistance);
		meanTotal = totalDistance/(iW.size()-1);
		
		return meanTotal;
	}
}

/**
 *
 * @author Rémi Wong
 * @version 1.0
 * Euclidean Distance Calculation Class
 *  
 */

public class EuclideanDistance {

	double x1, x2, y1, y2;

	//Each point A and B are mapped with their x,y coordinates
	public EuclideanDistance(CityCoordinates pointA, CityCoordinates pointB) {
		this.x1 = pointA.x;
		this.y1 = pointA.y;
		this.x2 = pointB.x;
		this.y2 = pointB.y;
	
	}
	
	//the Euclidean distance formula is used to determine distance between the paired points 
	public double distance() {
		return Math.sqrt((Math.pow((y2 - y1),2)) + (Math.pow((x2 - x1), 2)));
	}
}

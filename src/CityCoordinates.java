/**
 *
 * @author Rémi Wong
 * @version 1.0
 * City x and y coordinate object class
 *  
 */

//the coordinates of the city object is constructed
public class CityCoordinates {
	public double x;
	public double y;
	public CityCoordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double x() {return x;}
	public double y() {return y;}
	public String toString() {
		return "(" + x + ", " + y + ")";	
	}
	
}

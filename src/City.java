/**
 *
 * @author Rémi Wong
 * @version 1.0
 *  
 * City Object Class for different city attributes and classifiers
 */


public class City extends CityCoordinates{

	public String cityLabel;
	public int visited;
	public int rank;
	public String category;


	public City(String cityLabel, double x, double y, int visited, int rank, String category) {
		super(x, y);
		this.cityLabel = cityLabel;
		this.visited = visited;
		this.rank = rank;
		this.category =category;
	}

	public String cityLabel() {return cityLabel;}
	public int visited() {return visited;}
	public int rank() {return rank;}
	public String category() {return category;}
	public String toString() {
		return "City: " + cityLabel + " (" + x + ", " + y + ")" + "visited: " + visited + " rank: " + rank + " category: " + category;
	}
	
}

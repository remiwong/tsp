/**
 * @author Rémi Wong
 * @version 1.0
 * Text File .txt file loading class for all cities 
 *  
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileParser {
    public static List<City> loadPointsFromFile(String filename) throws IOException
	{
		// initialise an empty list of points
		List<City> pts = new ArrayList<City>();
		
		// set file path
		Path filepath = Paths.get(filename);
		System.out.println("Reading file '" + filepath + "'...");

		// read all the cities from lines in the text file
		List<String> lines = Files.readAllLines(filepath, Charset.defaultCharset());
			
		// for each text line...
		int lineNum = 0;
		for(String line : lines)
		{
			++lineNum;
			
			// split each line using space as a delimiter
			String[] dt = line.split(" ");
			
			// we expect 3 items on each line: city number, x, and y
			if (dt.length != 3)
				throw new IOException("Point data on line " + lineNum + " appears to be invalid! Expecting: \"<city label>,<x>,<y>,\" Found: \"" + line + "\"");

			// add the new points to the list and pre-fine the value of visited, rank, and category
			pts.add(new City((dt[0]), Double.parseDouble(dt[1]), Double.parseDouble(dt[2]), 0, 0, "0"));
		}					

		return pts;
	}
}

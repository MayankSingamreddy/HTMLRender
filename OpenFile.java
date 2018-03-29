/** 
* open files for reading and writing 
*
* Mayank Singamreddy
* September 7, 2017
**/
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OpenFile {

//open file to read using scanner class
//parameter fileName    =    name of the file to open
//return the scanner object to the file

	public static Scanner openToRead(String fileName) {
	
		Scanner input = null;
		try {
				input = new Scanner(new java.io.File(fileName));
		}
			catch (FileNotFoundException e){
				System.err.println("ERROR; cannot open " + fileName +
				" for reading.");
				
				System.exit(404);
			}
			return input;
	}

// opens a file to write using the Print Writer class
// @param: fileName - name of the file to open
//return Print Writer object to the file
	public static PrintWriter openToWrite(String fileName) {
		PrintWriter output = null;
		try {
			output = new PrintWriter(new java.io.File(fileName));
		}
		catch (FileNotFoundException e) {
			System.err.println("ERROR: Cannot open " + fileName + 
								" for writing.");
			System.exit(401);
		}
		return output;
	}
}
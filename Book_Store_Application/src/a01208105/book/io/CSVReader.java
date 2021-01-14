/**
 * 
 */
package a01208105.book.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;

/**
 * This class implements CSVReader functionality
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 *
 */
public class CSVReader {
	
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * Default constructor
	 */
	public CSVReader() {
		
	}
	/**
	 * Method to read file into iterable csv record
	 * @param filename the filename to read
	 * @throws ApplicationException
	 */
	public static Iterable<CSVRecord> reader(String filename) throws ApplicationException {
		File file = new File(filename);
		FileReader in;
		Iterable<CSVRecord> records;
		
		try {
			in = new FileReader(file);
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		} catch(IOException e) {
			LOG.error("IO exception");
			throw new ApplicationException(e);
		}
		
		return records;
	}

}

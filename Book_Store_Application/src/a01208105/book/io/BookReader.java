
/**
 * 
 */
package a01208105.book.io;

import java.sql.SQLException;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;
import a01208105.book.data.books.Book;
import a01208105.book.data.books.BookDao;

/**
 * This class reads Book information from the file into a Map
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 *
 */
public class BookReader {

	private static final Logger LOG = LogManager.getLogger();

	public final static String FILENAME = "books500.csv";

	/**
	 * Method to read books from the file to db
	 * 
	 * @param dao is a Book DAO
	 * @throws ApplicationException
	 */
	public static void readBooks(BookDao dao) throws ApplicationException {

		Iterable<CSVRecord> records;

		try {
			records = CSVReader.reader(FILENAME);
		} catch (ApplicationException e) {
			LOG.error("CSV Reader application exception");
			throw new ApplicationException();
		}

		for (CSVRecord record : records) {
			String id = record.get("book_id");
			String isbn = record.get("isbn");
			String authors = record.get("authors");
			String year = record.get("original_publication_year");
			String title = record.get("original_title");
			String ratingAvrg = record.get("average_rating");
			String ratingCount = record.get("ratings_count");
			String image = record.get("image_url");
			Book aBook = new Book(Long.valueOf(id), isbn, authors, Integer.valueOf(year), title,
					Double.valueOf(ratingAvrg), Long.valueOf(ratingCount), image);
			try {
				dao.add(aBook);
				LOG.debug("Added to the database: " + aBook);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

/**
 * 
 */
package a01208105.book.io;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;
import a01208105.book.data.books.Book;
import a01208105.book.data.books.BookDao;
import a01208105.util.Common;

/**
 * This class generated Book reports
 * 
 * @author Veronica A01208105
 * @version 2020-11-29
 *
 */
public class BookReport {

	private static final Logger LOG = LogManager.getLogger();

	public static final String HORIZONTAL_LINE = "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	public static final String HEADER_FORMAT = "%-8s %-40s %-55s %-30s %-90s %-30s %-60s %-40s%n";
	public static final String BOOK_FORMAT = "%-8d %-20s %-55s %-20d %-100s %-30.3f %-30d %-40s%n";

	/**
	 * Default constructor
	 */
	public BookReport() {

	}

	/**
	 * Method to write a report into a List of Strings
	 * 
	 * @param dao
	 * @param books
	 * @return
	 * @throws ApplicationException
	 */
	public static List<String> writeBookReport(BookDao dao, List<Book> books) throws ApplicationException {

		List<String> bookListReport = new ArrayList<String>();

		bookListReport.add(String.format(HEADER_FORMAT, "ID", "ISBN", "Authors", "Year", "Title", "Rating AVRG",
				"Rating CNT", "Image URL"));
		bookListReport.add(HORIZONTAL_LINE);

		for (Book book : books) {
			bookListReport.add(
					String.format(BOOK_FORMAT, book.getBookId(), book.getIsbn(), Common.ellipsis(book.getAuthors(), 40),
							book.getYearPublished(), Common.ellipsis(book.getTitle(), 40), book.getRatingAvrg(),
							book.getRatingCount(), Common.ellipsis(book.getImageURL(), 40)));
			LOG.debug("Book Record Entry: " + book.toString());
		}

		return bookListReport;

	}

}

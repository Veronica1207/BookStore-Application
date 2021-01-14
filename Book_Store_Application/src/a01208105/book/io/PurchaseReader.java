/**
 * 
 */
package a01208105.book.io;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;
import a01208105.book.data.books.BookDao;
import a01208105.book.data.customers.CustomerDao;
import a01208105.book.data.purchases.Purchase;
import a01208105.book.data.purchases.PurchaseDao;

/**
 * This class reads purchase information from a file into a Map
 * 
 * @author Veronica A01208105
 * @version 2020-11-29
 */
public class PurchaseReader {

	private static final Logger LOG = LogManager.getLogger();

	public final static String FILENAME = "purchases.csv";

	/**
	 * Default constructor
	 */
	public PurchaseReader() {
		
	}
	
	/**
	 * Method to read a file into a db
	 * 
	 * @param dao is a Customer DAO
	 * @param bookDao is a book DAO
	 * @param purchaseDao is a purchase DAO
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public static void readPurchases(CustomerDao dao, BookDao bookDao, PurchaseDao purchaseDao) throws NumberFormatException, Exception {

		Iterable<CSVRecord> purchaseRecords;

		try {
			purchaseRecords = CSVReader.reader(FILENAME);
		} catch (ApplicationException e) {
			LOG.error("CSV Reader application exception");
			throw new ApplicationException();
		}

		for (CSVRecord record : purchaseRecords) {
			String id = record.get("id");
			String customerID = record.get("customer_id");
			String bookID = record.get("book_id");
			String price = record.get("price");


			Purchase aPurchase = new Purchase(Long.valueOf(id), dao.readByCustomerId(customerID), bookDao.readByBookId(bookID), Double.valueOf(price));
			LOG.debug("Create Purchase Object: " + aPurchase.toString());
			
			purchaseDao.add(aPurchase);
			LOG.debug("Added to the database: " + aPurchase);		
		}

	}

}

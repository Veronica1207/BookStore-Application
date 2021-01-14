/**
 * 
 */
package a01208105.book.io;

import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;
import a01208105.book.data.books.BookDao;
import a01208105.book.data.customers.CustomerDao;
import a01208105.book.data.purchases.Purchase;
import a01208105.book.data.purchases.PurchaseDao;
import a01208105.util.Common;

/**
 * This class generates purchase reports
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 */
public class PurchaseReport {

	private static final Logger LOG = LogManager.getLogger();

	public static final String HORIZONTAL_LINE = "-----------------------------------------------------------------------------------------------------------------------------------";
	public static final String HEADER_FORMAT = "%-8s %-16s %-21s %-5s%n";
	public static final String PURCHASE_FORMAT = "%-15d %-20d %-20d $%-5.2f%n";

	public static final String HEADER_LESS = "%-60s %-63s %s%n";
	public static final String PURCHASE_FORMAT_LESS = "%-30s %-70s $%.2f%n";

	static Formatter output = null;
	static FileWriter fw = null;
	public static final String FILENAME = "purchases_report.txt";

	/**
	 * Default constructor
	 */
	public PurchaseReport() {
		super();
	}

	/**
	 * Method to generate a standard purchase report
	 * 
	 * @param dao is a purchase DAO
	 * @param purchases is a list of purchases
	 * @return purchaseList Report
	 * @throws ApplicationException
	 */
	public static List<String> writePurchaseReport(PurchaseDao dao, List<Purchase> purchases)
			throws ApplicationException {

		List<String> purchaseListReport = new ArrayList<String>();

		purchaseListReport.add(String.format(HEADER_FORMAT, "ID", "Customer ID", "Book ID", "Price"));
		purchaseListReport.add("---------------------------------------------------------------------");
		
		for (Purchase aPurchase : purchases) {
			purchaseListReport.add(String.format(PURCHASE_FORMAT, aPurchase.getPurchaseID(),
					aPurchase.getCustomer().getId(), aPurchase.getBook().getBookId(), aPurchase.getPrice()));
			LOG.debug("Entry added to the report: " + aPurchase);
		}
		try {
			purchaseListReport.add("\n");
			purchaseListReport.add(String.format("%nTotal: $%.2f", dao.countAllPurchases()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return purchaseListReport;

	}

	/**
	 * Method to generate an extended report with information from the Book and
	 * Customer DAO
	 * 
	 * @param dao is a purchase DAO
	 * @param purchases is a list of purchases
	 * @return purchaseListReportExtended as a List<String>
	 * @throws ApplicationException
	 */
	public static List<String> writePurchaseReportExtended(PurchaseDao dao, List<Purchase> purchases)
			throws ApplicationException {

		List<String> purchaseListReportExtended = new ArrayList<String>();

		purchaseListReportExtended.add(String.format(HEADER_LESS, "Name", "Title", "Price"));
		purchaseListReportExtended.add(HORIZONTAL_LINE);
		for (Purchase aPurchase : purchases) {
			String fullName = (String.format("%s %s", aPurchase.getCustomer().getLastName(),
					aPurchase.getCustomer().getFirstName()));
			purchaseListReportExtended.add(String.format(PURCHASE_FORMAT_LESS, fullName,
					Common.ellipsis(aPurchase.getBook().getTitle(), 40), aPurchase.getPrice()));
			LOG.debug("Purchase Record Entry " + aPurchase.toString());
		}
		try {
			purchaseListReportExtended.add("\n");
			purchaseListReportExtended.add(String.format("%nTotal: $%.2f", dao.countAllPurchases()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return purchaseListReportExtended;

	}

	/**
	 * Method to generate an individual report
	 * 
	 * @param individualPurchases is a list of purchases
	 * @param dao is a purchase DAO
	 * @param customerDao is a customer DAO
	 * @param bookDao is a book DAO 
	 * @param inputId is an input customer ID
	 * @return purchaseListReportIndividual as List<String>
	 * @throws ApplicationException
	 */
	public static List<String> writeIndividualReport(List<Purchase> individualPurchases, PurchaseDao dao,
			CustomerDao customerDao, BookDao bookDao, String inputId) throws ApplicationException {

			List<String> purchaseListReportIndividual = new ArrayList<String>();		

			purchaseListReportIndividual.add(String.format(HEADER_LESS, "Name", "Title", "Price"));
			purchaseListReportIndividual.add(HORIZONTAL_LINE);
			int books = 0;
			for (Purchase aPurchase : individualPurchases) {

				String fullName = (String.format("%s %s", aPurchase.getCustomer().getLastName(),
						aPurchase.getCustomer().getFirstName()));
				purchaseListReportIndividual.add(String.format(PURCHASE_FORMAT_LESS, fullName,
						Common.ellipsis(aPurchase.getBook().getTitle(), 40), aPurchase.getPrice()));
				LOG.debug("Purchase Record Entry " + aPurchase.toString());
				books++;
			}
			
			if (books == 0) {
				purchaseListReportIndividual
						.add(String.format("%n%nCustomer ID #%s has not bought any books yet%n%n%n", inputId));
			}

			try {
				purchaseListReportIndividual.add("\n");
				purchaseListReportIndividual.add(String.format("Total: $%.2f", dao.countTotalForACustomer(inputId)));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		
		return purchaseListReportIndividual;

	}

}

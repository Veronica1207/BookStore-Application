package a01208105.book.io;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.data.customers.Customer;
import a01208105.book.data.customers.CustomerDao;
import a01208105.util.Common;

/**
 * Prints a formated Customers report.
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 */
public class CustomerReport {

	private static final Logger LOG = LogManager.getLogger();
	

	public static final String HORIZONTAL_LINE = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	public static final String HEADER_FORMAT = "%2s. %-6s %15s %14s %40s %57s %45s %35s %58s %80s%n";
	public static final String CUSTOMER_FORMAT = "%d. %-10d %-20s %-25s %-30s %35s %35s %40s %55s %60s%n";
	

	/**
	 * private constructor to prevent instantiation
	 */
	private CustomerReport() {
	}

	/**
	 * Method to generate a customer report 
	 * 
	 * @param dao is a Customer DAO
	 * @param customers list of customers
	 * @return a customerListReport as a List<String>
	 */
	public static List<String> write (CustomerDao dao, List<Customer> customers) {
		List <String> customerListReport = new ArrayList<String>();
		int i = 0;
		customerListReport.add(String.format(HEADER_FORMAT, "#", "ID", "First name", "Last name", "Street", "City", "Postal Code",
				"Phone", "Email", "Join Date"));
		customerListReport.add(HORIZONTAL_LINE);
		for (Customer customer : customers) {
			LocalDate date = customer.getJoinedDate();
			customerListReport.add(String.format(CUSTOMER_FORMAT, ++i, customer.getId(), customer.getFirstName(),
					customer.getLastName(), customer.getStreet(), customer.getCity(), customer.getPostalCode(),
					customer.getPhone(), customer.getEmailAddress(), Common.DATE_FORMAT.format(date)));
			LOG.debug("Add customer to the report: " + customer.toString());
		}
		
		return customerListReport;
				
		
	}
}

/**
 * 
 */
package a01208105.book.data.purchases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.data.books.BookDao;
import a01208105.book.data.customers.CustomerDao;
import a01208105.book.db.Dao;
import a01208105.book.db.Database;
import a01208105.book.db.DbConstants;
import a01208105.util.BookStoreSorters;

/**
 * This class describes a purchase DAO
 * 
 * @author Veronica A01208105
 * @version 2020-11-29
 */
public class PurchaseDao extends Dao {

	public static final String PURCHASES_TABLE_NAME = DbConstants.PURCHASE_TABLE_NAME;

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * PurchaseDao constructor
	 * 
	 * @param database is the database
	 */
	public PurchaseDao(Database database) {
		super(database, PURCHASES_TABLE_NAME);
		LOG.debug("PurchaseDao constructor call");
	}

	/**
	 * Method to create a purchases db table
	 */
	@Override
	public void create() throws SQLException {
		LOG.debug("Creating a purchase database table: " + PURCHASES_TABLE_NAME);

		String sql = String.format(
				"CREATE TABLE %s(" 
						+ "purchaseId VARCHAR(8)," 
						+ "customerId VARCHAR(6)," 
						+ "bookId VARCHAR(6),"
						+ "price DECIMAL(10,2)," 
						+ "primary key (purchaseId),"
						+ "FOREIGN KEY(customerId) references %s (id)," 
						+ "FOREIGN KEY(bookId) references %s (id))",
				PURCHASES_TABLE_NAME, DbConstants.CUSTOMER_TABLE_NAME, DbConstants.BOOK_TABLE_NAME);
		super.create(sql);
		LOG.info("Table " + tableName + " is created");
	}

	/**
	 * Method to add a purchase record to the table
	 * 
	 * @param purchase to add
	 * @throws SQLException
	 */
	public void add(Purchase purchase) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format(
					"INSERT into %s" + "(purchaseId, customerId, bookId, price)" + "VALUES('%s', '%s', '%s', '%s')",
					PURCHASES_TABLE_NAME, purchase.getPurchaseID(), purchase.getCustomer().getId(),
					purchase.getBook().getBookId(), purchase.getPrice());

			LOG.debug(sql);
			statement.executeUpdate(sql);
			LOG.debug("Purchase added: " + sql);
		} finally {
			close(statement);
		}
	}

	/**
	 * Method to count the price of all purchases
	 * 
	 * @return total as a double
	 * @throws SQLException
	 */
	public double countAllPurchases() throws SQLException {
		Statement statement = null;
		double total = 0;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format("SELECT SUM(price) AS total FROM %s", PURCHASES_TABLE_NAME);
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				total = resultSet.getDouble("total");
			}
		} finally {
			close(statement);
		}
		return total;
	}

	/**
	 * Method to create a list of purchases
	 * 
	 * @param customerDao a customer DAO to refer the foreign key
	 * @param bookDao     a book DAO with to refer the foreign key
	 * @return purchases as List<Purchase>
	 */
	public List<Purchase> purchaseList(CustomerDao customerDao, BookDao bookDao) {
		List<Purchase> purchases = new ArrayList<Purchase>();
		Purchase aPurchase = null;
		Statement statement = null;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();

			String sql = String.format("SELECT * FROM %s", PURCHASES_TABLE_NAME);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				aPurchase = new Purchase(Long.valueOf(resultSet.getString(1)),
						customerDao.readByCustomerId(resultSet.getString(2)),
						bookDao.readByBookId(resultSet.getString(3)), Double.valueOf(resultSet.getString(4)));
				purchases.add(aPurchase);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}
		return purchases;
	}

	/**
	 * Method to sort a list by name
	 * 
	 * @param purchases is a list to sort
	 * @return purchases as a List<Purchase> sorted
	 */
	public List<Purchase> purchaseListByName(List<Purchase> purchases) {
		purchases.sort(new BookStoreSorters.CompareByLastName());
		return purchases;

	}

	/**
	 * Method to sort a list by title
	 * 
	 * @param purchases to sort
	 * @return purchases as a List<Purchase> sorted
	 */
	public List<Purchase> purchaseListByTitle(List<Purchase> purchases) {
		purchases.sort(new BookStoreSorters.CompareByTitle());
		return purchases;

	}

	/**
	 * Method to sort in descending order
	 * 
	 * @param purchases a list to sort
	 * @return purchases as a List<Purchase> sorted
	 */
	public List<Purchase> purchaseListDesc(List<Purchase> purchases) {
		Collections.reverse(purchases);
		return purchases;

	}

	/**
	 * Method to create a list of purchases by customer ID
	 * 
	 * @param inputId is a customer ID
	 * @param customerDao is a customer DAO
	 * @param bookDao is a book DAO
	 * @return purchases as a List<Purchase>
	 */
	public List<Purchase> findPurchasesByCustomerId(String inputId, CustomerDao customerDao, BookDao bookDao) {
		List<Purchase> purchases = new ArrayList<Purchase>();
		Statement statement = null;
		Purchase aPurchase = null;
		ResultSet resultSet = null;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();

			String sql = String.format("SELECT * FROM %s WHERE customerID = '%s'", PURCHASES_TABLE_NAME, inputId);
			LOG.debug(sql);
			resultSet = statement.executeQuery(sql);
			if (resultSet != null) {
				while (resultSet.next()) {
					aPurchase = new Purchase(Long.valueOf(resultSet.getString(1)),
							customerDao.readByCustomerId(resultSet.getString(2)),
							bookDao.readByBookId(resultSet.getString(3)), Double.valueOf(resultSet.getString(4)));
					purchases.add(aPurchase);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}

		return purchases;
	}

	/**
	 * Method to count total for a specific customer
	 * 
	 * @param inputId is a customer ID
	 * @return total as a double
	 * @throws SQLException
	 */
	public double countTotalForACustomer(String inputId) throws SQLException {
		Statement statement = null;
		double total = 0;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format("SELECT SUM(price) AS total FROM %s WHERE customerId = '%s'",
					PURCHASES_TABLE_NAME, inputId);
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				total = resultSet.getDouble("total");
			}
		} finally {
			close(statement);
		}
		return total;
	}
	

}

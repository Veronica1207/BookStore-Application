/**
 * 
 */
package a01208105.book.data.customers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;
import a01208105.book.db.Dao;
import a01208105.book.db.Database;
import a01208105.book.db.DbConstants;
import a01208105.util.BookStoreSorters;

/**
 * This class describes a customer DAO
 * 
 * @author Veronica A01208105
 * @version 2020-11-08
 */
public class CustomerDao extends Dao {

	public static final String TABLE_NAME = DbConstants.CUSTOMER_TABLE_NAME;

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * CustomerDao Constructor
	 * 
	 * @param database  the database
	 * @param tableName the name of the table
	 * @throws ApplicationException 
	 */
	public CustomerDao(Database database) throws ApplicationException {
		super(database, TABLE_NAME);
		LOG.debug("CustomerDao constructor call");

	}

	/**
	 * Method to create a db table
	 */
	@Override
	public void create() throws SQLException {
		LOG.debug("Creating a customer database table" + tableName);
		String sql = String.format("CREATE TABLE %s(" + "id VARCHAR(6), " // id
				+ "firstName VARCHAR(25), " // First Name
				+ "lastName VARCHAR(30), " // Last Name
				+ "street VARCHAR(60), " // Street
				+ "city VARCHAR(30), " // City
				+ "postalCode VARCHAR(15), " // Postal Code
				+ "phone VARCHAR(30), " // Phone
				+ "emailAddress VARCHAR(50), " // Email
				+ "joinedDate DATE, " // Joined Date
				+ "primary key (id))", // id
				tableName);
		super.create(sql);
		LOG.info("Table " + tableName + " is created");
	}

	/**
	 * Add customer object to the table
	 * 
	 * @param customer
	 * @throws SQLException
	 */
	public void add(Customer customer) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format(
					"insert into %s (id,firstName, lastName, street, city, postalCode, phone, emailAddress, joinedDate) "
							+ "values(" + "'%s', " // id -1
							+ "'%s', " // fn -2
							+ "'%s', " // ln -3
							+ "'%s', " // street -4
							+ "'%s', " // city -5
							+ "'%s', " // postal -6
							+ "'%s', " // phone -7
							+ "'%s', " // email -8
							+ "'%s')", // date -9
							tableName, customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getStreet(),
					customer.getCity(), customer.getPostalCode(), customer.getPhone(), customer.getEmailAddress(),
					customer.getJoinedDate());
			LOG.debug(sql);

			statement.executeUpdate(sql);
			LOG.debug("Customer added: " + sql);
		} finally {
			close(statement);
		}
	}

	/**
	 * Read by customer Id from db
	 * 
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public Customer readByCustomerId(String customerId) throws Exception {
		Statement statement = null;
		Customer customer = null;
		ResultSet resultSet = null;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();

			String sql = String.format("SELECT * FROM %s WHERE id = '%s'", tableName, customerId);
			LOG.debug(sql);
			resultSet = statement.executeQuery(sql);

			int count = 0;
			while (resultSet.next()) {
				count++;
				if (count > 1) {
					throw new Exception(String.format("Expected one result, got %d", count));
				}
				customer = new Customer.Builder(Long.valueOf(resultSet.getString(1)), resultSet.getString(7))
						.setFirstName(resultSet.getString(2)).setLastName(resultSet.getString(3))
						.setStreet(resultSet.getString(4)).setCity(resultSet.getString(5))
						.setPostalCode(resultSet.getString(6)).setEmailAddress(resultSet.getString(8))
						.setJoinedDate(resultSet.getDate(9).toLocalDate()).build();
				LOG.debug(customer);
			}
		} catch (NumberFormatException | SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}

		return customer;
	}

	/**
	 * Update customer data in db
	 * 
	 * @param customer
	 * @throws SQLException
	 */
	public void update(Customer customer) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();

			String sql = String.format(
					("UPDATE %s set id ='%s', " 
							+ "firstName = '%s'," 
							+ "lastName = '%s'," 
							+ "street = '%s',"
							+ "city = '%s'," 
							+ "postalCode = '%s'," 
							+ "phone = '%s'," 
							+ "emailAddress = '%s',"
							+ "joinedDate = '%s'" + "WHERE id = '%s'"),
					TABLE_NAME, customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getStreet(),
					customer.getCity(), customer.getPostalCode(), customer.getPhone(), customer.getEmailAddress(),
					customer.getJoinedDate(), customer.getId());
			LOG.debug(sql);
			int rowCount = statement.executeUpdate(sql);
			System.out.println(String.format("Updated %d rows", rowCount));

		} finally {
			close(statement);
		}
	}

	/**
	 * Method to delete customer data from the db
	 * 
	 * @param customer
	 * @throws SQLException
	 */
	public void delete(Customer customer) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();

			String sql = String.format("DELETE from %s WHERE id = '%s'", TABLE_NAME, customer.getId());
			LOG.debug(sql);
			int rowCount = statement.executeUpdate(sql);
			System.out.println(String.format("Deleted %d rows", rowCount));
		} finally {
			close(statement);
		}
	}

	/**
	 * Method to get ids of all existing customers from db
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<String> getIds() throws SQLException {
		List<String> customerIds = new ArrayList<String>();

		Connection connection;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();

			String sql = String.format("SELECT id FROM %s", TABLE_NAME);
			LOG.debug(sql);
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				customerIds.add(resultSet.getString("id"));
			}
			return customerIds;
		} finally {
			close(statement);
		}
	}

	/**
	 * Method to count all customers fromt he db
	 * @return count as an int
	 * @throws SQLException
	 */
	public int countAllCustomers() throws SQLException {
		Statement statement = null;
		int count = 0;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format("SELECT COUNT(*) AS total FROM %s", TABLE_NAME);
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				count = resultSet.getInt("total");
			}
		} finally {
			close(statement);
		}

		return count;
	}
	
	/**
	 * MEthod to get List of customers
	 * @return customers as a List<Customer>
	 */
	public List<Customer> customerList() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer aCustomer = null;
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format("SELECT * FROM %s", TABLE_NAME);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				
				aCustomer = new Customer.Builder(Long.valueOf(resultSet.getString(1)), resultSet.getString(7))
						.setFirstName(resultSet.getString(2)).setLastName(resultSet.getString(3))
						.setStreet(resultSet.getString(4)).setCity(resultSet.getString(5))
						.setPostalCode(resultSet.getString(6)).setEmailAddress(resultSet.getString(8))
						.setJoinedDate(resultSet.getDate(9).toLocalDate()).build();
				customers.add(aCustomer);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}

		return customers;
	}
	
	/**
	 * Method to sort by Joined Date
	 * @param customers
	 * @return customers as a List<Customer> sorted
	 */
	public List<Customer> customerListByDate(List<Customer> customers) {
		customers.sort(new BookStoreSorters.CompareByJoinedDate());
		return customers;
	}

	

}

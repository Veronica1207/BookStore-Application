/**
 * 
 */
package a01208105.book.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class describes a DAO
 * 
 * @author Veronica A01208105
 * @version 2020-11-08
 */
public abstract class Dao {

	protected final Database database;
	protected final String tableName;

	private static final Logger LOG = LogManager.getLogger();

	protected Dao(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	/**
	 * Abstract method to create a db table
	 * 
	 * @throws SQLException
	 */
	public abstract void create() throws SQLException;

	/**
	 * Method to delete a statement from the db
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	protected void create(String sql) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			LOG.debug(sql);
			statement.executeUpdate(sql);
		} finally {
			close(statement);
		}
	}

	/**
	 * Method to add a statement to the db table
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void add(String sql) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			LOG.debug(sql);
			statement.executeUpdate(sql);
		} finally {
			close(statement);
		}
	}

	/**
	 * Method to delete the db table
	 * 
	 * @throws SQLException
	 */
	public void drop() throws SQLException {
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			if (Database.tableExists(connection, tableName)) {
				String sql = "drop table " + tableName;
				LOG.debug(sql);
				statement.executeUpdate(sql);
			}
		} finally {
			close(statement);
		}
	}

	/**
	 * Method to close the statement
	 * @param statement
	 */
	protected void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			LOG.error("Failed to close statement" + e);
		}
	}

}

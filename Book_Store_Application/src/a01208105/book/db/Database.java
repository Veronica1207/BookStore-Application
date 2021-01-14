package a01208105.book.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class describes a database
 * 
 * @author Veronica A01208105
 * @version 2020-11-08
 */

public class Database {
	private static Logger LOG = LogManager.getLogger();

	private static Connection connection;
	private static Properties properties;

	/**
	 * Database constructor
	 * 
	 * @param properties
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Database(Properties properties) throws FileNotFoundException, IOException {
		LOG.debug("Loading database properties from db.properties");
		Database.properties = properties;
	}

	/**
	 * Accessor for connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (connection != null) {
			return connection;
		}

		try {
			connect();
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
			throw new SQLException(e);
		}

		return connection;
	}

	/**
	 * Method to connect to the database
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void connect() throws ClassNotFoundException, SQLException {
		Class.forName(properties.getProperty(DbConstants.DB_DRIVER_KEY));
		System.out.println("Driver loaded");
		connection = DriverManager.getConnection(properties.getProperty(DbConstants.DB_URL_KEY),
				properties.getProperty(DbConstants.DB_USER_KEY), properties.getProperty(DbConstants.DB_PASSWORD_KEY));
		System.out.println("Database connected");
	}

	/**
	 * Method to shut down the connection
	 */
	public void shutdown() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				LOG.debug("Connection is closeed. Database shut down");
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * Method to check if the table exists
	 * 
	 * @param connection
	 * @param tableName
	 * @return boolean true,if the table exists, false otherwise
	 * @throws SQLException
	 */
	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		String name = null;
		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				name = resultSet.getString("TABLE_NAME");
				if (name.equalsIgnoreCase(tableName)) {
					LOG.debug("Found the target table named: " + tableName);
					return true;
				}
			}
		} finally {
			resultSet.close();
		}

		return false;
	}


}

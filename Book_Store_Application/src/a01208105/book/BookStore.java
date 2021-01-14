package a01208105.book;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import a01208105.book.data.books.BookDao;
import a01208105.book.data.customers.CustomerDao;
import a01208105.book.data.purchases.PurchaseDao;
import a01208105.book.db.Database;
import a01208105.book.db.DbConstants;
import a01208105.book.io.BookReader;
import a01208105.book.io.CustomerReader;
import a01208105.book.io.PurchaseReader;
import a01208105.ui.MainFrame;

/**
 * This is the driver class
 * 
 * @author Veronica A01208105
 * @version 2020-11-28
 */
public class BookStore {

	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static final Logger LOG;
	
	private static Properties properties = null;
	private static Database database;
	public static CustomerDao customerDao;
	public static BookDao bookDao;
	public static PurchaseDao purchaseDao;


	static {
		configureLogging();
		LOG = LogManager.getLogger(BookStore.class);
	}

	/**
	 * Bcmc Constructor.
	 * 
	 * @throws ApplicationException
	 * @throws ParseException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public BookStore() throws ApplicationException, ParseException, FileNotFoundException, IOException {
		LOG.info("Created Bcmc");
		readProperties();
		database = new Database(properties);		
	}

	/**
	 * Entry point to GIS
	 * 
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		Instant startTime = Instant.now();
		LOG.info(startTime);
		// start the Book System
		try {
			BookStore book = new BookStore();
			book.run();
			createUI(customerDao, bookDao, purchaseDao);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		Instant endTime = Instant.now();
		LOG.info(endTime);
		LOG.info(String.format("Duration: %d ms", Duration.between(startTime, endTime).toMillis()));
	}

	/**
	 * Configures log4j2 from the external configuration file specified in
	 * LOG4J_CONFIG_FILENAME. If the configuration file isn't found then log4j2's
	 * DefaultConfiguration is used.
	 */
	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.out.println(String.format(
					"WARNING! Can't find the log4j logging configuration file %s; using DefaultConfiguration for logging.",
					LOG4J_CONFIG_FILENAME));
			Configurator.initialize(new DefaultConfiguration());
		}
	}

	/**
	 * @throws ApplicationException
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	@SuppressWarnings("static-access")
	private void run() throws ApplicationException, IOException, SQLException {
		LOG.debug("run()");		
		
		customerDao = new CustomerDao(database);
		bookDao = new BookDao(database);
		purchaseDao = new PurchaseDao(database);
		
		if(!Database.tableExists(database.getConnection(), CustomerDao.TABLE_NAME)) {
			LOG.debug("RESULT: Customer Table does not exist. Cteating the table " + CustomerDao.TABLE_NAME + "...");
			customerDao.create();
			CustomerReader.read(customerDao);
			LOG.debug("Customer Table Created");
		} 
		
		if(!Database.tableExists(database.getConnection(), BookDao.BOOK_TABLE_NAME)) {
			LOG.debug("RESULT: Book Table does not exist. Cteating the table " + BookDao.BOOK_TABLE_NAME + "...");
			bookDao.create();
			BookReader.readBooks(bookDao);
			LOG.debug("Book Table created");
		}
		
		if(!Database.tableExists(database.getConnection(), PurchaseDao.PURCHASES_TABLE_NAME)) {
			LOG.debug("RESULT: Purchase Table does not exist. Cteating the table " + PurchaseDao.PURCHASES_TABLE_NAME + "...");
			purchaseDao.create();
			try {
				PurchaseReader.readPurchases(customerDao, bookDao, purchaseDao);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
	}


	/**
	 * Method to read properties
	 * @throws ApplicationException
	 */
	public void readProperties() throws ApplicationException {
		properties = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(DbConstants.DB_PROPERTIES_FILENAME);
			properties.load(in);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new ApplicationException(e);
		} finally {
			if(in != null) {
			try {
				in.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
				throw new ApplicationException(e);
			}
		}
	}
	}
	
	/**
	 * Method to create a user interface
	 * @param customerDao
	 * @param bookDao
	 * @param purchaseDao
	 */
	public static void createUI(CustomerDao customerDao, BookDao bookDao, PurchaseDao purchaseDao) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(customerDao, bookDao, purchaseDao);
					frame.setVisible(true);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		});
	}
	
}

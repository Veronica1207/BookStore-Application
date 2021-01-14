/**
 * 
 */
package a01208105.book.db;

/**
 * @author Veronica A01208105
 * @version 2020-11-26
 */
public interface DbConstants {
	String DB_PROPERTIES_FILENAME = "db.properties";
	String DB_DRIVER_KEY = "db.driver";
	String DB_URL_KEY = "db.url";
	String DB_USER_KEY = "db.user";
	String DB_PASSWORD_KEY = "db.password";
	String TABLE_ROOT = "A01208105_";
	String CUSTOMER_TABLE_NAME = TABLE_ROOT + "Customers";
	String BOOK_TABLE_NAME = TABLE_ROOT + "Books";
	String PURCHASE_TABLE_NAME = TABLE_ROOT + "Purchase";
}

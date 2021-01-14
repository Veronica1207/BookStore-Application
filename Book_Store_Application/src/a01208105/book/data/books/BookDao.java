/**
 * 
 */
package a01208105.book.data.books;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.db.Dao;
import a01208105.book.db.Database;
import a01208105.book.db.DbConstants;
import a01208105.util.BookStoreSorters;

/**
 * This class describes a book DAO
 * 
 * @author Veronica A01208105
 * @version 2020-11-29
 */
public class BookDao extends Dao {
	public static final String BOOK_TABLE_NAME = DbConstants.BOOK_TABLE_NAME;

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * BookDao Constructor
	 * 
	 * @param database is an active database
	 */
	public BookDao(Database database) {
		super(database, BOOK_TABLE_NAME);
		LOG.debug("BookDao constructor call");
	}

	/**
	 * Method to create a book db table
	 * 
	 * @throws SQLException
	 */

	public void create() throws SQLException {
		LOG.debug("Creating a book database table: " + BOOK_TABLE_NAME);
		String sql = String.format("CREATE TABLE %s(" + "id VARCHAR(6)," // bookId - long 1
				+ "isbn VARCHAR(20)," // isbn - string 2
				+ "authors VARCHAR(300)," // authors - string 3
				+ "year VARCHAR(5)," // yearPublished - int 4
				+ "title VARCHAR(250)," // title - string 5
				+ "ratingAVR VARCHAR(6)," // ratingAvrg - double 6
				+ "ratingCNT VARCHAR(10)," // ratingCount - long 7
				+ "image VARCHAR(300)," + "primary Key (id))", // imageURL - String 8
				BOOK_TABLE_NAME);
		super.create(sql);
		LOG.info("Table " + tableName + " is created");
	}

	/**
	 * Method to add a book to the database
	 * 
	 * @param book is a Book to add
	 * @throws SQLException
	 */
	public void add(Book book) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format(
					"INSERT into %s (id, isbn, authors, year, title, ratingAVR, ratingCNT, image)"
							+ "VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					BOOK_TABLE_NAME, book.getBookId(), book.getIsbn(), book.getAuthors(), book.getYearPublished(),
					book.getTitle(), book.getRatingAvrg(), book.getRatingCount(), book.getImageURL());

			statement.executeUpdate(sql);
			LOG.debug("Customer added: " + sql);
		} finally {
			close(statement);
		}
	}

	/**
	 * Method to read books based on the id
	 * 
	 * @param bookId is a book id to search
	 */
	public Book readByBookId(String bookId) {
		Statement statement = null;
		Book book = null;
		ResultSet resultSet = null;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();

			String sql = String.format("SELECT * FROM %s WHERE id = '%s'", tableName, bookId);
			LOG.debug(sql);
			resultSet = statement.executeQuery(sql);
			// resultSet.next();
			while (resultSet.next()) {
				book = new Book(Long.valueOf(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3),
						Integer.parseInt(resultSet.getString(4)), resultSet.getString(5),
						Double.valueOf(resultSet.getString(6)), Long.valueOf(resultSet.getString(7)),
						resultSet.getString(8));
				LOG.debug(book);
			}

		} catch (NumberFormatException | SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}

		return book;

	}

	/**
	 * Method to count all books
	 * 
	 * @throws SQLException
	 * @return count as an int
	 */
	public int countAllBooks() throws SQLException {
		Statement statement = null;
		int count = 0;

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format("SELECT COUNT(*) AS total FROM %s", BOOK_TABLE_NAME);
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
	 * Method to create a collection of books
	 * 
	 * @return books as List<Book>
	 */
	public List<Book> bookList() {
		List<Book> books = new ArrayList<Book>();
		Book aBook = null;
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			String sql = String.format("SELECT * FROM %s", BOOK_TABLE_NAME);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				aBook = new Book(Long.valueOf(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3),
						Integer.parseInt(resultSet.getString(4)), resultSet.getString(5),
						Double.valueOf(resultSet.getString(6)), Long.valueOf(resultSet.getString(7)),
						resultSet.getString(8));
				books.add(aBook);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}

		return books;
	}

	/**
	 * Method to sort by Author with a desc option
	 * 
	 * @param books     is a list of books to sort
	 * @param descOrder true if descending option is chose; otherwise false
	 * @return books as List<Book>
	 */

	public List<Book> bookListByAuthor(List<Book> books, boolean descOrder) {
		books.sort(new BookStoreSorters.CompareByAuthor());
		if (descOrder == true) {
			Collections.reverse(books);
		}
		return books;
	}

}

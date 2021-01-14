package a01208105.book.data.books;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class describes a Book
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 */
public class Book {

	private static final Logger LOG = LogManager.getLogger();

	public static final int CURRENT_YEAR = 2020;
	public final static double RATING_MAX = 5.0;
	public final static int DEFAULT_YEAR = 0000;

	private long bookId;
	private String isbn;
	private String authors;
	private int yearPublished;
	private String title;
	private double ratingAvrg;
	private long ratingCount;
	private String imageURL;

	/**
	 * Default constructor
	 */
	public Book() {
		super();
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param bookId is an id of the book
	 * @param isbn is an isdn number of the book
	 * @param authors are author(s) of the book
	 * @param yearPublished is the year the book was published
	 * @param title is a title of the book
	 * @param ratingAvrg is an average rating of the book our of the RATING_MAX
	 * @param ratingCount is a rating count of the book
	 * @param imageURL is an image URL to display the book cover
	 */
	public Book(long bookId, String isbn, String authors, int yearPublished, String title, double ratingAvrg,
			long ratingCount, String imageURL) {
		setBookId(bookId);
		setIsbn(isbn);
		setAuthors(authors);
		setYearPublished(yearPublished);
		setTitle(title);
		setRatingAvrg(ratingAvrg);
		setRatingCount(ratingCount);
		setImageURL(imageURL);
		
		LOG.debug("Book class constructor");
	}

	/**
	 * Accessor for the bookID field
	 * 
	 * @return the bookId as long
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * Mutator for the bookId field
	 * 
	 * @param bookId the bookId to set
	 */
	public void setBookId(long bookId) {
		if (bookId > 0) {
			this.bookId = bookId;
		} else {
			System.out.println("Please enter a valid book ID");
			LOG.debug("Invalid book ID");
		}
	}

	/**
	 * Accessor for the isbn field
	 * 
	 * @return the isbn as String
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Mutator for the isbn field
	 * 
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		if (isbn != null && !isbn.trim().isEmpty()) {
			this.isbn = isbn;
		} else {
			this.isbn = "N/A";
			LOG.info("Incomplete record entry - isbn");
		}
	}

	/**
	 * Accessor for the authors field
	 * 
	 * @return the authors as String
	 */
	public String getAuthors() {
		return authors;
	}

	/**
	 * Mutator for the authors field
	 * 
	 * @param authors the authors to set
	 */
	public void setAuthors(String authors) {
		if (authors != null && !authors.trim().isEmpty()) {
			this.authors = authors;
		} else {
			this.authors = "TBD";
			LOG.info("Incomplete record entry - authors");
		}
	}

	/**
	 * Accessor for the yearPublished field
	 * 
	 * @return the yearPublished as an int
	 */
	public int getYearPublished() {
		return yearPublished;
	}

	/**
	 * Mutator for the yearPublished field
	 * 
	 * @param yearPublished the yearPublished to set
	 */
	public void setYearPublished(int yearPublished) {
		if (yearPublished > 0 && yearPublished < CURRENT_YEAR) {
			this.yearPublished = yearPublished;
		} else {
			this.yearPublished = DEFAULT_YEAR;
			LOG.info("Incomplete record entry - yearPublished");
		}
	}

	/**
	 * Accessor for the title field
	 * 
	 * @return the title as a String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Mutator for the title field
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if (title != null && !title.trim().isEmpty()) {
			this.title = title;
		} else {
			this.title = "N/A";
			LOG.info("Incomplete record entry - title");
		}
	}

	/**
	 * Accessor for the ratingAvrg field
	 * 
	 * @return the ratingAvrg as a double
	 */
	public double getRatingAvrg() {
		return ratingAvrg;
	}

	/**
	 * Mutator for the ratingAvrg field
	 * 
	 * @param ratingAvrg the ratingAvrg to set
	 */
	public void setRatingAvrg(double ratingAvrg) {
		if (ratingAvrg >= 0 && ratingAvrg <= RATING_MAX) {
			this.ratingAvrg = ratingAvrg;
		} else {
			this.ratingAvrg = 0;
			LOG.info("Incomplete record entry - ratingAvrg");
		}
	}

	/**
	 * Accessor for the ratingCount field
	 * 
	 * @return the ratingCount as long
	 */
	public long getRatingCount() {
		return ratingCount;
	}

	/**
	 * Mutator for the ratingCount field
	 * 
	 * @param ratingCount the ratingCount to set
	 */
	public void setRatingCount(long ratingCount) {
		if (ratingCount >= 0) {
			this.ratingCount = ratingCount;
		} else {
			this.ratingCount = 0;
			LOG.info("Incomplete record entry - ratingCount");
		}
	}

	/**
	 * Accessor for the imagURL field
	 * 
	 * @return the imageURL as a String
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * Mutator for the imageURL field
	 * 
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		if(imageURL != null && !imageURL.trim().isEmpty()) {
		this.imageURL = imageURL;
		} else {
			this.imageURL = "N/A";
			LOG.info("Incomplete record entry - imageURL");
		}
	}

	/**
	 * Debug to String method
	 */
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", isbn=" + isbn + ", authors=" + authors + ", yearPublished=" + yearPublished
				+ ", title=" + title + ", ratingAvrg=" + ratingAvrg + ", ratingCount=" + ratingCount + ", imageURL="
				+ imageURL + "]";
	}

}

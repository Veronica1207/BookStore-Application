/**
 * 
 */
package a01208105.util;

import java.util.Comparator;

import a01208105.book.data.books.Book;
import a01208105.book.data.customers.Customer;
import a01208105.book.data.purchases.Purchase;

/**
 * This class implements various sorting methods
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 */
public class BookStoreSorters {
	
	/**
	 * Compare by joined date
	 */
	public static class CompareByJoinedDate implements Comparator<Customer> {
		@Override
		public int compare(Customer customer1, Customer customer2) {
			return customer1.getJoinedDate().compareTo(customer2.getJoinedDate());
		}
		
	}
	
	/**
	 * Compare by Author
	 */
	public static class CompareByAuthor implements Comparator<Book> {
		@Override
		public int compare(Book book1, Book book2) {
			return book1.getAuthors().compareToIgnoreCase(book2.getAuthors());
		}
	}
	
	/**
	 * Compare by Customer's last name
	 */
	public static class CompareByLastName implements Comparator <Purchase> {

		@Override
		public int compare(Purchase p1, Purchase p2) {
			return p1.getCustomer().getLastName().compareToIgnoreCase(p2.getCustomer().getLastName());
		}
		
	}
	
	/**
	 * Compare by Book's title
	 */
	public static class CompareByTitle implements Comparator <Purchase> {

		@Override
		public int compare(Purchase p1, Purchase p2) {
			return p1.getBook().getTitle().compareToIgnoreCase(p2.getBook().getTitle());
		}
		
	}
	
		
}

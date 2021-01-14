/**
 * 
 */
package a01208105.book.data.purchases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.data.books.Book;
import a01208105.book.data.customers.Customer;

/**
 * This class describes purchases and links book and customer objects together
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 */
public class Purchase {

	private static final Logger LOG = LogManager.getLogger();

	private long purchaseID;
	private double price;
	Customer customer;
	Book book;

	/**
	 * Default constructor
	 */
	public Purchase() {
		super();
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param purchaseID is an id of the purchase
	 * @param price      is a price of the purchase
	 * @param customer   is a Customer object reference
	 * @param book       is a Book object reference
	 */
	public Purchase(long purchaseID, Customer customer, Book book, double price) {
		super();
		setPurchaseID(purchaseID);
		setCustomer(customer);
		setBook(book);
		setPrice(price);
	}

	/**
	 * Accessor for the purchaseID field
	 * 
	 * @return the purchaseID
	 */
	public long getPurchaseID() {
		return purchaseID;
	}

	/**
	 * Mutator for the purchaseID field
	 * 
	 * @param purchaseID the purchaseID to set
	 */
	public void setPurchaseID(long purchaseID) {
		if (purchaseID > 0) {
			this.purchaseID = purchaseID;
		} else {
			System.out.println("Please enter a valid purchase Id number");
			LOG.debug("Invalid purchase ID entry");
		}
	}

	/**
	 * Accessor for the price field
	 * 
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Mutator for the price field
	 * 
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		if (price > 0) {
			this.price = price;
		} else {
			System.out.println("Please enter a valid price");
			LOG.debug("Invalid price entry");
		}
	}

	/**
	 * Accessor for the customer field
	 * 
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Mutator for the customer field
	 * 
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		if (customer != null) {
			this.customer = customer;
		} else {
			System.out.println("The customer cannot be null");
			LOG.debug("Customer is null");
		}
	}

	/**
	 * Accessor for the book field
	 * 
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * Mutator for the book field
	 * 
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		if (book != null) {
			this.book = book;
		} else {
			System.out.println("book cannot be null");
			LOG.debug("Book is null");
		}
	}

	/**
	 * Debug toString method
	 */
	@Override
	public String toString() {
		return "Purchase [purchaseID=" + purchaseID + ", price=" + price + ", customer=" + customer + ", book=" + book
				+ "]";
	}

}

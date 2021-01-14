/**
 * Project: A00123456Lab1
 * File: Customer.java
 */

package a01208105.book.data.customers;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class describes a customer
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 */
public class Customer {

	private static final Logger LOG = LogManager.getLogger();

	public static final int ATTRIBUTE_COUNT = 9;

	private long id;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String postalCode;
	private String phone;
	private String emailAddress;
	private LocalDate joinedDate;

	public static class Builder {
		// Required parameters
		private final long id;
		private final String phone;

		// Optional parameters
		private String firstName;
		private String lastName;
		private String street;
		private String city;
		private String postalCode;
		private String emailAddress;
		private LocalDate joinedDate;

		public Builder(long id, String phone) {
			this.id = id;
			this.phone = phone;
		}

		/**
		 * @param firstName the firstName to set
		 * @return the Customer Builder
		 */
		public Builder setFirstName(String firstName) {
			if (firstName != null && !firstName.trim().isEmpty()) {
				this.firstName = firstName;
			}
			return this;
		}

		/**
		 * @param lastName the lastName to set
		 * @return the Customer Builder
		 */
		public Builder setLastName(String lastName) {
			if (lastName != null && !lastName.trim().isEmpty()) {
				this.lastName = lastName;
			}
			return this;
		}

		/**
		 * @param street the street to set
		 * @return the Customer Builder
		 */
		public Builder setStreet(String street) {
			if (street != null && !street.trim().isEmpty()) {
				this.street = street;
			}
			return this;
		}

		/**
		 * @param city the city to set
		 * @return the Customer Builder
		 */
		public Builder setCity(String city) {
			if (city != null && !city.trim().isEmpty()) {
				this.city = city;
			}
			return this;
		}

		/**
		 * @param postalCode the postalCode to set
		 * @return the Customer Builder
		 */
		public Builder setPostalCode(String postalCode) {
			if (postalCode != null && !postalCode.trim().isEmpty()) {
				this.postalCode = postalCode;
			}
			return this;
		}

		/**
		 * @param emailAddress the emailAddress to set
		 * @return the Customer Builder
		 */
		public Builder setEmailAddress(String emailAddress) {
			if (emailAddress != null && !emailAddress.trim().isEmpty()) {
				this.emailAddress = emailAddress;
			}
			return this;
		}

		/**
		 * Set the joined date
		 *
		 * @param year  the year, includes the century, ex. 1967
		 * @param month the month - must be 1-based
		 * @param day   the day of the month - 1-based
		 * @return the Customer Builder
		 */
		public Builder setJoinedDate(int year, int month, int day) {
			joinedDate = LocalDate.of(year, month, day);
			return this;
		}

		/**
		 * Set the joined date
		 *
		 * @param date the local date
		 * @return the Customer Builder
		 */
		public Builder setJoinedDate(LocalDate date) {
			joinedDate = date;
			return this;
		}

		/**
		 * Build method
		 * 
		 * @return new Customer object
		 */
		public Customer build() {
			return new Customer(this);
		}
	}

	/**
	 * Default Constructor
	 */
	private Customer(Builder builder) {
		id = builder.id;
		firstName = builder.firstName;
		lastName = builder.lastName;
		street = builder.street;
		city = builder.city;
		postalCode = builder.postalCode;
		phone = builder.phone;
		emailAddress = builder.emailAddress;
		joinedDate = builder.joinedDate;

		LOG.debug("Customer class constructor");
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		if (id > 0) {
			this.id = id;
		} else {
			System.out.print("Customer ID is required.");
			LOG.debug("No customer ID specified");
		}
	}

	/**
	 * Accessor for id field
	 * 
	 * @return the id as long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Accessor for the firstName field
	 * 
	 * @return the firstName as a String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		if (firstName != null && !firstName.trim().isEmpty() && firstName.length() <= 40) {
			this.firstName = firstName;
		} else {
			System.out.println("Please enter the customer's first name");
			LOG.debug("No first name specified");
		}
	}

	/**
	 * Accessor for the lastName field
	 * 
	 * @return the lastName as a String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		if (lastName != null && !lastName.trim().isEmpty()) {
			this.lastName = lastName;
		} else {
			System.out.println("Please enter the customer's last name");
			LOG.debug("No last name specified");
		}
	}

	/**
	 * Accessor for the street field
	 * 
	 * @return the street as a String
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		if (street != null && !street.trim().isEmpty()) {
			this.street = street;
		} else {
			System.out.println("Please enter the customer's street name");
			LOG.debug("No street specified");
		}
	}

	/**
	 * Accessor for the city field
	 * 
	 * @return the city as a String
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		if (city != null && !city.trim().isEmpty()) {
			this.city = city;
		} else {
			System.out.println("Please enter the customer's city");
			LOG.debug("No city specified");
		}
	}

	/**
	 * Accessor for the postalCode field
	 * 
	 * @return the postalCode as a String
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		if (postalCode != null && !postalCode.trim().isEmpty()) {
			this.postalCode = postalCode;
		} else {
			System.out.println("Please enter the customer's postal code");
			LOG.debug("No postal code specified");
		}
	}

	/**
	 * Accessor for the phone field
	 * 
	 * @return the phone as a String
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		if (phone != null && !phone.trim().isEmpty()) {
			this.phone = phone;
		} else {
			System.out.println("Customer's phone number is a required field");
			LOG.debug("No phone number specified");
		}
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		if (emailAddress != null && !emailAddress.trim().isEmpty()) {
			this.emailAddress = emailAddress;
		} else {
			System.out.println("Please enter the customer's email address");
			LOG.debug("No email specified");
		}
	}

	/**
	 * Accessor for the email field
	 * 
	 * @return the emailAddress as a String
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Set the joined date
	 *
	 * @param date the local date
	 */
	public void setJoinedDate(LocalDate joinedDate) {
		if (joinedDate != null) {
			this.joinedDate = joinedDate;
		} else {
			System.out.println("Please enter the customer's joined date");
			LOG.debug("No joined date specified");
		}
	}

	/**
	 * Set the joined date
	 *
	 * @param year  the year, includes the century, ex. 1967
	 * @param month the month - must be 1-based
	 * @param day   the day of the month - 1-based
	 */
	public void setJoinedDate(int year, int month, int day) {
		joinedDate = LocalDate.of(year, month, day);
	}

	/**
	 * Accessor for the joinedDate field
	 * 
	 * @return joinedDate as a LocalDate
	 */
	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", street=" + street
				+ ", city=" + city + ", postalCode=" + postalCode + ", phone=" + phone + ", emailAddress="
				+ emailAddress + ", joinedDate=" + joinedDate + "]";
	}

}

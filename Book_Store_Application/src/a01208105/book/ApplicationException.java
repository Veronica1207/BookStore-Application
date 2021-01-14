
package a01208105.book;

/**
 * This class creates an exception
 * 
 * @author Veronica A01208105
 * @version 2020-10-24
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	/**
	 * Default constructor
	 */
	public ApplicationException() {
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param message            is the detail message
	 * @param cause              is the cause
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param message is the detail message
	 * @param cause   is the cause
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param message is the detail message
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param cause is the cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

}

/**
 * Project: A00123456Lab3
 * File: Common.java
 */

package a01208105.util;

import java.time.format.DateTimeFormatter;

/**
 * @author Veronica A01208105
 * @version 2020-10-24
 */
public class Common {

	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
	
	/**
	 * Method to ellipse an entry String
	 * @param text text to evaluate and ellipse if too long
	 * @param length length to ellipse to
	 * @return edited String
	 */
	public static String ellipsis(String text, int length)
	 {
	    if (text.length() > length) {
	    	return text.substring(0, length - 3) + "...";
	    } else {
	    	return text;
	    }
	 }
}

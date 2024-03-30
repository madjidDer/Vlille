package exceptions;

/**
 * Exception thrown when a redistribution process in the bike station management system cannot be completed.
 * This exception might be used in scenarios where the redistribution of vehicles between stations fails due to various reasons such as lack of capacity, network issues, etc.
 */
@SuppressWarnings("serial")
public class RedistribuationNotCompletedException extends Exception {

	/**
     * Constructs a new RedistribuationNotCompletedException with the specified detail message.
     * 
     * @param s The detail message which provides more information on why the redistribution process failed.
     */
	public RedistribuationNotCompletedException(String s) {
		super(s);
	}
}

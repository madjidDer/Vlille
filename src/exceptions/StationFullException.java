package exceptions;

/**
 * Exception thrown when an operation cannot be completed because the station is full.
 * This can occur in scenarios where there's an attempt to add more vehicles to a station that has already reached its capacity.
 */
@SuppressWarnings("serial")
public class StationFullException extends Exception{

	/**
     * Constructs a new StationFullException with the specified detail message.
     *
     * @param s The detail message which provides more information on why the exception was thrown.
     */
	public StationFullException(String s) {
		super(s);
	}

}

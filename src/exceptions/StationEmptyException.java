package exceptions;

/**
 * Exception thrown when an operation cannot be completed because the station is empty.
 * This exception is typically raised in scenarios such as attempting to remove or rent a vehicle from an empty station.
 */
@SuppressWarnings("serial")
public class StationEmptyException extends Exception{

	/**
     * Constructs a new StationEmptyException with the specified detail message.
     *
     * @param s The detail message which provides more information on why the exception was thrown, typically explaining which station is empty.
     */
	public StationEmptyException(String s)  {
		super(s);
	}
}

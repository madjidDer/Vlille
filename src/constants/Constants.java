package constants;

/**
 * The Constants class contains static constant values used throughout the bike station management system.
 * This class provides a centralized location for managing fixed values that are used in various parts of the application.
 */
public class Constants {
    
    /**
     * The maximum number of rentals allowed for a vehicle before an intervention is required.
     * This constant is used to determine when a vehicle needs maintenance or inspection after a certain number of rentals.
     */
    public final static int MAX_NB_OF_RENTALS_BEFORE_INTERVANTION = 20;

    /**
     * Interval time in milliseconds.
     * This constant might be used for timing operations, representing a time interval of 1 second.
     */
    public static final long INTERVAL = 1000; // 1 second

    /**
     * The number of stations in a control center.
     * This constant is used to define how many stations are managed or monitored by a single control center.
     */
    public static final int NB_OF_STATION_IN_A_CONTROLCENTER = 10; 
}
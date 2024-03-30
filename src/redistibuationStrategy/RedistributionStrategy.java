package redistibuationStrategy;

import java.util.ArrayList;
import exceptions.RedistribuationNotCompletedException;
import stations.Station;

/**
 * The RedistributionStrategy interface defines methods for redistributing vehicles between stations
 * in a bike sharing system. Implementations of this interface should provide the logic for redistribution
 * when stations are either full or empty.
 */
public interface RedistributionStrategy {
	
	/**
     * Method to be called when a station is empty. This method should handle the redistribution logic
     * to ensure that the empty station receives vehicles from other stations.
     *
     * @param station The station that is currently empty and needs vehicles.
     * @param stations A list of all stations, potentially used for determining where to source vehicles from.
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed successfully.
     */
    void redistributeWhenEmpty(Station station, ArrayList<Station> stations) throws RedistribuationNotCompletedException;
    
    /**
     * Method to be called when a station is full. This method should handle the redistribution logic
     * to move excess vehicles from the full station to other stations.
     *
     * @param station The station that is currently full and needs to offload vehicles.
     * @param stations A list of all stations, potentially used for determining where to move the excess vehicles to.
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed successfully.
     */
    void redistributeWhenFull(Station station, ArrayList<Station> stations) throws RedistribuationNotCompletedException;
    
    /**
     * Overrides the equals method to compare redistribution strategy objects.
     *
     * @param o The object to compare with the current redistribution strategy.
     * @return true if the specified object is equal to the current redistribution strategy; false otherwise.
     */
    @Override
    boolean equals(Object o);
}


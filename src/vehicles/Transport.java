package vehicles;

import persons.AbstractPerson;
import stations.Station;

/**
 * The Transport interface defines common behaviors and properties for transport vehicles.
 * It includes methods for managing vehicle's location, usage statistics, and interactions with persons.
 */
public interface Transport {

	/**
     * Gets the unique identifier of the transport.
     * 
     * @return The unique ID of the transport.
     */
	int getId();
	
	/**
     * Gets the station where the transport is currently located.
     * 
     * @return The current Station of the transport.
     */
    Station getStation();
    
    /**
     * Gets the number of times the transport has been rented.
     * 
     * @return The number of rentals for the transport.
     */
    int getNumberOfRentals();
    
    /**
     * Sets the station of the transport.
     * 
     * @param station The new Station where the transport will be located.
     */
    void setStation(Station station);
    
    /**
     * Increases the rental count of the transport by one.
     * Typically called when the transport is rented out.
     */
    void increaseNumberOfRentals();
    
    /**
     * Resets the rental count of the transport to zero.
     * Typically used for maintenance or administrative purposes.
     */
    void resetNumberOfRentals();

    /**
     * Accepts a visit from an AbstractPerson, allowing for interaction.
     * This method can be used to implement various actions performed by different types of persons.
     * 
     * @param p The AbstractPerson interacting with the transport.
     */
    void accept(AbstractPerson p);

    /**
     * Provides a string representation of the transport's current state.
     * This method can be used for decorating the transport with additional features or information.
     * 
     * @return A string representing the transport's state.
     */
    String decorate();
}

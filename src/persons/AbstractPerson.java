package persons;

import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import stations.Station;
import vehicles.Vehicle;

/**
 * Abstract base class representing a person in the transportation system.
 * This class provides a framework for defining interactions that different types of people
 * can have with vehicles and stations. It can be extended to implement specific behaviors 
 * for different categories of people.
 */
public abstract class AbstractPerson {

	/**
     * Protected constructor for AbstractPerson.
     * This constructor is protected to ensure that only subclasses can instantiate AbstractPerson,
     * as it is meant to be an abstract representation.
     */
	protected AbstractPerson() {};
	
	/**
     * Defines the interaction a person has with a vehicle.
     * This method can be overridden by subclasses to implement specific behavior when visiting a vehicle.
     *
     * @param v The vehicle being visited by the person.
     */
	public void visit(Vehicle v) {
		
	}
	
	/**
     * Defines the interaction a person has with a station.
     * This method can be overridden by subclasses to implement specific behavior when visiting a station.
     * 
     * @param s The station being visited by the person.
     * @return A Vehicle object, which could represent a vehicle being taken or interacted with at the station.
     * @throws RedistribuationNotCompletedException if the redistribution process at the station cannot be completed.
     * @throws StationEmptyException if the station is empty during the interaction.
     */
	public Vehicle visit(Station s) throws RedistribuationNotCompletedException, StationEmptyException {
		return null;
	}
	
	/**
     * Abstract method to compare this AbstractPerson with another object for equality.
     * Subclasses should provide an implementation for this method.
     *
     * @param o The object to compare with this AbstractPerson.
     * @return true if the specified object is considered equal to this AbstractPerson; false otherwise.
     */
	public abstract boolean equals(Object o);
}
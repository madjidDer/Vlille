package persons;

import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import stations.Station;
import vehicles.Vehicle;

/**
 * Represents a client in the transportation system.
 * This class extends the AbstractPerson class, providing specific implementations
 * for a client's interactions with vehicles and stations, particularly focusing on renting vehicles.
 */
public class Client extends AbstractPerson{
	String name;

	/**
     * Constructs a new Client with a specified name.
     *
     * @param name The name of the client.
     */
	public Client(String name) {
		this.name = name;
	}
	
	/**
     * Defines the interaction a client has with a station, typically involving renting a vehicle.
     * This method overrides the visit method in AbstractPerson to implement behavior specific to clients.
     *
     * @param s The station being visited by the client.
     * @return The Vehicle object that the client rents from the station.
     * @throws RedistribuationNotCompletedException if the redistribution process at the station cannot be completed.
     * @throws StationEmptyException if the station is empty and no vehicle is available for rent.
     */
	@Override
	public Vehicle visit(Station s) throws RedistribuationNotCompletedException, StationEmptyException {
		Vehicle v = s.rentVehicle();
		return v;
	}
	
	/**
     * Compares this Client with another object for equality.
     * The equality is based on the type of the object, ensuring it is an instance of Client.
     *
     * @param o The object to compare with this Client.
     * @return true if the specified object is also a Client; false otherwise.
     */
	@Override
	public boolean equals(Object o) {
		return o instanceof Client;
	}
	
	/**
     * Provides a string representation of the Client.
     *
     * @return The name of the client.
     */
    @Override
	public String toString() {
		return this.name;
	}
}

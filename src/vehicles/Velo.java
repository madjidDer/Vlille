package vehicles;

import stations.Station;

/**
 * Abstract class representing a bike (Velo) in the transportation system.
 * This class extends the Vehicle class, inheriting its common properties and behaviors.
 * It serves as a base class for different types of bikes in the system.
 */
public abstract class Velo extends Vehicle {

	/**
     * Constructs a new Velo (bike) with a given ID and station.
     * Initializes the Velo by calling the constructor of the superclass, Vehicle.
     *
     * @param id The unique identifier of the Velo (bike).
     * @param station The station where the Velo (bike) is initially located.
     */
    public Velo(int id, Station station) {
    	super(id, station);
    }
}

package persons;

import stations.Station;
import vehicles.Vehicle;

/**
 * The `Thief` class represents a character who can visit a station
 * and steal a vehicle.
 */
public class Theif extends AbstractPerson{

	/**
     * Default constructor for the `Thief` class.
     */
	public Theif() {
	}
	
	/**
     * Visits a station and steals a vehicle.
     *
     * @param s The station to visit.
     * @return The stolen vehicle, if available; otherwise, returns null.
     */
	public Vehicle visit(Station s) {
		Vehicle v = s.stealVehicle();
		return v;
	}

	/**
     * Checks if an object is equal to this instance of `Thief`.
     *
     * @param o The object to compare.
     * @return true if the object is an instance of `Thief`, otherwise false.
     */
	@Override
	public boolean equals(Object o) {
		return o instanceof Theif;
	}
}

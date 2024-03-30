package vehicles;

import stations.Station;

/**
 * Represents a classic, non-electric bike (ClassicVelo) in the transportation system.
 * This class extends the Velo class, providing specific functionalities for a classic bike.
 */
public class ClassicVelo extends Velo {

	/**
     * Constructs a new ClassicVelo (classic bike) with a given ID and station.
     * Initializes the classic bike with the given identifier and station.
     *
     * @param id The unique identifier of the classic bike.
     * @param station The station where the classic bike is initially located.
     */
	public ClassicVelo(int id,Station station) {
    	super(id, station);
	}
	
	/**
     * Provides a string representation of the classic bike including its ID.
     *
     * @return A string representation of the classic bike.
     */
	@Override
	public String decorate() {
		return "A classic bike n°: "+this.getId();
	}
}

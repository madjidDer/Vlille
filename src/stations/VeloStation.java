package stations;

/**
 * Represents a VeloStation, a specific type of station designed for bikes (velos) in the transportation system.
 * This class extends the Station class, inheriting its basic functionalities and properties, and 
 * may have additional features or behaviors specific to bike stations.
 */
public class VeloStation extends Station {

	/**
     * Constructs a new VeloStation with the specified ID, name, and maximum capacity.
     * This constructor initializes a station specifically for bikes (velos) with given characteristics.
     *
     * @param id The unique identifier of the VeloStation.
     * @param name The name of the VeloStation.
     * @param maxCapacite The maximum capacity of bikes (velos) that the station can accommodate.
     */
	public VeloStation(int id, String name, int maxCapacite) {
		super(id, name, maxCapacite);
	}
}

package vehicles;

import stations.Station;

public class ClassicVeloTest extends VehicleTest{

	@Override
	protected Vehicle createVehicle(int id, Station station) {
		return new ClassicVelo(id, station);
	}
}

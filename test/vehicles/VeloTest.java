package vehicles;

import stations.Station;

public abstract class VeloTest extends VehicleTest {
	@Override
    protected abstract Vehicle createVehicle(int id, Station station);

}

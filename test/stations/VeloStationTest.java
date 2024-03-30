package stations;

public class VeloStationTest extends StationTest {

	@Override
	protected Station createStation(int id, String name, int maxCapacite) {
		return new VeloStation(id, name, maxCapacite);
	}

}

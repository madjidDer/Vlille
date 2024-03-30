package vehicles;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stations.*;

public class ElectricVeloTest extends VehicleTest{

	private ElectricVelo electricVelo;
    private Station station;
    

    @Override
    protected Vehicle createVehicle(int id, Station station) {
        return new ElectricVelo(id, station);
    }

    @BeforeEach
    public void init() {
    	this.station = new VeloStation(1, "station", 20);
        this.electricVelo = new ElectricVelo(1, station);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(1, this.electricVelo.getId());
        assertEquals(this.station, this.electricVelo.getStation());
        assertEquals(100, this.electricVelo.getBatteryLevel());
    }

    @Test
    public void testRechargeBatteryLevel() {
        electricVelo.decreaseBatteryLevel(50);
        electricVelo.rechargeBatteryLevel();
        assertEquals(100, electricVelo.getBatteryLevel());
    }

    @Test
    public void testDecreaseBatteryLevel() {
        electricVelo.decreaseBatteryLevel(20);
        assertEquals(80, electricVelo.getBatteryLevel());
    }

    @Test
    public void testGetBatteryLevel() {
        assertEquals(100, electricVelo.getBatteryLevel());
    }

}

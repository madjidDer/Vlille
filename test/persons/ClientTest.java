package persons;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;

import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import stations.Station;
import stations.VeloStation;
import vehicles.ClassicVelo;
import vehicles.ElectricVelo;
import vehicles.Vehicle;

public class ClientTest {

	protected Vehicle vehicle;
    protected Station station;
    protected Client client;
    
    @BeforeEach
    public void setUp() {
    	this.station = new VeloStation(1,"station1",5);
        this.vehicle = new ClassicVelo(1, this.station);
        ElectricVelo velo1 = new ElectricVelo(2, null);
        ElectricVelo velo2 = new ElectricVelo(2, null);
        this.station.addVehicle(vehicle);
        this.station.addVehicle(velo1);
        this.station.addVehicle(velo2);
        this.client = new Client("mimou");
    }
    
    @Test
    public void visitTest() throws RedistribuationNotCompletedException, StationEmptyException {
    	assertEquals(3, this.station.getNbVehicles());
        this.client.visit(this.station);
        assertEquals(2, this.station.getNbVehicles());
    }

}

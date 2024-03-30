package persons;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stations.Station;
import stations.VeloStation;
import vehicles.ClassicVelo;
import vehicles.Vehicle;
import vehiclesState.BrokenDown;
import vehiclesState.Disponible;

public class MechanicTest {

	protected Vehicle vehicle;
    protected Station station;
    protected Mechanic mechanic;
    
    @BeforeEach
    public void setUp() {
    	this.station = new VeloStation(1,"station1",5);
        this.vehicle = new ClassicVelo(1, this.station);
        this.vehicle.setState(new BrokenDown(this.vehicle));
        this.station.addVehicle(vehicle);
        this.mechanic = new Mechanic();
    }
    
    @Test
    public void visitTest() throws InterruptedException {
    	this.mechanic.visit(this.vehicle);
    	assertTrue(this.vehicle.getState().equals(new Disponible(this.vehicle)));
    	assertEquals(this.vehicle.getNumberOfRentals(), 0);
    }

}

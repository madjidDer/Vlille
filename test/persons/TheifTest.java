package persons;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stations.Station;
import stations.VeloStation;
import vehicles.ClassicVelo;
import vehicles.ElectricVelo;
import vehicles.Vehicle;

public class TheifTest {

	protected Vehicle vehicle;
    protected Station station;
    protected Theif theif;
    
    @BeforeEach
    public void setUp() {
    	this.station = new VeloStation(1,"station1",5);
        this.vehicle = new ClassicVelo(1, this.station);
        ElectricVelo velo1 = new ElectricVelo(2, null);
        ElectricVelo velo2 = new ElectricVelo(2, null);
        this.station.addVehicle(velo1);
        this.station.addVehicle(velo2);
        this.station.addVehicle(vehicle);
        this.theif = new Theif();
    }
   
    @Test
    public void visitTest() {
    	assertTrue(this.vehicle.getState().toString().equals("Disponible"));
    	assertEquals(this.station.getNbVehicles(), 3);
    	this.theif.visit(this.station);
    	assertEquals(this.station.getNbVehicles(), 2);
    	assertTrue(this.vehicle.getState().toString().equals("Stolen"));
    }

}

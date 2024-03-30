package controlCenters;

import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import redistibuationStrategy.ClassicStrategy;
import redistibuationStrategy.RandomStrategy;
import redistibuationStrategy.RedistributionStrategy;
import redistribuationStrategy.MockStrategy;
import stations.Station;
import stations.VeloStation;
import vehicles.ClassicVelo;
import vehicles.Vehicle;
import vehiclesState.BrokenDown;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




public class ControlCenterTest {

    private ControlCenter controlCenter;
    private RedistributionStrategy strategy;
    private Station station;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        strategy = new ClassicStrategy();
        station = new VeloStation(100,"station1",1);
        controlCenter = ControlCenter.getInstanceControlCenter();
        vehicle = new ClassicVelo(1,station);
        controlCenter.setStrategy(strategy);
    }
    
    @Test
    public void testControlCenterCreatedCorrectly() {
        assertEquals(this.controlCenter, ControlCenter.getInstanceControlCenter());
    }

    @Test
    public void testAddStation() {
        controlCenter.addStation(station);
        assertTrue(controlCenter.getStations().contains(station));
        controlCenter.removeStation(station);
    }

    @Test
    public void testRemoveStation() {
    	ControlCenter.getInstanceControlCenter().addStation(station);
        ControlCenter.getInstanceControlCenter().removeStation(station);
        assertFalse(ControlCenter.getInstanceControlCenter().getStations().contains(station));
    }

    @Test
    public void testUpdateStationWhenEmpty() throws RedistribuationNotCompletedException {
    	MockStrategy st = new MockStrategy();
    	this.controlCenter.setStrategy(st);
    	assertEquals(0,st.cptCalledWhenEmpty);
    	this.controlCenter.update(this.station,false);
    	assertEquals(1,st.cptCalledWhenEmpty);
    			}
    

    @Test
    public void testUpdateStationWhenFull() throws RedistribuationNotCompletedException {
    	MockStrategy st = new MockStrategy();
    	this.controlCenter.setStrategy(st);
    	assertEquals(0,st.cptCalledWhenFull);
    	this.station.addVehicle(this.vehicle);
    	this.controlCenter.update(this.station,true);
    	assertEquals(1,st.cptCalledWhenFull);
    }

    @Test
    public void testUpdateVehicle() throws RedistribuationNotCompletedException, StationEmptyException {
    	this.vehicle.setState(new BrokenDown(this.vehicle));
    	assertEquals("BrokenDown", this.vehicle.getState().toString());
    	this.controlCenter.update(vehicle,false);
    	this.controlCenter.repaireAllVehicles();
    	assertEquals("Disponible", this.vehicle.getState().toString());
    }

    @Test
    public void testSetVeloStations() {
    	// initialement on a 20 station + 5, on auras 25 en tout 
        controlCenter.setVeloStations(5);
        assertEquals(15, controlCenter.getStations().size());
    }
    
    @Test
    public void testgetStrategy() {
        assertTrue(controlCenter.getStrategy().equals(this.strategy));
        
    }
    
    @Test
    public void testsetStrategy() {
    	RandomStrategy rs3 = new RandomStrategy();
        controlCenter.setStrategy(rs3);
        assertTrue(controlCenter.getStrategy().equals(rs3));
        
    }
}

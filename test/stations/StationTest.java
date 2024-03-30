package stations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import constants.Constants;
import controlCenters.ControlCenter;
import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import exceptions.StationFullException;
import persons.Client;
import redistibuationStrategy.ClassicStrategy;
import redistribuationStrategy.MockStrategy;
import vehicles.ClassicVelo;
import vehicles.ElectricVelo;
import vehicles.Vehicle;
import vehiclesState.BrokenDown;

public abstract class StationTest {
	
	protected Vehicle vehicle;
    protected Station station;
    protected abstract Station createStation(int id, String name, int maxCapacite);
    protected MockStrategy ms;
    
    @BeforeEach
    public void setUp() {
    	
        this.station = this.createStation(1, "StationName", 5);
        this.vehicle = new ClassicVelo(1, this.station);
        this.ms = new MockStrategy();
        ControlCenter.getInstanceControlCenter().setStrategy(ms);
    }
    
    @Test
    public void testGetName() {
        assertEquals("StationName", station.getName());
    }

    @Test
    public void testGetStationID() {
        assertEquals(1, station.getStationID());
    }

    @Test
    public void testGetNbVehicles() {
        assertEquals(0, station.getNbVehicles());
    }

    @Test
    public void testGetMaxCapacite() {
        assertEquals(5, station.getMaxCapacite());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(station.isEmpty());
    }

    @Test
    public void testIsFull() {
        Vehicle vehicle2 = new ClassicVelo(2, this.station);
        Vehicle vehicle3 = new ElectricVelo(3, this.station);
        Vehicle vehicle4 = new ClassicVelo(4, this.station);
        Vehicle vehicle5 = new ElectricVelo(5, this.station);
        assertTrue(this.station.isEmpty());
        this.station.addVehicle(this.vehicle);
        this.station.addVehicle(vehicle2);
        this.station.addVehicle(vehicle3);
        this.station.addVehicle(vehicle4);
        this.station.addVehicle(vehicle5);
        assertTrue(this.station.isFull());
    }

    @Test
    public void testOnlyOneLeft() {
    	this.station.addVehicle(this.vehicle);
        assertTrue(station.onlyOneLeft());
    }

    @Test
    public void testVehicleStateAfterRent() throws RedistribuationNotCompletedException, StationEmptyException {
    	this.station.addVehicle(this.vehicle);
        this.station.rentVehicle();
        assertEquals(this.vehicle.getState().toString(), "Rented");
    }
    
    @Test
    public void testTimeToStealBecomesTrueWhenOnlyOneLeftAfterRent() throws RedistribuationNotCompletedException, StationEmptyException, Exception {
    	ControlCenter.getInstanceControlCenter().addStation(this.station);
    	Vehicle vehicle2 = new ClassicVelo(2, this.station);
    	this.station.addVehicle(vehicle2);
    	this.station.addVehicle(vehicle);
        station.rentVehicle();

        Thread.sleep(Constants.INTERVAL*2+100);
        assertTrue(station.isEmpty());
    }
    
    @Test
    public void testTimeToRedistibuateBecomesTrueWhenStationIsEmptyAfterRent() throws RedistribuationNotCompletedException, StationEmptyException, Exception {
    	this.station.addVehicle(vehicle);
    	assertEquals(0, this.ms.cptCalledWhenEmpty);
        station.rentVehicle();
        Thread.sleep(Constants.INTERVAL*2+100);
        assertEquals(3, this.ms.cptCalledWhenEmpty);
    }
    
    @Test
    public void testRentVehicleCallsNotifyObserver() throws RedistribuationNotCompletedException, StationEmptyException, InterruptedException {
    	MockStation mockStation = new MockStation(667,"leao",17);
    	mockStation.addVehicle(vehicle);
    	assertEquals(0,mockStation.cptNotifyCalled);
        mockStation.rentVehicle();
        assertTrue(mockStation.getVehicles().isEmpty());
        Thread.sleep(Constants.INTERVAL*2 + 100);
        assertEquals(1,mockStation.cptNotifyCalled);
    }
    
    @Test
    public void testRentVehicleThrowsStationEmptyException() {
        Executable rentVehicle = () -> station.rentVehicle();
        assertThrows(StationEmptyException.class, rentVehicle);
    }
    
    @Test
    public void testVehicleStateAfterDeposit() throws RedistribuationNotCompletedException, StationFullException, StationEmptyException {
    	this.station.addVehicle(vehicle);
        this.station.rentVehicle();
        this.station.deposit(this.vehicle);
        assertEquals(this.vehicle.getState().toString(), "Disponible");
    }
    
    @Test
    public void testTimeToStealBecomesTrueWhenOnlyOneLeftAfterDeposit() throws RedistribuationNotCompletedException, StationEmptyException, Exception {
    	this.station.addVehicle(vehicle);
        station.rentVehicle();
        this.station.deposit(this.vehicle);
        Thread.sleep(Constants.INTERVAL*2+100);
        assertTrue(this.station.isEmpty());
    }
    
    @Test
    public void testTimeToRedistibuateBecomesTrueWhenStationIsFullAfterDeposit() throws RedistribuationNotCompletedException, StationEmptyException, Exception {
    	MockStation s = new MockStation(15, null, 5);
    	Vehicle vehicle2 = new ClassicVelo(2, this.station);
        Vehicle vehicle3 = new ElectricVelo(3, this.station);
        Vehicle vehicle4 = new ClassicVelo(4, this.station);
        Vehicle vehicle5 = new ElectricVelo(5, this.station);
        assertTrue(s.isEmpty());
        s.addVehicle(this.vehicle);
        s.addVehicle(vehicle2);
        s.addVehicle(vehicle3);
        s.addVehicle(vehicle4);
        s.addVehicle(vehicle5);
        assertTrue(s.isFull());
        assertEquals(0, s.cptNotifyCalled);
        s.rentVehicle();
        s.deposit(this.vehicle);
        Thread.sleep(Constants.INTERVAL*2 + 100);
        assertEquals(1, s.cptNotifyCalled);
    }
    
    @Test
    public void testDepositVehicleCallsNotifyObserver() throws RedistribuationNotCompletedException, StationEmptyException, StationFullException, InterruptedException {
    	MockStation mockStation = new MockStation(667,"leao",17);
    	this.station.addVehicle(vehicle);
    	assertEquals(0,mockStation.cptNotifyCalled);
    	this.station.rentVehicle();
        mockStation.deposit(vehicle);
        Thread.sleep(Constants.INTERVAL*2+100);
        assertEquals(1,mockStation.cptNotifyCalled);
    }
    
    @Test
    public void testDepositVehicleThrowsStationFullException() throws RedistribuationNotCompletedException, StationEmptyException {
    	Vehicle vehicle2 = new ClassicVelo(2, this.station);
        Vehicle vehicle3 = new ElectricVelo(3, this.station);
        Vehicle vehicle4 = new ClassicVelo(4, this.station);
        Vehicle vehicle5 = new ElectricVelo(5, this.station);
        Vehicle vehicle6 = new ElectricVelo(6, this.station);
        assertTrue(this.station.isEmpty());
        this.station.addVehicle(vehicle6);
        this.station.addVehicle(vehicle2);
        this.station.addVehicle(vehicle3);
        this.station.addVehicle(vehicle4);
        this.station.addVehicle(vehicle5);
        assertTrue(this.station.isFull());
        Executable depositVehicle = () -> station.deposit(this.vehicle);
        assertThrows(StationFullException.class, depositVehicle);
    }
    
    @Test
    public void testTimeToRedistibuateStaysFalseAfterDeposit() throws RedistribuationNotCompletedException, StationFullException, StationEmptyException {
    	this.station.addVehicle(vehicle);
    	Vehicle vehicle5 = new ElectricVelo(5, this.station);
        Vehicle vehicle6 = new ElectricVelo(6, this.station);
        this.station.addVehicle(vehicle6);
        this.station.addVehicle(vehicle5);
        this.station.rentVehicle();
        this.station.deposit(this.vehicle);
        assertFalse(this.station.isTimeToRedistibuate());
    }
    
    @Test
    public void testTimeToStealStaysFalseAfterDeposit() throws RedistribuationNotCompletedException, StationFullException, StationEmptyException {
    	this.station.addVehicle(vehicle);
    	Vehicle vehicle5 = new ElectricVelo(5, this.station);
        Vehicle vehicle6 = new ElectricVelo(6, this.station);
        this.station.addVehicle(vehicle6);
        this.station.addVehicle(vehicle5);
        this.station.rentVehicle();
        this.station.deposit(this.vehicle);
        assertFalse(this.station.isTimeToSteal());
    }

    @Test
    public void testStealVehicle() {
    	this.station.addVehicle(this.vehicle);
        station.stealVehicle();
        assertEquals(this.vehicle.getState().toString(), "Stolen");
        assertTrue(this.station.isEmpty());
    }
    
    @Test
    public void testTimeToRedistibuateBecomesTrueWhenStationIsEmptyAfterSteal() throws RedistribuationNotCompletedException, StationEmptyException, Exception {
    	this.station.addVehicle(vehicle);
        station.stealVehicle();

        Thread.sleep(Constants.INTERVAL*2 + 100);
        assertEquals(1, ms.cptCalledWhenEmpty);
    }

    @Test
    public void testAccept() throws RedistribuationNotCompletedException, StationEmptyException, StationFullException {
        Client person = new Client("tarek");
        this.station.addVehicle(this.vehicle);
        assertEquals(0, this.vehicle.getNumberOfRentals());
        station.accept(person);
        station.deposit(this.vehicle);
        assertEquals(1, this.vehicle.getNumberOfRentals());
    }
    
    @Test
    public void testEquals() {
        assertTrue(station.equals(station)); //SameObject
        Station otherStation = createStation(1, "AnotherStation", 5);
        assertTrue(station.equals(otherStation));
        Station otherStation2 = createStation(2, "AnotherStation", 5);
        assertFalse(station.equals(otherStation2));
        assertFalse(station.equals(null));
        Object otherObject = new Object();
        assertFalse(station.equals(otherObject));
    }
    
    @Test
    public void testHasSurplusVehicles() {
    	Vehicle vehicle2 = new ClassicVelo(2, this.station);
        Vehicle vehicle3 = new ElectricVelo(3, this.station);
        Vehicle vehicle4 = new ClassicVelo(4, this.station);
        this.station.addVehicle(this.vehicle);
        assertFalse(station.hasSurplusVehicles());
        this.station.addVehicle(vehicle2);
        assertFalse(station.hasSurplusVehicles());
        this.station.addVehicle(vehicle3);
        this.station.addVehicle(vehicle4);
        assertTrue(station.hasSurplusVehicles());
    }
    
    @Test
    public void testHasInsufficiencyVehicles() {
    	Vehicle vehicle2 = new ClassicVelo(2, this.station);
    	Vehicle vehicle3 = new ElectricVelo(3, this.station);
        this.station.addVehicle(this.vehicle);
        assertTrue(station.hasInsufficiencyVehicles());
        this.station.addVehicle(vehicle2);
        assertFalse(station.hasInsufficiencyVehicles());
        this.station.addVehicle(vehicle3);
        assertFalse(station.hasInsufficiencyVehicles());
    }

    @Test
    public void testIsSufficientlyFilled() {
    	Vehicle vehicle2 = new ClassicVelo(2, this.station);
    	Vehicle vehicle3 = new ElectricVelo(3, this.station);
        this.station.addVehicle(this.vehicle);
        assertFalse(this.station.isSufficientlyFilled());
        this.station.addVehicle(vehicle2);
        assertTrue(this.station.isSufficientlyFilled());
        this.station.addVehicle(vehicle3);
        assertTrue(this.station.isSufficientlyFilled());
    }
    
    @Test
    public void testNotifyObserverWhenIsTimeToRedistibuateAfterRent() {
    	this.station.setTimeToRedistibuate(true);
    	ControlCenter.getInstanceControlCenter().setStrategy(new ClassicStrategy());
    	Executable notifyObserver = () -> station.notifyObserver(true,this.vehicle);
        assertThrows(RedistribuationNotCompletedException.class, notifyObserver);
    }
    
    @Test
    public void testNotifyObserverWhenIsTimeToRedistibuateAfterDeposit() throws RedistribuationNotCompletedException, StationEmptyException {
    	Vehicle vehicle2 = new ClassicVelo(2, this.station);
        Vehicle vehicle3 = new ElectricVelo(3, this.station);
        Vehicle vehicle4 = new ClassicVelo(4, this.station);
        Vehicle vehicle5 = new ElectricVelo(5, this.station);
        assertTrue(this.station.isEmpty());
        this.station.addVehicle(this.vehicle);
        this.station.addVehicle(vehicle2);
        this.station.addVehicle(vehicle3);
        this.station.addVehicle(vehicle4);
        this.station.addVehicle(vehicle5);
        assertTrue(this.station.isFull());
        assertEquals(0,ms.cptCalledWhenFull);
    	this.station.setTimeToRedistibuate(true);
    	//ControlCenter.getInstanceControlCenter().setStrategy(new ClassicStrategy());
    	this.station.notifyObserver(false,this.vehicle);
    	//assertFalse(this.station.isFull());
    	assertEquals(1,ms.cptCalledWhenFull);
    }
    
    @Test
    public void testNotifyObserverWhenIsTimeToStealAfterRent() throws RedistribuationNotCompletedException, StationEmptyException {
    	this.station.addVehicle(this.vehicle);
    	this.station.setTimeToSteal(true);
    	this.station.setTimeToRedistibuate(false);
    	assertEquals(this.vehicle.getState().toString(),"Disponible");
    	this.station.notifyObserver(true, this.vehicle);
    	assertEquals(this.vehicle.getState().toString(),"Stolen");
    }
    
    @Test
    public void testNotifyObserverWhenIsTimeToStealAfterDeposit() throws RedistribuationNotCompletedException, StationEmptyException {
    	this.station.addVehicle(this.vehicle);
    	this.station.setTimeToSteal(true);
    	this.station.setTimeToRedistibuate(false);
    	assertEquals(this.vehicle.getState().toString(),"Disponible");
    	this.station.notifyObserver(false, this.vehicle);
    	assertEquals(this.vehicle.getState().toString(),"Stolen");
    }
    
    @Test
    public void testNotifyObserverWhenIsTimeToRepairVehicleAfterDeposit() throws RedistribuationNotCompletedException, StationEmptyException, Exception {
    	this.vehicle.setState(new BrokenDown(this.vehicle));
    	this.station.addVehicle(this.vehicle);
    	this.station.notifyObserver(false, this.vehicle);
    	ControlCenter.getInstanceControlCenter().repaireAllVehicles();
    	Thread.sleep(Constants.INTERVAL + 100);
    	assertEquals(this.vehicle.getState().toString(),"Disponible");
    }
    
}

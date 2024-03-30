package vehicles;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stations.Station;
import stations.VeloStation;

public abstract class VehicleTest {
    protected Vehicle vehicle;
    protected Station station;
    protected abstract Vehicle createVehicle(int id, Station station);

    @BeforeEach
    public void setUp() {
    	
        this.station = new VeloStation(1, "StationName", 20);
        this.vehicle = this.createVehicle(1, station);
    }  

    @Test
    public void testGetId() {
        assertEquals(1, vehicle.getId());
    }

    @Test
    public void testGetStation() {
        assertEquals(station, vehicle.getStation());
    }

    @Test
    public void testGetNumberOfRentals() {
        assertEquals(0, vehicle.getNumberOfRentals());
    }

    @Test
    public void testIncreaseNumberOfRentals() {
        vehicle.increaseNumberOfRentals();
        assertEquals(1, vehicle.getNumberOfRentals());
    }

    @Test
    public void testResetNumberOfRentals() {
        vehicle.increaseNumberOfRentals();
        vehicle.resetNumberOfRentals();
        assertEquals(0, vehicle.getNumberOfRentals());
    }
    
    @Test
    public void testEquals() {
    	Vehicle vehicle2 = createVehicle(2, station);
        Vehicle vehicle3 = createVehicle(3, station);

        // Tester l'égalité entre deux véhicules avec des IDs différents
        assert !vehicle.equals(vehicle2) : "Test 1 Failed";

        // Tester l'égalité entre deux véhicules avec le même ID
        assert vehicle.equals(vehicle) : "Test 2 Failed";

        // Tester l'égalité entre deux véhicules de types différents
        assert !vehicle.equals(vehicle3) : "Test 3 Failed";

        // Création d'un nouveau véhicule avec le même ID que vehicle1
        Vehicle vehicle4 = createVehicle(1, station);

        // Tester l'égalité entre vehicle1 et vehicle4
        assert vehicle.equals(vehicle4) : "Test 4 Failed";

        // Création d'un véhicule avec un ID différent
        Vehicle vehicle5 = createVehicle(5, station);

        // Tester l'égalité entre vehicle1 et vehicle5
        assert !vehicle.equals(vehicle5) : "Test 5 Failed";
    }

}
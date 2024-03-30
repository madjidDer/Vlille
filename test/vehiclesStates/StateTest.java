package vehiclesStates;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stations.Station;
import stations.VeloStation;
import vehicles.ClassicVelo;
import vehicles.Vehicle;
import vehiclesState.Disponible;
import vehiclesState.Rented;
import vehiclesState.State;

public abstract class StateTest {
	protected State state;
	protected Vehicle vehicle;
    protected Station station;
    protected abstract State createState(Vehicle v);
    
    @BeforeEach
    public void setUp() {
    	this.station = new VeloStation(1, "Porte des postes", 5);
        this.vehicle = new ClassicVelo(1, this.station);
        this.state = this.createState(this.vehicle);
    }
    
    @Test
    public void testEqualsWithSameState() {
        State state1 = new Disponible(vehicle);
        State state2 = new Disponible(vehicle);

        assertEquals(state1, state2);
    }

    @Test
    public void testEqualsWithDifferentState() {
        State state1 = new Disponible(vehicle);
        State state2 = new Rented(vehicle);

        assertNotEquals(state1, state2);
    }

    @Test
    public void testEqualsWithNull() {
        State state = new Disponible(vehicle);

        assertNotEquals(state, null);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        State state = new Disponible(vehicle);

        assertNotEquals(state, new Object());
    }

    @Test
    public void testEqualsWithItself() {
        State state = new Disponible(vehicle);

        assertEquals(state, state);
    }

}

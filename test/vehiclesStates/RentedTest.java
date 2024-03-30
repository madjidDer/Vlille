package vehiclesStates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import vehicles.ClassicVelo;
import vehicles.Vehicle;
import vehiclesState.BrokenDown;
import vehiclesState.Disponible;
import vehiclesState.Rented;
import vehiclesState.State;

public class RentedTest extends StateTest {
	
	@Override
	protected State createState(Vehicle v) {
		return new Rented(v);
	}

    @Test
    public void testActionToBrokenDown() {
        Vehicle vehicle = new ClassicVelo(1, null);
        for (int i=0; i<20; i++)
        	vehicle.increaseNumberOfRentals();
        Rented state = new Rented(vehicle);
        state.action(false);
        assertTrue(vehicle.getState() instanceof BrokenDown);
    }

    @Test
    public void testActionToDisponible() {
        Vehicle vehicle = new ClassicVelo(1, null);
        Rented state = new Rented(vehicle);
        state.action(false);
        assertTrue(vehicle.getState() instanceof Disponible);
    }

}


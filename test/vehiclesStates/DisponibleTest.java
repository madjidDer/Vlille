package vehiclesStates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import vehicles.ClassicVelo;
import vehicles.Vehicle; // Ensure this is imported correctly
import vehiclesState.Disponible;
import vehiclesState.Rented;
import vehiclesState.State;
import vehiclesState.Stolen;

public class DisponibleTest extends StateTest {
	
	@Override
	protected State createState(Vehicle v) {
		return new Disponible(v);
	}

    @Test
    public void testActionToSteal() {
        Vehicle vehicle = new ClassicVelo(1, null);
        Disponible state = new Disponible(vehicle);
        state.action(true);
        assertTrue(vehicle.getState() instanceof Stolen);
    }

    @Test
    public void testActionNotToSteal() {
        Vehicle vehicle = new ClassicVelo(1, null);
        Disponible state = new Disponible(vehicle);
        state.action(false);
        assertTrue(vehicle.getState() instanceof Rented);
    }

}

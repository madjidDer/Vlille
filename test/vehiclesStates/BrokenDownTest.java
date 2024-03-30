package vehiclesStates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import vehicles.ClassicVelo;
import vehicles.Vehicle;
import vehiclesState.BrokenDown;
import vehiclesState.CurrentlyBeingRepaired;
import vehiclesState.State;
import vehiclesState.Stolen;

public class BrokenDownTest extends StateTest {
	
	@Override
	protected State createState(Vehicle v) {
		return new BrokenDown(v);
	}

    @Test
    public void testActionToSteal() {
        Vehicle vehicle = new ClassicVelo(1, null);
        BrokenDown state = new BrokenDown(vehicle);
        state.action(true);
        assertTrue(vehicle.getState() instanceof Stolen);
    }

    @Test
    public void testActionNotToSteal() {
        Vehicle vehicle = new ClassicVelo(1, null);
        BrokenDown state = new BrokenDown(vehicle);
        state.action(false);
        assertTrue(vehicle.getState() instanceof CurrentlyBeingRepaired);
    }

}


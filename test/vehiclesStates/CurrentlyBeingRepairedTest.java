package vehiclesStates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import vehicles.ClassicVelo;
import vehicles.Vehicle;
import vehiclesState.CurrentlyBeingRepaired;
import vehiclesState.Disponible;
import vehiclesState.State;

public class CurrentlyBeingRepairedTest extends StateTest {
	
	@Override
	protected State createState(Vehicle v) {
		return new CurrentlyBeingRepaired(v);
	}

    @Test
    public void testActionToDisponible() {
        Vehicle vehicle = new ClassicVelo(1, null);
        CurrentlyBeingRepaired state = new CurrentlyBeingRepaired(vehicle);
        state.action(false);
        assertTrue(vehicle.getState() instanceof Disponible);
    }

}

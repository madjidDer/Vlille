package vehiclesStates;


import vehicles.Vehicle;
import vehiclesState.State;
import vehiclesState.Stolen;

public class StolenTest extends StateTest {
	
	@Override
	protected State createState(Vehicle v) {
		return new Stolen(v);
	}

}

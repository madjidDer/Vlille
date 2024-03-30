package vehiclesState;

import vehicles.Vehicle;

/**
 * Represents the 'BrokenDown' state of a vehicle in the transportation system.
 * In this state, the vehicle is non-operational due to malfunction or damage.
 * This class extends the State class and provides specific behavior for the broken down state of a vehicle.
 */
public class BrokenDown extends State{

	/**
     * Constructs a new BrokenDown state for a given vehicle.
     *
     * @param vehicle The vehicle that is in a broken-down state.
     */
	public BrokenDown(Vehicle vehicle) {
        super(vehicle);
    }

	/**
     * Performs an action based on the broken-down state of the vehicle.
     * If 'toSteal' is false, the vehicle transitions to the 'CurrentlyBeingRepaired' state.
     * If 'toSteal' is true, the vehicle transitions to the 'Stolen' state.
     *
     * @param toSteal A boolean parameter that determines the next state of the vehicle.
     */
	@Override
	public void action(Boolean toSteal) {
		if(!toSteal) {
				this.vehicle.setState(new CurrentlyBeingRepaired(this.vehicle));
		}
		else {
			this.vehicle.setState(new Stolen(this.vehicle));
		}
	}

	/**
     * Provides a string representation of the BrokenDown state.
     *
     * @return A string "BrokenDown", representing the state.
     */
	@Override
    public String toString() {
		return "BrokenDown";
    }

}

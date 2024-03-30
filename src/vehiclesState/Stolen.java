package vehiclesState;

import vehicles.Vehicle;

/**
 * Represents the 'Stolen' state of a vehicle in the transportation system.
 * This state indicates that the vehicle has been stolen. The class extends the State class
 * and provides behavior specific to vehicles in a stolen state.
 */
public class Stolen extends State {
	
	/**
     * Constructs a new Stolen state for a given vehicle.
     *
     * @param vehicle The vehicle that has been stolen.
     */
    public Stolen(Vehicle vehicle) {
        super(vehicle);
    }

    /**
     * Performs an action based on the Stolen state of the vehicle.
     * Currently, this method does not perform any action, but it can be extended
     * to implement behavior specific to when a vehicle is stolen.
     *
     * @param toSteal A boolean parameter, currently not used but can be utilized for future extensions.
     */
	@Override
	public void action(Boolean toSteal) {}

	/**
     * Provides a string representation of the Stolen state.
     *
     * @return A string "Stolen", representing the state.
     */
    @Override
    public String toString() {
		return "Stolen";
    }
}
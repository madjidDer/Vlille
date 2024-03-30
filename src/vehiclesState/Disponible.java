package vehiclesState;

import vehicles.Vehicle;

/**
 * Represents the 'Disponible' (Available) state of a vehicle in the transportation system.
 * In this state, the vehicle is available for rent. This class extends the State class
 * and provides specific behavior for vehicles that are currently available for use.
 */
public class Disponible extends State {

	/**
     * Constructs a new Disponible (Available) state for a given vehicle.
     *
     * @param vehicle The vehicle that is available for use.
     */
	public Disponible(Vehicle vehicle) {
        super(vehicle);
    }
	
	/**
     * Performs an action based on the Disponible (Available) state of the vehicle.
     * If 'toSteal' is false, the vehicle transitions to the 'Rented' state,
     * indicating that the vehicle has been rented out.
     * If 'toSteal' is true, the vehicle transitions to the 'Stolen' state.
     *
     * @param toSteal A boolean parameter that determines the next state of the vehicle.
     */
	@Override
	public void action(Boolean toSteal) {
		if(!toSteal) {
			this.vehicle.setState(new Rented(this.vehicle));
		}
		else {
			this.vehicle.setState(new Stolen(this.vehicle));
		}
		
	}

	/**
     * Provides a string representation of the Disponible (Available) state.
     *
     * @return A string "Disponible", representing the state.
     */
	@Override
    public String toString() {
		return "Disponible";
    }

}

package vehiclesState;

import vehicles.Vehicle;

/**
 * Represents the 'CurrentlyBeingRepaired' state of a vehicle in the transportation system.
 * In this state, the vehicle is undergoing repairs. This class extends the State class
 * and provides specific behavior for vehicles that are currently being repaired.
 */
public class CurrentlyBeingRepaired extends State {
	
	/**
     * Constructs a new CurrentlyBeingRepaired state for a given vehicle.
     *
     * @param vehicle The vehicle that is currently being repaired.
     */
    public CurrentlyBeingRepaired(Vehicle vehicle) {
        super(vehicle);
    }

    /**
     * Performs an action based on the CurrentlyBeingRepaired state of the vehicle.
     * If 'toSteal' is false, the vehicle transitions to the 'Disponible' (Available) state,
     * indicating that the repairs are completed and the vehicle is ready for use.
     * If 'toSteal' is true, no action is performed and the state remains unchanged.
     * (This behavior may be modified in future to handle different scenarios).
     *
     * @param toSteal A boolean parameter that determines the next state of the vehicle.
     */
	@Override
	public void action(Boolean toSteal) {
		if(!toSteal) {
				this.vehicle.setState(new Disponible(this.vehicle));
		}
	}

	/**
     * Provides a string representation of the CurrentlyBeingRepaired state.
     *
     * @return A string "CurrentlyBeingRepaired", representing the state.
     */
	@Override
    public String toString() {
		return "CurrentlyBeingRepaired";
    }
}


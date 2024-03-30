package vehiclesState;

import vehicles.Vehicle;

/**
 * Abstract base class representing the state of a Vehicle in the transportation system.
 * This class is part of the State design pattern, which allows an object to alter its behavior when its internal state changes.
 */
public abstract class State {

	protected Vehicle vehicle;

	/**
     * Constructs a State with a specified vehicle.
     * This constructor initializes the state with a vehicle whose state is being represented.
     *
     * @param vehicle The vehicle associated with this state.
     */
    public State(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Defines an action that the vehicle performs depending on its current state.
     * This is an abstract method that must be implemented by concrete state subclasses.
     *
     * @param toSteal A boolean parameter that may influence the state's action.
     */
    public abstract void action(Boolean toSteal);
    
    /**
     * Provides a string representation of the state.
     * This is an abstract method that must be implemented by concrete state subclasses to describe the state.
     *
     * @return A string representing the state.
     */
    @Override
    public abstract String toString();
    
    /**
     * Compares this State with another object for equality.
     * Two states are considered equal if they represent the same state for a vehicle.
     *
     * @param o The object to compare with this State.
     * @return true if the specified object is also a State and represents the same state; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
    	if (o instanceof State) {
    		State other = (State) o;
    		return this.toString().equals(other.toString());
    	} else {
    		return false;
    	}
    }
}



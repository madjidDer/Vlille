package vehicles;

import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import persons.AbstractPerson;
import persons.Mechanic;
import stations.Station;
import vehiclesState.Disponible;
import vehiclesState.State;

/**
 * Abstract base class representing a vehicle in the transportation system.
 * This class provides common fields and methods for different types of vehicles,
 * including ID, station location, rental count, and current state.
 */
public abstract class Vehicle {
    protected int id;
    protected Station station;
    protected int nbOfRental;
    protected State state;
    
    /**
     * Constructs a Vehicle with a given ID and station.
     * Initializes the rental count to zero and sets the initial state to Disponible.
     *
     * @param id The unique identifier of the vehicle.
     * @param station The station where the vehicle is initially located.
     */
    public Vehicle(int id, Station station) {
        this.id = id;
        this.station = station;
        this.nbOfRental = 0;
        this.state = new Disponible(this);
    }

    /**
     * Gets the unique ID of the vehicle.
     *
     * @return The ID of the vehicle.
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Sets the current state of the vehicle.
     *
     * @param state The new state to be set for the vehicle.
     */
    public void setState(State state) {
    	this.state=state;
    }
    
    /**
     * Gets the current state of the vehicle.
     *
     * @return The current State of the vehicle.
     */
    public State getState() {
    	return this.state;
    }

    /**
     * Gets the station where the vehicle is currently located.
     *
     * @return The current Station of the vehicle.
     */
    public Station getStation() {
        return this.station;
    }
    
    /**
     * Updates the station location of the vehicle.
     *
     * @param station The new Station where the vehicle will be located.
     */
    public void updateStation(Station station) {
    	this.station=station;
    }
    
    /**
     * Gets the number of times the vehicle has been rented.
     *
     * @return The number of rentals of the vehicle.
     */
    public int getNumberOfRentals() {
        return this.nbOfRental;
    }

    /**
     * Increases the rental count of the vehicle by one.
     */
    public void increaseNumberOfRentals() {
    	this.nbOfRental++;
    }

    /**
     * Resets the rental count of the vehicle to zero.
     */
    public void resetNumberOfRentals() {
        this.nbOfRental = 0;
    }
    
    /**
     * Accepts a visit from an AbstractPerson, allowing for interaction.
     * The behavior of this method varies depending on the type of person (e.g., Mechanic).
     *
     * @param p The AbstractPerson interacting with the vehicle.
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed.
     * @throws StationEmptyException If the station is empty when trying to perform an action.
     */
    public void accept(AbstractPerson p) throws RedistribuationNotCompletedException, StationEmptyException {
    	if(p.equals(new Mechanic())) {
    		p.visit(this);
    	}
    	else {
    		p.visit(this.station);
    	}
	}
    
    /**
     * Compares this Vehicle with another object for equality.
     *
     * @param o The object to compare with this Vehicle.
     * @return true if the specified object is also a Vehicle with the same ID; false otherwise.
     */
    public boolean equals(Object o) {
    	if (o instanceof Vehicle) {
    		Vehicle other = (Vehicle) o;
    		return other.getId()==this.id;
    	} else {
    		return false;
    	}
	}
    
    /**
     * Provides a string representation of the vehicle's current state.
     * This abstract method must be implemented by subclasses to decorate the vehicle.
     *
     * @return A string representing the decorated state of the vehicle.
     */
    public abstract String decorate(); 
}

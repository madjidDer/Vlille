package vehicleDecorators;

import vehicles.Vehicle;

/**
 * A decorator class that adds a painting feature to a vehicle.
 * This class extends the Decorator class and is used to enhance a vehicle object 
 * by adding a painting description to it.
 */
public class Painting extends Decorator {
	    
	/**
     * Constructs a Painting decorator with a specified vehicle.
     * This constructor allows for adding a painting feature to the given vehicle.
     * 
     * @param vehicle The vehicle to which the painting feature will be added.
     */
	public Painting(Vehicle vehicle) {
		super(vehicle);
	}

	/**
     * Decorates the vehicle with painting.
     * This method extends the decoration of the vehicle by appending ", painted" to its description.
     * 
     * @return A string representation of the vehicle decorated with painting.
     */
	@Override
	public String decorate() {
		return decoratedVehicle.decorate() + ", painted";
	}
}

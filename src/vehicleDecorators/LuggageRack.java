package vehicleDecorators;

import vehicles.Vehicle;

/**
 * A decorator class that adds a luggage rack feature to a vehicle.
 * This class extends the Decorator class and is used to enhance a vehicle object 
 * by adding a luggage rack description to it.
 */
public class LuggageRack extends Decorator {
	
	/**
     * Constructs a LuggageRack decorator with a specified vehicle.
     * This constructor allows for adding a luggage rack feature to the given vehicle.
     * 
     * @param vehicle The vehicle to which the luggage rack feature will be added.
     */
    public LuggageRack(Vehicle vehicle) {
        super(vehicle);
    }

    /**
     * Decorates the vehicle with a luggage rack.
     * This method extends the decoration of the vehicle by appending ", with a luggage rack" to its description.
     * 
     * @return A string representation of the vehicle decorated with a luggage rack.
     */
    @Override
    public String decorate() {
        return decoratedVehicle.decorate() + ", with a luggage rack";
    }
}

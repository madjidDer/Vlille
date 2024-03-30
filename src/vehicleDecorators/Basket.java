package vehicleDecorators;

import vehicles.Vehicle;

/**
 * A decorator class that adds a basket feature to a vehicle.
 * This class extends the Decorator class and is used to enhance a vehicle object 
 * by adding a basket description to it.
 */
public class Basket extends Decorator {
	
	/**
     * Constructs a Basket decorator with a specified vehicle.
     * This constructor allows for adding a basket feature to the given vehicle.
     * 
     * @param vehicle The vehicle to which the basket feature will be added.
     */
    public Basket(Vehicle vehicle) {
        super(vehicle);
    }

    /**
     * Decorates the vehicle with a basket.
     * This method extends the decoration of the vehicle by appending ", with a basket" to its description.
     * 
     * @return A string representation of the vehicle decorated with a basket.
     */
    @Override
    public String decorate() {
        return decoratedVehicle.decorate() + ", with a basket";
    }
}

package vehicleDecorators;

import vehicles.Vehicle;

/**
 * Abstract decorator class that extends Vehicle and serves as the base for all decorators in the vehicle decoration system.
 * This class is a part of the Decorator pattern and is used to add additional responsibilities or features to Vehicle objects dynamically.
 */
public abstract class Decorator extends Vehicle {
    protected Vehicle decoratedVehicle;

    /**
     * Constructs a Decorator with a specified vehicle.
     * This constructor initializes the decorator with the vehicle that is to be decorated.
     *
     * @param vehicle The vehicle object that will be enhanced or decorated by this decorator.
     */
    public Decorator(Vehicle vehicle) {
        super(vehicle.getId(), vehicle.getStation());
        this.decoratedVehicle = vehicle;
    }
    
    /**
     * Retrieves the decorated vehicle.
     * This method returns the vehicle that has been enhanced or decorated by this decorator.
     *
     * @return The decorated vehicle.
     */
    public Vehicle getDecoratedVehicle() {
        return this.decoratedVehicle;
    }

    /**
     * An abstract method that subclasses must implement to add decoration to the vehicle.
     * This method should modify or enhance the vehicle's description or functionality.
     *
     * @return A string representing the decorated state of the vehicle.
     */
    public abstract String decorate();
}



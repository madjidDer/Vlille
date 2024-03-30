package persons;

import constants.Constants;
import vehicleDecorators.Decorator;
import vehicles.ElectricVelo;
import vehicles.Vehicle;

/**
 * The `Mechanic` class represents a character who can perform maintenance on vehicles.
 * This class extends the `AbstractPerson` class and provides specific implementations
 * for a mechanic's interactions with vehicles, particularly focusing on vehicle maintenance.
 */
public class Mechanic extends AbstractPerson {

	/**
     * Default constructor for the `Mechanic` class.
     */
	public Mechanic() {}
	
	/**
     * Performs maintenance on a vehicle, which may include recharging an electric vehicle's battery
     * and resetting the number of rentals.
     *
     * @param v The vehicle to be maintained.
     */
	public void visit(Vehicle v) {
		Vehicle actualVehicle = getElectricVeloIfDecorated(v);
		v.getState().action(false);
	    try {
			Thread.sleep(Constants.INTERVAL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    if (actualVehicle instanceof ElectricVelo) {
	    	((ElectricVelo) actualVehicle).rechargeBatteryLevel();
	    	v.resetNumberOfRentals();
		}
	    else {
	    	v.resetNumberOfRentals();
	    }
		v.getState().action(false);
	}
	
	/**
     * Recursively gets the underlying non-decorated vehicle if the provided vehicle is decorated.
     *
     * @param vehicle The decorated or non-decorated vehicle.
     * @return The underlying non-decorated vehicle.
     */
    private Vehicle getElectricVeloIfDecorated(Vehicle vehicle) {
        while (vehicle instanceof Decorator) {
            vehicle = ((Decorator) vehicle).getDecoratedVehicle();
        }
        return vehicle;
    }

    /**
     * Checks if an object is equal to this instance of `Mechanic`.
     *
     * @param o The object to compare.
     * @return true if the object is an instance of `Mechanic`, otherwise false.
     */
	@Override
	public boolean equals(Object o) {
		return o instanceof Mechanic;
	}
}

package vehiclesState;

import constants.Constants;
import vehicleDecorators.Decorator;
import vehicles.ElectricVelo;
import vehicles.Vehicle;

/**
 * Represents the 'Rented' state of a vehicle in the transportation system.
 * This state indicates that the vehicle is currently rented out. The class extends the State class
 * and provides behavior specific to vehicles in a rented state.
 */
public class Rented extends State {

	/**
     * Constructs a new Rented state for a given vehicle.
     *
     * @param vehicle The vehicle that is currently rented out.
     */
    public Rented(Vehicle vehicle) {
        super(vehicle);
    }

    /**
     * Performs an action based on the Rented state of the vehicle.
     * If 'toSteal' is false and the maximum number of rentals before intervention is reached,
     * the vehicle transitions to the 'BrokenDown' state.
     * Otherwise, it transitions back to the 'Disponible' (Available) state.
     *
     * @param toSteal A boolean parameter that determines the next state of the vehicle.
     *                If true, the method may incorporate logic to handle a stolen state.
     */
	@Override
	public void action(Boolean toSteal) {
        Vehicle actualVehicle = getElectricVeloIfDecorated(this.vehicle);
        if(!toSteal) {
            if ((actualVehicle instanceof ElectricVelo) && ((ElectricVelo) actualVehicle).getBatteryLevel()==20) {
                this.vehicle.setState(new BrokenDown(this.vehicle));
            }
            else if (this.vehicle.getNumberOfRentals()==Constants.MAX_NB_OF_RENTALS_BEFORE_INTERVANTION) {
                this.vehicle.setState(new BrokenDown(this.vehicle));
            } else {
                this.vehicle.setState(new Disponible(this.vehicle));
            }
        }
    }
	

	/**
	 * Recursively retrieves the innermost undecorated Vehicle instance, if the vehicle is decorated.
	 *
	 * @param vehicle The decorated or undecorated Vehicle instance.
	 * @return The innermost undecorated Vehicle instance, if the vehicle is decorated; otherwise, returns the same vehicle.
	 */
	private Vehicle getElectricVeloIfDecorated(Vehicle vehicle) {
        while (vehicle instanceof Decorator) {
            vehicle = ((Decorator) vehicle).getDecoratedVehicle();
        }
        return vehicle;
    }
	
	/**
     * Provides a string representation of the Rented state.
     *
     * @return A string "Rented", representing the state.
     */
	@Override
    public String toString() {
		return "Rented";
    }
}
package vehicles;

import stations.Station;

/**
 * Represents an electric bike (ElectricVelo) in the transportation system.
 * This class extends the Velo class and includes additional features specific to electric bikes,
 * such as battery level management.
 */
public class ElectricVelo extends Velo {
	
	    private int batteryLevel;

	    /**
	     * Constructs a new ElectricVelo (electric bike) with a given ID and station.
	     * Initializes the electric bike with a full battery level.
	     *
	     * @param id The unique identifier of the electric bike.
	     * @param station The station where the electric bike is initially located.
	     */
	    public ElectricVelo(int id,Station station) {
	    	super(id, station);
	    	this.batteryLevel=100;
	    }

	    /**
	     * Recharges the battery level of the electric bike to full capacity.
	     */
	    public void rechargeBatteryLevel() {
	    	this.setBatteryLevel(100);
	    }

	    /**
	     * Decreases the battery level of the electric bike by a specified amount.
	     *
	     * @param amount The amount to decrease the battery level.
	     */
	    public void decreaseBatteryLevel(int amount) {
	    	this.setBatteryLevel(this.batteryLevel-amount);
	    }
	    
	    /**
	     * Gets the current battery level of the electric bike.
	     *
	     * @return The current battery level.
	     */
	    public int getBatteryLevel() {
	    	return this.batteryLevel;
	    }
	    
	    /**
	     * Sets the battery level of the electric bike.
	     * This method is private as it is used internally for managing the battery level.
	     *
	     * @param level The new battery level.
	     */
	    private void setBatteryLevel(int level) {
	    	this.batteryLevel=level;
	    }
	    
	    /**
	     * Provides a string representation of the electric bike including its ID and battery level.
	     *
	     * @return A string representation of the electric bike.
	     */
	    public String decorate() {
			return "An electric bike n°: "+this.getId()+" with "+this.getBatteryLevel()+"% battery level";
		}
}



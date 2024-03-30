package stations;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import constants.Constants;
import controlCenters.ControlCenter;
import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import exceptions.StationFullException;
import persons.AbstractPerson;
import vehicleDecorators.Decorator;
import vehicles.ElectricVelo;
import vehicles.Vehicle;
import vehiclesState.Disponible;

/**
 * The `Station` class represents a location in the transportation system
 * where vehicles can be rented, deposited, or stolen. It abstracts the common
 * behavior of all types of stations.
 */
public abstract class Station {
    protected String name;
    protected int stationID;
    protected int nbVehicles;
    protected int maxCapacite;
    protected ArrayList<Vehicle> vehicles;
    protected Timer timer;
	protected TimerTask task;
    protected boolean timeToRedistibuate;
	protected boolean timeToSteal;
	
	/**
     * Constructs a new `Station` with the specified ID, name, and maximum capacity.
     *
     * @param id          The unique identifier of the station.
     * @param name        The name of the station.
     * @param maxCapacite The maximum capacity of the station.
     */
    public Station(int id, String name,int maxCapacite) {
    	this.name=name;
    	this.stationID=id;
    	this.maxCapacite=maxCapacite;
    	this.vehicles = new ArrayList<Vehicle>();
    	this.timer = new Timer();
    	this.timeToRedistibuate=false;
    	this.timeToSteal=false;
    	this.nbVehicles=0;
	}

    /**
     * Gets the name of the station.
     *
     * @return The name of the station.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the unique identifier of the station.
     *
     * @return The station's unique identifier.
     */
    public int getStationID() {
        return this.stationID;
    }

    /**
     * Gets the current number of vehicles at the station.
     *
     * @return The number of vehicles at the station.
     */
    public int getNbVehicles() {
    	return this.nbVehicles;
    }

    /**
     * Gets the maximum capacity of the station.
     *
     * @return The maximum capacity of the station.
     */
    public int getMaxCapacite() {
        return this.maxCapacite;
    }
    
    /**
	 * Sets the flag indicating whether it's time to redistribute vehicles.
	 *
	 * @param timeToRedistibuate true if it's time to redistribute vehicles, otherwise false.
	 */
	public void setTimeToRedistibuate(boolean timeToRedistibuate) {
		this.timeToRedistibuate = timeToRedistibuate;
	}

	/**
	 * Sets the flag indicating whether it's time to steal a vehicle.
	 *
	 * @param timeToSteal true if it's time to steal a vehicle, otherwise false.
	 */
	public void setTimeToSteal(boolean timeToSteal) {
		this.timeToSteal = timeToSteal;
	}

	/**
	 * Gets the list of vehicles currently available at the station.
	 *
	 * @return An ArrayList of Vehicle objects at the station.
	 */
    public ArrayList<Vehicle> getVehicles() {
        return this.vehicles;
    }
    
    /**
     * Adds a vehicle to the station.
     *
     * @param v The vehicle to be added to the station.
     */
    public void addVehicle(Vehicle v) {
    	this.vehicles.add(0, v);
    	this.nbVehicles++;
    	v.updateStation(this);
    }
    
    /**
     * Removes a vehicle from the station.
     *
     * @param vehicle The vehicle to be removed from the station.
     */
    public void removeVehicle(Vehicle vehicle) {
    	int idx=0;
    	for (Vehicle v : this.getVehicles()) {
    		if(v.equals(vehicle)) {
    			break;
    		}
    		idx=idx+1;
    	}
    	this.vehicles.remove(idx);
    	this.nbVehicles--;
    	vehicle.updateStation(null);
    }

    /**
     * Checks if the station is empty (no vehicles available).
     *
     * @return true if the station is empty, otherwise false.
     */
    public boolean isEmpty() {
    	return this.vehicles.size()==0;
    }
    
    /**
     * Checks if the station is full (reached maximum capacity).
     *
     * @return true if the station is full, otherwise false.
     */
    public boolean isFull() {
        return this.vehicles.size()==this.maxCapacite;
    }
    
    /**
     * Checks if only one vehicle is left at the station.
     *
     * @return true if there is only one vehicle left, otherwise false.
     */
    public boolean onlyOneLeft() {
    	return this.vehicles.size()==1;
    }
    
    /**
     * Checks if it's time for redistribution of vehicles.
     *
     * @return true if it's time to redistribute vehicles, otherwise false.
     */
    public boolean isTimeToRedistibuate() {
    	return this.timeToRedistibuate;
    }
    
    /**
     * Checks if it's time to steal a vehicle.
     *
     * @return true if it's time to steal a vehicle, otherwise false.
     */
    public boolean isTimeToSteal() {
    	return this.timeToSteal;
    }
    
    /**
     * Checks if the station is empty and throws an exception if it is.
     *
     * @throws StationEmptyException if the station is empty and no vehicle is available for rent.
     */
    private void checkIfStationIsEmpty() throws StationEmptyException {
        if (this.isEmpty()) {
            throw new StationEmptyException("Station is empty! You can't rent a vehicle.");
        }
    }

    /**
     * Finds an available vehicle to rent and throws an exception if none is available.
     *
     * @return The available vehicle to rent.
     * @throws StationEmptyException if all vehicles are not available for rent.
     */
    private Vehicle findAvailableVehicle() throws StationEmptyException {
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.getState().equals(new Disponible(vehicle))) {
                return vehicle;
            }
        }
        throw new StationEmptyException("All vehicles are not disponible! You can't rent a vehicle.");
    }

    /**
     * Handles the process of renting a vehicle, including reducing battery level and removing it from the station.
     *
     * @param vehicle The vehicle to be rented.
     */
    private void handleVehicleRent(Vehicle vehicle) {
        vehicle.getState().action(false);
        this.removeVehicle(vehicle);
        scheduleTasksAfterRent();
    }

    /**
     * Retrieves the base vehicle if the provided vehicle is decorated.
     *
     * @param vehicle The vehicle to check for decoration.
     * @return The base vehicle without any decoration.
     */
    private Vehicle getElectricVeloIfDecorated(Vehicle vehicle) {
        while (vehicle instanceof Decorator) {
            vehicle = ((Decorator) vehicle).getDecoratedVehicle();
        }
        return vehicle;
    }

    /**
     * Schedules tasks to be executed after renting a vehicle.
     * If the station becomes empty, it schedules redistribution, and if only one vehicle is left, it schedules stealing.
     */
    private void scheduleTasksAfterRent() {
        Station s = this;
        if (this.isEmpty()) {
            scheduleTask(() -> {
                s.timeToRedistibuate = true;
                try {
					s.notifyObserver(true, null);
				} catch (RedistribuationNotCompletedException | StationEmptyException e) {
					e.printStackTrace();
				}
            });
        } else if (this.onlyOneLeft()) {
            scheduleTask(() -> {
                s.timeToSteal = true;
                try {
					s.notifyObserver(true, s.getVehicles().get(0));
				} catch (RedistribuationNotCompletedException | StationEmptyException e) {
					e.printStackTrace();
				}
            });
        }
    }
    
    /**
     * Schedules redistribution when the station becomes empty.
     */
    private void scheduleRedistributionIfEmpty() {
        Station s = this;
        if (this.isEmpty()) {
            scheduleTask(() -> {
                s.timeToRedistibuate = true;
                try {
					s.notifyObserver(true, null);
				} catch (RedistribuationNotCompletedException | StationEmptyException e) {
					e.printStackTrace();
				}
            });
        }
    }

    /**
    * Schedules tasks based on the station's state after renting a vehicle.
    * If the station is full, it schedules redistribution, and if only one vehicle is left, it schedules stealing.
    *
    * @param vehicle The rented vehicle.
    */
    private void scheduleTasksBasedOnStationState(Vehicle vehicle) {
        if (this.isFull()) {
            scheduleTask(() -> {
                this.timeToRedistibuate = true;
                try {
					this.notifyObserver(false, vehicle);
				} catch (RedistribuationNotCompletedException | StationEmptyException e) {
					e.printStackTrace();
				}
            });
        } else if (this.onlyOneLeft()) {
            scheduleTask(() -> {
                this.timeToSteal = true;
                try {
					this.notifyObserver(false, vehicle);
				} catch (RedistribuationNotCompletedException | StationEmptyException e) {
					e.printStackTrace();
				}
            });
        } else {
        	 try {
                 this.notifyObserver(false, vehicle);
             } catch (RedistribuationNotCompletedException | StationEmptyException e) {
                 e.printStackTrace();
            cancelExistingTask();
        }
    }
    }

    /**
     * Cancels the existing scheduled task if it exists.
     */
    private void cancelExistingTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
    
    /**
     * Schedules a task to be executed after a specified interval.
     *
     * @param taskAction The action to be performed by the task.
     */
    private void scheduleTask(Runnable taskAction) {
        if (task != null) {
            task.cancel();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                taskAction.run();
            }
        };
        timer.schedule(task, Constants.INTERVAL*2);
    }
    
    /**
     * Rents a vehicle from the station.
     *
     * @return The rented vehicle.
     * @throws RedistribuationNotCompletedException if the redistribution process is not completed.
     * @throws StationEmptyException               if the station is empty and no vehicle is available for rent.
     */
    public Vehicle rentVehicle() throws RedistribuationNotCompletedException, StationEmptyException {
        checkIfStationIsEmpty();
        Vehicle vehicle = findAvailableVehicle();
        handleVehicleRent(vehicle);
        return vehicle;
    }

    /**
     * Steals a vehicle from the station.
     *
     * @return The stolen vehicle.
     */
    public Vehicle stealVehicle() {
    	Vehicle vehicle = this.vehicles.get(0);
        this.removeVehicle(vehicle);
        vehicle.getState().action(true);
        scheduleRedistributionIfEmpty();
        return vehicle;
    }
    
    /**
     * Deposits a vehicle at the station.
     *
     * @param vehicle The vehicle to be deposited.
     * @throws RedistribuationNotCompletedException if the redistribution process is not completed.
     * @throws StationFullException               if the station is already full and cannot accept more vehicles.
     * @throws StationEmptyException              if the station is empty and cannot accept a deposit.
     */
    public void deposit(Vehicle vehicle) throws RedistribuationNotCompletedException, StationFullException, StationEmptyException {
    	if (this.isFull()) {
            throw new StationFullException("Station is already full! You can't deposit your vehicle.");
        }
    	 Vehicle actualVehicle = getElectricVeloIfDecorated(vehicle);

         if (actualVehicle instanceof ElectricVelo) {
             ((ElectricVelo) actualVehicle).decreaseBatteryLevel(20);
         }
    	vehicle.increaseNumberOfRentals();
        vehicle.getState().action(false);
        this.addVehicle(vehicle);
        scheduleTasksBasedOnStationState(vehicle);
    }
    
    /**
     * Notifies an observer about station state changes.
     *
     * @param rented  true if a vehicle was rented, false otherwise.
     * @param vehicle The vehicle involved in the state change (null if not applicable).
     * @throws RedistribuationNotCompletedException if the redistribution process is not completed.
     * @throws StationEmptyException               if the station is empty and no vehicle is available for rent.
     */
    public void notifyObserver(boolean rented, Vehicle vehicle) throws RedistribuationNotCompletedException, StationEmptyException {
    	if(rented) {
    		if(this.isTimeToRedistibuate()) {
    			ControlCenter.getInstanceControlCenter().update(this,false);
    			this.timeToRedistibuate=false;
    		}
    		if(this.isTimeToSteal()) {
    			ControlCenter.getInstanceControlCenter().update(vehicle,true);
    			this.timeToSteal=false;
    		}
    		
    	}
    	else {
    		if(this.isTimeToRedistibuate()) {
    			try {
    			ControlCenter.getInstanceControlCenter().update(this,true);
    			this.timeToRedistibuate=false;
    			}
    			catch (RedistribuationNotCompletedException e) {
    	        	System.out.println("Redistribution has not been completed ! ");
    			}
    		}
    		if(this.isTimeToSteal()) {
    			ControlCenter.getInstanceControlCenter().update(vehicle,true);
    			this.timeToSteal=false;
    		}
    		if(vehicle.getState().toString().equals("BrokenDown")) {
    			ControlCenter.getInstanceControlCenter().update(vehicle,false);
    		}
    	}
    } 
 
    /**
     * Accepts a person's visit to the station and returns a vehicle.
     *
     * @param p The person visiting the station.
     * @return The vehicle provided to the person.
     * @throws RedistribuationNotCompletedException if the redistribution process is not completed.
     * @throws StationEmptyException               if the station is empty and no vehicle is available for rent.
     */


    public Vehicle accept(AbstractPerson p) throws RedistribuationNotCompletedException, StationEmptyException {
    	Vehicle v = p.visit(this);
    	return v;
    }
    
    /**
     * Checks if the station has surplus vehicles (more than 50% filled).
     *
     * @return true if the station has surplus vehicles, otherwise false.
     */
    public boolean hasSurplusVehicles () {
    	int nbOfVehicles = this.getNbVehicles();
        return (int)(this.getMaxCapacite() * 0.5) < nbOfVehicles;
    }

    /**
     * Checks if the station has insufficient vehicles (less than 50% filled).
     *
     * @return true if the station has insufficient vehicles, otherwise false.
     */
    public boolean hasInsufficiencyVehicles () {
        int nbOfVehicles = this.getNbVehicles();
        return (int)(this.getMaxCapacite() * 0.5) > nbOfVehicles;
    }
    
    /**
     * Checks if the station is sufficiently filled (at least 50% filled).
     *
     * @return true if the station is sufficiently filled, otherwise false.
     */
    public boolean isSufficientlyFilled() {
    	return (this.getNbVehicles()>=(int)(this.getMaxCapacite() * 0.5));
    }
    
    /**
     * Checks if an object is equal to this instance of `Station`.
     *
     * @param o The object to compare.
     * @return true if the object is an instance of `Station` with the same stationID, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
    	if(!(o instanceof Station)) {
    		return false;
    	}
    	else {
    		Station other = (Station) o;
    		if(other.getStationID()==this.stationID) {
    			return true;
    		}
    		return false;
    	}
    }
    
    /**
     * Returns a string representation of the station.
     *
     * @return A string containing the station's ID.
     */
    @Override
    public String toString() {
    	return "Station : "+this.stationID;
    }
}
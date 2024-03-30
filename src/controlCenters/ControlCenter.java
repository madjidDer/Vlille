package controlCenters;
import java.util.ArrayList;

import constants.Constants;
import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import persons.Mechanic;
import persons.Theif;
import redistibuationStrategy.RedistributionStrategy;
import stations.Station;
import stations.VeloStation;
import vehicles.Vehicle;

/**
 * The ControlCenter class represents the control hub in a bike station management system.
 * It implements the Observer interface and manages redistribution and maintenance operations of vehicles.
 */
public class ControlCenter implements Observer {
	private ArrayList<Station> stations;
	private ArrayList<Vehicle> brokenDownVehicles;
	private static ControlCenter instance;
	private RedistributionStrategy strategy;

	/**
     * Private constructor for ControlCenter.
     * Initializes the list of stations and sets up the bike stations.
     * 
     * @param nbOfStationTocreate The number of bike stations to create within the control center.
     */
	private ControlCenter(int nbOfStationTocreate) {
    	this.stations = new ArrayList<>();
    	this.brokenDownVehicles = new ArrayList<>();
        this.setVeloStations(nbOfStationTocreate);
        
    }

	/**
     * Retrieves the singleton instance of the ControlCenter.
     * Creates the instance if it does not already exist.
     * 
     * @return The single instance of the ControlCenter.
     */
    public static ControlCenter getInstanceControlCenter() {
        if (ControlCenter.instance  == null) {
            ControlCenter.instance = new ControlCenter(Constants.NB_OF_STATION_IN_A_CONTROLCENTER);
        }
        return ControlCenter.instance ;
    }

    /**
     * Gets the list of stations managed by the control center.
     * 
     * @return An ArrayList of Station objects.
     */
    public ArrayList<Station> getStations() {
        return this.stations;
    }

    /**
     * Adds a station to the control center's list of stations.
     * 
     * @param station The Station object to be added.
     */
    public void addStation(Station station) {
    	this.stations.add(station);
    }

    /**
     * Removes a station from the control center's list of stations.
     * 
     * @param station The Station object to be removed.
     */
    public void removeStation(Station station) {
    	this.stations.remove(station);
    }

    /**
     * Updates information about a station.
     * Redistributes vehicles based on the station's status (full or not full).
     * 
     * @param station The station to update.
     * @param full    A boolean indicating if the station is full.
     * @throws RedistribuationNotCompletedException If redistribution cannot be completed.
     */
    @Override
    public void update(Station station, boolean full) throws RedistribuationNotCompletedException {
    	if(!full) {
        this.strategy.redistributeWhenEmpty(station,this.getStations());
    	}
    	else {
    		this.strategy.redistributeWhenFull(station,this.getStations());
    	}
    }

    /**
     * Updates information about a station and performs actions on a vehicle.
     * Either steals or repairs the vehicle based on the given parameter.
     * 
     * @param vehicle The vehicle to be processed.
     * @param toSteal A boolean indicating whether the vehicle should be stolen (true) or repaired (false).
     * @throws RedistribuationNotCompletedException If redistribution cannot be completed.
     * @throws StationEmptyException If the station is empty when trying to steal a vehicle.
     */
    @Override
    public void update(Vehicle vehicle,boolean toSteal) throws RedistribuationNotCompletedException, StationEmptyException {
    	if(toSteal) {
    		vehicle.accept(new Theif());
    	}
    	else {
    		this.brokenDownVehicles.add(vehicle);
    	//vehicle.accept(new Mechanic());
    	}
    }
    
    /**
     * Sets up bike stations within the control center.
     * 
     * @param nbnbOfStationTocreate The number of bike stations to create.
     */
    public void setVeloStations(int nbnbOfStationTocreate) {
    	for(int i=0; i<nbnbOfStationTocreate; i++) {
    		this.addStation(new VeloStation(i+1, "Station-"+i+1, (int)(Math.random()*10)+10));
    	}
    }
    
    /**
     * Sets the redistribution strategy for the control center.
     * 
     * @param strategy The RedistributionStrategy to be used.
     */
    public void setStrategy(RedistributionStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Retrieves the current redistribution strategy used by the control center.
     * 
     * @return The current RedistributionStrategy object.
     */
	public RedistributionStrategy getStrategy() {
		return strategy;
	}
	
	public void  repaireAllVehicles() throws RedistribuationNotCompletedException, StationEmptyException {
		Mechanic ishak = new Mechanic();
		for (Vehicle v : this.brokenDownVehicles) {
			v.accept(ishak);
		}
	}

}

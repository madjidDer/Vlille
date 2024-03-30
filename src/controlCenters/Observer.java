package controlCenters;

import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import stations.Station;
import vehicles.Vehicle;

/**
 * The Observer interface for observing changes in stations and vehicles.
 * It is used in a context where stations and vehicles need to be monitored and actions might be taken upon certain changes.
 */
public interface Observer {
	
	/**
     * Updates the observer with the status of a station.
     * This method is called when there are changes in the occupancy or status of a station.
     *
     * @param station The station which status has changed.
     * @param full A boolean indicating whether the station is full (true) or not (false).
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed successfully.
     */
    void update(Station station,boolean full) throws RedistribuationNotCompletedException;
    
    /**
     * Updates the observer with the status of a vehicle.
     * This method is typically called in scenarios such as theft or maintenance of a vehicle.
     *
     * @param vehicle The vehicle which status needs to be updated.
     * @param toSteal A boolean indicating if the vehicle is to be stolen (true) or not (false). If false, it may imply maintenance or other actions.
     * @throws RedistribuationNotCompletedException If the redistribution process related to the vehicle cannot be completed.
     * @throws StationEmptyException If the station is empty and a theft action is attempted.
     */
    void update(Vehicle vehicle,boolean toSteal) throws RedistribuationNotCompletedException, StationEmptyException;
}

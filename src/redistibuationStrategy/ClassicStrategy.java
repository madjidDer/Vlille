package redistibuationStrategy;

import java.util.ArrayList;
import exceptions.RedistribuationNotCompletedException;
import stations.Station;
import vehicles.Vehicle;

/**
 * A classic redistribution strategy that moves vehicles between stations in a systematic way.
 * This strategy redistributes vehicles from stations with surplus vehicles to those with insufficient vehicles,
 * aiming to balance the vehicle distribution across all stations.
 */
public class ClassicStrategy implements RedistributionStrategy {
	
	/**
     * Redistributes vehicles to an empty station.
     * Vehicles are moved from stations with surplus vehicles to the empty station until it is sufficiently filled.
     *
     * @param emptyStation The station that is currently empty and needs vehicles.
     * @param stations     A list of all stations, used for determining where to source vehicles from.
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed successfully.
     */
	@Override
    public void redistributeWhenEmpty(Station emptyStation, ArrayList<Station> stations) throws RedistribuationNotCompletedException {
		int cpt = 0;
		while (emptyStation.hasInsufficiencyVehicles() && cpt<stations.size()) {
			cpt = 0;
	        for (Station station : stations) {
	        	if (!station.equals(emptyStation) && station.hasSurplusVehicles()) {
	        		Vehicle vehicle = station.getVehicles().get(0);
		            station.removeVehicle(vehicle);
		            emptyStation.addVehicle(vehicle);
		            if (emptyStation.isSufficientlyFilled()) {
		            	break;
		            }
	        	} else {
	        		cpt++;
	        	}
	        }
		}
		if (emptyStation.isEmpty()) {
			throw (new RedistribuationNotCompletedException("Station has not been completed enough!"));
		}
    }

	/**
     * Redistributes vehicles from a full station.
     * Vehicles are moved from the full station to other stations with insufficient vehicles until it no longer has surplus vehicles.
     *
     * @param fullStation The station that is currently full and needs to offload vehicles.
     * @param stations    A list of all stations, used for determining where to move the excess vehicles to.
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed successfully.
     */
    @Override
	public void redistributeWhenFull(Station fullStation, ArrayList<Station> stations) throws RedistribuationNotCompletedException {
		int cpt = 0;
	    while (fullStation.hasSurplusVehicles() && cpt<stations.size()) {
	    	cpt = 0;
	        for (Station station : stations) {
	            if (!station.equals(fullStation) && station.hasInsufficiencyVehicles()) {
	                Vehicle vehicle = fullStation.getVehicles().get(0);
	                fullStation.removeVehicle(vehicle);
	                station.addVehicle(vehicle);
	                if (!fullStation.hasSurplusVehicles()) {
	                    break;
	                }
	            } else {
	        		cpt++;
	        	}
	        }
	    }
	    if (fullStation.isFull()) {
			throw (new RedistribuationNotCompletedException("Station has not been emptied correctly !"));
		}	
	}

    /**
     * Compares this ClassicStrategy with another object for equality.
     *
     * @param o The object to compare with this ClassicStrategy.
     * @return true if the specified object is also a ClassicStrategy; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof ClassicStrategy;
    }
}

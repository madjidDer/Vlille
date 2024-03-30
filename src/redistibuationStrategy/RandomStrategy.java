package redistibuationStrategy;

import java.util.ArrayList;
import java.util.Random;
import exceptions.RedistribuationNotCompletedException;
import stations.Station;
import vehicles.Vehicle;

/**
 * A redistribution strategy that randomly redistributes vehicles between stations.
 * This strategy selects stations randomly for moving vehicles to balance the distribution when a station is either full or empty.
 */
public class RandomStrategy implements RedistributionStrategy {

	private Random random = new Random();

	/**
     * Redistributes vehicles when a station is empty.
     * Vehicles are moved from randomly selected stations with surplus vehicles to the empty station.
     *
     * @param emptyStation The station that is currently empty and needs vehicles.
     * @param stations     A list of all stations, used for determining where to source vehicles from.
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed successfully.
     */
    @Override
    public void redistributeWhenEmpty(Station emptyStation, ArrayList<Station> stations) throws RedistribuationNotCompletedException {
    	boolean getIn = true;
        while (!emptyStation.isSufficientlyFilled() && getIn) {
        	getIn = false;
        	for(Station s : stations) {
        		if (s.hasSurplusVehicles()) {
        			getIn = true;
        			break;
        		}
        	}
            Station randomStation = stations.get(random.nextInt(stations.size()));
            if (!randomStation.equals(emptyStation) && randomStation.hasSurplusVehicles()) {
                Vehicle vehicle = randomStation.getVehicles().get(0);
                randomStation.removeVehicle(vehicle);
                emptyStation.addVehicle(vehicle);
            }
        }
        if(emptyStation.isEmpty()) {
        	throw (new RedistribuationNotCompletedException("Station has not been completed enough!"));
        }
    }

    /**
     * Redistributes vehicles when a station is full.
     * Vehicles are moved from the full station to other stations randomly selected that need more vehicles.
     *
     * @param fullStation The station that is currently full and needs to offload vehicles.
     * @param stations    A list of all stations, used for determining where to move the excess vehicles to.
     * @throws RedistribuationNotCompletedException If the redistribution process cannot be completed successfully.
     */
	@Override
	public void redistributeWhenFull(Station fullStation, ArrayList<Station> stations) throws RedistribuationNotCompletedException {
		Random random = new Random();
	    while (fullStation.hasSurplusVehicles()) {
	        ArrayList<Integer> eligibleStationsIndices = new ArrayList<>();
	        for (int i = 0; i < stations.size(); i++) {
	            Station station = stations.get(i);
	            if (!station.equals(fullStation) && station.hasInsufficiencyVehicles()) {
	                eligibleStationsIndices.add(i);
	            }
	        }
	        if (eligibleStationsIndices.isEmpty() && fullStation.isFull()) {
	        	throw (new RedistribuationNotCompletedException("Station has not been emptied correctly !"));
	        }
	        int selectedIndex = eligibleStationsIndices.get(random.nextInt(eligibleStationsIndices.size()));
	        Station selectedStation = stations.get(selectedIndex);
	        Vehicle vehicle = fullStation.getVehicles().get(0);
	        selectedStation.addVehicle(vehicle);
	        fullStation.removeVehicle(vehicle);
	    }
	}
	
	/**
     * Compares this RandomStrategy with another object for equality.
     *
     * @param o The object to compare with this RandomStrategy.
     * @return true if the specified object is also a RandomStrategy; false otherwise.
     */
	@Override
	public boolean equals(Object o) {
		return o instanceof RandomStrategy;
	}
}

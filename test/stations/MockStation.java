package stations;

import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import vehicles.Vehicle;

public class MockStation extends Station{
	
	public int cptNotifyCalled;
	
	public MockStation(int id, String name, int maxCapacite) {
		super(id,name,maxCapacite);
	}
	
	@Override
	public void notifyObserver(boolean rented, Vehicle vehicle) throws RedistribuationNotCompletedException, StationEmptyException {
		this.cptNotifyCalled++;
		super.notifyObserver(rented,vehicle);
    }

}

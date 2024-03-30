package redistribuationStrategy;

import java.util.ArrayList;

import exceptions.RedistribuationNotCompletedException;
import redistibuationStrategy.RedistributionStrategy;
import stations.Station;

public class MockStrategy implements RedistributionStrategy {
	public int cptCalledWhenEmpty;
	public int cptCalledWhenFull;
	
	public MockStrategy() {
		this.cptCalledWhenEmpty=0;
		this.cptCalledWhenFull=0;
	}

	@Override
	public void redistributeWhenEmpty(Station station, ArrayList<Station> stations)
			throws RedistribuationNotCompletedException {
		this.cptCalledWhenEmpty++;
	}

	@Override
	public void redistributeWhenFull(Station station, ArrayList<Station> stations)
			throws RedistribuationNotCompletedException {
		this.cptCalledWhenFull++;

	}

}

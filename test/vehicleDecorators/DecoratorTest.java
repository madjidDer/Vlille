package vehicleDecorators;

import org.junit.jupiter.api.Test;

import stations.Station;
import stations.VeloStation;
import vehicles.ClassicVelo;
import vehicles.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public abstract class DecoratorTest {
	
	protected Vehicle vehicle;
    protected Station station;
    protected Decorator decorator;
    protected abstract Decorator createDecorator(Vehicle vehicle);
    
    @BeforeEach
    public void setUp() {
    	
        this.station = new VeloStation(1, "StationName", 5);
        this.vehicle = new ClassicVelo(1, this.station);
        this.decorator = this.createDecorator(vehicle);
    }
	
    @Test
    public void testDecorator() {
        assertNotNull(decorator.decorate());
    }
    
}

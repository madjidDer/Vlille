package vehicleDecorators;
import vehicles.Vehicle;


public class LuggageRackTest extends DecoratorTest {

	@Override
    protected Decorator createDecorator(Vehicle vehicle) {
        return new LuggageRack(vehicle);
    }

	
}

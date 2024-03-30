package vehicleDecorators;
import vehicles.Vehicle;


public class PaintingTest extends DecoratorTest {

	@Override
    protected Decorator createDecorator(Vehicle vehicle) {
        return new Painting(vehicle);
    }

	
}
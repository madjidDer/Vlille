package vehicleDecorators;
import vehicles.Vehicle;


public class BasketTest extends DecoratorTest {

    @Override
    protected Decorator createDecorator(Vehicle vehicle) {
        return new Basket(vehicle);
    }
}

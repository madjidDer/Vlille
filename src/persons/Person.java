package persons;

import stations.Station;
import vehicles.Vehicle;

/**
 * The Person interface defines the visit behaviors for different types of people 
 * in the transportation system. Implementations of this interface can interact 
 * with both vehicles and stations, representing various actions or effects 
 * a person might have on these entities.
 */
public interface Person {

    /**
     * Defines the action to be taken when a person visits a vehicle.
     * This method can be implemented to specify how different types of people
     * interact with or affect a vehicle.
     *
     * @param v The vehicle that the person is visiting.
     */
    void visit(Vehicle v);

    /**
     * Defines the action to be taken when a person visits a station.
     * This method can be implemented to specify how different types of people
     * interact with or affect a station.
     *
     * @param s The station that the person is visiting.
     */
    void visit(Station s);
}

package redistribuationStrategy;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import exceptions.RedistribuationNotCompletedException;
import redistibuationStrategy.RandomStrategy;
import stations.Station;
import stations.VeloStation;
import vehicles.ClassicVelo;

import java.util.ArrayList;

public class RandomStrategyTest {
    private RandomStrategy strategy;
    private ArrayList<Station> stations;
    private Station stationVide;
    private Station stationPleine;
    private Station stationAvecSurplus;
    private Station stationAvecPenurie;

    @BeforeEach
    public void setUp() {
        strategy = new RandomStrategy();
        stations = new ArrayList<>();
        stationVide = new VeloStation(1, "Station Vide", 10);
        stationPleine = new VeloStation(2, "Station Pleine", 10);
        stationAvecSurplus = new VeloStation(3, "Station Avec Surplus", 10);
        stationAvecPenurie = new VeloStation(4, "Station avec penurie", 10);

        for (int i = 0; i < 10; i++) {
            stationPleine.addVehicle(new ClassicVelo(i, stationPleine));
        }

        for (int i = 0; i < 9; i++) {
            stationAvecSurplus.addVehicle(new ClassicVelo(i, stationAvecSurplus));
        }
        
        for(int i = 0; i<4; i++) {
        	stationAvecPenurie.addVehicle(new ClassicVelo(i, stationAvecSurplus));
        }

        stations.add(stationVide);
        stations.add(stationPleine);
        stations.add(stationAvecSurplus);
        stations.add(stationAvecPenurie);
    }

    @Test
    public void testRedistributeWhenEmpty() throws RedistribuationNotCompletedException {
    	assertEquals(stationPleine.getNbVehicles(),10);
        strategy.redistributeWhenEmpty(stationVide, stations);
        assertEquals(stationAvecSurplus.getNbVehicles()+stationPleine.getNbVehicles(),14);
        assertFalse(stationVide.getVehicles().isEmpty());
        assertEquals(stationAvecPenurie.getNbVehicles(),4);
        assertTrue(stationVide.isSufficientlyFilled());
    }

    @Test
    public void testRedistributeWhenFull() throws RedistribuationNotCompletedException {
    	assertEquals(stationPleine.getNbVehicles(),10);
        strategy.redistributeWhenFull(stationPleine, stations);
        assertEquals(stationPleine.getNbVehicles(),5);
        assertEquals(stationAvecSurplus.getNbVehicles(),9);
        assertEquals(stationAvecPenurie.getNbVehicles()+stationVide.getNbVehicles(),9);
    }
    
    @Test
    public void testRedistributeWhenFullAndSurPlusVehicles() throws RedistribuationNotCompletedException {
    	
    	stations.remove(0);
    	stations.remove(2);
    	RedistribuationNotCompletedException thrown2 = Assertions.assertThrows(RedistribuationNotCompletedException.class, () -> {
    		strategy.redistributeWhenFull(stationPleine, stations);
    	}, "RedistribuationNotCompletedException was expected");
    	
    	Assertions.assertEquals("Station has not been emptied correctly !", thrown2.getMessage());
    	
    	assertEquals(stationAvecSurplus.getNbVehicles(),9);
    	assertEquals(stationPleine.getNbVehicles(),10);
    }
    
    @Test
    public void testRedistributeWhenEmptyAndNotEnoughVehicles() throws RedistribuationNotCompletedException {
    	stations.remove(1);
    	stations.remove(1);

    	Executable redistributeWhenEmpty = () -> strategy.redistributeWhenEmpty(stationVide, stations);
        assertThrows(RedistribuationNotCompletedException.class, redistributeWhenEmpty);
    	
    	assertEquals(stationAvecPenurie.getNbVehicles(),4);
    	assertEquals(stationVide.getNbVehicles(),0);
    }

}

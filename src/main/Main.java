package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import constants.Constants;
import controlCenters.ControlCenter;
import exceptions.RedistribuationNotCompletedException;
import exceptions.StationEmptyException;
import exceptions.StationFullException;
import persons.Client;
import redistibuationStrategy.ClassicStrategy;
import stations.Station;
import vehicleDecorators.Basket;
import vehicleDecorators.LuggageRack;
import vehicles.ClassicVelo;
import vehicles.ElectricVelo;
import vehicles.Vehicle;

/**
 * The main class that runs the bike-sharing system simulation.
 * This class contains simulations for renting, depositing, repairing bikes,
 * and bike theft within the system.
 */
public class Main {
	static Scanner scanner = new Scanner(System.in);
	static ControlCenter myCenter = ControlCenter.getInstanceControlCenter();
	static Client madjid = new Client("Madjid");
	static Client manil = new Client("Manil");
	static Client freeze = new Client("Freeze");
	static ArrayList<Vehicle> rentedVehicles= new ArrayList<Vehicle>();
	static ArrayList<Client> clients= new ArrayList<Client>();
	
	/**
	 * Waits for the user to press the Enter key to continue the simulation.
	 * This method repeatedly checks for Enter key presses until it receives one.
	 * It provides instructions to the user to press Enter.
	 */
	public static void pressEnterFunction() {
		while (true) {
            String input = scanner.nextLine();
            if (input.equals("")) {
                break;
            }
            System.out.println("Veuillez appuyer sur Entrer pour commencer.");
        }
	}
	
	/**
     * Initializes the bike-sharing system with bikes and stations.
     */
	public static void initialiseVlille() {
		int idVelo = 1;
		myCenter.setStrategy(new ClassicStrategy());
		
		for (Station s : myCenter.getStations()) {
            int nbVeloToAdd = (4 * s.getMaxCapacite()) / 5;
            int nbTypes = 8;

            for (int i = 0; i < nbVeloToAdd; i++) {
                switch (i % nbTypes) {
                    case 0:
                        s.addVehicle(new ClassicVelo(idVelo++, s));
                        break;
                    case 1:
                        s.addVehicle(new ElectricVelo(idVelo++, s));
                        break;
                    case 2:
                        s.addVehicle(new Basket(new ClassicVelo(idVelo++, s)));
                        break;
                    case 3:
                        s.addVehicle(new Basket(new ElectricVelo(idVelo++, s)));
                        break;
                    case 4:
                        s.addVehicle(new LuggageRack(new ClassicVelo(idVelo++, s)));
                        break;
                    case 5:
                        s.addVehicle(new LuggageRack(new ElectricVelo(idVelo++, s)));
                        break;
                    case 6:
                        s.addVehicle(new Basket(new LuggageRack(new ClassicVelo(idVelo++, s))));
                        break;
                    case 7:
                        s.addVehicle(new Basket(new LuggageRack(new ElectricVelo(idVelo++, s))));
                        break;
                }
            }
		}
	}
	
	/**
     * Displays the status of all stations in the system.
     *
     * @throws InterruptedException If the simulation is interrupted.
     */
	public static void displayVlille() throws InterruptedException {
		for(Station s : myCenter.getStations()) {
			Collections.shuffle(s.getVehicles());
			displayStation(s);
			//Thread.sleep(1000);
		}
	}
	
	/**
	 * Displays the status of a bike station, including the availability of vehicles.
	 *
	 * @param s The station to display the status for.
	 */
	public static void displayStation(Station s) {
		String vehicles="";
		int cpt=0;
        for (Vehicle velo : s.getVehicles()) {
        	if (velo.getState().toString().equals("Disponible")) {
        		vehicles+="🚲 | ";
        	} else {
        		vehicles+="🚳 | ";
        	}
        	cpt+=1;
        }
        int emptyPlaces = s.getMaxCapacite()-s.getNbVehicles();
        for(int i=0; i<emptyPlaces; i++) {
        	vehicles+="🚫 | ";
        	cpt+=1;
        }
        for(int j=cpt; j<20; j++) {
        	vehicles+="     ";
        }
        System.out.println("+----------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| "+s.toString()+" | "+vehicles+" |");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------+");     
    }
	
	/**
     * Displays the welcome page of the simulation.
     */
    public static void displayWelcomePage() {
        System.out.println("            ╔════════════════════════════════════════════════════════════╗");
        System.out.println("            ║                                                            ║");
        System.out.println("            ║    ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    ║");
        System.out.println("            ║    ░░░██░░░░██░██░░░░░░░██░░░░██░░░░░░██░░░░░░██████░░░    ║");
        System.out.println("            ║    ░░░██░░░░██░██░░░░░░░██░░░░██░░░░░░██░░░░░░██░░░░░░░    ║");
        System.out.println("            ║    ░░░░██░░██░░██░░░░░░░██░░░░██░░░░░░██░░░░░░██████░░░    ║");
        System.out.println("            ║    ░░░░░████░░░██░░░░░░░██░░░░██░░░░░░██░░░░░░██░░░░░░░    ║");
        System.out.println("            ║    ░░░░░░██░░░░██████░░░██░░░░██████░░██████░░██████░░░    ║");
        System.out.println("            ║    ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    ║");
        System.out.println("            ║                                                            ║");
        System.out.println("            ╚════════════════════════════════════════════════════════════╝");
        System.out.println("\nAppuyez sur Entrer pour commencer la simulation...");
    }
    
    /**
     * Displays the title for various simulations with an ASCII art banner.
     */
    public static void displaySimulationsTitle() {
    	System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("░░░███████░██░███░░░░███░██░░░░██░██░░░░░░░░░░░███░░░░░████████░██████░████████░███░░░██░░░░");
        System.out.println("░░░██░░░░░░██░████░░████░██░░░░██░██░░░░░░░░░░██░██░░░░░░░██░░░░░░██░░░██░░░░██░████░░██░░░░");
        System.out.println("░░░███████░██░██░████░██░██░░░░██░██░░░░░░░░░██░░░██░░░░░░██░░░░░░██░░░██░░░░██░██░██░██░░░░");
        System.out.println("░░░░░░░░██░██░██░░██░░██░██░░░░██░██░░░░░░░░██░███░██░░░░░██░░░░░░██░░░██░░░░██░██░░████░░░░");                       
        System.out.println("░░░███████░██░██░░░░░░██░████████░████████░██░░░░░░░██░░░░██░░░░██████░████████░██░░░███░░░░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("");
        System.out.println("");
    }
    
    /**
     * Displays a simulation banner with custom text.
     *
     * @param phrase The text to display in the simulation banner.
     */
    public static void displaySimulation(String phrase) {
    	int nbZeros = ("+----------------------------------------+".length()-phrase.length()-2)/2;
    	for (int i = 0; i<nbZeros; i++) {
    		phrase=" "+phrase;
    		phrase=phrase+" ";
    	}
        System.out.println("+----------------------------------------+");
        System.out.println("|                                        |");
        System.out.println("|"+phrase+"|");
        System.out.println("|                                        |");
        System.out.println("+----------------------------------------+");
        System.out.println("\nAppuyez sur Entrer pour commencer la simulation...");
    }
    
    /**
     * Displays the banner for the rent simulation.
     */
    public static void displayRentSimulation() {
        System.out.println("+----------------------------------------+");
        System.out.println("|                                        |");
        System.out.println("|   SIMULATION DE LOCATION DE VELOS      |");
        System.out.println("|                                        |");
        System.out.println("+----------------------------------------+");
        System.out.println("\nAppuyez sur Entrer pour commencer la simulation...");
    }
    
    public static void displayTheEnd() {
        System.out.println("+----------------------------------------+");
        System.out.println("|                                        |");
        System.out.println("|            THE END 🏁🏁🏁              |");
        System.out.println("|                                        |");
        System.out.println("+----------------------------------------+");
    }
    
    /**
     * Displays a message indicating that a client has rented a vehicle from a station.
     *
     * @param v The vehicle rented by the client.
     * @param c The client who rented the vehicle.
     * @param s The station from which the client rented the vehicle.
     */
    public static void displayRent(Vehicle v, Client c, Station s) {
    	System.out.println(c+" has rented "+v.decorate()+ " from "+s);
    }
    
    /**
     * Displays a message indicating that a client has deposited a vehicle in a station.
     *
     * @param v The vehicle deposited by the client.
     * @param c The client who deposited the vehicle.
     * @param s The station in which the client deposited the vehicle.
     */
    public static void displayDeposit(Vehicle v, Client c, Station s) {
    	System.out.println(c+" has deposit "+v.decorate()+ " in "+s);
    }
    
    /**
     * Simulates the process of renting and depositing vehicles at various stations.
     *
     * @throws RedistribuationNotCompletedException When redistribution is not completed.
     * @throws StationEmptyException                When attempting to rent from an empty station.
     * @throws InterruptedException                 When a thread is interrupted.
     * @throws StationFullException                When attempting to deposit at a full station.
     */
    public static void rentAndDepositSimulation() throws RedistribuationNotCompletedException, StationEmptyException, InterruptedException, StationFullException {
    	Station pdp = myCenter.getStations().get(0);
    	Station marbrerie = myCenter.getStations().get(5);
    	Station lilleFlandre = myCenter.getStations().get(9);
    	displayStation(pdp);
    	Vehicle vRentedByMadjid = pdp.accept(madjid);
    	displayRent(vRentedByMadjid,madjid,pdp);
    	displayStation(pdp);
    	//Thread.sleep(1000);
    	System.out.println();
    	
    	displayStation(marbrerie);
    	Vehicle vRentedByManil = marbrerie.accept(manil);
    	displayRent(vRentedByManil,manil,marbrerie);
    	displayStation(marbrerie);
    	//Thread.sleep(1000);
    	System.out.println();
    	
    	displayStation(lilleFlandre);
    	Vehicle vRentedByFreeze = lilleFlandre.accept(freeze);
    	displayRent(vRentedByFreeze,freeze,lilleFlandre);
    	displayStation(lilleFlandre);
    	//Thread.sleep(1000);
    	System.out.println();
    	
    	Station lilleSud = myCenter.getStations().get(1);
    	Station roubaix = myCenter.getStations().get(8);
    	Station triolo = myCenter.getStations().get(4);
    	
    	displayStation(roubaix);
    	roubaix.deposit(vRentedByMadjid);
    	displayDeposit(vRentedByMadjid,madjid,roubaix);
    	displayStation(roubaix);
    	//Thread.sleep(1000);
    	System.out.println();
    	
    	displayStation(triolo);
    	triolo.deposit(vRentedByManil);
    	displayDeposit(vRentedByManil,manil,triolo);
    	displayStation(triolo);
    	//Thread.sleep(1000);
    	System.out.println();
    	
    	displayStation(lilleSud);
    	lilleSud.deposit(vRentedByFreeze);
    	displayDeposit(vRentedByFreeze,freeze,lilleSud);
    	displayStation(lilleSud);
    	//Thread.sleep(1000);
    	System.out.println();
    	
    }
    
    /**
     * Simulates the process of renting vehicles from empty stations.
     *
     * @throws RedistribuationNotCompletedException When redistribution is not completed.
     * @throws StationEmptyException                When attempting to rent from an empty station.
     * @throws InterruptedException                 When a thread is interrupted.
     * @throws StationFullException                When attempting to deposit at a full station.
     */
    public static void rentWhenEmptyStationsSimulation() throws RedistribuationNotCompletedException, StationEmptyException, InterruptedException, StationFullException{
    	Station wazzemes = myCenter.getStations().get(3);
    	displayStation(wazzemes);
    	Vehicle v;
    	int nbOfVehicles = wazzemes.getNbVehicles();
    	for (int i = 0; i<nbOfVehicles; i++) {
    		Client c=new Client("Client-"+i);
    		v=wazzemes.accept(c);
    		clients.add(c);
    		rentedVehicles.add(v);
    		displayRent(v, c, wazzemes);
    	}
    	displayStation(wazzemes);
    	try {
    		wazzemes.accept(manil);
    	} catch (StationEmptyException e) {
    		System.out.println("You can't rent, the station is empty!");
    	}
    	
    	Thread.sleep(Constants.INTERVAL+100);
        System.out.println("Redistribution in progress... ");
        Thread.sleep(Constants.INTERVAL+2000);
        displayVlille();
        System.out.println();
    }
    
    /**
     * Simulates the process of depositing vehicles into full stations.
     *
     * @throws RedistribuationNotCompletedException When redistribution is not completed.
     * @throws InterruptedException                 When a thread is interrupted.
     * @throws StationFullException                When attempting to deposit at a full station.
     * @throws StationEmptyException                When attempting to rent from an empty station.
     */
    public static void depositWhenFullStationsSimulation() throws RedistribuationNotCompletedException, InterruptedException, StationFullException, StationEmptyException{
    	Station gambeta = myCenter.getStations().get(2);
        int nbOfVehicles = gambeta.getMaxCapacite()-gambeta.getNbVehicles();
        try {
	    	for (int i = 0; i<nbOfVehicles; i++) {
	    		if(!rentedVehicles.isEmpty()) {
	    			try {
	    				gambeta.deposit(rentedVehicles.get(0));
	    			} catch (RedistribuationNotCompletedException e) {
	    	        	System.out.println("Redistribution has not been completed !  Because all stations have already enough vehicles !");
	    	        }
		    		rentedVehicles.remove(0);
	    		}
	    	}
	    	Vehicle vehic;
	    	int i=0;
	    	while (!gambeta.isFull()) {
	    		vehic=myCenter.getStations().get(i%10).accept(madjid);
	    		try {
	    			gambeta.deposit(vehic);
    			} catch (RedistribuationNotCompletedException e) {
    	        	System.out.println("Redistribution has not been completed !  Because all stations have already enough vehicles !");
    	        }
	    		
	    		i++;
	    	}
	    } catch (RedistribuationNotCompletedException e) {
	        	System.out.println("Redistribution has not been completed !  Because all stations have already enough vehicles !");
        }
	    	
	    int i=0;
	    while (!rentedVehicles.isEmpty()) {
	    	if (i%10!=2 && !myCenter.getStations().get(i%10).isFull()) {
	    		myCenter.getStations().get(i%10).deposit(rentedVehicles.get(0));
	    		rentedVehicles.remove(0);
	    	}
	    	i++;
	    }
        
    	
    	Vehicle velo = myCenter.getStations().get(6).accept(freeze);
    	displayStation(gambeta);
    	try {
    		gambeta.deposit(velo);
    	} catch (StationFullException e) {
    		System.out.println("You can't deposit, the station is full!");
    	}
    	System.out.println("Redistribution in progress... ");
        Thread.sleep(Constants.INTERVAL+1000);
        displayVlille();
        System.out.println();
        
    }
    
    /**
     * Simulates the process of repairing a broken-down vehicle.
     *
     * @throws RedistribuationNotCompletedException When redistribution is not completed.
     * @throws StationEmptyException                When attempting to rent from an empty station.
     * @throws StationFullException                When attempting to deposit at a full station.
     * @throws InterruptedException                 When a thread is interrupted.
     */
    public static void repaireBrokenDownVehicle() throws RedistribuationNotCompletedException, StationEmptyException, StationFullException, InterruptedException{
    	Station gambeta = myCenter.getStations().get(2);
    	Station chu = myCenter.getStations().get(6);
        Vehicle velo = gambeta.accept(madjid);
        chu.deposit(velo);
        while(velo.getState().toString().equals("Disponible")) {
        	velo = chu.accept(manil);
        	displayRent(velo, manil, chu);
        	chu.deposit(velo);
        	displayDeposit(velo, manil, chu);
        }
        displayStation(chu);
        System.out.println();
        System.out.println(velo.decorate()+" is "+velo.getState().toString());
        System.out.println("Reparing in progress ...");
        myCenter.repaireAllVehicles();
        System.out.println(velo.decorate()+" is "+velo.getState().toString());
        displayStation(chu);
        
    }
    
    /**
     * Simulates the process of stealing a vehicle from a station.
     *
     * @throws RedistribuationNotCompletedException When redistribution is not completed.
     * @throws StationEmptyException                When attempting to rent from an empty station.
     * @throws StationFullException                When attempting to deposit at a full station.
     * @throws InterruptedException                 When a thread is interrupted.
     */
    public static void stealVehicleSimulation()throws RedistribuationNotCompletedException, StationEmptyException, StationFullException, InterruptedException{
        Station v2 = myCenter.getStations().get(3);
        displayStation(v2);
        System.out.println();
        int i =1;
        while(!v2.onlyOneLeft()) {
        	Client client = new Client("Client -"+i);
        	Vehicle v = v2.accept(client);
        	displayRent(v, client, v2);
        	rentedVehicles.add(v);
        	clients.add(client);
        	i++;
        }
        displayStation(v2);
        System.out.println("Warning ! Vehicle can be stolen ! ! ! ");
        Thread.sleep(Constants.INTERVAL*2+100);
        displayStation(v2);
        System.out.println(" A Bike is  Stolen !");
        Thread.sleep(Constants.INTERVAL*2+100);
        System.out.println("Redistribution in progress... ");
        Thread.sleep(Constants.INTERVAL+100);
        displayVlille();
    }
    
    
    /**
     * Simulates the process of the redistibution when the station is full..
     *
     * @throws RedistribuationNotCompletedException When redistribution is not completed.
     * @throws StationEmptyException                When attempting to rent from an empty station.
     * @throws StationFullException                When attempting to deposit at a full station.
     * @throws InterruptedException                 When a thread is interrupted.
     */
    public static void redstibutionWhenFullSimulation()throws RedistribuationNotCompletedException, StationEmptyException, StationFullException, InterruptedException{
    	int i=1;
    	Station tazmelt = myCenter.getStations().get(9);
    	while(tazmelt.getNbVehicles()>2) {
    		Client client = new Client("Client -"+i);
        	Vehicle v = tazmelt.accept(client);
        	displayRent(v, client, tazmelt);
        	rentedVehicles.add(v);
        	clients.add(client);
            i++;
    	}
        displayStation(tazmelt);
        System.out.println();
        i=0;
        Station lhouma = myCenter.getStations().get(5);
        while(!lhouma.isFull()) {
        	lhouma.deposit(rentedVehicles.get(0));
        	displayDeposit(rentedVehicles.get(0), clients.get(i+1), lhouma);
        	rentedVehicles.remove(0);
        	i++;
    	}
        displayStation(lhouma);
        System.out.println("Redistribution in progress... ");
        Thread.sleep(Constants.INTERVAL*2+100);
        displayVlille();
    }

	public static void main(String[] args) throws RedistribuationNotCompletedException, StationEmptyException, StationFullException, InterruptedException {
        displayWelcomePage();
        pressEnterFunction();
        initialiseVlille();
        displayVlille();
        displaySimulationsTitle();
        displayRentSimulation();
        pressEnterFunction();
        rentAndDepositSimulation();
        displaySimulation("Rent when empty ");
        pressEnterFunction();
        rentWhenEmptyStationsSimulation();
        displaySimulation("Deposit when full ");
        pressEnterFunction();
        depositWhenFullStationsSimulation();
        displaySimulation("BrokenDown Bike simulation");
        pressEnterFunction();
        repaireBrokenDownVehicle();
        displaySimulation("Stolen Bike simulation");
        pressEnterFunction();
        stealVehicleSimulation();
        displaySimulation("Redistribution when full");
        pressEnterFunction();
        redstibutionWhenFullSimulation();
        displayTheEnd();
        
        
	}
}



















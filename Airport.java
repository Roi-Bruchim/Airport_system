import java.util.*;

public class Airport {
    private static Airport instance; // Singleton instance variable
    private String name;
    private List<Flight> flights;
    private List<Passenger> passengers;

    // Private constructor to prevent external instantiation
    private Airport(String name) {
        this.name = name;
        this.flights = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    // Static method to access the single instance of the Airport class
    public static Airport getInstance(String name) {
        if (instance == null) {
            instance = new Airport(name);
        }
        return instance;
    }

    public void addFlight(Flight flight) {
        boolean flightExists = false;
        for (Flight existingFlight : flights) {
            if (existingFlight.getFlightNumber().equals(flight.getFlightNumber())) {
                flightExists = true;
                break;
            }
        }
        if (!flightExists) {
            flights.add(flight);
        } else {
            System.out.println("Flight " + flight.getFlightNumber() + " already exists.");
        }
    }

    public String getName() {
        return name;
    }

    public void checkInPassenger(Passenger passenger) {
        Flight bookedFlight = passenger.getBookedFlight();
        String seatNumber = passenger.getSeatNumber();

        if (bookedFlight != null && seatNumber != null) {
            boolean seatOccupied = false;
            boolean flightBoarding = bookedFlight.isBoarding();

            if (!flightBoarding) {
                if (isValidSeat(seatNumber)) {
                    for (Passenger p : bookedFlight.getPassengers()) {
                        if (p.getSeatNumber().equals(seatNumber)) {
                            seatOccupied = true;
                            break;
                        }
                    }

                    if (!seatOccupied) {
                        bookedFlight.addPassenger(passenger);
                        System.out.println(passenger.getName() + " checked in for flight " + bookedFlight.getFlightNumber() + " with seat number " + seatNumber);
                    }
                    else {
                        System.out.println("Seat " + seatNumber + " is already occupied. Cannot check in passenger " + passenger.getName());
                    }
                } else {
                    System.out.println("Invalid seat number. Cannot check in passenger " + passenger.getName());
                }
            } else {
                System.out.println("Flight " + bookedFlight.getFlightNumber() + " is already boarding. Cannot check in passenger " + passenger.getName());
            }
        } else {
            System.out.println("Passenger's booking information incomplete. Cannot check in.");
        }
    }

    public boolean isValidSeat(String seatNumber) {
        if (seatNumber.length() != 2 && seatNumber.length() != 3) {
            return false;
        }

        if (seatNumber.length() == 2) {
            int row = Character.getNumericValue(seatNumber.charAt(0));
            char seat = seatNumber.charAt(1);
            return row >= 1 && row <= 50 && (seat >= 'A' && seat <= 'F');
        } else {
            int row = Integer.parseInt(seatNumber.substring(0, 2));
            char seat = seatNumber.charAt(2);
            return row >= 1 && row <= 50 && (seat >= 'A' && seat <= 'F');
        }
    }


    public void boardFlight(Flight flight) {
        List<Passenger> passengers = flight.getPassengers();
        for (Passenger passenger : passengers) {
            System.out.println("Boarding passenger: " + passenger.getName() + " (Seat: " + passenger.getSeatNumber() + ")");
        }
        flight.setStatus(Flight.FlightStatus.BOARDING);

        Plane plane = flight.getModel();
        plane.boarding();
    }

    public void takeoffFlight(Flight flight) {
        if (flight.getStatus() == Flight.FlightStatus.BOARDING) {
            System.out.println("Flight " + flight.getFlightNumber() + " is taking off.");

            Plane plane = flight.getModel();
            if (plane != null) {
                ((Flyable) plane).takeoff();
            }

            flights.remove(flight);

            List<Passenger> passengers = flight.getPassengers();
            for (Passenger passenger : passengers) {
                System.out.println("Removing passenger: " + passenger.getName() + " from flight " + flight.getFlightNumber());
            }
            passengers.clear();
        } else {
            System.out.println("Cannot take off. Boarding has not started yet for flight " + flight.getFlightNumber());
        }
    }
    public void landingFlight(Flight flight) {

        System.out.println("Flight " + flight.getFlightNumber() + " is landing.");


        Plane plane = flight.getModel();
        if (plane != null) {
            ((Flyable) plane).landing();
        }
        addFlight(flight);

    }

    public void printFlightsSorted()
    {
        flights.sort(Comparator.comparing(Flight::getTake_off_time));
        System.out.println("Departures:");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    public boolean gate_avaliable(int gate)
    {
        if(gate<=0 || gate>20)
            return false;
        for (Flight flight : flights) {
            {
                if(flight.getGate()==gate)
                    return false;
            };
        }
        return true;
    }

    public boolean flight_number_avaliable(String flight_number)
    {
        for (Flight flight : flights) {
            {
                if(flight.getFlightNumber().equals(flight_number))
                    return false;
            };
        }
        return true;
    }

    public List<Flight> getFlights() {
        return flights;
    }

}





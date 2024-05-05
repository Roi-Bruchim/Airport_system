import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        // Create an airport
        Airport airport = Airport.getInstance("Ben Gurion");

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = currentDate.format(formatter);



        // Create some flights
        Flight flight2 = new Flight("DELTA", "DL8831", new Boeing787(), dateString+" 12:00", "New York", new ArrayList<>(),4);
        Flight flight1 = new Flight("EL AL", "LY301", new Boeing787(), dateString+" 10:20", "Barcelona", new ArrayList<>(),12);
        Flight flight3 = new Flight("AEGEAN", "AG676", new Boeing737(), dateString+" 11:00", "Rhodes", new ArrayList<>(),7);
        Flight flight4 = new Flight("Air France", "FR222", new Boeing777(), dateString+" 13:30", "Paris", new ArrayList<>(),1);
        Flight flight5 = new Flight("Arkia", "AR782", new Boeing737(), dateString+" 15:45", "Eilat", new ArrayList<>(),2);
        Flight flight6 = new Flight("EL AL", "LY902", new Boeing737(), dateString+" 20:00", "Vienna", new ArrayList<>(),9);
        Flight flight7 = new Flight("KLM", "KLM99", new Boeing787(), dateString+" 17:25", "Amsterdam", new ArrayList<>(),11);




        // Add flights to the airport
        airport.getFlights().add(flight1);
        airport.getFlights().add(flight2);
        airport.getFlights().add(flight3);
        airport.getFlights().add(flight4);
        airport.getFlights().add(flight5);
        airport.getFlights().add(flight6);
        airport.getFlights().add(flight7);


        flight1.setStatus(Flight.FlightStatus.DELAYED);


        // Create GUI and show it
        AirportGUI gui = new AirportGUI(airport);
        gui.createAndShowGUI();
    }
}

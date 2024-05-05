import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AirportGUI {
    private JFrame frame;
    private Airport airport;
    private JTextArea flightsTextArea;

    public AirportGUI(Airport airport) {
        this.airport = airport;
    }

    public void createAndShowGUI() {

        frame = new JFrame("Ben Gurion Airport");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1400);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 5, 5)); // Changed to 5 rows for the new button
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton checkInButton = new JButton("Check In Passenger");
        checkInButton.addActionListener(e -> checkInPassenger());
        buttonPanel.add(checkInButton);

        JButton boardFlightButton = new JButton("Board Flight");
        boardFlightButton.addActionListener(e -> boardFlight());
        buttonPanel.add(boardFlightButton);

        JButton takeoffFlightButton = new JButton("Takeoff Flight");
        takeoffFlightButton.addActionListener(e -> takeoffFlight());
        buttonPanel.add(takeoffFlightButton);

        JButton landingFlightButton = new JButton("Landing Flight");
        landingFlightButton.addActionListener(e -> landingFlight());
        buttonPanel.add(landingFlightButton);

        JButton gateFlightButton = new JButton("Change flight gate");
        gateFlightButton.addActionListener(e -> gateFlightButton());
        buttonPanel.add(gateFlightButton);

        JButton flightInfoButton = new JButton("Flight Information");
        flightInfoButton.addActionListener(e -> showFlightInfo());
        buttonPanel.add(flightInfoButton);

        JButton changeStatusButton = new JButton("Change Flight Status"); // New button
        changeStatusButton.addActionListener(e -> changeFlightStatus()); // ActionListener for the new button
        buttonPanel.add(changeStatusButton); // Adding the new button to the panel

        mainPanel.add(buttonPanel);

        JPanel flightPanel = new JPanel(new BorderLayout());
        flightPanel.setBorder(BorderFactory.createTitledBorder("Flights"));

        flightsTextArea = new JTextArea();
        flightsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(flightsTextArea);
        flightPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(flightPanel);

        panel.add(mainPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        // Update flights display initially
        updateFlightsDisplay();
    }

    private void showFlightInfo() {
        String flightNumber = JOptionPane.showInputDialog("Enter flight number:");
        Flight flight = findFlightByNumber(flightNumber);

        if (flight != null) {
            StringBuilder flightInfo = new StringBuilder();
            flightInfo.append("Flight Information for ").append(flightNumber).append(":\n");
            flightInfo.append("Destination: ").append(flight.getDestination()).append("\n");
            flightInfo.append("Company: ").append(flight.getCompany()).append("\n");
            flightInfo.append("Plane Model: ").append(flight.getModel().getModel()).append("\n");
            flightInfo.append("Take-off Time: ").append(flight.getTake_off_time()).append("\n");
            flightInfo.append("Flight Status: ").append(flight.getStatus()).append("\n");
            flightInfo.append("Flight gate: ").append(flight.getGate()).append("\n");
            flightInfo.append("Total Possible Passengers (Capacity): ").append(flight.getModel().getCapacity()).append("\n");
            List<Passenger> passengers = flight.getPassengers();
            flightInfo.append("Number of Passengers: ").append(passengers.size()).append("\n");
            if (!passengers.isEmpty()) {
                flightInfo.append("Passengers:\n");
                for (Passenger passenger : passengers) {
                    flightInfo.append("- ").append(passenger.getName()).append(" (Seat: ").append(passenger.getSeatNumber()).append(")\n");
                }
            } else {
                flightInfo.append("No passengers booked for this flight.\n");
            }

            // Add information about the number of passengers and the total possible on the plane



            JOptionPane.showMessageDialog(null, flightInfo.toString(), "Flight Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Flight not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void gateFlightButton() {
        String flightNumber = JOptionPane.showInputDialog("Enter flight number:");
        Flight flight = findFlightByNumber(flightNumber);

        if (flight != null) {
            if (flight.getStatus() == Flight.FlightStatus.BOARDING) {
                JOptionPane.showMessageDialog(null, "Cannot change gate. Flight is currently boarding.");
                return;
            }

            int newGate = Integer.parseInt(JOptionPane.showInputDialog("Enter new gate:"));

            if (!airport.gate_avaliable(newGate)) {
                JOptionPane.showMessageDialog(null, "Gate " + newGate + " is already in use.");
                return;
            }

            flight.setGate(newGate);
            JOptionPane.showMessageDialog(null, "Gate for flight " + flightNumber + " changed to " + newGate);
            updateFlightsDisplay();
        } else {
            JOptionPane.showMessageDialog(null, "Flight not found!");
        }
    }

    private void changeFlightStatus() {
        LocalDate currentDate = LocalDate.now();
        // Format the current date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = currentDate.format(formatter); // Define dateString here

        String flightNumber = JOptionPane.showInputDialog("Enter flight number:");
        Flight flight = findFlightByNumber(flightNumber);

        if (flight != null) {
            String[] options = {"On Time", "Boarding", "Delayed", "Landed"}; // Status options
            int choice = JOptionPane.showOptionDialog(null, "Select new status for flight " + flightNumber + ":", "Change Flight Status", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice != JOptionPane.CLOSED_OPTION) {
                Flight.FlightStatus newStatus = Flight.FlightStatus.values()[choice];

                if (newStatus == Flight.FlightStatus.DELAYED) {
                    String newTakeoffTime = JOptionPane.showInputDialog("Enter new take-off time (HH:mm):");
                    if (isValidTime(newTakeoffTime)) {
                        String full = dateString + " " + newTakeoffTime;
                        flight.setTake_off_time(full);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid time format. Please enter time in HH:mm format.");
                        return;
                    }
                } else if (newStatus == Flight.FlightStatus.BOARDING && !flight.getStatus().equals(Flight.FlightStatus.BOARDING)) {
                    JOptionPane.showMessageDialog(null, "Boarding hasn't started yet for flight " + flightNumber);
                    return;
                }

                flight.setStatus(newStatus);
                JOptionPane.showMessageDialog(null, "Flight " + flightNumber + " status changed to " + newStatus);
                updateFlightsDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Flight not found!");
        }
    }




    private void checkInPassenger() {
        String passengerName = JOptionPane.showInputDialog("Enter passenger name:");
        String flightNumber = JOptionPane.showInputDialog("Enter flight number:");
        String seatNumber = JOptionPane.showInputDialog("Enter seat number:");

        Flight flight = findFlightByNumber(flightNumber);
        if (flight.getStatus() == Flight.FlightStatus.BOARDING) {
            Passenger passenger = new Passenger(passengerName, flight, seatNumber);
            airport.checkInPassenger(passenger);
            JOptionPane.showMessageDialog(null, "Flight " + flight.getFlightNumber() + "is already boarding. Cannot check in passenger " + passengerName);
        } else {
            if (flight != null) {
                if (isSeatOccupied(flight, seatNumber)) {
                    JOptionPane.showMessageDialog(null, "Seat " + seatNumber + " is already occupied. Cannot check in passenger.");
                } else {
                    Passenger passenger = new Passenger(passengerName, flight, seatNumber);
                    airport.checkInPassenger(passenger);
                    if (airport.isValidSeat(seatNumber))
                        JOptionPane.showMessageDialog(null, passengerName + " has been checked in for flight " + flightNumber + " with seat number " + seatNumber);
                    else
                        JOptionPane.showMessageDialog(null, "Invalid seat. Cannot check in passenger.");
                    updateFlightsDisplay();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Flight not found!");
            }
        }
    }

    private boolean isSeatOccupied(Flight flight, String seatNumber) {
        for (Passenger passenger : flight.getPassengers()) {
            if (passenger.getSeatNumber().equals(seatNumber)) {
                return true;
            }
        }
        return false;
    }


    private void boardFlight() {
        String flightNumber = JOptionPane.showInputDialog("Enter flight number:");

        Flight flight = findFlightByNumber(flightNumber);
        if (flight != null) {
            List<Passenger> boardingPassengers = flight.getPassengers();
            StringBuilder boardingPassengersInfo = new StringBuilder();
            for (Passenger passenger : boardingPassengers) {
                boardingPassengersInfo.append(passenger.getName()).append(" (Seat: ").append(passenger.getSeatNumber()).append(")\n");
            }

            airport.boardFlight(flight);
            flight.setBoarding(true); // Set boarding status to true
            String message = boardingPassengers.size() + " Passengers are boarding for flight " + flightNumber + ":\n" + boardingPassengersInfo.toString();
            JOptionPane.showMessageDialog(null, message);
            updateFlightsDisplay();
        } else {
            JOptionPane.showMessageDialog(null, "Flight not found!");
        }
    }

    private void takeoffFlight() {
        String flightNumber = JOptionPane.showInputDialog("Enter flight number:");

        Flight flight = findFlightByNumber(flightNumber);
        if (flight != null) {
            if (flight.getStatus() == Flight.FlightStatus.BOARDING) {
                JOptionPane.showMessageDialog(null, "Flight " + flightNumber + " is taking off with "+flight.numOfPass()+" passengers");
                airport.takeoffFlight(flight);

                updateFlightsDisplay();
            } else {
                JOptionPane.showMessageDialog(null, "Cannot take off. Boarding has not started yet for flight " + flightNumber);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Flight not found!");
        }
    }

    private void landingFlight() {
        LocalDate currentDate = LocalDate.now();

        // Format the current date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = currentDate.format(formatter);
        String company = JOptionPane.showInputDialog("Enter Company:");
        String flightNumber = JOptionPane.showInputDialog("Enter flight number:");
        String model = JOptionPane.showInputDialog("Enter plane model:");
        String destination = JOptionPane.showInputDialog("Enter destination:");
        String takeoff = JOptionPane.showInputDialog("Enter take off time (HH:mm):");

        if (isValidTime(takeoff)) {
            String full_take = dateString + " " + takeoff;
            int gate = Integer.parseInt(JOptionPane.showInputDialog("Enter gate:"));
            if (!airport.flight_number_avaliable(flightNumber)) {
                JOptionPane.showMessageDialog(null, "Flight number is taken");
            } else {
                if (!airport.gate_avaliable(gate)) {
                    JOptionPane.showMessageDialog(null, "Gate already in used");
                } else {
                    if (model.equals("Boeing737") || model.equals("Boeing 737") || model.equals("boeing737") || model.equals("boeing 737") || model.equals("BOEING 737") || model.equals("BOEING737")) {
                        // Create a new instance of Flight
                        Flight landedFlight = new Flight(company, flightNumber, new Boeing737(), full_take, destination, new ArrayList<>(), gate);
                        landedFlight.setStatus(Flight.FlightStatus.LANDED);

                        // Landing the flight
                        airport.landingFlight(landedFlight);

                        // Show message and update flights display
                        JOptionPane.showMessageDialog(null, "Flight " + flightNumber + " is landing");
                        updateFlightsDisplay();
                    } else if (model.equals("Boeing777") || model.equals("Boeing 777") || model.equals("boeing777") || model.equals("boeing 777") || model.equals("BOEING 777") || model.equals("BOEING777")) {
                        Flight landedFlight = new Flight(company, flightNumber, new Boeing777(), full_take, destination, new ArrayList<>(), gate);
                        landedFlight.setStatus(Flight.FlightStatus.LANDED);
                        // Landing the flight
                        airport.landingFlight(landedFlight);

                        // Show message and update flights display
                        JOptionPane.showMessageDialog(null, "Flight " + flightNumber + " is landing");
                        updateFlightsDisplay();
                    } else if (model.equals("Boeing787") || model.equals("Boeing 787") || model.equals("boeing787") || model.equals("boeing 787") || model.equals("BOEING 787") || model.equals("BOEING787")) {
                        Flight landedFlight = new Flight(company, flightNumber, new Boeing787(), full_take, destination, new ArrayList<>(), gate);
                        landedFlight.setStatus(Flight.FlightStatus.LANDED);
                        // Landing the flight
                        airport.landingFlight(landedFlight);

                        // Show message and update flights display
                        JOptionPane.showMessageDialog(null, "Flight " + flightNumber + " is landing");
                        updateFlightsDisplay();
                    } else {
                        JOptionPane.showMessageDialog(null, "Model does not exist");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid time format. Please enter time in HH:mm format.");
        }
    }

    private Flight findFlightByNumber(String flightNumber) {
        for (Flight flight : airport.getFlights()) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    private void updateFlightsDisplay() {
        LocalDate currentDate = LocalDate.now();

        // Format the current date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = currentDate.format(formatter);
        // Sort flights by departure time
        airport.printFlightsSorted();

        StringBuilder flightsInfo = new StringBuilder();
        flightsInfo.append("Flights for "+ dateString+" in terminal 3:\n\n");
        List<Flight> flights = airport.getFlights();
        for (Flight flight : flights) {
            flightsInfo.append(flight.toString());
            flightsInfo.append(" - ");

            String status;
            switch (flight.getStatus()) {
                case ON_TIME:
                    status = "On Time";
                    break;
                case BOARDING:
                    status = "Boarding";
                    break;
                case DELAYED:
                    status = "Delayed";
                    break;
                case LANDED:
                    status = "Landed";
                    break;
                default:
                    status = "Unknown";
                    break;
            }

            flightsInfo.append(status);
            flightsInfo.append("\n\n");
        }

        flightsTextArea.setText(flightsInfo.toString());

        // Set color for the status
        flightsTextArea.setForeground(Color.BLACK); // Reset color first
        for (Flight flight : flights) {
            switch (flight.getStatus()) {
                case ON_TIME:
                    setTextColorForStatus("On Time", Color.GREEN);
                    break; // Green color for "On Time" status
                case BOARDING:
                    setTextColorForStatus("Boarding", Color.ORANGE); // Orange color for "Boarding" status
                    break;
                case DELAYED:
                    setTextColorForStatus("Delayed", Color.RED); // Red color for "Delayed" status
                    break;
                case LANDED:
                    setTextColorForStatus("Landed", Color.BLUE); // Blue color for "Landed" status
                    break;
                default:
                    break;
            }
        }

        // Set font size
        flightsTextArea.setFont(new Font("BLAKE", Font.PLAIN, 14));
    }


    private void setTextColorForStatus(String status, Color color) {
        String text = flightsTextArea.getText();
        int index = text.indexOf(status);
        while (index >= 0) {
            flightsTextArea.setForeground(color);
            flightsTextArea.select(index, index + status.length());
            index = text.indexOf(status, index + status.length());
        }
        flightsTextArea.setForeground(Color.BLACK); // Reset color after changing the status color
    }

    private boolean isValidTime(String time) {
        if (time == null || !time.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
    }
}


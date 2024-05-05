import java.util.List;

public class Flight{

    private String company;
    private String flightNumber;
    private Plane model;
    private String take_off_time;
    private String destination;
    private List<Passenger> passengers;
    private FlightStatus status;
    private int gate;
    private boolean boarding;

    public enum FlightStatus {
        ON_TIME,
        BOARDING,
        DELAYED,
        LANDED
    }

    public Flight(String company,String flightNumber, Plane model, String take_off_time, String destination,  List<Passenger> passengers, int gate)
    {
        this.company=company;
        this.flightNumber=flightNumber;
        this.model=model;
        this.take_off_time=take_off_time;
        this.destination=destination;
        this.passengers=passengers;
        this.status = FlightStatus.ON_TIME;
        this.gate=gate;
        this.boarding = false;
    }

    public boolean isBoarding() {
        return boarding;
    }

    public void setBoarding(boolean boarding) {
        this.boarding = boarding;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Plane getModel() {
        return model;
    }

    public void setModel(Plane model) {
        this.model = model;
    }

    public String getTake_off_time() {
        return take_off_time;
    }

    public void setTake_off_time(String take_off_time) {
        this.take_off_time = take_off_time;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public int getGate() {
        return gate;
    }

    public void setGate(int gate) {
        this.gate = gate;
    }

    public int numOfPass()
    {
        return passengers.size();
    }

    @Override
    public String toString() {
        return "Flight "+flightNumber+" of company "+company+" ("+model.getModel()+") will fly to "+destination+" on: "+take_off_time+ " from gate "+gate;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    }




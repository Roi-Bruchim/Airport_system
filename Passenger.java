public class Passenger {
    private String name;
    private Flight bookedFlight;
    private String seatNumber;

    public Passenger(String name, Flight bookedFlight, String seatNumber) {
        this.name = name;
        this.bookedFlight = bookedFlight;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flight getBookedFlight() {
        return bookedFlight;
    }

    public void setBookedFlight(Flight bookedFlight) {
        this.bookedFlight = bookedFlight;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        if (bookedFlight != null) {
            return name + " is flying to " + bookedFlight.getDestination() + " with " + bookedFlight.getCompany();
        } else {
            return name + " does not have a booked flight.";
        }
    }
}

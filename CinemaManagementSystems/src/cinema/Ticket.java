package cinema;

import java.io.Serializable;

public class Ticket implements Serializable {
    private int customerID;
    private Movie movie;
    private String customerName;
    private String seatNumber;
    private double price;

    public Ticket(int customerID, Movie movie, String customerName, String seatNumber, double price) {
        this.customerID = customerID;
        this.movie = movie;
        this.customerName = customerName;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public Movie getMovie() { return movie; }
    public String getSeatNumber() { return seatNumber; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "Ticket for " + movie.getTitle() + " (Seat: " + seatNumber + ", Price: $" + price + ")";
    }
}

package cinema;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<String> favoriteMovies; // List of favorite movies
    private List<String> ticketReceipts; // List of ticket receipts

    public Customer(String username, String password, String fullName) {
        super(username, password, fullName);
        this.favoriteMovies = new ArrayList<>();
        this.ticketReceipts = new ArrayList<>();
    }

    @Override
    public void performAction() {
        System.out.println("Customer is performing an action.");
    }

    // Add a favorite movie
    public void addFavoriteMovie(String movie) {
        favoriteMovies.add(movie);
    }

    // Get the list of favorite movies
    public List<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    // Add a ticket receipt
    public void addTicketReceipt(String ticketReceipt) {
        ticketReceipts.add(ticketReceipt);
    }

    // Get the list of ticket receipts
    public List<String> getTicketReceipts() {
        return ticketReceipts;
    }
}

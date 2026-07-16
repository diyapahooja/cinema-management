package cinema;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDashboardPanel {

    private Stage stage;
    private Customer customer;
    private List<Movie> movieList;
    private List<String> favoritesList = new ArrayList<>();
    private String lastBookedMovie = null;
    private String lastBookedSeat = null;
    private Scene mainScene;

    public CustomerDashboardPanel(Stage stage, Customer customer, List<Movie> movieList) {
        this.stage = stage;
        this.customer = customer;
        this.movieList = movieList;
        this.mainScene = createMainScene();
    }

    public Scene getCustomerDashboardScene() {
        return mainScene;
    }

    private Scene createMainScene() {
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: #2f2f2f; -fx-padding: 20;");

        Label welcomeLabel = new Label("Welcome, " + customer.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #e50914;");

        Button viewMoviesButton = createStyledButton("View Movies");
        Button addFavoritesButton = createStyledButton("Select Favorite Movies");
        Button viewFavoritesButton = createStyledButton("View Favorite Movies");
        Button bookMovieButton = createStyledButton("Book a Ticket");
        Button viewReceiptButton = createStyledButton("View Receipt");
        Button logoutButton = createStyledButton("Logout");
        
        // Add Show by Genre Button
        Button showByGenreButton = createStyledButton("Show by Genre");
        showByGenreButton.setOnAction(e -> transitionToScene(this::showGenreSelectionScene));

        viewMoviesButton.setOnAction(e -> transitionToScene(this::viewMoviesScene));
        addFavoritesButton.setOnAction(e -> transitionToScene(this::addFavoritesScene));
        viewFavoritesButton.setOnAction(e -> transitionToScene(this::viewFavoritesScene));
        bookMovieButton.setOnAction(e -> transitionToScene(this::bookMovieScene));
        viewReceiptButton.setOnAction(e -> transitionToScene(this::viewReceiptScene));
        logoutButton.setOnAction(e -> logout());

        root.getChildren().addAll(welcomeLabel, viewMoviesButton, addFavoritesButton, 
                                  viewFavoritesButton, bookMovieButton, viewReceiptButton, 
                                  showByGenreButton, logoutButton);

        return new Scene(root, 800, 600);
    }

    private void showGenreSelectionScene() {
        VBox genreLayout = new VBox(10);
        genreLayout.setStyle("-fx-background-color: #2f2f2f; -fx-padding: 20;");

        Label genreLabel = new Label("Select a Genre:");
        genreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #e50914;");

        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("All", "Action", "Drama", "Sci-Fi", "Fantasy", "Horror", "Comedy", "Romance");
        genreComboBox.setValue("All"); // Default value

        ListView<Movie> filteredMovieListView = new ListView<>();
        filteredMovieListView.setStyle("-fx-background-color: #3f3f3f; -fx-text-fill: white; -fx-border-color: #e50914; -fx-border-radius: 5;");

        // Update the movie list based on selected genre
        genreComboBox.setOnAction(e -> {
            String selectedGenre = genreComboBox.getValue();
            List<Movie> filteredMovies;
            if (selectedGenre.equals("All")) {
                filteredMovies = movieList; // Show all movies
            } else {
                filteredMovies = movieList.stream()
                        .filter(movie -> movie.getGenre().equalsIgnoreCase(selectedGenre))
                        .collect(Collectors.toList());
            }
            filteredMovieListView.getItems().setAll(filteredMovies);
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(mainScene)));

        genreLayout.getChildren().addAll(genreLabel, genreComboBox, filteredMovieListView, backButton);
        stage.setScene(new Scene(genreLayout, 800, 600));
    }

    private Button createStyledButton(String text) {
        Button button = new Button (text);
        button.setStyle("-fx-font-size: 16px; -fx-background-color: #e50914; -fx-text-fill: white; " +
                        "-fx-padding: 10px; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 1);");
        button.setPrefWidth(200);
        return button;
    }

    private void viewMoviesScene() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #2f2f2f; -fx-padding: 20;");

        Label label = new Label("Available Movies:");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: #e50914;");

        ListView<Movie> movieListView = new ListView<>();
        movieListView.getItems().addAll(movieList);
        movieListView.setStyle("-fx-background-color: #3f3f3f; -fx-text-fill: white; -fx-border-color: #e50914; -fx-border-radius: 5;");

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(mainScene)));

        layout.getChildren().addAll(label, movieListView, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void addFavoritesScene() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #2f2f2f; -fx-padding: 20;");

        Label label = new Label("Select Favorite Movies:");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: #e50914;");

        ListView<Movie> movieListView = new ListView<>();
        movieListView.getItems().addAll(movieList);
        movieListView.setStyle("-fx-background-color: #3f3f3f; -fx-text-fill: white; -fx-border-color: #e50914; -fx-border-radius: 5;");

        Button addButton = createStyledButton("Add to Favorites");
        addButton.setOnAction(e -> {
            Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null && !favoritesList.contains(selectedMovie.getTitle())) {
                favoritesList.add(selectedMovie.getTitle());
                showAlert("Success", "Movie added to favorites!");
            } else {
                showAlert("Error", "Movie already in favorites or not selected!");
            }
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(mainScene)));

        layout.getChildren().addAll(label, movieListView, addButton, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void viewFavoritesScene() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #2f2f2f; -fx-padding: 20;");

        Label label = new Label("Your Favorite Movies:");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: #e50914;");

        ListView<String> favoritesView = new ListView<>();
        favoritesView.getItems().addAll(favoritesList);
        favoritesView.setStyle("-fx-background-color: #3f3f3f; -fx-text-fill: white; -fx-border-color: #e50914; -fx-border-radius: 5;");

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(mainScene)));

        layout.getChildren().addAll(label, favoritesView, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void bookMovieScene() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #2f2f2f; -fx-padding: 20;");

        Label label = new Label("Select a Movie to Book:");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: #e50914;");

        ListView<Movie> movieListView = new ListView<>();
        movieListView.getItems().addAll(movieList);
        movieListView.setStyle("-fx-background-color: #3f3f3f; -fx-text-fill: white; -fx-border-color: #e50914; -fx-border-radius: 5;");

        TextField seatInput = new TextField();
        seatInput.setPromptText("Enter Seat Number");
        seatInput.setStyle("-fx-background-color: #3f3f3f; -fx-text-fill: white; -fx-border-color: #e50914; -fx-border-radius: 5;");

        Button bookButton = createStyledButton("Book Ticket");
        bookButton.setOnAction(e -> {
            Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
            String seatNumber = seatInput.getText().trim();

            if (selectedMovie != null && !seatNumber.isEmpty()) {
                lastBookedMovie = selectedMovie.getTitle();
                lastBookedSeat = seatNumber;
                showAlert("Booking Confirmed", "You booked " + lastBookedMovie + " at seat " + lastBookedSeat + "!");
            } else {
                showAlert("Error", "Please select a movie and enter a seat number!");
            }
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(mainScene)));

        layout.getChildren().addAll(label, movieListView, seatInput, bookButton, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }
    


    private void viewReceiptScene() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #2f2f2f; -fx-padding: 20;");

        Label label = new Label("Booking Receipt:");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: #e50914;");

        // Display the booking details
        if (lastBookedMovie != null && lastBookedSeat != null) {
            Label receiptLabel = new Label("Movie: " + lastBookedMovie + "\nSeat: " + lastBookedSeat);
            receiptLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            layout.getChildren().add(receiptLabel);
        } else {
            Label noReceiptLabel = new Label("No booking receipt available.");
            noReceiptLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            layout.getChildren().add(noReceiptLabel);
        }

        // Display the price
        Label priceLabel = new Label("Price: $90"); // You can dynamically calculate this based on the movie and seat
        priceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        layout.getChildren().add(priceLabel);

        // Create the "Download Receipt" button
        Button downloadButton = createStyledButton("Download Receipt");
        downloadButton.setOnAction(e -> downloadReceipt());

        // Create the "Back" button
        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(mainScene)));

        layout.getChildren().addAll(label, downloadButton, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }


    private void downloadReceipt() {
        // Create a FileChooser to allow the user to select where to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialFileName("Receipt_" + customer.getUsername() + ".txt");

        // Show the save dialog and get the selected file
        File file = fileChooser.showSaveDialog(stage);
        
        // If a file is selected, write the receipt content to the file
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                String receiptContent = "Movie: " + lastBookedMovie + "\nSeat: " + lastBookedSeat;
                writer.write(receiptContent);
                showAlert("Success", "Receipt downloaded successfully to " + file.getAbsolutePath());
            } catch (IOException ex) {
                showAlert("Error", "An error occurred while saving the receipt.");
                ex.printStackTrace();
            }
        }
    }



    private void logout() {
        showAlert("Logout", "You have been logged out.");
        stage.close(); // You can redirect to the login scene here
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void transitionToScene(Runnable sceneChangeAction) {
        VBox root = (VBox) stage.getScene().getRoot();
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            sceneChangeAction.run();
            VBox newRoot = (VBox) stage.getScene().getRoot();
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }
}
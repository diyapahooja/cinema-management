package cinema;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class AdminMovieManagementPanel {

    private Stage stage;
    private Admin admin;
    private List<Movie> movieList;
    private Scene loginScene; // Reference to the login scene

    public AdminMovieManagementPanel(Stage stage, Admin admin, List<Movie> movieList, Scene loginScene) {
        this.stage = stage;
        this.admin = admin;
        this.movieList = movieList;
        this.loginScene = loginScene;
    }

    public Scene getAdminDashboardScene() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Adding background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("netflicbg.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false)
        );
        root.setBackground(new Background(backgroundImage));

        Label welcomeLabel = new Label("Admin Dashboard");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Create buttons
        Button addMovieButton = createButton("Add Movie");
        Button deleteMovieButton = createButton("Delete Movie");
        Button viewMoviesButton = createButton("View Movies");
        Button managePricingButton = createButton("Manage Ticket Pricing");
        Button movieCategoriesButton = createButton("Manage Movie Categories");
        Button logoutButton = createButton("Logout");

        // Set button actions
        addMovieButton.setOnAction(e -> transitionToScene(this::openAddMovieScene));
        deleteMovieButton.setOnAction(e -> transitionToScene(this::openDeleteMovieScene));
        viewMoviesButton.setOnAction(e -> transitionToScene(this::openViewMoviesScene));
        managePricingButton.setOnAction(e -> transitionToScene(this::openManageTicketPricingScene));
        movieCategoriesButton.setOnAction(e -> transitionToScene(this::openMovieCategoriesScene));
        logoutButton.setOnAction(e -> transitionToScene(this::logout));

        // Add buttons to the layout
        root.getChildren().addAll(welcomeLabel, addMovieButton, deleteMovieButton, viewMoviesButton,
                managePricingButton, movieCategoriesButton, logoutButton);

        return new Scene(root, 1000, 700);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #e50914; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: white; -fx-border-width: 2px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #ff1f3f; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: white; -fx-border-width: 2px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: white; -fx-border-width: 2px; -fx-background-radius: 5px;"));
        return button;
    }

    private void openAddMovieScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #141414;");
        layout.setPadding(new Insets(20));

        Label label = new Label("Add Movie");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");

        Button addButton = createButton("Add");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String genre = genreField.getText();
            String description = descriptionField.getText();

            if (!title.isEmpty() && !genre.isEmpty() && !description.isEmpty()) {
                movieList.add(new Movie(title, genre, description));
                showAlert("Success", "Movie added successfully!");
                transitionToScene(() -> stage.setScene(getAdminDashboardScene()));
            } else {
                showAlert("Error", "All fields must be filled!");
            }
        });

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(getAdminDashboardScene())));

        layout.getChildren().addAll(label, titleField, genreField, descriptionField, addButton, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void openDeleteMovieScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #141414;");
        layout.setPadding(new Insets(20));

        Label label = new Label("Delete Movie");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        ListView<Movie> movieListView = new ListView<>();
        movieListView.getItems().addAll(movieList);

        Button deleteButton = createButton("Delete");
        deleteButton.setOnAction(e -> {
            Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                movieList.remove(selectedMovie);
                showAlert("Success", "Movie deleted successfully!");
                transitionToScene(() -> stage.setScene(getAdminDashboardScene()));
            } else {
                showAlert("Error", "No movie selected!");
            }
        });

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(getAdminDashboardScene())));

        layout.getChildren().addAll(label, movieListView, deleteButton, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void openViewMoviesScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #141414;");
        layout.setPadding(new Insets(20));

        Label label = new Label("View Movies");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        ListView<Movie> movieListView = new ListView<>();
        movieListView.getItems().addAll(movieList);

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(getAdminDashboardScene())));

        layout.getChildren().addAll(label, movieListView, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void openManageTicketPricingScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #141414;");
        layout.setPadding(new Insets(20));

        Label label = new Label("Manage Ticket Pricing");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        TextField priceField = new TextField();
        priceField.setPromptText("Enter new ticket price");

        Button updateButton = createButton("Update");
        updateButton.setOnAction(e -> {
            try {
                double newPrice = Double.parseDouble(priceField.getText());
                showAlert("Success", "Ticket price updated to: $" + newPrice);
                transitionToScene(() -> stage.setScene(getAdminDashboardScene()));
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid price entered!");
            }
        });

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(getAdminDashboardScene())));

        layout.getChildren().addAll(label, priceField, updateButton, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void openMovieCategoriesScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #141414;");
 layout.setPadding(new Insets(20));

        Label label = new Label("Manage Movie Categories");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        ComboBox<String> categoryDropdown = new ComboBox<>();
        categoryDropdown.getItems().addAll("Horror", "Sci-Fi", "Action", "Drama");
        categoryDropdown.setPromptText("Select Category");

        ListView<Movie> movieListView = new ListView<>();
        categoryDropdown.setOnAction(e -> {
            String selectedCategory = categoryDropdown.getValue();
            movieListView.getItems().clear();
            for (Movie movie : movieList) {
                if (movie.getGenre().equalsIgnoreCase(selectedCategory)) {
                    movieListView.getItems().add(movie);
                }
            }
        });

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> transitionToScene(() -> stage.setScene(getAdminDashboardScene())));

        layout.getChildren().addAll(label, categoryDropdown, movieListView, backButton);
        stage.setScene(new Scene(layout, 800, 600));
    }

    private void logout() {
        transitionToScene(() -> stage.setScene(loginScene));
        showAlert("Logout", "You have been logged out successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void transitionToScene(Runnable sceneChangeAction) {
        VBox root = (VBox) stage.getScene().getRoot(); // Get the current root layout
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            sceneChangeAction.run();
            VBox newRoot = (VBox) stage.getScene().getRoot(); // Get the new root layout
            newRoot.setStyle("-fx-background-color: #141414;"); // Ensure background color is set
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }
}
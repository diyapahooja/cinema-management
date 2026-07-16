package cinema;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import java.util.function.Consumer;
public class LoginPanel {
    private Stage stage;
    private List<Movie> movieList;
    private List<User> users;
    private Consumer<User> loginSuccessHandler;

    public LoginPanel(Stage stage, List<Movie> movieList, List<User> users) {
        this.stage = stage;
        this.movieList = movieList;
        this.users = users;

        // Set the application icon
        Image appIcon = new Image(getClass().getResourceAsStream("netflix.png")); // Use the image name directly
        stage.getIcons().add(appIcon);
    }

    public Scene getLoginScene() {
        GridPane loginLayout = new GridPane();
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setHgap(10);
        loginLayout.setVgap(10);
        loginLayout.setStyle("-fx-background-color: #141414;"); // Dark background
        loginLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Login to Cinema Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-prompt-text-fill: #888; -fx-border-color: #e50914; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-prompt-text-fill: #888; -fx-border-color: #e50914; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 5px;");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Add elements to layout
        loginLayout.add(titleLabel, 0, 0, 2, 1);
        loginLayout.add(usernameField, 0, 1, 2, 1);
        loginLayout.add(passwordField, 0, 2, 2, 1);
        loginLayout.add(loginButton, 0, 3);

        return new Scene(loginLayout, 400, 300); // Adjusted height to accommodate the layout
    }

    private void handleLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (loginSuccessHandler != null) {
                    loginSuccessHandler.accept(user);
                }
                return;
            }
        }
        showAlert("Login Failed", "Invalid username or password.");
    }

    public void setOnLoginSuccess(Consumer<User> handler) {
        this.loginSuccessHandler = handler;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

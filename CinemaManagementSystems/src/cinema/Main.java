package cinema;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create list of users
        List<User> users = new ArrayList<>();
        users.add(new Admin("admin", "admin123", "Admin User"));
        users.add(new Customer("diya", "diya", "Customer One"));

        // Create movie list
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Inception", "Sci-Fi", "A mind-bending thriller."));
        movieList.add(new Movie("Avatar", "Action", "A visually stunning alien conflict."));
        movieList.add(new Movie("Interstellar", "Sci-Fi", "A space exploration story."));
        movieList.add(new Movie("The Dark Knight", "Action", "Batman faces the Joker in Gotham City."));
        movieList.add(new Movie("Forrest Gump", "Drama", "The life story of a slow-witted but kind-hearted man."));
        movieList.add(new Movie("The Shawshank Redemption", "Drama", "Two imprisoned men bond over a number of years."));
        movieList.add(new Movie("Pulp Fiction", "Crime", "The lives of two mob hitmen, a boxer, and a gangster's wife."));
        movieList.add(new Movie("The Matrix", "Sci-Fi", "A computer hacker learns about the true nature of reality."));
        movieList.add(new Movie("The Godfather", "Crime", "An organized crime dynasty's aging patriarch transfers control to his reluctant son."));
        movieList.add(new Movie("Fight Club", "Drama", "An insomniac office worker forms an underground fight club."));
        movieList.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Fantasy", "A hobbit embarks on a quest to destroy a powerful ring."));
        movieList.add(new Movie("Gladiator", "Action", "A former Roman General sets out to exact vengeance against the corrupt emperor."));
        movieList.add(new Movie("Titanic", "Romance", "A seventeen-year-old aristocrat falls in love with a kind but poor artist."));
        movieList.add(new Movie("Jurassic Park", "Adventure", "A theme park suffers a major power breakdown that allows its cloned dinosaur exhibits to run amok."));
        movieList.add(new Movie("The Silence of the Lambs", "Thriller", "A young FBI cadet must confide in an incarcerated and manipulative killer to catch another serial killer."));

        // Initialize login panel
        LoginPanel loginPanel = new LoginPanel(primaryStage, movieList, users);
        Scene loginScene = loginPanel.getLoginScene(); // Save the login scene reference
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Cinema Management - Login");
        primaryStage.show();

        // Handle login success
        loginPanel.setOnLoginSuccess(user -> {
            if (user instanceof Admin) {
                AdminMovieManagementPanel panel = new AdminMovieManagementPanel(primaryStage, (Admin) user, movieList, loginScene);
                primaryStage.setScene(panel.getAdminDashboardScene());
            } else if (user instanceof Customer) {
                CustomerDashboardPanel customerPanel = new CustomerDashboardPanel(primaryStage, (Customer) user, movieList);
                primaryStage.setScene(customerPanel.getCustomerDashboardScene());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}



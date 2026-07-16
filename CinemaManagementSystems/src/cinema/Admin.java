package cinema;

import java.util.List;

public class Admin extends User {

    public Admin(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public void performAction() {
        System.out.println("Admin is performing an action.");
    }
}

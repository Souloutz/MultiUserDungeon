package multiuserdungeon.authentication;

public class Authenticator {

    private User currentUser;
    private static Authenticator INSTANCE;

    private Authenticator() {
        this.currentUser = new User();
    }

    public static Authenticator instance() {
        if (INSTANCE == null) {
            INSTANCE = new Authenticator();
        }
        return INSTANCE;
    }

    public User getUser() {
        return this.currentUser;
    }

    private void setUser(User u) {
        if (u != null) {
            this.currentUser = u;
        } else {
            this.currentUser = new User();
        }
    }

    public boolean login(String username, String password) {
        //check if user is not logged in
        if (this.currentUser instanceof User) {
            //if so, check username and password
            //TODO: check if user exists
            if (username != null && password != null) {
                //TODO: get existing user from persistence
                this.setUser(new Profile(username, password, "placeholder desc"));
                return true;
            }
        }
        return false;
    }

    public boolean logout() {
        //check if user is logged in
        if (this.currentUser instanceof Profile) {
            //if so replace with new User (represents logged-out user) and return true
            this.setUser(null); //logged out user
            return true;
        }
        //do nothing and return false
        return false;
    }
}

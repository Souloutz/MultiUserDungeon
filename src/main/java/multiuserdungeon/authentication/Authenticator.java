package multiuserdungeon.authentication;

import multiuserdungeon.persistence.PersistenceManager;

public class Authenticator {

    private User currentUser;
    private static Authenticator INSTANCE;

    private Authenticator() {
        this.currentUser = null;
    }

    public static Authenticator instance() {
        if(INSTANCE == null) INSTANCE = new Authenticator();
        return INSTANCE;
    }

    public User getUser() {
        return this.currentUser;
    }

    private void setUser(User u) {
        this.currentUser = u;
    }

    public boolean loggedIn() {
        return this.currentUser != null && this.currentUser instanceof Profile;
    }

    public boolean login(String username, String password) {
        if(loggedIn()) return false;
        if(username == null || password == null) return false;

        Profile profile = PersistenceManager.getInstance().loadProfile(username);
        if(profile == null) return false;
        if(!profile.getPassword().equals(password)) return false;

        setUser(profile);
        return true;
    }

    public boolean register(String username, String password, String description) {
        if(loggedIn()) return false;

        this.setUser(new Profile(username, password, description));
        return true;
    }

    public boolean handleChangePassword(String curPassword, String newPassword) {
        // check if user is logged in
        if (this.currentUser instanceof Profile) {
            return ((Profile) this.currentUser).changePassword(curPassword, newPassword);
            // handle changing of DB
        }

        return false;
    }

    public boolean logout() {
        if(!loggedIn()) return false;
        setUser(null);
        return true;
    }

}

package multiuserdungeon.authentication;

import multiuserdungeon.persistence.PersistenceManager;

public class Authenticator {

    private User currentUser;
    private static Authenticator INSTANCE;

    private Authenticator() {
        this.currentUser = null;
    }

    public static Authenticator getInstance() {
        if(INSTANCE == null) INSTANCE = new Authenticator();
        return INSTANCE;
    }

    public User getUser() {
        return this.currentUser;
    }

    private void setUser(User user) {
        this.currentUser = user;
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

        Profile newProfile = new Profile(username, password, description);
        PersistenceManager.getInstance().saveProfile(newProfile);
        setUser(newProfile);
        return true;
    }

    public boolean handleChangePassword(String curPassword, String newPassword, String confirmPassword) {
        if (!loggedIn()) return false;
        if (curPassword.equals(newPassword)) return false;
        if (!newPassword.equals(confirmPassword)) return false;
        
        ((Profile) this.currentUser).changePassword(curPassword, newPassword);
        PersistenceManager.getInstance().saveProfile((Profile) this.currentUser);
        return true;
    }

    public boolean logout() {
        if(!loggedIn()) return false;
        setUser(null);
        return true;
    }

}

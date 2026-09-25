package uk.ac.shef.com3529.forum;

import java.time.Instant;
import java.util.Objects;

class User {
    private String name;
    private String username;
    private String emailAddress;
    private int numPostsMade;
    private Instant lastOnline;
    private boolean banned;

    User(String name, String username, String emailAddress) {
        this.name = name;
        this.username = username;
        this.emailAddress = emailAddress;
        numPostsMade = 0;
        lastOnline = null;
        banned = false;
    }

    String getName() {
        return name;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getEmailAddress() {
        return emailAddress;
    }
    
    int numPostsMade() {
        return numPostsMade;
    }

    Instant lastOnline() {
        return lastOnline;
    }

    boolean isBanned() {
        return banned;
    }

    void setBanned(boolean banned) {
        this.banned = banned;
    }

    void setLastOnline(Instant instant) {
        this.lastOnline = instant;
    }

    void incrementPostsMade() {
        numPostsMade ++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.username, that.username) &&
                this.numPostsMade == that.numPostsMade &&
                Objects.equals(this.lastOnline, that.lastOnline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, numPostsMade, lastOnline);
    }

    @Override
    public String toString() {
        return "User[" +
                "name=" + name + ", " +
                "username=" + username + ", " +
                "numPosts=" + numPostsMade + ", " +
                "lastOnline=" + lastOnline + ']';
    }

}

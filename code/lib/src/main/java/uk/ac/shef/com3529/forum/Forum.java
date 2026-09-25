package uk.ac.shef.com3529.forum;

import java.time.Instant;
import java.util.*;

public class Forum {

    Set<User> users;
    Set<User> online;
    List<Post> posts;

    public Forum() {
        users = new HashSet<>();
        online = new HashSet<>();
        posts = new LinkedList<>();
    }

    User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    User getUserThrowExceptionIfDoesNotExist(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new UnknownUserException("Unknown user " + user);
        }
        return user;
    }

    public Set<String> getUsernames() {
        Set<String> usernames = new HashSet<>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }
    
    public void registerUser(String name, String username, String emailAddress) {
        if (getUser(username) != null) {
            throw new UserNameAlreadyExistsException("User already exists with username " + username);
        }
        for (User user : users) {
            if (user.getEmailAddress().equals(emailAddress)) {
                throw new EmailAddressAlreadyExistsException("User already registered with email address " + emailAddress);
            }
        }
        User user = new User(name, username, emailAddress);
        users.add(user);
    }

    public boolean changeUsername(String oldUsername, String newUsername) {
        User user = getUserThrowExceptionIfDoesNotExist(oldUsername);
        if (getUser(newUsername) != null) {
            return false;
        } else {
            user.setUsername(newUsername);
            return true;
        }
    }

    public void ban(String username) {
        User user = getUserThrowExceptionIfDoesNotExist(username);
        user.setBanned(true);
    }

    public boolean login(String username) {
        User user = getUserThrowExceptionIfDoesNotExist(username);
        if (user.isBanned()) {
            return false;
        }
        online.add(user);
        user.setLastOnline(Instant.now());
        return true;
    }

    public void post(String username, String subject, String content) {
        User user = getUserThrowExceptionIfDoesNotExist(username);
        if (!user.isBanned()) {
            posts.add(new Post(username, subject, content, Instant.now()));
            user.incrementPostsMade();
        }
    }

    public boolean logout(String username) {
        User user = getUserThrowExceptionIfDoesNotExist(username);
        online.remove(user);
        return true;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Post> getPostsByUser(String username) {
        List<Post> postsByUser = new ArrayList<Post>();
        for (Post post : posts) {
            if (post.getUsername().equals(username)) {
                postsByUser.add(post);
            }
        }
        return postsByUser;
    }

    public String getMostProlificUser() {
        String mostProlific = null;
        int max = 0;
        for (User user: users) {
            if (user.numPostsMade() > max) {
                mostProlific = user.getUsername();
                max = user.numPostsMade();
            }
        }
        return mostProlific;
    }
}

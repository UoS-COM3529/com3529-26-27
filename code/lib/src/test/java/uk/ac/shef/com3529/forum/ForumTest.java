package uk.ac.shef.com3529.forum;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class ForumTest {

    @Test
    public void shouldNotLoginBannedUser() {
        // Given a forum with registered users
        Forum forum = makeForum();

        // When the user is banned
        forum.ban("billy");

        // Then the user cannot login
        boolean loggedIn = forum.login("billy");
        assertFalse(loggedIn);
    }

    @Test
    public void shouldGetRegisteredUser() {
        // Given a forum
        Forum forum = new Forum();

        // When a user is registered
        forum.registerUser("John Egan", "captain", "j.egan@sufc.co.uk");

        // Then can get the user
        assertNotNull(forum.getUser("captain"));
    }

    @Test
    public void testBan() {
        Forum forum = makeForum();
        for (String username : forum.getUsernames()) {
            forum.ban(username);
        }
        for (String username : forum.getUsernames()) {
            User user = forum.getUser(username);
            assertTrue(user.isBanned());
        }
    }

    @Test
    public void testGetMostProfilicPoster() {
        Forum forum = makeForum();
        makePosts(forum);
        assertThat(forum.getUser(forum.getMostProlificUser()).numPostsMade(), equalTo(2));
    }

    Forum makeForum() {
        Forum forum = new Forum();
        forum.registerUser("Billy Sharp", "billy", "b.sharp@sufc.co.uk");
        forum.registerUser("Iliman NDiaye", "ili", "i.ndiaye@sufc.co.uk");
        forum.registerUser("Chris Basham", "bash", "c.basham@sufc.co.uk");
        return forum;
    }

    void makePosts(Forum forum) {
        forum.post("bash", "Billy Sharp scores goals", "We got Billy Sharp, Billy Sharp. We got Billy Sharp");
        forum.post("bash", "Boxing Day", "Hark now hear, United sing, the Wednesday Ran Away");
        forum.post("billy", "Bladesmen", "We are Bladesmen, super Bladesmen, we are Blademen, from the Lane");
    }
}

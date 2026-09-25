package uk.ac.shef.com3529.forum;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ForumTest_Better {

    @Test
    public void shouldNotLoginBannedUser() {
        // Given a forum with a registered user
        Forum forum = new Forum();
        registerUserWithUsername(forum, "billy");

        // When the user is banned
        forum.ban("billy");

        // Then the user cannot login
        boolean loggedIn = forum.login("billy");
        assertThat(loggedIn, equalTo(false));
    }

    @Test
    public void shouldGetRegisteredUser() {
        // Given a forum
        Forum forum = new Forum();

        // When a user is registered
        registerUserWithUsername(forum, "captain");

        // Then can get the username
        assertThat(forum.getUsernames(), contains("captain"));
    }

    @Test
    public void shouldNotLetBannedUsersPost() {
        // Given a forum with a registered user and no existing posts
        Forum forum = new Forum();
        registerUserWithUsername(forum, "billy");

        // When the user is banned, and then posts
        forum.ban("billy");
        makePostByUsername(forum, "billy");

        // Then the number of posts made by the user is zero (i.e., the post was not made)
        int numPosts = forum.getPostsByUser("billy").size();
        assertThat(numPosts, equalTo(0));
    }

    @Test
    public void shouldComputeMostProlificPoster() {
        // Given a forum with two registered users and no existing posts
        Forum forum = new Forum();
        registerUserWithUsernameAndUniqueEmailAddress(forum, "billy", "b.sharp@sufc.co.uk");
        registerUserWithUsernameAndUniqueEmailAddress(forum, "bash", "c.basham@sufc.co.uk");

        // When one post is made by billy
        makePostByUsername(forum, "billy");

        // Then billy is the most prolific poster
        assertThat(forum.getMostProlificUser(), equalTo("billy"));

        // But when more (two) posts are made by another user – bash
        makePostByUsername(forum, "bash");
        makePostByUsername(forum, "bash");

        // Then bash is the most prolific poster
        assertThat(forum.getMostProlificUser(), equalTo("bash"));
    }

    @Test
    public void shouldHaveUserNameFollowingRegistering() {
        // Given a forum with one registered user
        Forum forum = new Forum();
        registerUserWithUsernameAndUniqueEmailAddress(forum, "billy", "b.sharp@sufc.co.uk");
        registerUserWithUsernameAndUniqueEmailAddress(forum, "bash", "c.basham@sufc.co.uk");

        // Then that username should be in the list of registered users
        Set<String> usernames = forum.getUsernames();
        assertThat(usernames.size(), equalTo(2));
        assertThat(usernames, containsInAnyOrder("bash", "billy"));
    }

    @Test
    public void shouldNotRegisterUsernameThatAlreadyExists() {
        // Given a forum with one registered user
        Forum forum = new Forum();
        registerUserWithUsernameAndUniqueEmailAddress(forum, "billy", "b.sharp@sufc.co.uk");

        // Then that username cannot be registered again
        assertThrows(UserNameAlreadyExistsException.class, () -> {
            forum.registerUser("doesn't matter", "billy", "doesn't matter");
        });
    }

    @Test
    public void shouldNotRegisterEmailAddressThatAlreadyExists() {
        // Given a forum with one registered user
        Forum forum = new Forum();
        registerUserWithUsernameAndUniqueEmailAddress(forum, "billy", "b.sharp@sufc.co.uk");

        // Then that email address cannot be registered again
        assertThrows(EmailAddressAlreadyExistsException.class, () -> {
            forum.registerUser("doesn't matter", "doesn't matter", "b.sharp@sufc.co.uk");
        });
    }

    @Test
    public void shouldSuccessfullyChangeUsername() {
        // Given a forum with one registered user
        Forum forum = new Forum();
        registerUserWithUsername(forum, "billy");

        // Then changing their username to one not already used is successful
        assertThat(forum.changeUsername("billy", "William"), equalTo(true));
    }

    @Test
    public void shouldNotSuccessfullyChangeUsernameIfAlreadyInUse() {
        // Given a forum with two registered users
        Forum forum = new Forum();
        registerUserWithUsernameAndUniqueEmailAddress(forum, "billy", "b.sharp@sufc.co.uk");
        registerUserWithUsernameAndUniqueEmailAddress(forum, "bash", "c.basham@sufc.co.uk");

        // Then one user changing their user name to that of an existing user fails
        assertThat(forum.changeUsername("billy", "bash"), equalTo(false));
    }

    @Test
    public void shouldNotSuccessfullyChangeUsernameIfUserDoesNotExist() {
        // Given a forum with no users
        Forum forum = new Forum();

        // Then changing the name of an (unknown) user fails
        assertThrows(UnknownUserException.class, () -> {
            forum.changeUsername("billy", "bash");
        });
    }

    @Test
    public void shouldLoginUser() {
        // Given a forum with one registered user
        Forum forum = new Forum();
        registerUserWithUsername(forum, "billy");

        // Then the user can successfully login
        assertThat(forum.login("billy"), equalTo(true));
    }

    @Test
    public void shouldLogoutUser() {
        // Given a forum with one registered user
        Forum forum = new Forum();
        registerUserWithUsername(forum, "billy");

        // When the user logins
        forum.login("billy");

        // Then the user can successfully logout
        assertThat(forum.logout("billy"), equalTo(true));
    }

    @Test
    public void shouldGetPosts() {
        // Given a forum with two registered users and no existing posts
        Forum forum = new Forum();
        registerUserWithUsernameAndUniqueEmailAddress(forum, "billy", "b.sharp@sufc.co.uk");
        registerUserWithUsernameAndUniqueEmailAddress(forum, "bash", "c.basham@sufc.co.uk");

        // When three posts are made, 1 one by billy, 2 by bash
        makePostByUsername(forum, "billy");
        makePostByUsername(forum, "bash");
        makePostByUsername(forum, "bash");

        // Then the total number of posts is three
        assertThat(forum.getPosts().size(), equalTo(3));

        // Then the number of posts by billy is 1
        assertThat(forum.getPostsByUser("billy").size(), equalTo(1));

        // Then the number of posts by bash is 2
        assertThat(forum.getPostsByUser("bash").size(), equalTo(2));
    }

    void registerUserWithUsername(Forum forum, String username) {
        forum.registerUser("doesn't matter", username, "doesn't matter");
    }

    void registerUserWithUsernameAndUniqueEmailAddress(Forum forum, String username, String emailAddress) {
        forum.registerUser("doesn't matter", username, emailAddress);
    }

    void makePostByUsername(Forum forum, String username) {
        forum.post(username, "doesn't matter", "doesn't matter");
    }
}

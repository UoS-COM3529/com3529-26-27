package uk.ac.shef.com3529.forum;

import java.time.Instant;

class Post {

    String username;
    String subject;
    String content;
    Instant instant;

    Post(String username, String subject, String content, Instant instant) {
        this.username = username;
        this.subject = subject;
        this.content = content;
        this.instant = instant;
    }

    public String getUsername() {
        return username;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Instant getInstant() {
        return instant;
    }
}

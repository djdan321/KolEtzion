package edu.etzion.koletzion.models;

public class Comment {
    private Profile profile;
    private String content;

    //constructor

    public Comment(Profile profile, String content) {
        this.profile = profile;
        this.content = content;
    }

    // all the getter methods

    public Profile getProfile() {
        return profile;
    }
    public String getContent() {
        return content;
    }


    // toString
    @Override
    public String toString() {
        return "Comment{" +
                "profile=" + profile +
                ", content='" + content + '\'' +
                '}';
    }
}

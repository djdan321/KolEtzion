package edu.etzion.koletzion.models;

public class Comment {
    private MyProfile myProfile;
    private String content;

    //constructor

    public Comment(MyProfile myProfile, String content) {
        this.myProfile = myProfile;
        this.content = content;
    }

    // all the getter methods

    public MyProfile getMyProfile() {
        return myProfile;
    }
    public String getContent() {
        return content;
    }


    // toString
    @Override
    public String toString() {
        return "Comment{" +
                "myProfile=" + myProfile +
                ", content='" + content + '\'' +
                '}';
    }
}

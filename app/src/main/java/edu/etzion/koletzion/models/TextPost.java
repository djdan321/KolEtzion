package edu.etzion.koletzion.models;

import java.util.List;

public class TextPost extends Post{
    private String text;
    private String writerName;

    // Constructors


    public TextPost(int count, List<Comment> comments, List<MyProfile> likes, String text) {
        super(count, comments, likes);
        this.text = text;

        writerName = "רדיו קול עציון";
    }

    public TextPost(String _id, String _rev, int count, List<Comment> comments, List<MyProfile> likes, String text) {
        super(_id, _rev, count, comments, likes);
        this.text = text;
        writerName = "רדיו קול עציון";
    }

    // Getters

    public String getText() {
        return text;
    }
    public String getWriterName() {
        return writerName;
    }

    //toString
    @Override
    public String toString() {
        return "TextPost{" +
                "text='" + text + '\'' +
                ", writerName='" + writerName + '\'' +
                '}';
    }
}

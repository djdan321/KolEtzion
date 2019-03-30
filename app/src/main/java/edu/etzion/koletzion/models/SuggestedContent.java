package edu.etzion.koletzion.models;

import java.util.Date;

public class SuggestedContent {
    private String _id;
    private String _rev;
    private MyProfile profile;
    private String content;
    private int count;
    private long timeStamp;
    //todo add image (learn how to add to server)

    //Constructors


    public SuggestedContent(MyProfile profile, String content, int count) {
        this.profile = profile;
        this.content = content;
        this.count = count;
    }

    public SuggestedContent(String _id, String _rev, MyProfile profile, String content, int count) {
        this._id = _id;
        this._rev = _rev;
        this.profile = profile;
        this.content = content;
        this.count=count;
    }

    // Getters

    public String get_id() {
        return _id;
    }
    public String get_rev() {
        return _rev;
    }
    public MyProfile getProfile() {
        return profile;
    }
    public String getContent() {
        return content;
    }
    public int getCount() {
        return count;
    }
    public long getTimeStamp() {
        return timeStamp;
    }

    //setter
    public void setTimestamp(long timestamp) {
        this.timeStamp = timestamp;
    }

    // toString
    @Override
    public String toString() {
        return "SuggestedContent{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", profile=" + profile +
                ", content='" + content + '\'' +
                ", count=" + count +
                '}';
    }
}

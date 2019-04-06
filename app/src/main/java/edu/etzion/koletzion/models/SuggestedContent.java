package edu.etzion.koletzion.models;

public class SuggestedContent {
    private String _id;
    private String _rev;
    private Profile profile;
    private String content;
    private long timeStamp;

    //Constructors


    public SuggestedContent(Profile profile, String content) {
        this.profile = profile;
        this.content = content;

    }

    public SuggestedContent(String _id, String _rev, Profile profile, String content) {
        this._id = _id;
        this._rev = _rev;
        this.profile = profile;
        this.content = content;

    }

    // Getters

    public String get_id() {
        return _id;
    }
    public String get_rev() {
        return _rev;
    }
    public Profile getProfile() {
        return profile;
    }
    public String getContent() {
        return content;
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
                ", timeStamp=" + timeStamp +
                '}';
    }
}

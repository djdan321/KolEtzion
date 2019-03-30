package edu.etzion.koletzion.models;

import java.util.List;

public class BroadcastPost extends Post{
    private String title;
    private BroadcastCategory type;
    private String description;
    private String streamURL;
    private List<MyProfile> broadcasters;
    private List<MyProfile> listeners;
    private long duration;

    // Constructors


    public BroadcastPost(int count, List<Comment> comments, List<MyProfile> likes, String title, BroadcastCategory type, String description, String streamURL, List<MyProfile> broadcasters, List<MyProfile> listeners, long duration) {
        super(count, comments, likes);
        this.title = title;
        this.type = type;
        this.description = description;
        this.streamURL = streamURL;
        this.broadcasters = broadcasters;
        this.listeners = listeners;
        this.duration = duration;
    }

    public BroadcastPost(String _id, String _rev, int count, List<Comment> comments, List<MyProfile> likes, String title, BroadcastCategory type, String description, String streamURL, List<MyProfile> broadcasters, List<MyProfile> listeners, long duration) {
        super(_id, _rev, count, comments, likes);
        this.title = title;
        this.type = type;
        this.description = description;
        this.streamURL = streamURL;
        this.broadcasters = broadcasters;
        this.listeners = listeners;
        this.duration = duration;
    }

    // Getters

    public String getTitle() {
        return title;
    }
    public BroadcastCategory getType() {
        return type;
    }
    public String getDescription() {
        return description;
    }
    public String getStreamURL() {
        return streamURL;
    }
    public List<MyProfile> getBroadcasters() {
        return broadcasters;
    }
    public List<MyProfile> getListeners() {
        return listeners;
    }
    public long getDuration() {
        return duration;
    }

    // toString
    @Override
    public String toString() {
        return "BroadcastPost{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", streamURL='" + streamURL + '\'' +
                ", broadcasters=" + broadcasters +
                ", listeners=" + listeners +
                ", duration=" + duration +
                '}';
    }

    // Methods

    // this method add a listener to the listeners's list
    public void addListener(MyProfile profile){
        listeners.add(profile);
    }
}

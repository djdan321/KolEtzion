package edu.etzion.koletzion.models;

import java.util.List;

public class BroadcastPost{
    private BroadcastCategory type;
    private String description;
    private String streamURL;
    private List<MyProfile> broadcasters;
    private List<MyProfile> listeners;
    private long duration;
    private String _id;
    private String _rev;
    private int count;
    private String title;
    private List<Comment> comments;
    private List<MyProfile> likes;
    private long timeStamp;

    // Constructors

    public BroadcastPost(BroadcastCategory type, String description, String streamURL, List<MyProfile> broadcasters, List<MyProfile> listeners, long duration, String _id, String _rev, int count, String title, List<Comment> comments, List<MyProfile> likes) {
        this.type = type;
        this.description = description;
        this.streamURL = streamURL;
        this.broadcasters = broadcasters;
        this.listeners = listeners;
        this.duration = duration;
        this._id = _id;
        this._rev = _rev;
        this.count = count;
        this.title = title;
        this.comments = comments;
        this.likes = likes;
    }

    public BroadcastPost(BroadcastCategory type, String description, String streamURL, List<MyProfile> broadcasters, List<MyProfile> listeners, long duration, int count, String title, List<Comment> comments, List<MyProfile> likes) {
        this.type = type;
        this.description = description;
        this.streamURL = streamURL;
        this.broadcasters = broadcasters;
        this.listeners = listeners;
        this.duration = duration;
        this.count = count;
        this.title = title;
        this.comments = comments;
        this.likes = likes;
    }

    // Getters

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
    public String get_id() {
        return _id;
    }
    public String get_rev() {
        return _rev;
    }
    public int getCount() {
        return count;
    }
    public String getTitle() {
        return title;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public List<MyProfile> getLikes() {
        return likes;
    }
    public long getTimeStamp() {
        return timeStamp;
    }

    //setter

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


    // toString

    @Override
    public String toString() {
        return "BroadcastPost{" +
                "type=" + type +
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

    //this method add a comment to the comment's list
    public void addComment(Comment comment){
        comments.add(comment);
    }

    //this method add a like to the likes's list
    public void addLike(MyProfile myProfile){
        likes.add(myProfile);
    }
}

package edu.etzion.koletzion.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BroadcastPost implements Comparable<BroadcastPost>, Parcelable {
    private BroadcastCategory type;
    private String description;
    private String streamURL;
    private List<Profile> broadcasters;
    private List<Profile> listeners;
    private long duration;
    private String _id;
    private String _rev;
    private String title;
    private List<Comment> comments;
    private List<Profile> likes;
    private long timeStamp;

    // Constructors

    public BroadcastPost(BroadcastCategory type, String description, String streamURL, List<Profile> broadcasters, List<Profile> listeners, long duration, String _id, String _rev, String title, List<Comment> comments, List<Profile> likes, long timestamp) {
        this.type = type;
        this.description = description;
        this.streamURL = streamURL;
        this.broadcasters = broadcasters;
        this.listeners = listeners;
        this.duration = duration;
        this._id = _id;
        this._rev = _rev;
        this.title = title;
        this.comments = comments;
        this.likes = likes;
        this.timeStamp=timestamp;
    }

    public BroadcastPost(BroadcastCategory type, String description, String streamURL, List<Profile> broadcasters, List<Profile> listeners, long duration, String title, List<Comment> comments, List<Profile> likes) {
        this.type = type;
        this.description = description;
        this.streamURL = streamURL;
        this.broadcasters = broadcasters;
        this.listeners = listeners;
        this.duration = duration;
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
    public List<Profile> getBroadcasters() {
        return broadcasters;
    }
    public List<Profile> getListeners() {
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
    public String getTitle() {
        return title;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public List<Profile> getLikes() {
        return likes;
    }
    public long getTimeStamp() {
        return timeStamp;
    }
    public int getLikesCount(){return likes.size();}
    public int getCommentsCount(){return comments.size();}
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
                ", _id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", title='" + title + '\'' +
                ", comments=" + comments +
                ", likes=" + likes +
                ", timeStamp=" + timeStamp +
                '}';
    }


    // Methods

    // this method add a listener to the listeners's list
    public void addListener(Profile profile){
        listeners.add(profile);
    }

    //this method add a comment to the comment's list
    public void addComment(Comment comment){
        comments.add(comment);
    }

    //this method add a like to the likes's list
    public void addLike(Profile profile){
        likes.add(profile);
    }


    @Override
    public int compareTo(BroadcastPost o) {
        return (int) (this.timeStamp-o.getTimeStamp());
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.description);
        dest.writeString(this.streamURL);
        dest.writeTypedList(this.broadcasters);
        dest.writeTypedList(this.listeners);
        dest.writeLong(this.duration);
        dest.writeString(this._id);
        dest.writeString(this._rev);
        dest.writeString(this.title);
        dest.writeList(this.comments);
        dest.writeTypedList(this.likes);
        dest.writeLong(this.timeStamp);
    }
    
    protected BroadcastPost(Parcel in) {
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : BroadcastCategory.values()[tmpType];
        this.description = in.readString();
        this.streamURL = in.readString();
        this.broadcasters = in.createTypedArrayList(Profile.CREATOR);
        this.listeners = in.createTypedArrayList(Profile.CREATOR);
        this.duration = in.readLong();
        this._id = in.readString();
        this._rev = in.readString();
        this.title = in.readString();
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
        this.likes = in.createTypedArrayList(Profile.CREATOR);
        this.timeStamp = in.readLong();
    }
    
    public static final Parcelable.Creator<BroadcastPost> CREATOR = new Parcelable.Creator<BroadcastPost>() {
        @Override
        public BroadcastPost createFromParcel(Parcel source) {
            return new BroadcastPost(source);
        }
        
        @Override
        public BroadcastPost[] newArray(int size) {
            return new BroadcastPost[size];
        }
    };
}

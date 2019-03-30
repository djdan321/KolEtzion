package edu.etzion.koletzion.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Profile implements Parcelable , Comparable<Profile> {
    private String _id;
    private String _rev;
    private String username; // from current user.
    private String firstName;
    private String lastName;
    private boolean isBroadcaster;
    private List<BroadcastPost> relatedPosts;
    private long timeStamp;
    private boolean isStudent;
    private int mood;
    // constant mood's options
    public static final int MOOD_HAPPY=1;
    public static final int MOOD_FINE=2;
    public static final int MOOD_SAD=3;
    public static final int MOOD_NONE=-1;
//todo add image(learn how to save the image to the database)


    // Constructors

    public Profile(String _id, String _rev, String username, String firstName, String lastName, boolean isBroadcaster, List<BroadcastPost> relatedPosts, long timeStamp, boolean isStudent, int mood ) {
        this._id = _id;
        this._rev = _rev;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBroadcaster = isBroadcaster;
        this.relatedPosts = relatedPosts;
        this.timeStamp = timeStamp;
        this.isStudent = isStudent;
        this.mood=mood;
    }

    public Profile(String username, String firstName, String lastName, boolean isBroadcaster, List<BroadcastPost> relatedPosts, boolean isStudent, int mood) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBroadcaster = isBroadcaster;
        this.relatedPosts = relatedPosts;
        this.isStudent = isStudent;
        this.mood=mood;
    }

    // Getters

    public String get_id() {
        return _id;
    }
    public String get_rev() {
        return _rev;
    }
    public String getUsername() {
        return username;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public boolean isBroadcaster() {
        return isBroadcaster;
    }
    public List<BroadcastPost> getRelatedPosts() {
        return relatedPosts;
    }
    public long getTimeStamp() {
        return timeStamp;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBroadcaster(boolean broadcaster) {
        isBroadcaster = broadcaster;
    }
    public void setMood(int mood) {
        this.mood = mood;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    // toString

    @Override
    public String toString() {
        return "Profile{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isBroadcaster=" + isBroadcaster +
                ", relatedPosts=" + relatedPosts +
                ", timeStamp=" + timeStamp +
                ", isStudent=" + isStudent +
                ", mood=" + mood +
                '}';
    }


    //methods

    //this method adds a broadcast post to the list.

    public void addBroadcastPost(BroadcastPost broadcastPost){
        relatedPosts.add(broadcastPost);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this._rev);
        dest.writeString(this.username);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeByte(this.isBroadcaster ? (byte) 1 : (byte) 0);
        dest.writeList(this.relatedPosts);
        dest.writeLong(this.timeStamp);
        dest.writeByte(this.isStudent ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mood);
    }

    protected Profile(Parcel in) {
        this._id = in.readString();
        this._rev = in.readString();
        this.username = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.isBroadcaster = in.readByte() != 0;
        this.relatedPosts = new ArrayList<BroadcastPost>();
        in.readList(this.relatedPosts, BroadcastPost.class.getClassLoader());
        this.timeStamp = in.readLong();
        this.isStudent = in.readByte() != 0;
        this.mood = in.readInt();
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int compareTo(Profile o) {
        return (int) (this.timeStamp-o.getTimeStamp());
    }
}

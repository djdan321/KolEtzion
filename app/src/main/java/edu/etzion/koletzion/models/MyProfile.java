package edu.etzion.koletzion.models;

import android.os.Bundle;

import java.util.Date;
import java.util.List;

public class MyProfile {
    private String _id;
    private String _rev;
    private String username; // from current user.
    private int count;
    private String firstName;
    private String lastName;
    private boolean isBroadcaster;
    private List<BroadcastPost> relatedPosts;
    private long timeStamp;
    private boolean isStudent;
    private int mood;
    // constant mood's options
    public static final int HAPPY=1;
    public static final int FINE=2;
    public static final int SAD=3;
    public static final int NONE=-1;
//todo add image(learn how to save the image to the database)


    // Constructors

    public MyProfile(String _id, String _rev, String username, int count, String firstName, String lastName, boolean isBroadcaster, List<BroadcastPost> relatedPosts, long timeStamp, boolean isStudent,int mood ) {
        this._id = _id;
        this._rev = _rev;
        this.username = username;
        this.count = count;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBroadcaster = isBroadcaster;
        this.relatedPosts = relatedPosts;
        this.timeStamp = timeStamp;
        this.isStudent = isStudent;
        this.mood=mood;
    }

    public MyProfile(String username, int count, String firstName, String lastName, boolean isBroadcaster, List<BroadcastPost> relatedPosts, long timeStamp, boolean isStudent, int mood) {
        this.username = username;
        this.count = count;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBroadcaster = isBroadcaster;
        this.relatedPosts = relatedPosts;
        this.timeStamp = timeStamp;
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
    public int getCount() {
        return count;
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

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    // toString

    @Override
    public String toString() {
        return "MyProfile{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", username='" + username + '\'' +
                ", count=" + count +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isBroadcaster=" + isBroadcaster +
                ", relatedPosts=" + relatedPosts +
                ", timeStamp=" + timeStamp +
                '}';
    }


    //methods

    //this method adds a broadcast post to the list.

    public void addBroadcastPost(BroadcastPost broadcastPost){
        relatedPosts.add(broadcastPost);
    }

}

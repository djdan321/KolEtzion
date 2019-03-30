package edu.etzion.koletzion.models;

import android.os.Bundle;

import java.util.List;

public class MyProfile {
    private String _id;
    private String _rev;
    private String username; // from current user.
    private int count;
    private String fullName;
    private boolean isBroadcaster;
    private List<BroadcastPost> relatedPosts;
//todo add image(learn how to save the image to the database)


    // Constructors


    public MyProfile(String username, int count, String fullName, boolean isBroadcaster, List<BroadcastPost> relatedPosts) {
        this.username = username;
        this.count = count;
        this.fullName = fullName;
        this.isBroadcaster = isBroadcaster;
        this.relatedPosts = relatedPosts;
    }

    public MyProfile(String _id, String _rev, String username, int count, String fullName, boolean isBroadcaster, List<BroadcastPost> relatedPosts) {
        this._id = _id;
        this._rev = _rev;
        this.username = username;
        this.count = count;
        this.fullName = fullName;
        this.isBroadcaster = isBroadcaster;
        this.relatedPosts = relatedPosts;
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
    public String getFullName() {
        return fullName;
    }
    public boolean isBroadcaster() {
        return isBroadcaster;
    }
    public List<BroadcastPost> getRelatedPosts() {
        return relatedPosts;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setBroadcaster(boolean broadcaster) {
        isBroadcaster = broadcaster;
    }

    // toString
    @Override
    public String toString() {
        return "MyProfile{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", username='" + username + '\'' +
                ", count=" + count +
                ", fullName='" + fullName + '\'' +
                ", isBroadcaster=" + isBroadcaster +
                ", RelatedPosts=" + relatedPosts +
                '}';
    }

    //methods

    //this method adds a broadcast post to the list.

    public void addBroadcastPost(BroadcastPost broadcastPost){
        relatedPosts.add(broadcastPost);
    }

}

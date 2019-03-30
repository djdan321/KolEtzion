package edu.etzion.koletzion.models;

import java.util.List;

public class StudentProfile extends MyProfile {
    private Mood mood;

    // Constructors


    public StudentProfile(String username, int count, String fullName, boolean isBroadcaster, List<BroadcastPost> relatedPosts, Mood mood) {
        super(username, count, fullName, isBroadcaster, relatedPosts);
        this.mood = mood;
    }

    public StudentProfile(String _id, String _rev, String username, int count, String fullName, boolean isBroadcaster, List<BroadcastPost> relatedPosts, Mood mood) {
        super(_id, _rev, username, count, fullName, isBroadcaster, relatedPosts);
        this.mood = mood;
    }

    // Getters

    public Mood getMood() {
        return mood;
    }

    // toString
    @Override
    public String toString() {
        return "StudentProfile{" +
                "mood=" + mood +
                '}';
    }
}

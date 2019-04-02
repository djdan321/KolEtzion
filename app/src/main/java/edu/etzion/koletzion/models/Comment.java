package edu.etzion.koletzion.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private Profile profile;
    private String content;

    //constructor

    public Comment(Profile profile, String content) {
        this.profile = profile;
        this.content = content;
    }

    // all the getter methods

    public Profile getProfile() {
        return profile;
    }
    public String getContent() {
        return content;
    }


    // toString
    @Override
    public String toString() {
        return "Comment{" +
                "profile=" + profile +
                ", content='" + content + '\'' +
                '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.profile, flags);
        dest.writeString(this.content);
    }
    
    protected Comment(Parcel in) {
        this.profile = in.readParcelable(Profile.class.getClassLoader());
        this.content = in.readString();
    }
    
    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }
        
        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}

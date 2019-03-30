package edu.etzion.koletzion.models;

import java.util.List;

public class Post {
    private String _id;
    private String _rev;
    private int count;
    private List<Comment> comments;
    private List<MyProfile> likes;
    //todo add creation date;

    // Constructors


    Post(int count, List<Comment> comments, List<MyProfile> likes) {
        this.count = count;
        this.comments = comments;
        this.likes = likes;
    }

    Post(String _id, String _rev, int count, List<Comment> comments, List<MyProfile> likes) {
        this._id = _id;
        this._rev = _rev;
        this.count = count;
        this.comments = comments;
        this.likes = likes;
    }

    // Getters

    public String get_id() {
        return _id;
    }
    public String get_rev() {
        return _rev;
    }
    public int getCount() {
        return count;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public List<MyProfile> getLikes() {
        return likes;
    }

    // toString
    @Override
    public String toString() {
        return "Post{" +
                "_id='" + _id + '\'' +
                ", _rev='" + _rev + '\'' +
                ", count=" + count +
                ", comments=" + comments +
                ", likes=" + likes +
                '}';
    }

    //methods

    // this method adds a comment to the comments's list
    public void addComment (Comment comment){
        comments.add(comment);
    }

    // this method adds profile to the likes's list
    public void addLike (MyProfile profile){
        likes.add(profile);
    }
}

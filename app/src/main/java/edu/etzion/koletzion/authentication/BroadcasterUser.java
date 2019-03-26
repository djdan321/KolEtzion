package edu.etzion.koletzion.authentication;

import java.util.List;

import edu.etzion.koletzion.player.Vod;
// TODO change it to singelton,
// TODO learn how to create myOwn VOD LIST

public class BroadcasterUser {
    private String firstName;
    private String lastName;
    private int imageResourceId;
    private List<Vod> myVodList;

    public BroadcasterUser(String firstName, String lastName, int imageResourceId) {
        // TODO addd VOD LIST
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageResourceId = imageResourceId;
    }
    public void addVod(Vod vod){
        myVodList.add(vod);
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    public void setMyVodList(List<Vod> myVodList) {
        this.myVodList = myVodList;
    }
    public void removeVodById(String id){
        for (Vod vod : myVodList) {
            if(vod.getVodId().equals(id)){
                myVodList.remove(vod);
                return;
            }
        }
    }
    public List<Vod> getMyVodList() {
        return myVodList;
    }

    @Override
    public String toString() {
        return "BroadcasterUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", myVodList=" + myVodList +
                '}';
    }
}

package edu.etzion.koletzion.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {
	private int id;
	private String firstName;
	private String lastName;
	private String imgURL;
	
	public Profile(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		//todo get image url from server
		//todo get ID from server
	}
	
	public int getId() {
		return id;
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
	
	public String getImgURL() {
		return imgURL;
	}
	
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.firstName);
		dest.writeString(this.lastName);
		dest.writeString(this.imgURL);
	}
	
	protected Profile(Parcel in) {
		this.id = in.readInt();
		this.firstName = in.readString();
		this.lastName = in.readString();
		this.imgURL = in.readString();
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
	public static Profile getProfileById(int id){
		//get profile from server
		return null;
	}
}

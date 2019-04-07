package edu.etzion.koletzion.database;

import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.models.SuggestedContent;

public class DataDAO {
	
	private static DataDAO instance;
	
	private DataDAO() {
	}
	
	
	public static DataDAO getInstance() {
		if (instance == null)
			instance = new DataDAO();
		return instance;
	}
	
	
	// this method writes Profile Object to the server
	public void writeMyProfile(Profile profile) {
		new WriteProfileTask(profile).execute();
	}
	
	
	// this method writes BroadcastPost Object to the server
	public void writeBroadcastPost(BroadcastPost broadcastPost) {
		new WritePostTask(broadcastPost).execute();
	}
	
	
	// this method writes SuggestedContent Object to the server
	public void writeSuggestedContent(SuggestedContent suggestedContent) {
		new WriteSuggestedContentTask(suggestedContent).execute();
	}
	
}
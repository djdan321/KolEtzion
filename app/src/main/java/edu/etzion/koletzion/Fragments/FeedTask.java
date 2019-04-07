package edu.etzion.koletzion.Fragments;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.VodDataSource;

public class FeedTask extends AsyncTask<Void, Void, Profile> {
	private boolean isMainFeed;
	WeakReference<RecyclerView> rvFeed;
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	public FeedTask(RecyclerView rvFeed) {
		this.rvFeed = new WeakReference<>(rvFeed);
		this.isMainFeed = true;
	}
	public FeedTask(RecyclerView rvFeed, boolean isMainFeed) {
		this.rvFeed = new WeakReference<>(rvFeed);
		this.isMainFeed = isMainFeed;
	}
	@Override
	protected Profile doInBackground(Void... voids) {
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(PROFILES_API_KEY)
				.password(PROFILES_API_SECRET)
				.build();
		
		Database db = client.database(PROFILES_DB, false);
		
		List<Profile> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"username\": \"" + FirebaseAuth.getInstance().getCurrentUser().getEmail() + "\"\n" +
				"   }\n" +
				"}", Profile.class);
		
		return list.get(list.size() - 1);
	}
	
	@Override
	protected void onPostExecute(Profile profile) {
		new VodDataSource(rvFeed, profile, isMainFeed).execute();
	}
}


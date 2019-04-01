package edu.etzion.koletzion.Fragments;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvFeedAdapter;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.BroadcastersDataSource;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.VodDataSource;

public class FeedFragment extends Fragment {
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	private RecyclerView rvFeed;
	private RecyclerView rvBroadcasters;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_feed, container, false);
		
	}
	
	@SuppressLint("StaticFieldLeak")
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		rvFeed = view.findViewById(R.id.rvPost);
		rvBroadcasters = view.findViewById(R.id.rvBroadcasters);

// getting the current latest update profile object from the server
		new AsyncTask<Void, Void, Profile>() {
			@Override
			protected Profile doInBackground(Void... voids) {
				Profile profile = null;
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
				for (Profile item : list) {
					Log.e("check", "checkResult: " + item.toString());
					profile = item;
				}
				Log.e("check", list.toString());
				return profile;
			}
			
			@Override
			protected void onPostExecute(Profile profile) {
				new VodDataSource(rvFeed, profile, new rvFeedAdapter(getContext(), profile), true).execute();
			}
		}.execute();
		
		
		new BroadcastersDataSource(rvBroadcasters, false).execute();
		
	}
}

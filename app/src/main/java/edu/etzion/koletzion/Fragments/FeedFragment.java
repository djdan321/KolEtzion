package edu.etzion.koletzion.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvBroadcastersAdapter;
import edu.etzion.koletzion.R;

import edu.etzion.koletzion.database.BroadcastersDataSource;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.VodDataSource;

public class FeedFragment extends Fragment {
	
	private RecyclerView rvFeed;
	private RecyclerView rvBroadcasters;
	private List<Profile> broadcasters;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_feed, container, false);
		
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		rvFeed = view.findViewById(R.id.rvPost);
		rvBroadcasters = view.findViewById(R.id.rvBroadcasters);

//todo get the profile from the server.
		List<BroadcastPost> posts = new ArrayList<>();
		Profile profile = new Profile("yossi","yossi","appo",true,posts,true, Profile.MOOD_FINE);

		new BroadcastersDataSource(rvBroadcasters,false).execute();
		new VodDataSource(rvFeed,profile).execute();
	}
}

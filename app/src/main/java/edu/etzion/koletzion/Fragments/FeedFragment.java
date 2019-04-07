package edu.etzion.koletzion.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.BroadcastersDataSource;

public class FeedFragment extends Fragment {
	
	private RecyclerView rvFeed;
	private RecyclerView rvBroadcasters;
	
	
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
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		new FeedTask(rvFeed).execute();
		new BroadcastersDataSource(new WeakReference<>(rvBroadcasters), false, this).execute();
	}
}

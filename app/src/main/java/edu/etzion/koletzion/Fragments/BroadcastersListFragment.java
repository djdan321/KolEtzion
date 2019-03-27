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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvBroadcastersAdapter;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 */
public class BroadcastersListFragment extends Fragment {
	
	RecyclerView rvBroadcastersList;
	List<Profile> broadcasters;
	
	public BroadcastersListFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		broadcasters = new ArrayList<>();
		broadcasters.add(new Profile("yair", "frid"));
		broadcasters.add(new Profile("yossi", "appo"));
		broadcasters.add(new Profile("joe", "joy"));
		return inflater.inflate(R.layout.fragment_broadcasters_list, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		rvBroadcastersList = view.findViewById(R.id.rvBroadcastersList);
		
		rvBroadcastersList.setAdapter(new rvBroadcastersAdapter(broadcasters, getContext()));
		rvBroadcastersList.setLayoutManager(new GridLayoutManager(getContext(), 3));
	}
}

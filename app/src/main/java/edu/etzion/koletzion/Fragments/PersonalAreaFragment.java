package edu.etzion.koletzion.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvFeedAdapter;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.VodDataSource;


public class PersonalAreaFragment extends Fragment {

	private RecyclerView rv;
	//if no profile, set user profile.
	
	private Profile profile;
	private ImageView imagePersonalArea;
	private TextView tvPersonalName;
	private TextView tvPersonalExtra;
	
	public static PersonalAreaFragment newInstance() {
		PersonalAreaFragment fragment = new PersonalAreaFragment();
		return fragment;
	}
	
	
	public static PersonalAreaFragment newInstance(Profile p) {
		
		Bundle args = new Bundle();
		args.putParcelable("profile", p);
		args.putString("flag", "f");
		PersonalAreaFragment fragment = new PersonalAreaFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findViews(view);
		tvPersonalName.setText(String.format("%s %s", profile.getFirstName(),
				profile.getLastName()));
		displayMyFeed();
		//todo get image from profile.
		
		

		// else tvPersonalExtra.setText("Favorites")

	}
	
	private void displayMyFeed() {
		//todo change to rvFeedAdapter instance with related posts
		new VodDataSource(rv, profile,false).execute();
	}
	
	private void findViews(@NonNull View view) {
		rv = view.findViewById(R.id.rvProfileType);
		imagePersonalArea = view.findViewById(R.id.imagePersonalArea);
		tvPersonalName = view.findViewById(R.id.tvPersonalName);
		tvPersonalExtra = view.findViewById(R.id.tvPersonalExtra);
		profile = getArguments().getParcelable("profile");
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_personal_area, container, false);
	}
	
	
}

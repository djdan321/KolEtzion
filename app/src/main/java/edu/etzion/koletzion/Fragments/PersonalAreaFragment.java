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
	private final static String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final static String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final static String PROFILES_DB = "profiles";
	private final static String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	
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
		
		//todo get image from profile.
		
		
		//todo if(broadcaster) tvPersonalExtra.setText("Broadcast list")
		// else tvPersonalExtra.setText("Favorites")
		
		if(getArguments().getString("flag") == null){
			displayMyFeed(new rvFeedAdapter(getContext(), profile, true));
		}else{
			displayMyFeed(new rvFeedAdapter(getContext(), profile));
		}
	}
	
	private void displayMyFeed(rvFeedAdapter adapter) {
		//todo change to rvFeedAdapter instance with related posts
		new VodDataSource(rv, profile, adapter).execute();
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

package edu.etzion.koletzion.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.authentication.User;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.VodDataSource;


public class PersonalAreaFragment extends Fragment {
	RecyclerView rv;
	//todo put profile through to set fields
	//if no profile, set user profile.
	private Profile profile;
	private ImageView imagePersonalArea;
	private TextView tvPersonalName;
	private TextView tvPersonalExtra;
	public static PersonalAreaFragment newInstance() {
		
		Bundle args = new Bundle();
		args.putParcelable("profile" ,User.getInstance().getProfile());
		PersonalAreaFragment fragment = new PersonalAreaFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	public static PersonalAreaFragment newInstance(Profile p) {
		
		Bundle args = new Bundle();
		args.putParcelable("profile", p);
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
		//todo picasso
		//todo if(broadcaster) tvPersonalExtra.setText("Broadcast list")
		// else tvPersonalExtra.setText("Favorites")
		displayMyFeed();
	}
	private void displayMyFeed() {
		//todo now it displays the intire feed from the rest api, we should display specific feed for each profile.
		//todo this method will be written here and change the method below.
		new VodDataSource(rv, profile).execute();
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

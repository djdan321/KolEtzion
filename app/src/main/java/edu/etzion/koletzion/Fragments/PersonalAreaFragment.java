package edu.etzion.koletzion.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.BitmapSerializer;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.VodDataSource;

import static android.app.Activity.RESULT_OK;


public class PersonalAreaFragment extends Fragment {
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	private RecyclerView rv;
	//if no profile, set user profile.
	
	private Profile profile;
	private ImageView imagePersonalArea;
	private TextView tvPersonalName;
	private TextView tvSuggestContent;
	
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
		
		imagePersonalArea.
				setImageBitmap(BitmapSerializer.
						decodeStringToBitmap(profile.getEncodedBitMapImage()));
		
		displayMyFeed();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		displayMyFeed();
	}
	
	//todo
	private void displayMyFeed() {
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
				new VodDataSource(rv, profile, false).execute();
			}
		}.execute();
	}
	
	private void findViews(@NonNull View view) {
		rv = view.findViewById(R.id.rvProfileType);
		imagePersonalArea = view.findViewById(R.id.imagePersonalArea);
		tvPersonalName = view.findViewById(R.id.tvPersonalName);
		tvSuggestContent = view.findViewById(R.id.tvSuggestContent);
		profile = getArguments().getParcelable("profile");
		
		//todo set up button
//		btn.setOnClickListener((v -> {
//			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//			photoPickerIntent.setType("image/*");
//			startActivityForResult(photoPickerIntent, 1);
//		}));
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_personal_area, container, false);
	}
	
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		
		AppCompatActivity activity = (AppCompatActivity) getContext();
		
		if (resultCode == RESULT_OK) {
			try {
				final Uri imageUri = data.getData();
				final InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
				final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
				imagePersonalArea.setImageBitmap(selectedImage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getContext(), "לא נמצאה תמונה", Toast.LENGTH_LONG).show();
		}
	}
}

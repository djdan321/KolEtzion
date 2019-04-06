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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.DataDAO;
import edu.etzion.koletzion.database.GetProfileByUserNameTask;
import edu.etzion.koletzion.database.WriteSuggestedContentTask;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.models.SuggestedContent;

import static android.app.Activity.RESULT_OK;


public class SuggestContentFragment extends Fragment {
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	private static int RESULT_LOAD_IMAGE = 1;
	
	private Button btn;
	private Button btnSuggest;
	private ImageView imageView;
	private EditText etSuggest;
	private Profile profile;
	
	public SuggestContentFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_suggest_content, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		btn = view.findViewById(R.id.button);
		imageView = view.findViewById(R.id.ivSuggestedContent);
		btnSuggest = view.findViewById(R.id.btnSuggest);
		etSuggest = view.findViewById(R.id.etSuggest);
		btn.setOnClickListener((v -> {
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
		}));
		btnSuggest.setOnClickListener((v -> {
			if (etSuggest.getText().toString().length() > 15) {
				writeToServer();
			} else {
				Toast.makeText(getContext(), "התוכן חייב להכיל 15 תווים לפחות", Toast.LENGTH_SHORT).show();
			}
		}));
		
	}
	
	//writing the suggestedContent to the server.
	private void writeToServer() {
		// getting the current latest update profile object from the server
		new GetProfileByUserNameTask(FirebaseAuth.getInstance().getCurrentUser().getEmail(), new Runnable() {
			@Override
			public void run() {
				new WriteSuggestedContentTask(new SuggestedContent(
						profile, etSuggest.getText().toString()));
			}
		});
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
				imageView.setImageBitmap(selectedImage);
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

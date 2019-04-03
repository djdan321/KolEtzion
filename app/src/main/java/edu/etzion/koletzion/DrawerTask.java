package edu.etzion.koletzion;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;
import java.util.List;

import edu.etzion.koletzion.models.Profile;

public class DrawerTask extends AsyncTask<View, Void, Profile> {
	WeakReference<View> v;
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	public DrawerTask(View v) {
		this.v = new WeakReference<>(v);
	}
	
	@Override
	protected Profile doInBackground(View... views) {
		v = new WeakReference<>(views[0]);
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
			Log.d("check", "checkResult: " + item.toString());
			profile = item;
		}
		Log.d("check", list.toString());
		return profile;
	}
	
	@Override
	protected void onPostExecute(Profile profile) {
		TextView tvDrawerName = v.get().findViewById(R.id.tvDrawerName);
		tvDrawerName.setText(profile.getFirstName() + " " + profile.getLastName());
		//todo image
	}
}


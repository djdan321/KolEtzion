package edu.etzion.koletzion.database;

import android.os.AsyncTask;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.lang.ref.WeakReference;
import java.util.List;

import edu.etzion.koletzion.models.Profile;


public class GetProfileByUserNameTask extends AsyncTask<Void, Void, Profile> {
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	Runnable postExecuteRunnable;
	WeakReference<String> userNameWeakRef;
	public GetProfileByUserNameTask(String userName) {
		this.userNameWeakRef = new WeakReference<>(userName);
	}
	
	public GetProfileByUserNameTask(String userName, Runnable runnable) {
		this.userNameWeakRef = new WeakReference<>(userName);
		this.postExecuteRunnable = runnable;
	}
	
	@Override
	protected Profile doInBackground(Void... voids) {
		String userName = userNameWeakRef.get();
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(PROFILES_API_KEY)
				.password(PROFILES_API_SECRET)
				.build();
		
		Database db = client.database("profiles", false);
		
		List<Profile> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"username\": \"" + userName + "\"\n" +
				"   }\n" +
				"}", Profile.class);
		return list.get(list.size() - 1);
	}
	
	@Override
	protected void onPostExecute(Profile profile) {
		if (postExecuteRunnable != null) {
			postExecuteRunnable.run();
		}
	}
}

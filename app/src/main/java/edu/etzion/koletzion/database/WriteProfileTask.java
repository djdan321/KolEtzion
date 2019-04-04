package edu.etzion.koletzion.database;

import android.os.AsyncTask;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.lang.ref.WeakReference;
import java.util.Date;

import edu.etzion.koletzion.models.Profile;

public class WriteProfileTask extends AsyncTask<Void, Void, Void> {
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	WeakReference<Profile> profile;
	
	public WriteProfileTask(Profile profile) {
		this.profile = new WeakReference<>(profile);
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(PROFILES_API_KEY)
				.password(PROFILES_API_SECRET)
				.build();
		Profile profile = this.profile.get();
		Database db = client.database(PROFILES_DB, false);
		profile.setTimeStamp(new Date().getTime());
		db.save(profile);
		return null;
	}
	
}


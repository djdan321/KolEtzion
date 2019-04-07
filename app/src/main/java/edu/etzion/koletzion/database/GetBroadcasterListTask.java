package edu.etzion.koletzion.database;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.etzion.koletzion.models.Profile;

public class GetBroadcasterListTask extends AsyncTask<Void, Void, List<Profile>> {
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	
	@Override
	protected List<Profile> doInBackground(Void... voids) {
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(PROFILES_API_KEY)
				.password(PROFILES_API_SECRET)
				.build();
		
		Database db = client.database("profiles", false);
		
		List<Profile> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"isBroadcaster\": true\n" +
				"   }\n" +
				"}", Profile.class);
		List<Profile> profiles = new ArrayList<>(list);
		Collections.sort(profiles);
		return profiles;
	}
}

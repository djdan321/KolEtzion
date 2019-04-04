package edu.etzion.koletzion.database;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.lang.ref.WeakReference;
import java.util.List;

import edu.etzion.koletzion.models.BroadcastPost;

public class GetBroadcastPostByIdTask extends AsyncTask<Void, Void, BroadcastPost> {
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	private final String POSTS_API_KEY = "mitereeneringledituriess";
	private final String POSTS_API_SECRET = "7a76edb293ad60dbef1a92be96248116b74d9ea3";
	private final String POSTS_DB = "posts";
	WeakReference<String> idWeakRef;
	
	public GetBroadcastPostByIdTask(String id) {
		this.idWeakRef = new WeakReference<>(id);
	}
	
	@Override
	protected BroadcastPost doInBackground(Void... voids) {
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(POSTS_API_KEY)
				.password(POSTS_API_SECRET)
				.build();
		
		Database db = client.database(POSTS_DB, false);
		String id = idWeakRef.get();
		List<BroadcastPost> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"_id\": \"" + id + "\"\n" +
				"   }\n" +
				"}", BroadcastPost.class);
		
		return list.get(list.size() - 1);
	}
}

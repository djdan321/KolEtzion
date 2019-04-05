package edu.etzion.koletzion.database;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.lang.ref.WeakReference;
import java.util.Date;

import edu.etzion.koletzion.models.BroadcastPost;

public class WritePostTask extends AsyncTask<Void, Void, Void> {
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	private final String POSTS_API_KEY = "mitereeneringledituriess";
	private final String POSTS_API_SECRET = "7a76edb293ad60dbef1a92be96248116b74d9ea3";
	private final String POSTS_DB = "posts";
	WeakReference<BroadcastPost> post;
	
	public WritePostTask(BroadcastPost post) {
		this.post = new WeakReference<>(post);
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(POSTS_API_KEY)
				.password(POSTS_API_SECRET)
				.build();
		BroadcastPost broadcastPost = post.get();
		Database db = client.database(POSTS_DB, false);
		broadcastPost.setTimeStamp(new Date().getTime());
		db.save(broadcastPost);
		return null;
	}
	
	
}


package edu.etzion.koletzion.database;

import android.os.AsyncTask;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.etzion.koletzion.models.BroadcastPost;

public class GetPostListTask extends AsyncTask<Void, Void, List<BroadcastPost>> {
	private final String POSTS_API_KEY = "mitereeneringledituriess";
	private final String POSTS_API_SECRET = "7a76edb293ad60dbef1a92be96248116b74d9ea3";
	private final String POSTS_DB = "posts";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	@Override
	protected List<BroadcastPost> doInBackground(Void... voids) {
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(POSTS_API_KEY)
				.password(POSTS_API_SECRET)
				.build();
		
		Database db = client.database("posts", false);
		
		List<BroadcastPost> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"_id\": {\n" +
				"         \"$gt\": \"0\"\n" +
				"      }\n" +
				"   }\n" +
				"}", BroadcastPost.class);
		
		List<BroadcastPost> postsList = new ArrayList<>(list);
		Collections.sort(postsList);
		return postsList;
	}
}

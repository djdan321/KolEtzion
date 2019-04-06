package edu.etzion.koletzion.player;

import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvFeedAdapter;
import edu.etzion.koletzion.database.DataDAO;
import edu.etzion.koletzion.models.BroadcastCategory;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Comment;
import edu.etzion.koletzion.models.Profile;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VodDataSource extends AsyncTask<Void, Void, List<Object>> {


	List<Profile> broadcasters = new ArrayList<>();


	private boolean isMainFeed;
	private WeakReference<RecyclerView> rv;
	private Profile profile;
	private final String POSTS_API_KEY = "mitereeneringledituriess";
	private final String POSTS_API_SECRET = "7a76edb293ad60dbef1a92be96248116b74d9ea3";
	private final String POSTS_DB = "posts";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	public VodDataSource(RecyclerView rv, Profile profile, boolean isMainFeed) {
		this.rv = new WeakReference<>(rv);
		this.profile = profile;
		this.isMainFeed = isMainFeed;
	}
	
	
	private List<Vod> getVodList() {
		List<Vod> vods = new ArrayList<>();
		String link = "http://be.repoai.com:5080/WebRTCAppEE/rest/broadcast/getVodList/0/100";
		OkHttpClient client = new OkHttpClient();
		try (Response response = client.newCall(new Request.Builder().url(link).build())
				.execute()) {
			if (response.body() == null) return vods;
			String json = response.body().string();
			parseJson(vods, json);
		} catch (IOException e) {
			//todo handle exception, cant reach server
			e.printStackTrace();
		} catch (JSONException e) {
			//todo handle exception, cant parse json from server
			e.printStackTrace();
		}
		
		
		return vods;
	}
	
	private void parseJson(List<Vod> vods, String json) throws JSONException {
		
		
		JSONArray jsonArray = new JSONArray(json);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject o = (JSONObject) jsonArray.get(i);
			String streamName = o.getString("streamName");
			String vodName = o.getString("vodName");
			String streamId = o.getString("streamId");
			String filePath = o.getString("filePath");
			String vodId = o.getString("vodId");
			String type = o.getString("type");
			long creationDate = o.getLong("creationDate");
			long duration = o.getLong("duration");
			long fileSize = o.getLong("fileSize");
			Vod vod = new Vod(streamName, vodName, streamId, filePath, vodId,
					type, creationDate, duration, fileSize);
			vods.add(vod);
		}
	}
	//collecting 2 lists, one of vods from the restapi and the other is from our server.
	
	@Override
	protected List<Object> doInBackground(Void... voids) {
		//todo use it to create fictive data base. it includes everything!! but images,includes also related posts
		// createProfiles();
		List<Object> lists = new ArrayList<>();
		List<Vod> vods = getVodList();
		List<BroadcastPost> broadcastPosts = getBroadcastPostsFromCloud();
		lists.add(vods);
		lists.add(broadcastPosts);
		return lists;
	}
//this methods gets a list with all the broadcasts from the cloud.
	
	private List<BroadcastPost> getBroadcastPostsFromCloud() {
		List<BroadcastPost> postsList = new ArrayList<>();
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(POSTS_API_KEY)
				.password(POSTS_API_SECRET)
				.build();
		
		Database db = client.database(POSTS_DB, false);
		
		List<BroadcastPost> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"_id\": {\n" +
				"         \"$gt\": \"0\"\n" +
				"      }\n" +
				"   }\n" +
				"}", BroadcastPost.class);
		
		for (BroadcastPost item : list) {
			Log.d("check", "checkResult: " + item.toString());
			postsList.add(item);
		}
		Log.d("check", list.toString());
		Collections.sort(postsList);
		return postsList;
	}
	
	// after getting the two lists , we compare between them and looking for a difference.
	// if the vods list has an extra vods we will update them in our server as well.
	
	@Override
	protected void onPostExecute(List<Object> list) {
		List<Vod> vods = (List<Vod>) list.get(0);
		List<BroadcastPost> broadcastPosts = (List<BroadcastPost>) list.get(1);
		if (vods.size() != broadcastPosts.size()) {
			//checking how many new vods are updated and getting them in to our server.
			int diffNum = vods.size() - broadcastPosts.size();
			List<Vod> newVods = new ArrayList<>();
			for (int i = 1; i < diffNum + 1; i++) {
				Vod vod = vods.get(vods.size() - i);


//			 these are fictive lists and will be original when the admin will upload the files directly to our app.
//			 they are nessecery for the instance so i made them
				if(i<8) {
					List<Profile> broadcasters = new ArrayList<>();


				List<Profile> listeners = new ArrayList<>();
				List<Comment> comments = new ArrayList<>();
				List<Profile> likes = new ArrayList<>();
				
				BroadcastPost broadcastPost = new BroadcastPost(
						BroadcastCategory.MUSIC,
						"description will be added",
						vod.getFilePath(),
						broadcasters,
						listeners,
						getDurationFromFile(ExoPlayerFragment.APP_PATH + vod.getFilePath()),
						vod.getVodName(),
						comments, likes);
				broadcastPosts.add(broadcastPost);
				DataDAO.getInstance().writeBroadcastPost(broadcastPost);
				for (int j = 0; j < broadcasters.size(); j++) {
					broadcasters.get(j).addBroadcastPost(broadcastPost);
				}}
			}
		}
		// creating instance of the recyclerview with the updated list from our server.
		RecyclerView rv = this.rv.get();
		rv.setLayoutManager(new LinearLayoutManager(this.rv.get().getContext()));
		if(isMainFeed) {
			rv.setAdapter(new rvFeedAdapter(rv.getContext(), broadcastPosts, profile));
		}else{
			rv.setAdapter(new rvFeedAdapter(rv.getContext(), profile));
		}
	}
	
	private long getDurationFromFile(String filePath) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(filePath, new HashMap<String, String>());
		long l = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
		retriever.release();
		return l;
	}//osher mozes, omri ben hanan,azriel friedman , rotem hadad,ori hasin, daniel shapira,shalev kody,elad termatzy
	private void createProfiles(){
		broadcasters.add(new Profile("broadcaster1","oher","mozes",true,null,true,Profile.MOOD_NONE));
		broadcasters.add(new Profile("broadcaster2","omri","ben hanan",true,null,true,Profile.MOOD_NONE));
		broadcasters.add(new Profile("broadcaster3","azriel","friedman",true,null,true,Profile.MOOD_NONE));
		broadcasters.add(new Profile("broadcaster4","rotem","hadad",true,null,true,Profile.MOOD_NONE));
		broadcasters.add( new Profile("broadcaster5","ori","hasin",true,null,true,Profile.MOOD_NONE));
		broadcasters.add( new Profile("broadcaster6","daniel","shapira",true,null,true,Profile.MOOD_NONE));
		broadcasters.add( new Profile("broadcaster7","shalev","kody",true,null,true,Profile.MOOD_NONE));
		broadcasters.add( new Profile("broadcaster8","elad","termatzy",true,null,true,Profile.MOOD_NONE));

	}
}

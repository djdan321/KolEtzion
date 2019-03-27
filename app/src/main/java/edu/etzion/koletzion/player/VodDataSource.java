package edu.etzion.koletzion.player;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvFeedAdapter;
import edu.etzion.koletzion.models.Profile;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VodDataSource extends AsyncTask<Void, Void, List<Vod>> {
	private WeakReference<RecyclerView> rv;
	private Profile profile;
	
	public VodDataSource(RecyclerView rv, Profile profile) {
		this.rv = new WeakReference<>(rv);
		this.profile = profile;
	}
	
	public VodDataSource(RecyclerView rv) {
		this.rv = new WeakReference<>(rv);
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
	
	@Override
	protected List<Vod> doInBackground(Void... voids) {
		List<Vod> vods = new ArrayList<>();
		//todo if profile instanceof broadcaster get his arraylist.
		vods = getVodList();
		return vods;
	}
	
	@Override
	protected void onPostExecute(List<Vod> vods) {
		//changing feed fragment recyclerview
		RecyclerView rv = this.rv.get();
		rv.setLayoutManager(new LinearLayoutManager(this.rv.get().getContext()));
		rv.setAdapter(new rvFeedAdapter(rv.getContext(), vods));
	}
}

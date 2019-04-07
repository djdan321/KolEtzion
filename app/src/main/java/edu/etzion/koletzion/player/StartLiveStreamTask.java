package edu.etzion.koletzion.player;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartLiveStreamTask extends AsyncTask<Void, Void, LiveStream> {
	private WeakReference<ExoPlayerFragment> playerFragmentWeakRef;
	
	public StartLiveStreamTask(ExoPlayerFragment playerFragment) {
		this.playerFragmentWeakRef = new WeakReference<>(playerFragment);
	}
	
	@Override
	protected LiveStream doInBackground(Void... voids) {
		
		return getLiveStream();
	}
	
	@Override
	protected void onPostExecute(LiveStream stream) {
			if (stream == null) return;
			playerFragmentWeakRef.get().initPlayer(stream.getStreamUrl());
	}
	
	private LiveStream getLiveStream() {
		String link = "http://be.repoai.com:5080/WebRTCAppEE/rest/broadcast/get";
		OkHttpClient client = new OkHttpClient();
		try (Response response = client.newCall(new Request.Builder().url(link).build())
				.execute()) {
			if (response.body() == null) return null;
			String json = response.body().string();
			return parseJSON(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private LiveStream parseJSON(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			if(obj.getString("name").equals("null")) return null;
			return new LiveStream(
					obj.getString("name"),
					obj.getString("status"),
					obj.getString("streamUrl")
			);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

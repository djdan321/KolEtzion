package edu.etzion.koletzion.database;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadToImgurTask extends AsyncTask<Void, Void, Void> {
	final String upload_to = "https://api.imgur.com/3/image";
	String imageString;
	OnImgurTaskFinishedListener listener;
	String link;
	
	public UploadToImgurTask(String imageString) {
		this.imageString = imageString;
	}
	
	public UploadToImgurTask(String imageString, OnImgurTaskFinishedListener listener) {
		this.imageString = imageString;
		this.listener = listener;
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		try {
			System.out.println("started");
			OkHttpClient client = new OkHttpClient();
			Response authorization = client.newCall(new Request.Builder().url(upload_to).
					addHeader("name", "koletzion").
					addHeader("type", "base64").
					addHeader("Authorization", "Client-ID ae229e1672dd3e5").
					post(RequestBody.create(null, imageString
					)).build()).execute();
			JSONObject obj = new JSONObject(authorization.body().string());
			link = obj.getJSONObject("data").getString("link");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void aVoid) {
		if (listener != null) listener.run(link);
	}
}

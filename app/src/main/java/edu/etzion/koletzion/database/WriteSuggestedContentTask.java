package edu.etzion.koletzion.database;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.lang.ref.WeakReference;
import java.util.Date;

import edu.etzion.koletzion.models.SuggestedContent;

public class WriteSuggestedContentTask extends AsyncTask<Void, Void, Void> {
	private final String SC_API_KEY = "tstaroartindedgerfachoun";
	private final String SC_API_SECRET = "b873ae0e2398ad46034ed603fe12c721c1b90b5b";
	private final String SC_DB = "suggested_content";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	private WeakReference<SuggestedContent> sc;
	
	public WriteSuggestedContentTask(SuggestedContent sc) {
		this.sc = new WeakReference<>(sc);
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(SC_API_KEY)
				.password(SC_API_SECRET)
				.build();
		SuggestedContent suggestedContent = sc.get();
		Database db = client.database(SC_DB, false);
		suggestedContent.setTimestamp(new Date().getTime());
		db.save(suggestedContent);
		return null;
	}
}
	

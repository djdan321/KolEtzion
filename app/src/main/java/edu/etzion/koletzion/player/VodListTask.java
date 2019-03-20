package edu.etzion.koletzion.player;

import android.os.AsyncTask;

import java.util.List;

public class VodListTask extends AsyncTask<Void, Void, List<Vod>> {
	@Override
	protected List<Vod> doInBackground(Void... voids) {
		return VodDataSource.getVodList();
	}
}

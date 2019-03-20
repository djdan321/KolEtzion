package edu.etzion.koletzion.player;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static edu.etzion.koletzion.player.ExoPlayerFragment.APP_PATH;

public class VodDataSource {
	public static List<Vod> getVodList(){
		
		//instantiate retrofit
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(APP_PATH)
        .addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();
		
		// create an instance of the ApiService
		VodApiService apiService = retrofit.create(VodApiService.class);
	// make a request by calling the corresponding method
		Single<List<Vod>> vods = apiService.getVodData(0, 50);
		return null;
	}
}

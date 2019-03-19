package edu.etzion.koletzion.player;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class VodDataSource {
	public static List<Vod> getVodList(){
		List<Vod> vodList = new ArrayList<>();
		
		//instantiate retrofit
		Retrofit retrofit = new Retrofit.Builder()
				//todo: set url
				.baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();
		
		// create an instance of the ApiService
		VodApiService apiService = retrofit.create(VodApiService.class);
	// make a request by calling the corresponding method
		Single<Vod> vod = apiService.getVodData(0);
		
		
		return vodList;
	}
}

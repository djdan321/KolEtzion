package edu.etzion.koletzion.player;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VodApiService {
	@GET("broadcast/getVodList/{offset}/{size}")
	Single<Vod> getVodData(@Path("id") int vodId
//todo: query??
//               @Query("api_key") String apiKey
	);
}

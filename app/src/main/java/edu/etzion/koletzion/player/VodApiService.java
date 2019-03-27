package edu.etzion.koletzion.player;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VodApiService {
	@GET("/broadcast/getVodList/{offset}/{size}")
	Single<List<Vod>> getVodData(@Path("offset") int offset,
	                             @Path("size") int size
//               @Query("api_key") String apiKey
	);
}

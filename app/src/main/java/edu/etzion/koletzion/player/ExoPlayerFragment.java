package edu.etzion.koletzion.player;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.etzion.koletzion.R;


public class ExoPlayerFragment extends Fragment {
	SimpleExoPlayer player;
	PlayerView playerView;
	String filePath;
	final static String APP_PATH = "http://be.repoai.com:5080/WebRTCAppEE/";
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_exo_player, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		//class level instances
		playerView = view.findViewById(R.id.pvPlayer);
		
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	
	public void initPlayer(String filePath) {
		if(player != null && player.getPlaybackState() == 3) player.stop(true);
		//instantiate exoplayer
		player = ExoPlayerFactory.newSimpleInstance(getContext());
		
		//bind exoplayer to a view
		playerView.setPlayer(player);
		playerView.setControllerHideOnTouch(false);
		// Produces DataSource instances through which media data is loaded.
		Uri audioUri = Uri.parse(APP_PATH + filePath);
		ExtractorMediaSource audioSource =
				new ExtractorMediaSource.Factory(
						new DefaultHttpDataSourceFactory("exoplayer-codelab")).
						createMediaSource(audioUri);
		// Prepare the player with the source.
		player.prepare(audioSource);
		player.setPlayWhenReady(true);
	}
}

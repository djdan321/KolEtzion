package edu.etzion.koletzion;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.etzion.koletzion.player.ExoPlayerFragment;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Toolbar toolbar = findViewById(R.id.toolbar);
//		setSupportActionBar(toolbar);
		FrameLayout frame = findViewById(R.id.frame);
		getSupportFragmentManager().beginTransaction().replace(frame.getId(),
				new ExoPlayerFragment()).commit();
	}
	
}

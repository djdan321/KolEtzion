package edu.etzion.koletzion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import edu.etzion.koletzion.Fragments.MainViewPagerFragment;
import edu.etzion.koletzion.authentication.AuthenticationActivity;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Comment;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.ExoPlayerFragment;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	
	
	private FirebaseAuth auth;
	private ExoPlayerFragment playerFragment;
	private FrameLayout frame;
	private Toolbar toolbar;
	private DrawerLayout drawer;
	//todo get profile from current user
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//method that includes all the FindViewById
		setSupportActionBar(toolbar);
		
		main();
	}
	
	private void main() {
		findViews();
		startAuthenticationActivityIfNeeded();
		initFragments();
		//todo yossi?? WTF? APP!
		testDB();
		initDrawer();
	}
	
	private void initFragments() {
		getSupportFragmentManager().beginTransaction().replace(frame.getId(),
				playerFragment).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, MainViewPagerFragment.newInstance()).commit();
	}
	
	@SuppressLint("StaticFieldLeak")
	private void initDrawer() {
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		
		new AsyncTask<View, Void, Profile>() {
			View v;
			
			@Override
			protected Profile doInBackground(View... views) {
				v = views[0];
				Profile profile = null;
				CloudantClient client = ClientBuilder.account(DB_USER_NAME)
						.username(PROFILES_API_KEY)
						.password(PROFILES_API_SECRET)
						.build();
				
				Database db = client.database(PROFILES_DB, false);
				
				List<Profile> list = db.findByIndex("{\n" +
						"   \"selector\": {\n" +
						"      \"username\": \"" + FirebaseAuth.getInstance().getCurrentUser().getEmail() + "\"\n" +
						"   }\n" +
						"}", Profile.class);
				for (Profile item : list) {
					Log.e("check", "checkResult: " + item.toString());
					profile = item;
				}
				Log.e("check", list.toString());
				return profile;
			}
			
			@Override
			protected void onPostExecute(Profile profile) {
				TextView tvDrawerName = v.findViewById(R.id.tvDrawerName);
				tvDrawerName.setText(profile.getFirstName() + " " + profile.getLastName());
				//todo image
			}
		}.execute(drawer);
	}
	
	private void testDB() {
//		DataDAO db = DataDAO.getInstance();
		List<BroadcastPost> posts = new ArrayList<>();
		List<Profile> profiles = new ArrayList<>();
		List<Comment> comments = new ArrayList<>();
//		Profile profile = new Profile("joy","gedgje","appo",true,posts,true, Profile.MOOD_FINE);

//		db.writeSuggestedContent(new SuggestedContent(profile,"you should right this"));

//		db.writeBroadcastPost(new BroadcastPost(BroadcastCategory.POLITICS,"blablablabla","URL",profiles,profiles,43545,"title",comments,profiles));
//		List<BroadcastPost> list = db.getAllPosts();
//		profiles1 = db.getBroadcasters();
	
	
	}
	
	private void startAuthenticationActivityIfNeeded() {
		if (auth.getCurrentUser() == null) {
			startActivity(new Intent(this, AuthenticationActivity.class));
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			finish();
		}
	}
	
	
	private void findViews() {
		auth = FirebaseAuth.getInstance();
		frame = findViewById(R.id.frame);
		toolbar = findViewById(R.id.toolbar);
		drawer = findViewById(R.id.drawer_layout);
		playerFragment = new ExoPlayerFragment();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		
		if (id == R.id.homePage) {
			// Handle the camera action
			
			getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, MainViewPagerFragment.newInstance()).commit();
			
		} else if (id == R.id.logOut) {
			auth.signOut();
			startAuthenticationActivityIfNeeded();
		}
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	public void initPlayer(String filePath) {
		playerFragment.initPlayer(filePath);
	}
}


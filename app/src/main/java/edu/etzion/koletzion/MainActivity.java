package edu.etzion.koletzion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import edu.etzion.koletzion.Fragments.MainViewPagerFragment;
import edu.etzion.koletzion.authentication.AuthenticationActivity;


import edu.etzion.koletzion.database.DataDAO;
import edu.etzion.koletzion.models.BroadcastCategory;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Comment;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.models.SuggestedContent;
import edu.etzion.koletzion.player.ExoPlayerFragment;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private FirebaseAuth auth;
	private ExoPlayerFragment playerFragment;
	private FrameLayout frame;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//method that includes all the FindViewById
		findViews();
		startAuthenticationActivityIfNeeded();
		setSupportActionBar(toolbar);
		
		getSupportFragmentManager().beginTransaction().replace(frame.getId(),
				playerFragment).commit();
		
		getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, MainViewPagerFragment.newInstance()).commit();

		testDB();

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		
	}

	private void testDB() {
//		DataDAO db = DataDAO.getInstance(this);
		List<BroadcastPost> posts = new ArrayList<>();
		List<Profile> profiles = new ArrayList<>();
		List<Comment> comments = new ArrayList<>();
		Profile profile = new Profile("yossi","yossi","appo",true,posts,true, Profile.MOOD_FINE);
//		db.writeSuggestedContent(new SuggestedContent(profile,"you should right this"));
//		db.writeMyProfile(profile);
//		db.writeMyProfile(profile);
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

			getSupportFragmentManager().beginTransaction().replace(R.id.contentMain,MainViewPagerFragment.newInstance()).commit();

		} else if (id == R.id.logOut){
			auth.signOut();
			startAuthenticationActivityIfNeeded();
		}
		
		DrawerLayout drawer =  findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	public void initPlayer(String filePath) {
		playerFragment.initPlayer(filePath);
	}
}


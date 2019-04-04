package edu.etzion.koletzion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;

import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import edu.etzion.koletzion.Fragments.MainViewPagerFragment;
import edu.etzion.koletzion.authentication.AuthenticationActivity;
import edu.etzion.koletzion.player.ExoPlayerFragment;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	
	
	private FirebaseAuth auth;
	private ExoPlayerFragment playerFragment;
	public FrameLayout frame;
	private Toolbar toolbar;
	private DrawerLayout drawer;
	MFPPush push;
	MFPPushNotificationListener notificationListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setSupportActionBar(toolbar);
		main();
		initIBMPush();
		
		ContextCompat.startForegroundService(this,
				new Intent(this, ForegroundService.class));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (push != null) {
			System.out.println("onresume");
			push.listen(notificationListener);
		}
	}
	
	private void initIBMPush() {
		// Initialize the SDK
		BMSClient.getInstance().initialize(this, BMSClient.REGION_UK);
		//Initialize client Push SDK
		push = MFPPush.getInstance();
		push.initialize(getApplicationContext(), "179c47d1-0e7a-43cd-b274-8b284ace1f2e",
				"456ec4ec-bf74-4cc9-bc0f-0a00b194e1ea");
		
		//callback wether success or not
		push.registerDevice(new MFPPushResponseListener<String>() {
			@Override
			public void onSuccess(String response) {
			}
			
			@Override
			public void onFailure(MFPPushException exception) {
			}
		});
		
		notificationListener = message -> {
			System.out.println(message);
		};
	}
	
	
	private void main() {
		findViews();
		startAuthenticationActivityIfNeeded();
		initFragments();
		initDrawer();
	}
	
	private void initFragments() {
		getSupportFragmentManager().beginTransaction().replace(frame.getId(),
				playerFragment).commit();
		frame.setVisibility(View.GONE);
		getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new MainViewPagerFragment()).commit();
	}
	
	private void initDrawer() {
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		if (auth.getCurrentUser() == null) return;
		new DrawerTask(findViewById(R.id.tvDrawerName)).execute(drawer);
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
			List<Fragment> fragments = getSupportFragmentManager().getFragments();
			for (Fragment fragment : fragments) {
				if (fragment instanceof MainViewPagerFragment) {
					((MainViewPagerFragment) fragment).vpMain.setCurrentItem(2);
					return true;
				}
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new MainViewPagerFragment()).commit();
		} else if (id == R.id.logOut) {
			auth.signOut();
			playerFragment.stopPlayer();
			startAuthenticationActivityIfNeeded();
		}
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	public void initPlayer(String filePath) {
		playerFragment.initPlayer(filePath);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (push != null) {
			push.hold();
		}
	}
}
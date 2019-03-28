package edu.etzion.koletzion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import edu.etzion.koletzion.Adapters.ViewPagerAdapter;
import edu.etzion.koletzion.Fragments.BroadcastersListFragment;
import edu.etzion.koletzion.Fragments.FeedFragment;
import edu.etzion.koletzion.Fragments.PersonalAreaFragment;
import edu.etzion.koletzion.authentication.AuthenticationActivity;
import edu.etzion.koletzion.authentication.LoginFragment;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.ExoPlayerFragment;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	FirebaseAuth auth;
	ExoPlayerFragment playerFragment;
	FrameLayout frame;
	Toolbar toolbar;
	private static ViewPager vpMain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//method that includes all the FindViewById
		findviews();
		if(auth.getCurrentUser() == null){
			startActivity(new Intent(this, AuthenticationActivity.class).
					addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}
		setSupportActionBar(toolbar);
		
		getSupportFragmentManager().beginTransaction().replace(frame.getId(),
				playerFragment).commit();

        
        getSupportFragmentManager().beginTransaction().replace(R.id.contentMain,new MainViewPagerFragment()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    private void findviews() {
        frame = findViewById(R.id.frame);
        toolbar = findViewById(R.id.toolbar);

        playerFragment = new ExoPlayerFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }
    public void setBackStack(boolean bol){
        System.out.println(bol);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
//            vpMain.setCurrentItem(0);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void initPlayer(String filePath){
        playerFragment.initPlayer(filePath);
    }

		
		
		ViewPagerAdapterMainActivity();
		
		
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}
	
	private void ViewPagerAdapterMainActivity() {
		//this method includes the viewpager adapter that includes all the mainactivity fragments.
		ViewPagerAdapter vpMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		vpMainAdapter.addFragment(PersonalAreaFragment.newInstance(/*todo get from firebsae*/
				new Profile("yair", "frid")), "PersonalAreaFragment");
		vpMainAdapter.addFragment(new BroadcastersListFragment(), "BroadcastersListFragment");
		vpMainAdapter.addFragment(new FeedFragment(), "FeedFragment");
		
		vpMain.setAdapter(vpMainAdapter);
		vpMain.setCurrentItem(2);
	}
	
	public static void test(int id) {
		
		vpMain.setCurrentItem(2, false);
		
	}
	
	private void findviews() {
		auth = FirebaseAuth.getInstance();
		frame = findViewById(R.id.frame);
		toolbar = findViewById(R.id.toolbar);
		vpMain = findViewById(R.id.vpMain);
		playerFragment = new ExoPlayerFragment();
	}
	
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
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
		
		if (id == R.id.nav_camera) {
			// Handle the camera action
			vpMain.setCurrentItem(0);
		} else if (id == R.id.nav_gallery) {
		
		} else if (id == R.id.nav_slideshow) {
		
		} else if (id == R.id.nav_manage) {
		
		} else if (id == R.id.nav_login) {
		
		} else if (id == R.id.nav_send) {
		
		}
		
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	public void initPlayer(String filePath) {
		playerFragment.initPlayer(filePath);
	}
}


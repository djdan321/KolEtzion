package edu.etzion.koletzion;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager.widget.ViewPager;
import edu.etzion.koletzion.Adapters.ViewPagerAdapter;
import edu.etzion.koletzion.Fragments.BroadcastersListFragment;
import edu.etzion.koletzion.Fragments.FeedFragment;
import edu.etzion.koletzion.Fragments.MainViewPagerFragment;
import edu.etzion.koletzion.Fragments.SuggestContentFragment;
import edu.etzion.koletzion.Fragments.PersonalAreaFragment;
import edu.etzion.koletzion.database.MyData;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.ExoPlayerFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ExoPlayerFragment playerFragment;
    FrameLayout frame;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //method that includes all the FindViewById
        findviews();
        
        setSupportActionBar(toolbar);
        
		getSupportFragmentManager().beginTransaction().replace(frame.getId(),
				playerFragment).commit();
        
        getSupportFragmentManager().beginTransaction().replace(R.id.contentMain,new MainViewPagerFragment()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.contentMain,new SuggestContentFragment()).commit();
//        ViewPagerAdapterMainActivity();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
//
//    private void ViewPagerAdapterMainActivity() {
//        //this method includes the viewpager adapter that includes all the mainactivity fragments.
//        ViewPagerAdapter vpMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        vpMainAdapter.addFragment(PersonalAreaFragment.newInstance(/*todo get from firebsae*/
//        new Profile("yair", "frid")),"PersonalAreaFragment");
//        vpMainAdapter.addFragment(new BroadcastersListFragment(), "BroadcastersListFragment");
//        vpMainAdapter.addFragment(new FeedFragment(),"FeedFragment");
//
//        vpMain.setAdapter(vpMainAdapter);
//        vpMain.setCurrentItem(2);
//    }

    private void findviews() {
        frame = findViewById(R.id.frame);
        toolbar = findViewById(R.id.toolbar);
//        vpMain = findViewById(R.id.vpMain);
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
}


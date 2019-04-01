package edu.etzion.koletzion.Fragments;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import edu.etzion.koletzion.Adapters.ViewPagerAdapter;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.player.VodDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainViewPagerFragment extends Fragment {
    private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
    private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
    private final String PROFILES_DB = "profiles";
    private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";

    private ViewPager vpMain;
    public MainViewPagerFragment(){}
    public static MainViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        MainViewPagerFragment fragment = new MainViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main_view_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vpMain = view.findViewById(R.id.vpMain);

        ViewPagerAdapterMainActivity();
    }

    @SuppressLint("StaticFieldLeak")
    private void ViewPagerAdapterMainActivity() {
        //this method includes the viewpager adapter that includes all the mainactivity fragments.
        ViewPagerAdapter vpMainAdapter = new ViewPagerAdapter(getFragmentManager());

        // getting the current latest update profile object from the server
        new AsyncTask<Void,Void,Profile>(){
            @Override
            protected Profile doInBackground(Void... voids) {
                Profile profile = null;
                CloudantClient client = ClientBuilder.account(DB_USER_NAME)
                        .username(PROFILES_API_KEY)
                        .password(PROFILES_API_SECRET)
                        .build();

                Database db = client.database(PROFILES_DB, false);

                List<Profile> list = db.findByIndex("{\n" +
                        "   \"selector\": {\n" +
                        "      \"username\": \""+ FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\"\n" +
                        "   }\n" +
                        "}", Profile.class);
                for (Profile item : list) {
                    Log.e("check", "checkResult: "+item.toString());
                    profile=item;
                }
                Log.e("check", list.toString());
                return profile;
            }

            @Override
            protected void onPostExecute(Profile profile) {
                vpMainAdapter.addFragment(PersonalAreaFragment.newInstance(profile),"PersonalAreaFragment");

                vpMainAdapter.addFragment(new BroadcastersListFragment(), "BroadcastersListFragment");
                vpMainAdapter.addFragment(new FeedFragment(),"FeedFragment");

                vpMainAdapter.addFragment(new SuggestContentFragment(),"Suggest Content Fragment");

                vpMain.setAdapter(vpMainAdapter);
                vpMain.setCurrentItem(2);
            }
        }.execute();


    }
}

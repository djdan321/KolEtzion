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

public class MainViewPagerFragment extends Fragment {
 
    public ViewPager vpMain;
    public MainViewPagerFragment(){}
    
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
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        ViewPagerAdapterMainActivity();
    }
    
    private void ViewPagerAdapterMainActivity() {
        //this method includes the viewpager adapter that includes all the mainactivity fragments.

        // getting the current latest update profile object from the server
        new ViewPagerTask(vpMain, getChildFragmentManager()).execute();

    }
}

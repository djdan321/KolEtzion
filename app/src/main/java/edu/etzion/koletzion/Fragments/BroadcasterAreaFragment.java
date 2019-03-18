package edu.etzion.koletzion.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import edu.etzion.koletzion.Adapters.ViewPagerAdapter;
import edu.etzion.koletzion.R;

/**
 * A simple {@link Fragment} subclass.
 * TODO create tab layout between two fragments:
 * BroadcasterProfile | ContentsToBroadcast
 *
 */
public class BroadcasterAreaFragment extends Fragment {

    public BroadcasterAreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broadcaster_area, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tlBroadCaster = view.findViewById(R.id.tlBroadCaster);
        ViewPager vpBroadCaster = view.findViewById(R.id.vpBroadCaster);
        ViewPagerAdapter vpBroadCasterAdapter = new ViewPagerAdapter(getFragmentManager());
        vpBroadCasterAdapter.addFragment(new BroadcasterProfileFragment(),"הפיד שלי");
        vpBroadCasterAdapter.addFragment(new BroadcastingContentsFragment(),"תכנים לשידור");
        vpBroadCaster.setAdapter(vpBroadCasterAdapter);
        tlBroadCaster.setupWithViewPager(vpBroadCaster);
    }
}

package edu.etzion.koletzion.Fragments.PersonalArea;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import edu.etzion.koletzion.Adapters.CustomViewPager;
import edu.etzion.koletzion.Adapters.ViewPagerAdapter;
import edu.etzion.koletzion.R;

/**
 * A simple {@link Fragment} subclass.
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
        /*
         * tab layout between two fragments:
         * BroadcasterProfile | ContentsToBroadcast
         */

        TabLayout tlBroadCaster = view.findViewById(R.id.tlBroadCaster);
        CustomViewPager vpBroadCaster = view.findViewById(R.id.vpBroadCaster);
        vpBroadCaster.setPagingEnabled(false);
        ViewPagerAdapter vpBroadCasterAdapter = new ViewPagerAdapter(getChildFragmentManager());
        vpBroadCasterAdapter.addFragment(new BroadcasterProfileFragment(),"הפיד שלי");
        vpBroadCasterAdapter.addFragment(new BroadcastingContentsFragment(),"תכנים לשידור");
        vpBroadCaster.setAdapter(vpBroadCasterAdapter);
        tlBroadCaster.setupWithViewPager(vpBroadCaster);
    }
}

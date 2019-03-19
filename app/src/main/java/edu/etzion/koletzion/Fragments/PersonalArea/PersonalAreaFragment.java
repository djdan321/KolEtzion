package edu.etzion.koletzion.Fragments.PersonalArea;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.etzion.koletzion.Adapters.CustomViewPager;
import edu.etzion.koletzion.Adapters.ViewPagerAdapter;
import edu.etzion.koletzion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalAreaFragment extends Fragment {

    //todo BUG with returning to this fragment after launch
    public PersonalAreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_area, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPagerScreensByProfileType(view);
    }

    private void viewPagerScreensByProfileType(@NonNull View view) {
        //viewPagerAdapter that includes two fragments : BroadCasterAreaFragment & UserAreaFragment.
        // the displayed fragment is depend on the Type of the user that connected (Regular/BroadCaster)
        CustomViewPager vpProfileType = view.findViewById(R.id.vpProfileType);
        vpProfileType.setPagingEnabled(false);
        ViewPagerAdapter vpProfileTypeAdapter = new ViewPagerAdapter(getFragmentManager());
        vpProfileTypeAdapter.addFragment(new BroadcasterAreaFragment(),"BroadCasterAreaFragment"); //item 0
        vpProfileTypeAdapter.addFragment(new UserAreaFragment(),"UserAreaFragment");// item 1
        vpProfileType.setAdapter(vpProfileTypeAdapter);
        //TODO check the type of the user and display the relevant fragment
        //if(user instanceOf User)
//        vpProfileType.setCurrentItem(1);
//        if(user instanceof Broadcaster)
        vpProfileType.setCurrentItem(0);

    }
}

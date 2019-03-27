package edu.etzion.koletzion.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.CustomViewPager;
import edu.etzion.koletzion.Adapters.ViewPagerAdapter;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.player.VodDataSource;


public class PersonalAreaFragment extends Fragment {
    RecyclerView rv;
    //todo put profile through to set fields
    //if no profile, set user profile.
    public static PersonalAreaFragment newInstance() {

        Bundle args = new Bundle();
        //user.getprofile
        PersonalAreaFragment fragment = new PersonalAreaFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static PersonalAreaFragment newInstance(Profile p) {

        Bundle args = new Bundle();

        PersonalAreaFragment fragment = new PersonalAreaFragment();
        fragment.setArguments(args);
        return fragment;
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
        rv = view.findViewById(R.id.rvProfileType);
        new VodDataSource(rv).execute();
    }

}

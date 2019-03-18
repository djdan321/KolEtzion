package edu.etzion.koletzion.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.etzion.koletzion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BroadcasterProfileFragment extends Fragment {


    public BroadcasterProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broadcaster_profile, container, false);
    }

}

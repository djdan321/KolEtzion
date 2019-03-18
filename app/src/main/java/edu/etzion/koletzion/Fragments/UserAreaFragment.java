package edu.etzion.koletzion.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.etzion.koletzion.R;

/**
 * A simple {@link Fragment} subclass.
 * TODO create a recyclerviewer<Post>  > feed for the posts that the user favored.
 */
public class UserAreaFragment extends Fragment {


    public UserAreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_area, container, false);
    }

}

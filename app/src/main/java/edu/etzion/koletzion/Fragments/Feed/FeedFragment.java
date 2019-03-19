package edu.etzion.koletzion.Fragments.Feed;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.RecyclerViewAdapter;
import edu.etzion.koletzion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private ArrayList<String> headers;
    private ArrayList<String> descriptions;
    public FeedFragment() {
        headers= new ArrayList<>();
        descriptions= new ArrayList<>();
        headers.add("1");
        headers.add("2");
        headers.add("3");
        headers.add("4");
        headers.add("5");
        headers.add("6");
        headers.add("7");
        headers.add("8");
        headers.add("9");

        descriptions.add("aaaaaaaaaaaaa");
        descriptions.add("aaaaaaaaaaaaaaaaaaaaaaaaa");
        descriptions.add("bbbbbbbbbbb");
        descriptions.add("aaaaaaaa   aaaaaaaaaaaaa");
        descriptions.add("aaaaaaaaaaatetasrs    testt ta");
        descriptions.add("aaa63463476865856756aaa");
        descriptions.add("aaaaa tetre   earear   eraa");
        descriptions.add("aaaaaaaa5466666666666654");
        descriptions.add("aaartgbfhfghhhhhhhhhhhhh");
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView =view.findViewById(R.id.rvPost);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(headers,descriptions,getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}

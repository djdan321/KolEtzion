package edu.etzion.koletzion.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Adapters.rvBroadcastersAdapter;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.authentication.BroadcasterUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class BroadcastersListFragment extends Fragment {

    RecyclerView rvBroadcastersList;
    List<BroadcasterUser> broadcasters;
    public BroadcastersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broadcasters_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBroadcastersList = view.findViewById(R.id.rvBroadcastersList);
        broadcasters = new ArrayList<>();
        broadcasters.add(new BroadcasterUser("yossi","appo",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("daniel","gedje",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("yair","frid",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("yossi","appo",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("daniel","gedje",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("yair","frid",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("yossi","appo",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("daniel","gedje",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("yair","frid",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("yossi","appo",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("daniel","gedje",R.mipmap.ic_launcher));
        broadcasters.add(new BroadcasterUser("yair","frid",R.mipmap.ic_launcher));
        rvBroadcastersList.setAdapter(new rvBroadcastersAdapter(broadcasters,getContext()));
        rvBroadcastersList.setLayoutManager(new GridLayoutManager(getContext(),3));
    }
}

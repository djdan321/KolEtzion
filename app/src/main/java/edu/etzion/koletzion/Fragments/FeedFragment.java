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
import edu.etzion.koletzion.player.VodDataSource;
import edu.etzion.koletzion.stream.Broadcast;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    
    RecyclerView rvFeed;
    RecyclerView rvBroadcasters;
    List<BroadcasterUser> broadcasters;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFeed = view.findViewById(R.id.rvPost);
        rvBroadcasters = view.findViewById(R.id.rvBroadcasters);
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
        rvBroadcasters.setAdapter(new rvBroadcastersAdapter(broadcasters,getContext()));
        rvBroadcasters.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new VodDataSource(rvFeed).execute();
    }
}

package edu.etzion.koletzion.comments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.PostDataSource;
import edu.etzion.koletzion.models.BroadcastPost;


public class LikeFragment extends AppCompatDialogFragment {
	RecyclerView rv;
	BroadcastPost post;
	
	public static LikeFragment newInstance(BroadcastPost post) {
		
		Bundle args = new Bundle();
		args.putParcelable("post", post);
		LikeFragment fragment = new LikeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		post = getArguments().getParcelable("post");
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_like, container, false);
	}
	//todo static
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		rv = view.findViewById(R.id.likeRecycler);
		new PostDataSource(post, rv){
			@Override
			protected void onPostExecute(BroadcastPost broadcastPost) {
				RecyclerView rv = this.rv.get();
				rv.setAdapter(new LikeAdapter(post));
				rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
			}
		}.execute();
	}
}

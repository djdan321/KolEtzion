package edu.etzion.koletzion.comments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.PostDataSource;
import edu.etzion.koletzion.models.BroadcastPost;


public class CommentFragment extends AppCompatDialogFragment {
	
	RecyclerView rv;
	BroadcastPost post;
	
	public static CommentFragment newInstance(BroadcastPost post) {
		
		Bundle args = new Bundle();
		args.putParcelable("post" ,post);
		CommentFragment fragment = new CommentFragment();
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		post = getArguments().getParcelable("post");
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_comment, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		rv = view.findViewById(R.id.commentRecycler);
		PostDataSource source = new PostDataSource(post, rv);
		source.execute();
		
	}
}

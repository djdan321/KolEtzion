package edu.etzion.koletzion.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.BitmapSerializer;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Profile;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {
	List<Profile> likes;
	
	LikeAdapter(BroadcastPost post) {
		likes = post.getLikes();
	}
	
	@NonNull
	@Override
	public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new LikeViewHolder(LayoutInflater.
				from(parent.getContext()).
				inflate(R.layout.like_item, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {
		Profile p = likes.get(position);
		holder.tvLikeName.setText(p.getFirstName() + " " + p.getLastName());
		holder.ivLike.setImageBitmap(BitmapSerializer.decodeStringToBitmap(
				likes.get(position).getEncodedBitMapImage()
		));
	}
	
	@Override
	public int getItemCount() {
		return likes.size();
	}
	
	class LikeViewHolder extends RecyclerView.ViewHolder {
		ImageView ivLike;
		TextView tvLikeName;
		
		public LikeViewHolder(@NonNull View itemView) {
			super(itemView);
			ivLike = itemView.findViewById(R.id.ivLike);
			tvLikeName = itemView.findViewById(R.id.tvLikeName);
		}
	}
}

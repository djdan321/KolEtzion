package edu.etzion.koletzion.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import edu.etzion.koletzion.MainActivity;
import edu.etzion.koletzion.R;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.database.DataDAO;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Comment;
import edu.etzion.koletzion.models.Profile;



public class rvFeedAdapter extends RecyclerView.Adapter<rvFeedAdapter.ViewHolder> {
    private Context context;
    private List<BroadcastPost> broadcasts;
    private Profile profile;
    private int likesCounter=0;
    private int commentsCounter=0;

    public rvFeedAdapter(Context context, Profile profile) {
        this.context = context;
        this.profile = profile;
        broadcasts = profile.getRelatedPosts();
    }

    public rvFeedAdapter(Context context, List<BroadcastPost> broadcastPosts,Profile profile) {
        this.context = context;
        this.broadcasts=broadcastPosts;
        this.profile=profile;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.broadcaster_post,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (broadcasts == null || broadcasts.size() == 0) return;
        String name = broadcasts.get(position).getTitle().replaceAll("_", " ");
        name = name.substring(0, name.length()-4);
        holder.tvPostHeader.setText(name);
        likesCounter=broadcasts.get(position).getLikesCount();
        commentsCounter=broadcasts.get(position).getCommentsCount();
        holder.tvPostDescription.setText(broadcasts.get(position).getDescription());
        holder.tvLikesCount.setText(likesCounter+" לייקים");
        holder.tvCommentsCount.setText(commentsCounter+ " תגובות");

        // playing the broadcast
        holder.imagePostPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) ((MainActivity) context)
                        .initPlayer(broadcasts.get(position).getStreamURL());
            }
        });

        //adding a like to the broadcast(only once per user)
        holder.ivLike.setOnClickListener((v)->{
            List<Profile> likes=broadcasts.get(position).getLikes();
            for (int i = 0; i < likes.size(); i++) {
                if(likes.get(i).getUsername().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                    return;
            }
            broadcasts.get(position).addLike(profile);
            DataDAO.getInstance().updateBroadcastPost(broadcasts.get(position));
            likesCounter++;
            holder.tvLikesCount.setText(likesCounter+" לייקים");
        });

        //adding a comment to the broadcast
        holder.ivComment.setOnClickListener((v -> {
            if(holder.etComment.getText().toString().length()>0) {
                broadcasts.get(position).addComment(new Comment(profile, holder.etComment.getText().toString()));
                DataDAO.getInstance().updateBroadcastPost(broadcasts.get(position));
                commentsCounter++;
                holder.tvCommentsCount.setText(commentsCounter + " תגובות");
            }
        }));


        //adding the broadcast to the user's favorites.
        if(profile.isBroadcaster())
            holder.ivFavorite.setVisibility(View.INVISIBLE);
        else{
            holder.ivFavorite.setVisibility(View.VISIBLE);
            holder.ivFavorite.setOnClickListener((v -> {
                List<BroadcastPost> favorites = profile.getRelatedPosts();
                for (int i = 0; i < favorites.size(); i++) {
                    if(favorites.get(i).get_id().equals(broadcasts.get(position).get_id()))
                        return;
                }
                profile.addBroadcastPost(broadcasts.get(position));
                DataDAO.getInstance().updateMyProfile(profile);
            }));
        }
    }

    @Override
    public int getItemCount() {
        return broadcasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView broadcastPost;
        TextView tvPostHeader;
        TextView tvPostDescription;
        ImageView imagePostPlayBtn;
        TextView tvLikesCount;
        TextView tvCommentsCount;
        TextView tvListenersCount;
        ImageView ivLike;
        ImageView ivComment;
        ImageView ivFavorite;
        EditText etComment;

        public ViewHolder(View itemView){
            super(itemView);
            broadcastPost = itemView.findViewById(R.id.broadcasterPostLayout);
            tvPostHeader = itemView.findViewById(R.id.tvPostHeader);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
            imagePostPlayBtn = itemView.findViewById(R.id.imagePostPlayClick);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            tvCommentsCount = itemView.findViewById(R.id.tvCommentsCount);
            tvListenersCount = itemView.findViewById(R.id.tvListenersCount);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            etComment = itemView.findViewById(R.id.etComment);

        }
    }
}

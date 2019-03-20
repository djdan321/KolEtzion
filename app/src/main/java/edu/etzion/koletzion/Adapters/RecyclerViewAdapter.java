package edu.etzion.koletzion.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import edu.etzion.koletzion.MainActivity;
import edu.etzion.koletzion.R;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.player.Vod;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Vod> vods;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Vod> vods) {
        this.context = context;
        this.vods = vods;
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
        if (vods == null || vods.size() == 0) return;
        String name = vods.get(position).getVodName().replaceAll("_", " ");
        name = name.substring(0, name.length()-4);
        holder.tvPostHeader.setText(name);
        holder.tvPostDescription.setText("TEMP: " + vods.get(position).getStreamId());
        holder.imagePostPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) ((MainActivity) context)
                        .initPlayer(vods.get(position).getFilePath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView broadcastPost;
        TextView tvPostHeader;
        TextView tvPostDescription;
        ImageView imagePostPlayBtn;
        public ViewHolder(View itemView){
            super(itemView);
            broadcastPost = itemView.findViewById(R.id.broadcasterPostLayout);
            tvPostHeader = itemView.findViewById(R.id.tvPostHeader);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
            imagePostPlayBtn = itemView.findViewById(R.id.imagePostPlayClick);
        }
    }
}

package edu.etzion.koletzion.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import edu.etzion.koletzion.R;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<String> headers = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    Context mContext;

    public RecyclerViewAdapter(ArrayList<String> headers, ArrayList<String> descriptions, Context mContext) {
        this.headers = headers;
        this.descriptions = descriptions;
        this.mContext = mContext;
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
        holder.tvPostHeader.setText(headers.get(position));
        holder.tvPostDescription.setText(descriptions.get(position));
        holder.imagePostPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, headers.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return headers.size();
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

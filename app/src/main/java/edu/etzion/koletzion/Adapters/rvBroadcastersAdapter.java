package edu.etzion.koletzion.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.MainActivity;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.authentication.BroadcasterUser;
import edu.etzion.koletzion.stream.Broadcast;

public class rvBroadcastersAdapter extends RecyclerView.Adapter<rvBroadcastersAdapter.ViewHolder> {
    List<BroadcasterUser> broadcasters;
    Context context;

    public rvBroadcastersAdapter(List<BroadcasterUser> broadcasters, Context context) {
        this.broadcasters = broadcasters;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.broadcaster_cv_icon,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivBroadcaster.setImageResource(broadcasters.get(position).getImageResourceId());
        holder.tvBroadcaster.setText(broadcasters.get(position).getFirstName()+" "+broadcasters.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return broadcasters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvBroadcaster;
        ImageView ivBroadcaster;
        TextView tvBroadcaster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvBroadcaster = itemView.findViewById(R.id.cvBroadcaster);
            ivBroadcaster = itemView.findViewById(R.id.imageBroadcaster);
            tvBroadcaster = itemView.findViewById(R.id.tvBroadcaster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    AppCompatActivity activity= (AppCompatActivity) view.getContext();
                    MainActivity.test(0);
                }
            });
            }
        }
    }


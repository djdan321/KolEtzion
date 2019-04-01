package edu.etzion.koletzion.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.Fragments.PersonalAreaFragment;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.models.Profile;

public class rvBroadcastersAdapter extends RecyclerView.Adapter<rvBroadcastersAdapter.ViewHolder> {
    private List<Profile> broadcasters;
    private Context context;
    private View view;


    public rvBroadcastersAdapter(List<Profile> broadccasters,Context context) {
        this.broadcasters = broadccasters;
        this.context = context;
    }

    @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.broadcaster_cv_icon, parent, false);
       ViewHolder holder = new ViewHolder(view);
       return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.tvBroadcaster.setText(broadcasters.get(position).getFirstName() + " " + broadcasters.get(position).getLastName());
   //todo set image resource
       holder.itemView.setOnClickListener((v) -> {
           AppCompatActivity activity = (AppCompatActivity) v.getContext();
           Log.d("tag", String.valueOf(v.getContext()));
           activity.getSupportFragmentManager().beginTransaction().replace(
                   R.id.contentMain, PersonalAreaFragment.newInstance(broadcasters.get(position))
            ).commit();
    });
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
        }
    }


}


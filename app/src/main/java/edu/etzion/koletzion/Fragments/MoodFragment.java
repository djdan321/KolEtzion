package edu.etzion.koletzion.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.GetProfileByUserNameTask;
import edu.etzion.koletzion.database.RunWithProfile;
import edu.etzion.koletzion.database.UpdateProfileTask;
import edu.etzion.koletzion.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoodFragment extends AppCompatDialogFragment {
    private ImageView ivHappy;
    private ImageView ivFine;
    private ImageView ivSad;
    private TextView tvMessage;
    private Button btnNone;

//todo add on click listeners for each click, write to server.(first read from server.);
    public MoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.feeling, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivFine = view.findViewById(R.id.ivFine);
        ivHappy = view.findViewById(R.id.ivHappy);
        ivSad = view.findViewById(R.id.ivSad);
        btnNone = view.findViewById(R.id.btnNone);
        tvMessage = view.findViewById(R.id.tvMessage);

        new GetProfileByUserNameTask(FirebaseAuth.getInstance().getCurrentUser().getEmail(), new RunWithProfile() {
            @Override
            public void run(Profile profile) {
                tvMessage.setText("שלום "+profile.getFirstName()+", איך אתה מרגיש?");
            }
        }).execute();

        ivHappy.setOnClickListener((v)->{
            setMood(1);
            dismiss();
        });
        ivFine.setOnClickListener((v)->{
            setMood(2);
            dismiss();
        });
        ivSad.setOnClickListener((v)->{
            setMood(3);
            dismiss();
        });
        btnNone.setOnClickListener((v)->{
            setMood(-1);
            dismiss();
        });
    }
// happy 1, fine 2, sad 3 , none -1;
    private void setMood(int mood){
        new GetProfileByUserNameTask(FirebaseAuth.getInstance().getCurrentUser().getEmail(), new RunWithProfile() {
            @Override
            public void run(Profile profile) {
                profile.setMood(mood);
                new UpdateProfileTask(profile).execute();
            }
        }).execute();
    }
}

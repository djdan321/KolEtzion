package edu.etzion.koletzion.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.etzion.koletzion.R;
import edu.etzion.koletzion.database.DataDAO;
import edu.etzion.koletzion.models.Profile;
import edu.etzion.koletzion.models.SuggestedContent;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestContentFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;

    private Button btn;
    private Button btnSuggest;
    private ImageView imageView;
    private EditText etSuggest;
    private Profile profile;
    public SuggestContentFragment() {
        // Required empty public constructor
        //todo get current profile from server
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggest_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.button);
        imageView = view.findViewById(R.id.ivSuggestedContent);
        btnSuggest=view.findViewById(R.id.btnSuggest);
        etSuggest=view.findViewById(R.id.etSuggest);
        btn.setOnClickListener((v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
        }));
        btnSuggest.setOnClickListener((v -> {
            if(etSuggest.getText().toString().length()>15) {
                SuggestedContent sc = new SuggestedContent(profile, etSuggest.getText().toString());
                DataDAO.getInstance().writeSuggestedContent(sc);
            }
            else{
                Toast.makeText(getContext(), "התוכן חייב להכיל 15 תווים לפחות", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        AppCompatActivity activity = (AppCompatActivity) getContext();

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}

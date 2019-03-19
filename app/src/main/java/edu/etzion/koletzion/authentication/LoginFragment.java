package edu.etzion.koletzion.authentication;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.etzion.koletzion.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginFragment extends Fragment {
	private FirebaseAuth auth;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		auth = FirebaseAuth.getInstance();
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_login, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		//login button calls signIn with info from email et and password et
		view.findViewById(R.id.btnLoginSubmit).setOnClickListener(v -> {
			signIn(((EditText) view.findViewById(R.id.etLoginEmail)).getText().toString(),
					((EditText) view.findViewById(R.id.etLoginPassword)).getText().toString());
		});
		
		//google button calls googleSignIn method
		view.findViewById(R.id.btnLoginGoogle).setOnClickListener(v -> {
			googleSignIn();
		});
	}
	
	private void googleSignIn() {
		
	}
	
	private void signIn(String email, String password) {
		if (getActivity() == null) return;
		auth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(getActivity(), task -> {
					if (task.isSuccessful()) {
						// Sign in success, update UI with the signed-in user's information
						Log.d(TAG, "signInWithEmail:success");
						//todo: send to MainActivity
						Toast.makeText(getContext(), "WORKS", Toast.LENGTH_SHORT).show();
						//handle getting UserCredentials from server;
						User.getInstance().setCredentials(email, "nameFromServer"//todo: get name from server
								,password);
					} else {
						// If sign in fails, display a message to the user.
						Log.w(TAG, "signInWithEmail:failure", task.getException());
						//todo: handle authentication failed
						Toast.makeText(getContext(), "Authentication failed.",
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}

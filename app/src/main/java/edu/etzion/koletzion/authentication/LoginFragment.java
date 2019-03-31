package edu.etzion.koletzion.authentication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.etzion.koletzion.MainActivity;
import edu.etzion.koletzion.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginFragment extends Fragment implements View.OnClickListener {
	private FirebaseAuth auth;
	private EditText etLoginEmail;
	private EditText etLoginPassword;
	private String email;
	private String password;
	
	
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
		findViews(view);
		setOnClickListeners(view);
		
		etLoginEmail.requestFocus();
	}
	
	private void setOnClickListeners(@NonNull View view) {
		view.findViewById(R.id.btnLoginSubmit).setOnClickListener(this);
		view.findViewById(R.id.tvRegisterFromLogin).
				setOnClickListener(v -> {
					((AuthenticationActivity) getContext()).getSupportFragmentManager().
							beginTransaction().addToBackStack("loginFragment").
							replace(R.id.authenticationFrame, new RegisterFragment()).
							commit();
				});
	}
	
	private void findViews(@NonNull View view) {
		etLoginEmail = view.findViewById(R.id.etLoginEmail);
		etLoginPassword = view.findViewById(R.id.etLoginPassword);
	}
	
	private void signIn(String email, String password) {
		if (getActivity() == null) return;
		auth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(getActivity(), task -> {
					if (task.isSuccessful()) {
						// Sign in success, update UI with the signed-in user's information
						Log.d(TAG, "signInWithEmail:success");
						//handle getting UserCredentials from server;
						startActivity(new Intent(getContext(), MainActivity.class));
					} else {
						// If sign in fails, display a message to the user.
						Log.w(TAG, "signInWithEmail:failure", task.getException());
						toast("התחברות נכשלה, שם משתמש או סיסמא לא נכונים.");
					}
				});
	}
	
	private void toast(String text) {
		//todo only do this if keyboard is open, else do normal toast
		Toast t = Toast.makeText(getContext(), text,
				Toast.LENGTH_SHORT);
		t.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,
				0, 0);
		t.show();
	}
	
	@Override
	public void onClick(View v) {
		email = etLoginEmail.getText().toString();
		if (email.length() < 5) {
			etLoginEmail.setError("כתובת הדואר קצרה מדי");
			etLoginEmail.requestFocus();
			return;
		}
		password = etLoginPassword.getText().toString();
		if (password.length() < 3) {
			etLoginPassword.setError("סיסמא קצרה מדי");
			etLoginPassword.requestFocus();
			return;
		}
		signIn(email, password);
	}
}

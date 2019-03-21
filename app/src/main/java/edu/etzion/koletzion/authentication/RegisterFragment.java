package edu.etzion.koletzion.authentication;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.etzion.koletzion.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class RegisterFragment extends Fragment implements Button.OnClickListener {
	private EditText etRegisterName;
	private EditText etRegisterEmail;
	private EditText etRegisterPassword;
	private EditText etRegisterConfirmPassword;
	private Button btnRegisterSubmit;
	private boolean match;
	private FirebaseAuth auth;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_register, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		FirebaseApp.initializeApp(getContext());
		findViews(view);
		btnRegisterSubmit.setOnClickListener(this);
		etRegisterConfirmPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (!etRegisterConfirmPassword.getText().toString().equals(
						etRegisterPassword.getText().toString())) {
					match = false;
					//todo show error to user, passwords dont allign
				} else {
					match = true;
				}
			}
		});
		super.onViewCreated(view, savedInstanceState);
		
	}
	
	private void findViews(@NonNull View view) {
		auth = FirebaseAuth.getInstance();
		match = false;
		etRegisterName = view.findViewById(R.id.etRegisterName);
		etRegisterEmail = view.findViewById(R.id.etRegisterEmail);
		etRegisterPassword = view.findViewById(R.id.etRegisterPassword);
		etRegisterConfirmPassword = view.findViewById(R.id.etRegisterConfirmPassword);
		btnRegisterSubmit = view.findViewById(R.id.btnRegisterSubmit);
	}
	
	//button onclick
	@Override
	public void onClick(View v) {
		if (match && etRegisterPassword.getText().toString().length() >= 6) {
			createUser(etRegisterEmail.getText().toString(), etRegisterName.getText().toString(),
					etRegisterPassword.getText().toString());
		}
	}
	
	public void createUser(String email, String name, String password) {
		//todo auth create user
		auth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						FirebaseUser currentUser = auth.getCurrentUser();
						if (task.isSuccessful()) {
							//todo:
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "createUserWithEmail:success");
						} else {
							//todo:
							// If sign in fails, display a message to the user.
							Log.w(TAG, "createUserWithEmail:failure", task.getException());
							Toast.makeText(getContext(), "Authentication failed.",
									Toast.LENGTH_SHORT).show();
						}
						User.getInstance().setCredentials(email, name, password);
						//todo, send user Details to server.
						// ...
						User.getInstance().setPassword(null);
						//
					}
				});
		
	}
}

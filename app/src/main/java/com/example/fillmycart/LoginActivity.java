package com.example.fillmycart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    EditText email,pass;
    Button btnSignin;
    TextView tvSignup;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailEditText);
        pass = findViewById((R.id.passwordEditText));
        btnSignin = findViewById(R.id.btnSignin);
        tvSignup = findViewById(R.id.gotosignup);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                String emailstr = email.getText().toString();
                String password = pass.getText().toString();
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this,"you are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,test.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Please Log In..",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailstr = email.getText().toString();
                String password = pass.getText().toString();
                if (emailstr.isEmpty()) {
                    email.setError("please enter email..");
                    email.requestFocus();
                }
                else if(password.isEmpty()) {
                    pass.setError("please enter password..");
                    pass.requestFocus();
                }
                else if(emailstr.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this,"fields are empty!",Toast.LENGTH_SHORT);
                }
                else if(!emailstr.isEmpty() && !password.isEmpty()){
                    mAuth.signInWithEmailAndPassword(emailstr,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"account does not exist, please try again.",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Login failed, please Login again.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this,"error ocurred",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSingUp = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intSingUp);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}

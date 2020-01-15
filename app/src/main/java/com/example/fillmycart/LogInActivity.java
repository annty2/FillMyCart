package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    EditText email,pass;
    Button btnSignin;
    TextView tvSignup;
    String emailstr, password;
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


        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSingUp = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intSingUp);
            }
        });

    }

    public void signIn (View v){

        emailstr = email.getText().toString();
        password = pass.getText().toString();
        if (emailstr.isEmpty()) {
            email.setError("please enter email..");
            email.requestFocus();
        }
        else if(password.isEmpty()) {
            pass.setError("please enter password..");
            pass.requestFocus();
        }
        else if(emailstr.isEmpty() && password.isEmpty()) {
            Toast.makeText(LoginActivity.this,"fields are empty!", Toast.LENGTH_SHORT);
        }
        else if(!emailstr.isEmpty() && !password.isEmpty()){
           mAuth.signInWithEmailAndPassword(emailstr,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "account does not exist, please try again.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(emailstr.equals("danivngopro@gmail.com")||emailstr.equals("anna@gmail.com")||emailstr.equals("avi@gmail.com")){
                            Toast.makeText(LoginActivity.this, "Hello, Welcome Back!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, MangerActionActivity.class);
                            i.putExtra("email",email.getText().toString().split("@")[0]);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "you are logged in", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, CartListActivity.class);
                            i.putExtra("email",email.getText().toString().split("@")[0]);
                            startActivity(i);
                        }
                    }
                }
            });
        }
        else {
            Toast.makeText(LoginActivity.this,"error ocurred",Toast.LENGTH_SHORT).show();
        }
    }

}

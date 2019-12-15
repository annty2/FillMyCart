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

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    EditText email,pass;
    Button btnSignUp;
    TextView tvSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailid);
        pass = findViewById((R.id.passid));
        btnSignUp = findViewById(R.id.btn_signup);
        tvSignIn = findViewById(R.id.link_login);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(SignupActivity.this,"fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!emailstr.isEmpty() && !password.isEmpty()){
                    mAuth.createUserWithEmailAndPassword(emailstr,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignupActivity.this,"signup unsuccessful,please try again!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignupActivity.this,"error ocurred",Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


    }
}

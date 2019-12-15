package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class test extends AppCompatActivity {
    Button LogOut;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStatelistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        LogOut = findViewById(R.id.btnLogOut);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(test.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

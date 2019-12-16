package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    Button LogOut;
    Button pluss;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStatelistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        /*recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter= new MyAdapter(this, getMyList());
        recyclerView.setAdapter(myAdapter);*/


        pluss = findViewById(R.id.plus);

        pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartListActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        LogOut = findViewById(R.id.logOutButton);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CartListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList<Model> getMyList(){

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("News Feed");
        m.setPrice("20$");
        models.add(m);

        return models;


    }

}

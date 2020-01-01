package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    MyAdapter myAdapter;
    Button LogOut;
    Button pluss;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStatelistener;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        email = getIntent().getStringExtra("email").split("@")[0];
//        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        mrecyclerView = (RecyclerView) findViewById(R.id.recylerview_pending);
        new FirebaseDatabaseHelperPersonalList(email).readPending(new FirebaseDatabaseHelperPersonalList.DataStatus() {
            @Override
            public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {
                //here i need to create another config file with only delete option and maybe add number of products.
                new PersonalListRecyclerView_Config().setConfig(email, mrecyclerView,CartListActivity.this,pendingProducts,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        pluss = findViewById(R.id.plus);

        pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartListActivity.this, ProductsActivity.class);
                intent.putExtra("email",email);
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

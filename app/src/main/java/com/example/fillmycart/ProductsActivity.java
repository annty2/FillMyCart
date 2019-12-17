package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.view.View;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    Button goBack, addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter= new MyAdapter(this, getMyList());
        recyclerView.setAdapter(myAdapter);

        goBack= (Button)findViewById(R.id.goBackButton);
        addProduct= (Button)findViewById(R.id.addProductButton);

    }

    public void backToList (View v){
        Intent intent = new Intent(ProductsActivity.this ,CartListActivity.class);
        startActivity(intent);
    }

    public void addItem (View v){
        Intent intent = new Intent(ProductsActivity.this ,UserAddProduct.class);
        startActivity(intent);
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

package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProductsActivity extends AppCompatActivity {


    Button goBack, addProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products);


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


}

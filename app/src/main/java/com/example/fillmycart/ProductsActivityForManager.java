package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class ProductsActivityForManager extends AppCompatActivity {


    Button goBack, addProduct;
    private RecyclerView mrecyclerView;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products_for_manager);
        email = getIntent().getStringExtra("email");

        goBack= (Button)findViewById(R.id.goBackButton);
        addProduct= (Button)findViewById(R.id.addProductButton);
        mrecyclerView = (RecyclerView) findViewById(R.id.recylerview_pending);
        new FirebaseDatabaseHelperUsers().readPending(new FirebaseDatabaseHelperUsers.DataStatus() {
            @Override
            public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {
                new UsersRecyclerView_Config().setConfig(email,mrecyclerView,ProductsActivityForManager.this,pendingProducts,keys);
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

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivityForManager.this ,CarListActivityManager.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivityForManager.this ,UserAddProductForManager.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


    }


}

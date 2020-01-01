package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private Button backbtn;
    private RecyclerView mrecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        backbtn = (Button) findViewById(R.id.bkbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, MangerActionActivity.class);
                startActivity(intent);
            }
        });
        mrecyclerView = (RecyclerView) findViewById(R.id.recylerview_pending);

        new FirebaseDatabaseHelperProducts().readPending(new FirebaseDatabaseHelperProducts.DataStatus() {
            @Override
            public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {
                new ProductRecyclerView_Config().setConfig(mrecyclerView,ProductListActivity.this,pendingProducts,keys);
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


    }
}

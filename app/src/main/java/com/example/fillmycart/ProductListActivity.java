package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// Manager- manage products

public class ProductListActivity extends AppCompatActivity {
    private Button backbtn, deleteSelectedButton;
    private String email;
    private RecyclerView mrecyclerView;

    //NotificationHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        backbtn = (Button) findViewById(R.id.bkbtn);
        email = getIntent().getStringExtra("email");
        //helper = new NotificationHelper(this);
        deleteSelectedButton= (Button)findViewById(R.id.deleteSelectedButton);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, MangerActionActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        mrecyclerView = (RecyclerView) findViewById(R.id.recylerview_pending);




        new FirebaseDatabaseHelperProducts().readPending(new FirebaseDatabaseHelperProducts.DataStatus() {
            @Override
            public void DataIsLoaded(final List<PendingProduct> pendingProducts, final List<String> keys) {
                ProductRecyclerView_Config.PendingAdapter p= new ProductRecyclerView_Config().setConfig(mrecyclerView,ProductListActivity.this,pendingProducts,keys);

                final ArrayList<PendingProduct> selected= p.getSelectedProducts();


                deleteSelectedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = " ";

                        for (final PendingProduct s : selected) {
                            for (int i = 0; i < pendingProducts.size(); i++) {
                                if (pendingProducts.get(i).getName().equals(s.getName())) {
                                    key = keys.get(i);
                                }


                                    new FirebaseDatabaseHelperProducts().deletePending(key, new FirebaseDatabaseHelperProducts.DataStatus() {
                                        @Override
                                        public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {

                                        }

                                        @Override
                                        public void DataIsInserted() {

                                        }

                                        @Override
                                        public void DataIsUpdated() {

                                        }

                                        @Override
                                        public void DataIsDeleted() {
                                            String productName = s.getName();
                                            Toast.makeText(ProductListActivity.this, "deleted item: " + productName, Toast.LENGTH_LONG).show();
                                            /*Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", productName+" deleted successfully!");
                                            helper.getManager().notify(new Random().nextInt(), builder.build());*/
                                            return;
                                        }
                                    });
                                }


                        }
                        if (key.equals(" ")){
                            Toast.makeText(ProductListActivity.this, "No items were checked", Toast.LENGTH_LONG).show();
                        }
                    }
                });





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

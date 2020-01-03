package com.example.fillmycart;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;

public class ProductsDetailsActivity extends AppCompatActivity {

    private DatabaseReference myRef;

    private EditText Category;
    private EditText pname;
    private EditText pPrice;

    private Button updatebtn;
    private Button deletebtn;
    private Button backbth;

    private String category;
    private String name;
    private String pprice;
    private String key;
    NotificationHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        myRef = FirebaseDatabase.getInstance().getReference("Products");

        key = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("mCategory");
        name = getIntent().getStringExtra("mProductname");
        pprice = getIntent().getStringExtra("mprice");

        Category = (EditText) findViewById(R.id.pcategory);
        Category.setText(category.split(": ")[1]);
        pname = (EditText) findViewById(R.id.pname);
        pname.setText(name);
        pPrice = (EditText) findViewById(R.id.pprice);
        pPrice.setText(pprice.split(": ")[1]);

        updatebtn = (Button) findViewById(R.id.updateproductpending);
        deletebtn = (Button) findViewById(R.id.DeclineProduct);
        backbth = (Button) findViewById(R.id.Backbtn);

        helper = new NotificationHelper(this);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingProduct pendingProduct = new PendingProduct();
                pendingProduct.setName(pname.getText().toString());
                pendingProduct.setPrice(pPrice.getText().toString());
                pendingProduct.setCategory(Category.getText().toString());

                new FirebaseDatabaseHelperProducts().updatePending(key, pendingProduct, new FirebaseDatabaseHelperProducts.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(ProductsDetailsActivity.this, "This item has been updated successfuly!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        String productName= pname.getText().toString();

                        Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", productName+" deleted successfully!");
                        helper.getManager().notify(new Random().nextInt(), builder.build());
                        finish();return;
                    }
                });
            }
        });
        backbth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();return;
            }
        });


    }

}

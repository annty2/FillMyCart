package com.example.fillmycart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PendingDetailsActivity extends AppCompatActivity {

    private DatabaseReference myRef;

    private TextView updateTextView;
    private TextView categoryTextView;
    private TextView productName;
    private TextView priceTextView;


    private EditText Category;
    private EditText pname;
    private EditText pPrice;


    private Button updatebtn;
    private Button backbth;


    private String category;
    private String name;
    private String pprice;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_details);

        updateTextView = (TextView)findViewById(R.id.updateTextView);
        categoryTextView = (TextView)findViewById(R.id.category);
        productName = (TextView)findViewById(R.id.productName);
        priceTextView = (TextView)findViewById(R.id.priceTextView);

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
        backbth = (Button) findViewById(R.id.Backbtn);




        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingProduct pendingProduct = new PendingProduct();
                pendingProduct.setName(pname.getText().toString());
                pendingProduct.setPrice(pPrice.getText().toString());
                pendingProduct.setCategory(Category.getText().toString());

                new FirebaseDatabaseHelper().updatePending(key, pendingProduct, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(PendingDetailsActivity.this, "This item has been updated successfuly!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

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

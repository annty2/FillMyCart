package com.example.fillmycart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity {

    String category, productName, price;

    EditText categoryEditText, productEditText, priceEditText;
    Button addButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        categoryEditText= (EditText)findViewById(R.id.category_name);
        productEditText= (EditText)findViewById(R.id.product_name);
        priceEditText= (EditText)findViewById(R.id.product_price);
        addButton= (Button) findViewById(R.id.addButton);

        category= categoryEditText.getText().toString().trim();
        productName= productEditText.getText().toString().trim();
        price= priceEditText.getText().toString().trim();


        mFirebaseDatabase= FirebaseDatabase.getInstance();
        myRef=FirebaseDatabase.getInstance().getReference();
        product= new Product(category,productName,price);




    }

    public void insert (View view){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("Products")) {
                  
                    Toast.makeText(AddProductActivity.this, "Added to firebase successfully!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddProductActivity.this, "Not added to firebase!",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

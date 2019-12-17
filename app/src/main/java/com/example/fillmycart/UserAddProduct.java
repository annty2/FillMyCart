package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAddProduct extends AppCompatActivity {

    EditText categoryEditText, productEditText, priceEditText;
    Button addButton, returnToProducts;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_product);

        categoryEditText= (EditText)findViewById(R.id.category_name);
        productEditText= (EditText)findViewById(R.id.product_name);
        priceEditText= (EditText)findViewById(R.id.product_price);
        addButton= (Button) findViewById(R.id.addButton);
        returnToProducts=(Button) findViewById(R.id.returnButton);

        Firebase.setAndroidContext(this);
        mFirebaseDatabase= FirebaseDatabase.getInstance();

        returnToProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAddProduct.this , ProductsActivity.class);
                startActivity(intent);
            }
        });

    }

    //when add is pressed, add the product by category
    public void insert (View v){
        Toast.makeText(UserAddProduct.this, "Add button pressed",Toast.LENGTH_SHORT).show();

        //check if one of the fields is empty' if it is send a toast
        if(categoryEditText.getText().toString().equals(""))
            Toast.makeText(UserAddProduct.this, "Category is null!",
                    Toast.LENGTH_LONG).show();
        else if(productEditText.getText().toString().equals(""))
            Toast.makeText(UserAddProduct.this, "Product name is null!",
                    Toast.LENGTH_LONG).show();
        else if(priceEditText.getText().toString().equals(""))
            Toast.makeText(UserAddProduct.this, "Price is null!",
                    Toast.LENGTH_LONG).show();

        else {
            //add to firebase
            myRef = FirebaseDatabase.getInstance().getReference().child("Pending").child("Products").child(categoryEditText.getText().toString())
                    .child(productEditText.getText().toString());
            myRef.child("Price").setValue(priceEditText.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UserAddProduct.this,"Item added! Item will be confirmed soon!",
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(UserAddProduct.this,"Failed adding item",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

}




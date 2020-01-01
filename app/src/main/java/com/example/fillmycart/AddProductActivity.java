package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {

    EditText categoryEditText, productEditText, priceEditText;
    Button addButton, returnToMenu;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        categoryEditText= (EditText)findViewById(R.id.category_name);
        productEditText= (EditText)findViewById(R.id.product_name);
        priceEditText= (EditText)findViewById(R.id.product_price);
        addButton= (Button) findViewById(R.id.addButton);
        returnToMenu=(Button) findViewById(R.id.returnButton);
        myRef = FirebaseDatabase.getInstance().getReference("Products");
        Firebase.setAndroidContext(this);

        returnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProductActivity.this , MangerActionActivity.class);
                startActivity(intent);
            }
        });

    }


    //when add is pressed, add the product by category
    public void insert (View v){
       // Toast.makeText(AddProductActivity.this, "The button was pressed", Toast.LENGTH_SHORT).show();

            //check if one of the fields is empty' if it is send a toast
            if(categoryEditText.getText().toString().equals(""))
                Toast.makeText(AddProductActivity.this, "Category is null!",
                        Toast.LENGTH_LONG).show();
            else if(productEditText.getText().toString().equals(""))
                Toast.makeText(AddProductActivity.this, "Product name is null!",
                        Toast.LENGTH_LONG).show();
            else if(priceEditText.getText().toString().equals(""))
                Toast.makeText(AddProductActivity.this, "Price is null!",
                        Toast.LENGTH_LONG).show();

            else {
                //add to firebase
                Product p = new Product(categoryEditText.getText().toString(),productEditText.getText().toString(),priceEditText.getText().toString());
                myRef.push().setValue(p)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddProductActivity.this,"Item was added successfully!!",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(AddProductActivity.this,"Failed adding item",
                                        Toast.LENGTH_LONG).show();
                            }
                        });


                //clear the text from the fields
                categoryEditText.getText().clear();
                productEditText.getText().clear();
                priceEditText.getText().clear();
            }
        }


}

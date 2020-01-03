package com.example.fillmycart;

import android.app.Notification;
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

import java.util.Random;

public class UserAddProductForManager extends AppCompatActivity {

    EditText categoryEditText, productEditText, priceEditText;
    Button addButton, returnToMenu;

    private DatabaseReference myRef;
    private String email;
    NotificationHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_product_for_manager);

        categoryEditText= (EditText)findViewById(R.id.category_name);
        productEditText= (EditText)findViewById(R.id.product_name);
        priceEditText= (EditText)findViewById(R.id.product_price);
        addButton= (Button) findViewById(R.id.addButton);
        returnToMenu=(Button) findViewById(R.id.returnButton);
        myRef = FirebaseDatabase.getInstance().getReference("Products");
        Firebase.setAndroidContext(this);
        email = getIntent().getStringExtra("email");
        helper = new NotificationHelper(this);

        returnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAddProductForManager.this , CarListActivityManager.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

    }


    //when add is pressed, add the product by category
    public void insert (View v){
        // Toast.makeText(AddProductActivity.this, "The button was pressed", Toast.LENGTH_SHORT).show();

        //check if one of the fields is empty' if it is send a toast
        if(categoryEditText.getText().toString().equals(""))
            Toast.makeText(UserAddProductForManager.this, "Category is null!",
                    Toast.LENGTH_LONG).show();
        else if(productEditText.getText().toString().equals(""))
            Toast.makeText(UserAddProductForManager.this, "Product name is null!",
                    Toast.LENGTH_LONG).show();
        else if(priceEditText.getText().toString().equals(""))
            Toast.makeText(UserAddProductForManager.this, "Price is null!",
                    Toast.LENGTH_LONG).show();

        else {
            //add to firebase
            Product p = new Product(categoryEditText.getText().toString(),productEditText.getText().toString(),priceEditText.getText().toString());
            myRef.push().setValue(p)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", "Item added successfully!wait for managers to approve");
                            helper.getManager().notify(new Random().nextInt(), builder.build());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", "Item not added!");
                            helper.getManager().notify(new Random().nextInt(), builder.build());
                        }
                    });


            //clear the text from the fields
            categoryEditText.getText().clear();
            productEditText.getText().clear();
            priceEditText.getText().clear();
        }
    }


}

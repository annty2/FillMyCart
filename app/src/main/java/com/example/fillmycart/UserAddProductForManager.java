package com.example.fillmycart;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class UserAddProductForManager extends AppCompatActivity {

    EditText categoryEditText, productEditText, priceEditText;
    Button addButton, returnToMenu;
    private ImageView btnspeak1, btnspeak2;
    private int identifier1=0, identifier2=0;
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
        btnspeak1= (ImageView) findViewById(R.id.btnSpeak);
        btnspeak2= (ImageView) findViewById(R.id.btnSpeak2);
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
        btnspeak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifier1 = 1;
                getSpeechInput(view);
            }
        });

        btnspeak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifier2 = 1;
                getSpeechInput(view);
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
                            openDialog();
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
    public void openDialog() {
        DialogAddtoGlobalList dialogAddtoGlobalList = new DialogAddtoGlobalList();
        dialogAddtoGlobalList.show(getSupportFragmentManager(),"item has been added");
    }
    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    if(identifier1 == 1) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        categoryEditText.setText(result.get(0));
                        identifier1 = 0;
                    }
                    else if (identifier2 == 1) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        productEditText.setText(result.get(0));
                        identifier2 = 0;
                    }
                }
                break;
        }
    }

}

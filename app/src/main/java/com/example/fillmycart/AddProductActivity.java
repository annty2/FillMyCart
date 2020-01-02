package com.example.fillmycart;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class AddProductActivity extends AppCompatActivity {

    ImageView productImg;
    EditText categoryEditText, productEditText, priceEditText;
    Button addButton, returnToMenu;
    String productKey, imgUrl;
    private Uri imgUri;
    private static final int SELECTED_IMAGE_CODE = 1;

    private DatabaseReference myRef;
    private StorageReference productImgRef;
    private ProgressBar progressBar;

    NotificationHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productImg = (ImageView) findViewById(R.id.productImage);
        categoryEditText= (EditText)findViewById(R.id.category_name);
        productEditText= (EditText)findViewById(R.id.product_name);
        priceEditText= (EditText)findViewById(R.id.product_price);
        addButton= (Button) findViewById(R.id.addButton);
        returnToMenu=(Button) findViewById(R.id.returnButton);
        myRef = FirebaseDatabase.getInstance().getReference("Products");
        productImgRef = FirebaseStorage.getInstance().getReference("Images");

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        helper = new NotificationHelper(this);


        returnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProductActivity.this , MangerActionActivity.class);
                startActivity(intent);
            }
        });


        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromGallery();
            }
        });

    }


    public void chooseImageFromGallery() {

        //intent to pick an image from gallery
        Intent pickImageIntent = new Intent();
        pickImageIntent.setType("image/*");      //show only images
        pickImageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickImageIntent, SELECTED_IMAGE_CODE);

    }


    //after that we can load image from the gallery phone to the imageview
    protected void onActivityResult(int requestCode, int resultCode, Intent selectedImage) {
        super.onActivityResult(requestCode, resultCode, selectedImage);

        //check image request,manager choose image successfully and the data that return not null
        if (requestCode == SELECTED_IMAGE_CODE && resultCode == RESULT_OK
                && selectedImage != null){

            //contain image  uri
            imgUri = selectedImage.getData();

            //load image uri to the imagview
            Picasso.with(this).load(imgUri).into(productImg);
        }

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
                timeAndDate();
                uploadImage();
                addInfoToDB();

                //clear the text from the fields
                categoryEditText.getText().clear();
                productEditText.getText().clear();
                priceEditText.getText().clear();
            }
    }


    //handle time and date that the product added by the manager
    private void timeAndDate() {

        progressBar.setVisibility(View.VISIBLE);

        Calendar cal = Calendar.getInstance();

        //date
        SimpleDateFormat date = new SimpleDateFormat("MMM dd, yyyy");
        String currentDate = date.format(cal.getTime());

        //time
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss a");
        String currentTime = time.format(cal.getTime());

        //unique date and time for each product(for Firebase)
        productKey = currentDate + " " + currentTime;

    }




    //upload product image to storage
    private void uploadImage(){

        //save with unique key
        final StorageReference imgRef = productImgRef.child(imgUri.getLastPathSegment()+productKey+".jpg");

        final UploadTask uploadTask = imgRef.putFile(imgUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProductActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProductActivity.this, "Upload img successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> url = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        imgUrl = imgRef.getDownloadUrl().toString();
                        return imgRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            imgUrl = task.getResult().toString();
                            addInfoToDB();
                        }
                    }
                });
            }
        });
    }




    //add product info to db
    private void addInfoToDB(){

        //product info
        HashMap<String,Object> productInfo = new HashMap<>();
        productInfo.put("productID", productKey);
        productInfo.put("Category", categoryEditText.getText().toString());
        productInfo.put("Name", productEditText.getText().toString());
        productInfo.put("Price", priceEditText.getText().toString());
        productInfo.put("Image",imgUrl);

        //add to firebase
        myRef.child(productKey).updateChildren(productInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.GONE);
                        Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", "Item added successfully!");
                        helper.getManager().notify(new Random().nextInt(), builder.build());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", "Item not added!");
                        helper.getManager().notify(new Random().nextInt(), builder.build());
                    }
                });

    }



}

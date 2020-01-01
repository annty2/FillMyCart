package com.example.fillmycart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PersonalListDetailsActivity extends AppCompatActivity {

    private DatabaseReference myRef;

    private EditText Category;
    private EditText pname;
    private EditText pPrice;

    private Button delitem;
    private Button backbth;

    private String category;
    private String name;
    private String pprice;
    private String key;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_list_details);

        myRef = FirebaseDatabase.getInstance().getReference("Products");

        key = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("mCategory");
        name = getIntent().getStringExtra("mProductname");
        pprice = getIntent().getStringExtra("mprice");
        email = getIntent().getStringExtra("email");

        Category = (EditText) findViewById(R.id.pcategory);
        Category.setText(category.split(": ")[1]);
        pname = (EditText) findViewById(R.id.pname);
        pname.setText(name);
        pPrice = (EditText) findViewById(R.id.pprice);
        pPrice.setText(pprice.split(": ")[1]);

        delitem = (Button) findViewById(R.id.delItem);
        backbth = (Button) findViewById(R.id.Backbtn);

        delitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FirebaseDatabaseHelperPersonalList(email).deletePending(key, new FirebaseDatabaseHelperPersonalList.DataStatus() {
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
                        Toast.makeText(PersonalListDetailsActivity.this, "item Deleted Successfuly", Toast.LENGTH_SHORT).show();
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

package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class PendingList_Activity extends AppCompatActivity {
    private Button backbtn;
    private String email;
    private RecyclerView mrecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_list_);

        email = getIntent().getStringExtra("email");
        backbtn = (Button) findViewById(R.id.bkbtn);

        //go back to action screen
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PendingList_Activity.this, MangerActionActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        mrecyclerView = (RecyclerView) findViewById(R.id.recylerview_pending);

        new FirebaseDatabaseHelper().readPending(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {
                new PendingRecyclerView_Config().setConfig(mrecyclerView,PendingList_Activity.this,pendingProducts,keys);
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

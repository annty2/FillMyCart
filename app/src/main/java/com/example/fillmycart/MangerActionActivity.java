package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MangerActionActivity extends AppCompatActivity {

    private Button LogOut;
    private Button _approveAdmin,_add,_productApprove,_productUpdate,_list;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_action);

        _add = (Button)findViewById(R.id.addButton);
        _productApprove = (Button)findViewById(R.id.approveButton);
        _productUpdate = (Button)findViewById(R.id.updateButton);
        _list = (Button)findViewById(R.id.makeListButton);
        email = getIntent().getStringExtra("email");
        LogOut = findViewById(R.id.lgoutbtn);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MangerActionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        _add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , AddProductActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        _list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , CarListActivityManager.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        _productApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , PendingList_Activity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        _productUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , ProductListActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });


    }
}

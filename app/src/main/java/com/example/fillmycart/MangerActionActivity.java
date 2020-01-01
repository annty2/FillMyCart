package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MangerActionActivity extends AppCompatActivity {

    private Button _approveAdmin,_add,_productApprove,_productUpdate,_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_action);

        _approveAdmin = (Button)findViewById(R.id.approveAdminButton);
        _add = (Button)findViewById(R.id.addButton);
        _productApprove = (Button)findViewById(R.id.approveButton);
        _productUpdate = (Button)findViewById(R.id.updateButton);
        _list = (Button)findViewById(R.id.makeListButton);


        _add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , AddProductActivity.class);
                startActivity(intent);
            }
        });

        _list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , CartListActivity.class);
                startActivity(intent);
            }
        });

        _productApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , PendingList_Activity.class);
                startActivity(intent);
            }
        });

    }
}

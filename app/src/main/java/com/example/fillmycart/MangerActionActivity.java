package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MangerActionActivity extends AppCompatActivity {

    private Button _remove,_add,_productApprove,_priceUpdate,_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_action);

        _remove = (Button)findViewById(R.id.deleteButton);
        _add = (Button)findViewById(R.id.addButton);
        _productApprove = (Button)findViewById(R.id.approveButton);
        _priceUpdate = (Button)findViewById(R.id.updateButton);
        _list = (Button)findViewById(R.id.makeListButton);


        _add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , AddProductActivity.class);
                startActivity(intent);
            }
        });



    }
}

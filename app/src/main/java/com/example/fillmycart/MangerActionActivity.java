package com.example.fillmycart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MangerActionActivity extends AppCompatActivity {

    private Button _remove,_add,_sales,_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_action);

        _remove = (Button)findViewById(R.id.btn_remove);
        _add = (Button)findViewById(R.id.btn_add);
        _sales = (Button)findViewById(R.id.btn_sale);
        _price = (Button)findViewById(R.id.btn_price);


        _add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangerActionActivity.this , ProductsActivity.class);
                startActivity(intent);
            }
        });

    }
}

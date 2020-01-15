package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//list of products to add to list- User

public class ProductsActivity extends AppCompatActivity {


    Button goBack, addProduct, addToList;

    private RecyclerView mrecyclerView;
    private String email;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products);
        email = getIntent().getStringExtra("email");

        goBack= (Button)findViewById(R.id.goBackButton);
        addProduct= (Button)findViewById(R.id.addProductButton);
        addToList= (Button)findViewById(R.id.addToListButton);





        mrecyclerView = (RecyclerView) findViewById(R.id.recylerview_pending);


            new FirebaseDatabaseHelperUsers().readPending(new FirebaseDatabaseHelperUsers.DataStatus() {
            @Override
            public void DataIsLoaded(final List<PendingProduct> pendingProducts, final List<String> keys) {
                UsersRecyclerView_Config.PendingAdapter p= new UsersRecyclerView_Config().setConfig(email,mrecyclerView,ProductsActivity.this,pendingProducts,keys);

                final ArrayList<PendingProduct> selected= p.getSelectedProducts();


               addToList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key= " ";

                        for (final PendingProduct s : selected) {
                            s.setSelected(false);
                            for (int i = 0; i < pendingProducts.size(); i++) {
                                if (pendingProducts.get(i).getName().equals(s.getName())) {
                                    key = keys.get(i);

                                }




                                new FirebaseDatabaseHelperPersonalList(email).updatePending(key,s, new FirebaseDatabaseHelperPersonalList.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys) {



                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                        String productName= s.getName();
                                        selected.remove(s);

                                        openDialog();

                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
                            }


                        }
                        if (key.equals(" ")){
                            Toast.makeText(ProductsActivity.this, "No items were checked", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivity.this ,CartListActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivity.this ,UserAddProduct.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


    }

    public void openDialog() {
        DialogItemAdded dialogItemAdded = new DialogItemAdded();
        dialogItemAdded.show(getSupportFragmentManager(),"items added to list");
    }


}

package com.example.fillmycart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CartListActivity extends AppCompatActivity {

    // The personal product list - User

    private RecyclerView mrecyclerView;
    MyAdapter myAdapter;
    Button LogOut;
    Button pluss;
    Button buy;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStatelistener;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        email = getIntent().getStringExtra("email");

        mrecyclerView = (RecyclerView) findViewById(R.id.recylerview_pending);
        buy= (Button)findViewById(R.id.buyButton);




        new FirebaseDatabaseHelperPersonalList(email).readPending(new FirebaseDatabaseHelperPersonalList.DataStatus() {
            @Override
            public void DataIsLoaded(final List<PendingProduct> pendingProducts, final List<String> keys) {

                PersonalListRecyclerView_Config.PendingAdapter p = new PersonalListRecyclerView_Config().setConfig(email, mrecyclerView,CartListActivity.this,pendingProducts,keys);



                final ArrayList<PendingProduct> selected= p.getSelectedProducts();


                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = " ";

                        for (final PendingProduct s : selected) {
                            for (int i = 0; i < pendingProducts.size(); i++) {
                                if (pendingProducts.get(i).getName().equals(s.getName())) {
                                    key = keys.get(i);
                                }


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
                                        String productName = s.getName();
                                        Toast.makeText(CartListActivity.this, "deleted item: " + productName, Toast.LENGTH_LONG).show();
                                            /*Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", productName+" deleted successfully!");
                                            helper.getManager().notify(new Random().nextInt(), builder.build());*/
                                        return;

                                    }


                                });
                            }


                        }
                        if (key.equals(" ")){
                            Toast.makeText(CartListActivity.this, "No items were checked", Toast.LENGTH_LONG).show();
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

        pluss = findViewById(R.id.plus);

        pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartListActivity.this, ProductsActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        LogOut = findViewById(R.id.logOutButton);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CartListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    }



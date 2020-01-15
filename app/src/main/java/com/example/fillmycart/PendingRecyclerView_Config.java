package com.example.fillmycart;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


//Unite adapter and holder so we can see recyclerview
public class PendingRecyclerView_Config {
    private Context context;
    private PendingAdapter mpendingAdapter;


    public void setConfig(RecyclerView recyclerView, Context mcontext, List<PendingProduct> pendingProducts, List<String> keys){
        context = mcontext;
        mpendingAdapter = new PendingAdapter(pendingProducts,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(mpendingAdapter);
    }



    //holder for the view of one recyclerview item
    class PendingItemView extends RecyclerView.ViewHolder {
        public TextView mProductname;
        public TextView mPrice;
        public TextView mCategory;
        private TextView buttonViewOption;


        public String key;

        public PendingItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.pending_list_item,parent,false));

            mProductname = (TextView) itemView.findViewById(R.id.productname);
            mPrice = (TextView) itemView.findViewById(R.id.price);
            mCategory = (TextView) itemView.findViewById(R.id.mCategory);
            buttonViewOption= (TextView)itemView.findViewById(R.id.textViewOptions);

        }

        public void bind(PendingProduct pendingProduct, String key) {
            mProductname.setText(pendingProduct.getName());
            mPrice.setText("Price: " + pendingProduct.getPrice());
            mCategory.setText("Category: " + pendingProduct.getCategory());
            this.key = key;
        }

        public String getmProductname() {
            String s= mProductname.getText().toString();
            return s;
        }

        public void setmProductname(TextView mProductname) {
            this.mProductname = mProductname;
        }

        public String getmPrice() {
            String s= mPrice.getText().toString();
            return s;
        }

        public void setmPrice(TextView mPrice) {
            this.mPrice = mPrice;
        }

        public String getmCategory() {
            String s= mCategory.getText().toString();
            return s;
        }

        public void setmCategory(TextView mCategory) {
            this.mCategory = mCategory;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }



    //adapter for pending product
    class PendingAdapter extends RecyclerView.Adapter<PendingItemView>{
        private List<PendingProduct> mpendingProducts;
        private List<String> mkeys;

        public PendingAdapter(List<PendingProduct> mpendingProducts, List<String> mkeys) {
            this.mpendingProducts = mpendingProducts;
            this.mkeys = mkeys;
        }

        @NonNull
        @Override
        public PendingItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PendingItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull final PendingItemView pendingItemView, int i) {
            pendingItemView.bind(mpendingProducts.get(i), mkeys.get(i));

            pendingItemView.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu popup = new PopupMenu(context, pendingItemView.buttonViewOption);

                    popup.inflate(R.menu.popup_menu_manage_products);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.add:
                                    //add item to the general product list

                                    PendingProduct pendingProduct = new PendingProduct();
                                    pendingProduct.setName(pendingItemView.getmProductname());
                                    pendingProduct.setPrice(pendingItemView.getmPrice());
                                    pendingProduct.setCategory(pendingItemView.getmCategory());


                                    new FirebaseDatabaseHelper().addProduct(pendingItemView.getKey(), pendingProduct, new FirebaseDatabaseHelper.DataStatus() {
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
                                            return;
                                        }
                                    });


                                    break;
                                case R.id.delete:
                                    //delete item from the list and deny it

                                    new FirebaseDatabaseHelper().deletePending(pendingItemView.getKey(), new FirebaseDatabaseHelper.DataStatus() {
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
                                            Log.i("DELETE", " item deleted");

                                            return;
                                                }
                                            });


                                    break;




                                case R.id.update:
                                    //go to another screen and update the product

                                   Intent i = new Intent(context, PendingDetailsActivity.class);
                                    i.putExtra("key", pendingItemView.getKey());
                                    i.putExtra("mProductname",pendingItemView.getmProductname());
                                    i.putExtra("mCategory",pendingItemView.getmCategory());
                                    i.putExtra("mprice", pendingItemView.getmPrice());

                                    context.startActivity(i);

                                    break;
                            }

                            return true;
                        }
                    });

                    popup.show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mpendingProducts.size();
        }
    }
}

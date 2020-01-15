package com.example.fillmycart;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerView_Config {
    private Context context;
    private PendingAdapter mpendingAdapter;



    public PendingAdapter setConfig(RecyclerView recyclerView, Context mcontext, List<PendingProduct> pendingProducts, List<String> keys){
        context = mcontext;
        mpendingAdapter = new PendingAdapter(pendingProducts,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(mpendingAdapter);

        return mpendingAdapter;

    }


    class PendingItemView extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        private TextView mProductname;
        private TextView mPrice;
        private TextView mCategory;
        private TextView buttonViewOption;
        private ElegantNumberButton addOrTake;

        private String key;





        // Holder
        public PendingItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.pending_list_item,parent,false));

            mProductname = (TextView) itemView.findViewById(R.id.productname);
            mPrice = (TextView) itemView.findViewById(R.id.price);
            mCategory = (TextView) itemView.findViewById(R.id.mCategory);
            buttonViewOption= (TextView)itemView.findViewById(R.id.textViewOptions);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            addOrTake= (ElegantNumberButton) itemView.findViewById(R.id.addOrTake);

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






    class PendingAdapter extends RecyclerView.Adapter<PendingItemView>{
        private List<PendingProduct> mpendingProducts;
        private List<String> mkeys;
        ArrayList<PendingProduct> selectedProducts;

        public PendingAdapter(List<PendingProduct> mpendingProducts, List<String> mkeys) {
            this.mpendingProducts = mpendingProducts;
            this.mkeys = mkeys;
            this.selectedProducts= new ArrayList<PendingProduct>();
        }

        public ArrayList<PendingProduct> getSelectedProducts() {
            return selectedProducts;
        }

        public void setSelectedProducts(ArrayList<PendingProduct> selectedProducts) {
            this.selectedProducts = selectedProducts;
        }

        @NonNull
        @Override
        public PendingItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PendingItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull final PendingItemView pendingItemView, final int i) {
            pendingItemView.bind(mpendingProducts.get(i), mkeys.get(i));

            final PendingProduct pProduct  = mpendingProducts.get(i);


            pendingItemView.checkBox.setChecked(pProduct.isSelected());
            pendingItemView.checkBox.setTag(mpendingProducts.get(i));


            pendingItemView.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PendingProduct p1= (PendingProduct) pendingItemView.checkBox.getTag();

                    p1.setSelected(pendingItemView.checkBox.isChecked());

                    mpendingProducts.get(i).setSelected(pendingItemView.checkBox.isChecked());


                        if (mpendingProducts.get(i).isSelected() == true) {

                            selectedProducts.add(mpendingProducts.get(i));

                            //Log.i("selected items", " " + mpendingProducts.get(j).getName());
                        }
                        if(mpendingProducts.get(i).isSelected()== false){
                            selectedProducts.remove(mpendingProducts.get(i));
                        }
                        setSelectedProducts(selectedProducts);


                    /*String s= " ";
                    for(int k=0; k<selectedProducts.size();k++){

                        s=s+ " \n"+ selectedProducts.get(k).getName();
                    }
                    Toast.makeText(context, "Selected Products: \n"+s, Toast.LENGTH_LONG).show();*/
                }
            });

            pendingItemView.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu popup = new PopupMenu(context, pendingItemView.buttonViewOption);

                    popup.inflate(R.menu.popup_menu_delete_or_update);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    //delete item from the list and deny it

                                    new FirebaseDatabaseHelperProducts().deletePending(pendingItemView.getKey(), new FirebaseDatabaseHelperProducts.DataStatus() {
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
                                            String productName= pendingItemView.getmProductname();

                                           /* Notification.Builder builder = helper.getEDMTChannelNotification("Fill My Cart", productName+" deleted successfully!");
                                            helper.getManager().notify(new Random().nextInt(), builder.build());*/
                                            return;
                                        }
                                    });



                                    break;




                                case R.id.update:
                                    //go to another screen and update the product

                                    Intent i = new Intent(context, ProductsDetailsActivity.class);
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

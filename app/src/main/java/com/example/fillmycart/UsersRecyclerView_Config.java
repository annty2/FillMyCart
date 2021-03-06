package com.example.fillmycart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;


//Config for list of all products

public class UsersRecyclerView_Config {
    private Context context;
    private PendingAdapter mpendingAdapter;
    String str;

    public UsersRecyclerView_Config.PendingAdapter setConfig(String email, RecyclerView recyclerView, Context mcontext, List<PendingProduct> pendingProducts, List<String> keys){
        context = mcontext;
        mpendingAdapter = new PendingAdapter(pendingProducts,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(mpendingAdapter);
        str = email;
        return mpendingAdapter;
    }

    //holder
    class PendingItemView extends RecyclerView.ViewHolder {
        private TextView mProductname;
        private TextView mPrice;
        private TextView mCategory;
        private ElegantNumberButton addOrTake;
        public CheckBox checkBox;

        private String key;

        public PendingItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.recycler_view_item,parent,false));

            mProductname = (TextView) itemView.findViewById(R.id.product);
            mPrice = (TextView) itemView.findViewById(R.id.product_description);
            mCategory = (TextView) itemView.findViewById(R.id.product_category);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkBox);
            addOrTake = (ElegantNumberButton) itemView.findViewById(R.id.addOrTake);


            //i need to make it so when the user taps the item it will be insterted to his list auto.
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, UsersDetailsActivity.class);
                    i.putExtra("key",key);
                    i.putExtra("mProductname",mProductname.getText().toString());
                    i.putExtra("mCategory", mCategory.getText().toString());
                    i.putExtra("mprice", mPrice.getText().toString());
                    i.putExtra("email", str);
                    context.startActivity(i);
                }
            });*/
        }

        public void bind(PendingProduct pendingProduct, String key) {
            mProductname.setText(pendingProduct.getName());
            mPrice.setText("Price: " + pendingProduct.getPrice());
            mCategory.setText("Category: " + pendingProduct.getCategory());
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






        }

        @Override
        public int getItemCount() {
            return mpendingProducts.size();
        }
    }
}

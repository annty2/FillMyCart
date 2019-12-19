package com.example.fillmycart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class PendingRecyclerView_Config {
    private Context context;
    private PendingAdapter mpendingAdapter;

    public void setConfig(RecyclerView recyclerView, Context mcontext, List<PendingProduct> pendingProducts, List<String> keys){
        context = mcontext;
        mpendingAdapter = new PendingAdapter(pendingProducts,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(mpendingAdapter);
    }


    class PendingItemView extends RecyclerView.ViewHolder {
        private TextView mProductname;
        private TextView mPrice;

        private String key;

        public PendingItemView(ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.pending_list_item,parent,false));

            mProductname = (TextView) itemView.findViewById(R.id.productname);
            mPrice = (TextView) itemView.findViewById(R.id.price);
        }

        public void bind(PendingProduct pendingProduct, String key) {
            mProductname.setText(pendingProduct.getProductName());
            mPrice.setText(pendingProduct.getPrice());
            this.key = key;
        }
    }
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
        public void onBindViewHolder(@NonNull PendingItemView pendingItemView, int i) {
            pendingItemView.bind(mpendingProducts.get(i), mkeys.get(i));
        }

        @Override
        public int getItemCount() {
            return mpendingProducts.size();
        }
    }
}

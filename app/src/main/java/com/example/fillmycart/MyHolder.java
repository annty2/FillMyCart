package com.example.fillmycart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title, price;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.productImage);
        this.title= itemView.findViewById(R.id.product);
        this.price = itemView.findViewById(R.id.product_description);

    }
}

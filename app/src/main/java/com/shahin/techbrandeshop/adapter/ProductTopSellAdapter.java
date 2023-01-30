package com.shahin.techbrandeshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.entity.Product;
import com.shahin.techbrandeshop.util.ImageConverterHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductTopSellAdapter extends RecyclerView.Adapter<ProductTopSellAdapter.ProductTopSellViewHolder> {

    private final Context context;
    private final List<Product> productList;

    public ProductTopSellAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @NotNull
    @Override
    public ProductTopSellViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View ProductTopSellView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_top_sell, parent, false);
        return new ProductTopSellViewHolder(ProductTopSellView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductTopSellViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productImg.setImageResource(
                ImageConverterHelper.getResourceIdFromString(context,
                        product.getProductImages().get(0)));
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getPriceFormat());
        holder.productRate.setText(String.valueOf(product.getRate()));
    }

    @Override
    public int getItemCount() {
        if (productList == null || productList.isEmpty()) return 0;
        return productList.size();
    }

    public static class ProductTopSellViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private final ImageView productImg;
        private final TextView productName, productPrice, productRate;

        public ProductTopSellViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.product_top_sell_container);
            productImg = itemView.findViewById(R.id.product_top_sell_img);
            productName = itemView.findViewById(R.id.product_top_sell_name);
            productPrice = itemView.findViewById(R.id.product_top_sell_price);
            productRate = itemView.findViewById(R.id.product_top_sell_rate);
        }
    }
}
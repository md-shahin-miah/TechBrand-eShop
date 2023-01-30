package com.shahin.techbrandeshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.activity.ContainerActivity;
import com.shahin.techbrandeshop.entity.Product;
import com.shahin.techbrandeshop.fragment.ProductDetailFragment;
import com.shahin.techbrandeshop.util.ImageConverterHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product, parent, false);
        return new ProductViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productImg.setImageResource(
                ImageConverterHelper.getResourceIdFromString(context,
                        product.getProductImages().get(0))
                );

        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getPriceFormat());

        if (product.isFreeShipping())
            holder.productFreeShipIcon.setVisibility(View.VISIBLE);

        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContainerActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable(ProductDetailFragment.PRODUCT_DETAIL_KEY, product);
            bundle.putInt(ContainerActivity.CONTAINER_TOOLBAR_MENU_MODE, 1);
            intent.putExtra(ContainerActivity.CONTAINER_KEY, bundle);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (productList == null || productList.isEmpty()) return 0;
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final CardView parent;
        private final ImageView productImg, productFreeShipIcon;
        private final TextView productName, productPrice;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.product_card_container);
            productImg = itemView.findViewById(R.id.product_img);
            productFreeShipIcon = itemView.findViewById(R.id.product_free_ship);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
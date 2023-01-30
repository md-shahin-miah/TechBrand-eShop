package com.shahin.techbrandeshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.entity.Product;
import com.shahin.techbrandeshop.fragment.ShoppingCartFragment;
import com.shahin.techbrandeshop.myInterface.ICheckBoxChangedListener;
import com.shahin.techbrandeshop.util.ImageConverterHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private final Context context;
    private final List<Product> productList;
    private final List<CartItemViewHolder> cartItemViewHolders;
    private final ICheckBoxChangedListener checkBoxChangedListener;
    private final ShoppingCartFragment cartFragment;

    public CartItemAdapter(Context context, List<Product> productList, ICheckBoxChangedListener checkBoxChangedListener) {
        this.context = context;
        this.productList = productList;
        cartItemViewHolders = new ArrayList<>();
        this.checkBoxChangedListener = checkBoxChangedListener;
        cartFragment = (ShoppingCartFragment) checkBoxChangedListener;
    }

    @NonNull
    @NotNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View cartItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart_item, parent, false);
        return new CartItemViewHolder(cartItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartItemViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.setItemData(product);

        holder.itemQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable == null || editable.toString().isEmpty()) return;

                int quantityInStock = product.getQuantityInStock();
                int orderQuantity = Integer.parseInt(editable.toString());

                if (orderQuantity > quantityInStock) {
                    showAlert(quantityInStock);
                    holder.itemQuantity.setText(String.valueOf(quantityInStock));
                }

                checkBoxChangedListener.updateTotal(cartSumTotal(), getDeliveryCharge());
            }
        });

        holder.btnMinus.setOnClickListener(view -> {
            int currentQuantity = holder.getCurrentOrderQuantity();
            if (currentQuantity > 1) {
                currentQuantity--;
                holder.itemQuantity.setText(String.valueOf(currentQuantity));
            } else if (currentQuantity == 1)
                holder.btnMinus.setClickable(false);
        });

        holder.btnPlus.setOnClickListener(view -> {
            if (!holder.btnPlus.isClickable())
                holder.btnPlus.setClickable(true);

            if (holder.getCurrentOrderQuantity() >= product.getQuantityInStock()) {
                showAlert(product.getQuantityInStock());
                return;
            }

            holder.itemQuantity.setText(String.valueOf(holder.getCurrentOrderQuantity() + 1));
        });

        cartItemViewHolders.add(holder);
    }

    @Override
    public int getItemCount() {
        if (productList == null || productList.isEmpty()) return 0;
        return productList.size();
    }

    private void showAlert(int quantityInStock) {
        new AlertDialog.Builder(context)
                .setMessage("We only have " + quantityInStock + " quantity remaining for this item!")
                .setPositiveButton("Ok", null).show();
    }

    public void toggleAllCheckBox(boolean isChecked) {
        cartItemViewHolders.forEach(holder -> {
            holder.itemCheckBox.setChecked(isChecked);
        });
    }

    public double cartSumTotal() {
        double sumTotal = 0;

        for (CartItemViewHolder holder : cartItemViewHolders)
            if (holder.itemCheckBox.isChecked())
                sumTotal += (holder.price
                        * Integer.parseInt(holder.itemQuantity.getText().toString()));

        return sumTotal;
    }

    public double getDeliveryCharge() {
        double total = 0;

        for (CartItemViewHolder holder : cartItemViewHolders)
            if (holder.itemCheckBox.isChecked() && !holder.isFreeShip)
                total += 20000;

        return total;
    }

    private boolean isCheckOutAll() {
        for (CartItemViewHolder cartItemViewHolder : cartItemViewHolders)
            if (!cartItemViewHolder.itemCheckBox.isChecked()) return false;
        return true;
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout parent;
        private final ImageView itemImg, itemFreeShip;
        private final TextView itemName, itemDiscount, itemPrice;
        private final CheckBox itemCheckBox;
        private final EditText itemQuantity;
        private final ImageButton btnMinus, btnPlus;
        private double price;
        private boolean isFreeShip = false;

        public CartItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.cart_item_parent);
            itemImg = itemView.findViewById(R.id.cart_item_img);
            itemFreeShip = itemView.findViewById(R.id.cart_item_free_ship);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemDiscount = itemView.findViewById(R.id.cart_item_discount);
            itemPrice = itemView.findViewById(R.id.cart_item_price);
            itemCheckBox = itemView.findViewById(R.id.cart_item_check_box);
            itemQuantity = itemView.findViewById(R.id.cart_item_edt_quantity);
            btnMinus = itemView.findViewById(R.id.cart_item_btn_minus);
            btnPlus = itemView.findViewById(R.id.cart_item_btn_add);
        }

        private void setItemData(Product product) {
            itemImg.setImageResource(
                    ImageConverterHelper.getResourceIdFromString(context,
                            product.getProductImages().get(0))
            );

            itemName.setText(product.getProductName());
            itemDiscount.setText(product.getDiscountFormat());
            itemPrice.setText(product.getPriceFormat());
            price = product.getPrice();
            isFreeShip = product.isFreeShipping();

            if (product.isFreeShipping())
                itemFreeShip.setVisibility(View.VISIBLE);

            itemCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
                cartFragment.toggleCheckOutAllCheckBox(isCheckOutAll());
                checkBoxChangedListener.updateTotal(cartSumTotal(), getDeliveryCharge());
            });
        }

        private int getCurrentOrderQuantity() {
            return Integer.parseInt(itemQuantity.getText().toString());
        }
    }
}
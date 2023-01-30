package com.shahin.techbrandeshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.activity.ContainerActivity;
import com.shahin.techbrandeshop.adapter.CartItemAdapter;
import com.shahin.techbrandeshop.database.AppDatabase;
import com.shahin.techbrandeshop.myInterface.ICheckBoxChangedListener;

import java.text.DecimalFormat;

public class ShoppingCartFragment extends Fragment implements ICheckBoxChangedListener {

    private RecyclerView cartRecyclerView;
    private CheckBox cbxCheckOutAll;
    private TextView txtSubTotal, txtDeliveryCharge, txtTotal;
    private TextView btnCheckOut;
    private ContainerActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cartView = inflater.
                inflate(R.layout.fragment_shopping_cart, container, false);

        cartRecyclerView = cartView.findViewById(R.id.cart_recycler_view);
        cbxCheckOutAll = cartView.findViewById(R.id.cbx_buy_all);
        btnCheckOut = cartView.findViewById(R.id.check_out);
        txtSubTotal = cartView.findViewById(R.id.cart_sub_total);
        txtDeliveryCharge = cartView.findViewById(R.id.cart_delivery_charges);
        txtTotal = cartView.findViewById(R.id.cart_total);

        activity = (ContainerActivity) getActivity();
        activity.cartToolbarBackground();

        CartItemAdapter adapter = new CartItemAdapter(
                getContext(),
                AppDatabase.getInstance(getContext()).productDAO().getAllProducts()
                , this);

        cartRecyclerView.setAdapter(adapter);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        cartRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        cbxCheckOutAll.setOnClickListener(view -> {
            adapter.toggleAllCheckBox(cbxCheckOutAll.isChecked());
            updateTotal(adapter.cartSumTotal(), adapter.getDeliveryCharge());
        });

        return cartView;
    }

    public void toggleCheckOutAllCheckBox(boolean isCheck) {
        cbxCheckOutAll.setChecked(isCheck);
    }

    @Override
    public void updateTotal(double total, double charge) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        txtSubTotal.setText(String.format("đ %s", formatter.format(total)));
        txtDeliveryCharge.setText(String.format("đ %s", formatter.format(charge)));
        txtTotal.setText(String.format("đ %s", formatter.format(total + charge)));
    }
}
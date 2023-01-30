package com.shahin.techbrandeshop.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.entity.Product;

public class ProductSpecificationsFragment extends Fragment {

    private TextView cpu, os, ram, gpu, monitor,
            hardDrive, gate, keyboard, battery, weight;

    public ProductSpecificationsFragment(Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductDetailFragment.PRODUCT_DETAIL_KEY, product);
        setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View productSpecificationView = inflater.inflate(
                R.layout.fragment_product_specifications, container, false);

        initUI(productSpecificationView);

        if (getArguments() != null) {
            Product product = (Product) getArguments()
                    .getSerializable(ProductDetailFragment.PRODUCT_DETAIL_KEY);
            if (product != null)
                setUIData(product);
        }

        return productSpecificationView;
    }

    private void initUI(View productSpecificationView) {
        cpu = productSpecificationView.findViewById(R.id.product_specification_cpu);
        os = productSpecificationView.findViewById(R.id.product_specification_os);
        ram = productSpecificationView.findViewById(R.id.product_specification_ram);
        gpu = productSpecificationView.findViewById(R.id.product_specification_gpu);
        monitor = productSpecificationView.findViewById(R.id.product_specification_monitor);
        hardDrive = productSpecificationView.findViewById(R.id.product_specification_hard_drive);
        gate = productSpecificationView.findViewById(R.id.product_specification_gate);
        keyboard = productSpecificationView.findViewById(R.id.product_specification_keyboard);
        battery = productSpecificationView.findViewById(R.id.product_specification_battery);
        weight = productSpecificationView.findViewById(R.id.product_specification_weight);
    }

    @SuppressLint("DefaultLocale")
    private void setUIData(Product product) {
        cpu.setText(product.getSpecification().getCpu());
        os.setText(product.getSpecification().getOs());
        ram.setText(product.getSpecification().getRam());
        gpu.setText(product.getSpecification().getGpu());
        monitor.setText(product.getSpecification().getMonitor());
        hardDrive.setText(product.getSpecification().getHardDrive());
        gate.setText(product.getSpecification().getConnectionGate());
        keyboard.setText(product.getSpecification().getKeyboard());
        battery.setText(product.getSpecification().getBattery());
        weight.setText(String.format("%f Kg", product.getSpecification().getWeight()));
    }
}
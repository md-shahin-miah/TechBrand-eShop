package com.shahin.techbrandeshop.fragment;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.activity.ContainerActivity;
import com.shahin.techbrandeshop.adapter.SliderAdapter;
import com.shahin.techbrandeshop.entity.Product;
import com.shahin.techbrandeshop.model.SliderItem;
import com.shahin.techbrandeshop.util.ImageConverterHelper;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import me.relex.circleindicator.CircleIndicator3;

public class ProductDetailFragment extends Fragment {

    public static final String PRODUCT_DETAIL_KEY = "product";

    private ContainerActivity activity;
    private Product product;

    private ViewPager2 productImg;
    private CircleIndicator3 productIndicator;
    private List<SliderItem> sliderItems;

    private ImageView productShip;
    private TextView productName, productPrice, productDiscount,
            productReviewQuantity, txtFreeShipping, productQuantityInStock;

    private RatingBar productRate;
    private TextView productDesc;
    private FrameLayout productSpecificationsContainer;
    private ScrollView scrollView;

    private LinearLayout btnAddToCart;
    private TextView btnBuyNow;

    private View bottomSheetLayout;
    private ImageView orderProductImg;
    private TextView orderProductName, orderProductDiscount,
            orderProductPrice, orderProductInStock;
    private ImageButton btnQuantityMinus, btnQuantityPlus;
    private EditText edtQuantity;
    private BottomSheetDialog orderBottomSheetDialog;
    private Button btnOder;

    public ProductDetailFragment(Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCT_DETAIL_KEY, product);
        setArguments(bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View productDetailView = inflater
                .inflate(R.layout.fragment_product_detail, container, false);

        this.product = (Product) getArguments().getSerializable(PRODUCT_DETAIL_KEY);

        initFragmentUI(productDetailView);
        initFragmentData(product);

        initBottomSheetUI();
        addActionForBottomSheetOrderButtons();
        setBottomSheetData(product);

        toggleDisplayToolbarOnScroll();
        toggleDisplayCheckOutBottomSheetLayout();

        return productDetailView;
    }

    private void initFragmentUI(View productDetailView) {
        activity = (ContainerActivity) getActivity();
        activity.updateCartQuantityNotification(3);
        activity.hideToolBarTitle();
        activity.hideBackground();
        activity.productDetailToolbarBackIcon();

        productImg = productDetailView.findViewById(R.id.product_detail_img);
        productIndicator = productDetailView.findViewById(R.id.product_detail_slider_indicator);

        productShip = productDetailView.findViewById(R.id.product_detail_ship);
        productName = productDetailView.findViewById(R.id.product_detail_name);
        productPrice = productDetailView.findViewById(R.id.product_detail_price);
        productDiscount = productDetailView.findViewById(R.id.product_detail_discount);
        productReviewQuantity = productDetailView.findViewById(R.id.product_detail_review);
        productRate = productDetailView.findViewById(R.id.product_detail_rate);
        productQuantityInStock = productDetailView.findViewById(R.id.product_detail_quantity_in_stock);
        txtFreeShipping = productDetailView.findViewById(R.id.txt_free_shipping);

        productDesc = productDetailView.findViewById(R.id.product_detail_desc);
        scrollView = productDetailView.findViewById(R.id.scroll_view);
        productSpecificationsContainer = productDetailView.findViewById(R.id.product_detail_desc_container);

        btnAddToCart = productDetailView.findViewById(R.id.btn_add_to_cart);
        btnBuyNow = productDetailView.findViewById(R.id.btn_buy_now);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<SliderItem> initSliderItem(List<String> products) {
            return products.stream()
                    .map(product -> new SliderItem(ImageConverterHelper
                            .getResourceIdFromString(getContext(), product)))
                    .collect(Collectors.toList());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initFragmentData(Product product) {
        sliderItems = initSliderItem(product.getProductImages());
        productImg.setAdapter(new SliderAdapter(sliderItems));
        productIndicator.setViewPager(productImg);

        productName.setText(product.getProductName());
        productPrice.setText(product.getPriceFormat());
        productDiscount.setText(product.getDiscountFormat());
        productRate.setRating((float) product.getRate());
        productQuantityInStock.append(" " + product.getQuantityInStock());
        productDesc.setText(product.getDescription());
        productReviewQuantity.setText("(" + new Random().nextInt(30) + " reviews)");

        if (product.isFreeShipping()) {
            productShip.setVisibility(ImageView.VISIBLE);
            txtFreeShipping.setVisibility(TextView.VISIBLE);
        }

        getChildFragmentManager()
                .beginTransaction()
                .add(productSpecificationsContainer.getId(),
                        new ProductSpecificationsFragment(product))
                .commit();
    }

    private void initBottomSheetUI() {
        bottomSheetLayout = getLayoutInflater()
                .inflate(R.layout.layout_checkout_bottom_sheet, null);

        orderBottomSheetDialog = new BottomSheetDialog(getContext());
        orderBottomSheetDialog.setContentView(bottomSheetLayout);

        orderProductImg = bottomSheetLayout.findViewById(R.id.order_product_img);
        orderProductName = bottomSheetLayout.findViewById(R.id.order_product_name);
        orderProductDiscount = bottomSheetLayout.findViewById(R.id.order_product_discount);
        orderProductPrice = bottomSheetLayout.findViewById(R.id.order_product_price);
        orderProductInStock = bottomSheetLayout.findViewById(R.id.order_product_in_stock);

        btnQuantityMinus = bottomSheetLayout.findViewById(R.id.btn_order_minus);
        btnQuantityMinus.setClickable(false);
        btnQuantityPlus = bottomSheetLayout.findViewById(R.id.btn_order_plus);
        edtQuantity = bottomSheetLayout.findViewById(R.id.edt_order_quantity);

        btnOder = bottomSheetLayout.findViewById(R.id.btn_order);
    }

    private void addActionForBottomSheetOrderButtons() {
        edtQuantity.addTextChangedListener(new TextWatcher() {
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
                    edtQuantity.setText(String.valueOf(quantityInStock));
                }
            }
        });

        btnQuantityMinus.setOnClickListener(view -> {
            addActionForBtnMinus();
        });

        btnQuantityPlus.setOnClickListener(view -> {
            addActionForBtnPlus(product.getQuantityInStock());
        });
    }

    private void addActionForBtnMinus() {
        int currentQuantity = getCurrentOrderQuantity();
        if (currentQuantity > 1) {
            currentQuantity--;
            edtQuantity.setText(String.valueOf(currentQuantity));
        } else if (currentQuantity == 1)
            btnQuantityMinus.setClickable(false);
    }

    private void addActionForBtnPlus(int quantityInStock) {
        if (!btnQuantityMinus.isClickable())
            btnQuantityMinus.setClickable(true);

        if (getCurrentOrderQuantity() >= quantityInStock) {
            showAlert(quantityInStock);
            return;
        }

        edtQuantity.setText(String.valueOf(getCurrentOrderQuantity() + 1));
    }

    private void setBottomSheetData(Product product) {
        orderProductImg.setImageResource(
                ImageConverterHelper.getResourceIdFromString(getContext(),
                        product.getProductImages().get(0))
        );
        orderProductName.setText(product.getProductName());
        orderProductPrice.setText(product.getPriceFormat());
        orderProductDiscount.setText(product.getDiscountFormat());
        orderProductInStock.append(" " + product.getQuantityInStock());
    }

    private int getCurrentOrderQuantity() {
        return Integer.parseInt(edtQuantity.getText().toString());
    }

    private void toggleDisplayToolbarOnScroll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY >= 150) //if user scroll for 150 px or whatever -> set elevation
                    activity.showBackground();
                else
                    activity.hideBackground();
            });
        }
    }

    private void toggleDisplayCheckOutBottomSheetLayout() {
        btnBuyNow.setOnClickListener(view -> {
            orderBottomSheetDialog.show();
            changeButtonState(R.string.buy_now, R.color.secondaryColor);
        });

        btnAddToCart.setOnClickListener(view -> {
            orderBottomSheetDialog.show();
            changeButtonState(R.string.add_to_cart, R.color.teal_500);
        });
    }

    private void changeButtonState(int buttonText, int color) {
        btnOder.setText(getResources().getString(buttonText));
        btnOder.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    private void showAlert(int quantityInStock) {
        new AlertDialog.Builder(getContext())
                .setMessage("We only have " + quantityInStock + " quantity remaining for this item!")
                .setPositiveButton("Ok", null).show();
    }
}
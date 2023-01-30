package com.shahin.techbrandeshop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.activity.HomeActivity;
import com.shahin.techbrandeshop.adapter.ProductAdapter;
import com.shahin.techbrandeshop.adapter.ProductTopSellAdapter;
import com.shahin.techbrandeshop.adapter.SliderAdapter;
import com.shahin.techbrandeshop.database.AppDatabase;
import com.shahin.techbrandeshop.entity.Product;
import com.shahin.techbrandeshop.model.SliderItem;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private static final long DELAY_TIME = 3 * 1000; //secs

    //Banner Slider
    private ViewPager2 bannerViewPager;
    private CircleIndicator3 circleIndicator;
    private List<SliderItem> sliderItems;
    private Handler mBannerSliderHandler;
    private Runnable mBannerRunnable;
    private HomeActivity homeActivity;

    //Top sell list
    private RecyclerView topSellRecyclerView;

    //Product List
    private RecyclerView productRecyclerView;
    private List<Product> productList;
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initFragmentUI(view);

        //Banner Slider
        initSliderViewPager();
        startBannerSlider();
        addAutoRunEventForViewPager();

        //Product Top Sell
        topSellRecyclerView.setAdapter(new ProductTopSellAdapter(getActivity(), productList));
        topSellRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        //Product List
        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    private void initFragmentUI(View view) {
        bannerViewPager = view.findViewById(R.id.banner_view_pager);
        circleIndicator = view.findViewById(R.id.banner_slider_indicator);
        sliderItems = initSliderItems();

        mBannerSliderHandler = new Handler(Looper.getMainLooper());
        mBannerRunnable = () -> {
            if (bannerViewPager.getCurrentItem() == sliderItems.size() - 1)
                bannerViewPager.setCurrentItem(0);
            else
                bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
        };

        topSellRecyclerView = view.findViewById(R.id.top_sell_recycler_view);

        productRecyclerView = view.findViewById(R.id.list_product_recycler_view);
        productList = initProductData();
        productAdapter = new ProductAdapter(getActivity(), productList);
    }

    private List<Product> initProductData() {
        List<Product> products = new ArrayList<>();
        products = AppDatabase.getInstance(getContext()).productDAO().getAllProducts();
        homeActivity = (HomeActivity) getActivity();
        homeActivity.updateCartQuantityNotification(products.size());
        return products;
    }

    private List<SliderItem> initSliderItems() {
        List<SliderItem> items = new ArrayList<>();

        items.add(new SliderItem(R.drawable.lp1));
        items.add(new SliderItem(R.drawable.lp3));
        items.add(new SliderItem(R.drawable.lp5));

        return items;
    }

    private void initSliderViewPager() {
        SliderAdapter sliderAdapter = new SliderAdapter(sliderItems);

        bannerViewPager.setAdapter(sliderAdapter);
        bannerViewPager.setClipToPadding(false);
        bannerViewPager.setClipChildren(false);
        bannerViewPager.setOffscreenPageLimit(3);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        bannerViewPager.setPageTransformer(transformer);

        circleIndicator.setViewPager(bannerViewPager);
    }

    public void startBannerSlider() {
        mBannerSliderHandler.postDelayed(mBannerRunnable, DELAY_TIME);
    }

    private void addAutoRunEventForViewPager() {
        bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mBannerSliderHandler.removeCallbacks(mBannerRunnable);
                startBannerSlider();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerSliderHandler.removeCallbacks(mBannerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        startBannerSlider();
    }
}
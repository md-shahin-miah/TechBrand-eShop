package com.shahin.techbrandeshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.model.SliderItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private final List<SliderItem> sliderItems;

    public SliderAdapter(List<SliderItem> sliderItems) {
        this.sliderItems = sliderItems;
    }

    @NonNull
    @NotNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_banner_slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SliderViewHolder holder, int position) {
        SliderItem sliderItem = sliderItems.get(position);
        holder.sliderImg.setImageResource(sliderItem.getImageResource());
    }

    @Override
    public int getItemCount() {
        if (sliderItems == null || sliderItems.isEmpty()) return 0;
        return sliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        private final ImageView sliderImg;

        public SliderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            sliderImg = itemView.findViewById(R.id.slider_item_img);
        }
    }
}
package com.adnroidstudy.huguangyan_app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class TopCarouselAdapter extends RecyclerView.Adapter<TopCarouselAdapter.ViewHolder> {
    private Context context;
    private List<Integer> imageResIds;

    public TopCarouselAdapter(Context context, List<Integer> imageResIds) {
        this.context = context;
        this.imageResIds = imageResIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new ImageView(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.itemView;
        imageView.setImageResource(imageResIds.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public int getItemCount() {
        return imageResIds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
package com.nobrain.samples.adapterrolesample.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobrain.samples.adapterrolesample.R;
import com.nobrain.samples.adapterrolesample.main.adapter.model.PhotoDataModel;
import com.nobrain.samples.adapterrolesample.main.adapter.view.PhotoAdapterView;
import com.nobrain.samples.adapterrolesample.network.domain.Photo;
import com.nobrain.samples.adapterrolesample.views.interfaces.OnRecyclerItemClickListener;
import com.nobrain.samples.adapterrolesample.views.interfaces.OnRecyclerItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlickerPhotoAdapter extends RecyclerView.Adapter<FlickerPhotoAdapter.FlickerPhotoViewHolder>
        implements PhotoDataModel, PhotoAdapterView {

    private final Context context;
    private List<Photo> photoList;
    private OnRecyclerItemLongClickListener onRecyclerItemLongClickListener;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public FlickerPhotoAdapter(Context context) {
        this.context = context;
        this.photoList = new ArrayList<>();
    }

    @Override
    public FlickerPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_flicker_photo, parent, false);
        return new FlickerPhotoViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    @Override
    public void onBindViewHolder(FlickerPhotoViewHolder holder, int position) {
        Photo photo = getPhoto(position);
        String farmId = String.valueOf(photo.getFarm());
        String serverId = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();


        holder.itemView.setOnLongClickListener(v -> {
            if (onRecyclerItemLongClickListener != null) {
                return onRecyclerItemLongClickListener.onItemLongClick(FlickerPhotoAdapter.this, position);
            }
            return false;
        });

        holder.itemView.setOnClickListener(v -> {
            if (onRecyclerItemClickListener != null) {
                onRecyclerItemClickListener.onItemClick(FlickerPhotoAdapter.this, position);
            }
        });

        Glide.with(context)
                .load(String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", farmId, serverId, id, secret))
                .centerCrop()
                .crossFade()
                .into(holder.ivPhoto);

        holder.tvTitle.setText(photo.getTitle());
    }

    @Override
    public void add(Photo photo) {
        photoList.add(photo);
    }

    @Override
    public Photo remove(int position) {
        return photoList.remove(position);
    }

    @Override
    public Photo getPhoto(int position) {
        return photoList.get(position);
    }

    @Override
    public int getSize() {
        return photoList.size();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    public void setOnRecyclerItemLongClickListener(OnRecyclerItemLongClickListener onRecyclerItemLongClickListener) {
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    class FlickerPhotoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_item_flicker_photo)
        ImageView ivPhoto;

        @Bind(R.id.tv_item_flicker_photo)
        TextView tvTitle;

        public FlickerPhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

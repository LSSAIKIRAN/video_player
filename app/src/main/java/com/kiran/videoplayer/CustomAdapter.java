package com.kiran.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<File> files;

    public CustomAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.name.setText(files.get(position).getName());
        holder.name.setSelected(true);

        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(files.get(position).getAbsolutePath(),
                MediaStore.Images.Thumbnails.MINI_KIND);
        holder.Image.setImageBitmap(thumb);


    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView name;
        View layout;

        public ViewHolder(View itemView) {
            super(itemView);

            Image = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            layout = itemView.findViewById(R.id.mainContainer);
        }
    }


}

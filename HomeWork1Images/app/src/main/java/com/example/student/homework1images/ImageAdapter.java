package com.example.student.homework1images;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {


    private Context context;
    private final String[] array;

    public ImageAdapter(Context context, final String[] array) {
        this.context = context;
        this.array = array;
    }


    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.images_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.MyViewHolder holder, final int position) {

        String image = context.getResources().getStringArray(R.array.image_array)[position];

        holder.textImage.setText(image);
        holder.textImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).updateText(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textImage;

        MyViewHolder(View itemView) {
            super(itemView);
            textImage = itemView.findViewById(R.id.text_item);
        }
    }

}

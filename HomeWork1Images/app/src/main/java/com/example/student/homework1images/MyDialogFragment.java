package com.example.student.homework1images;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

@SuppressLint("ValidFragment")
class MyDialogFragment extends DialogFragment {

    private final List<ImageModel> list;
    private final int position;


    public MyDialogFragment(List<ImageModel> list, int position) {
        this.list = list;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_dialog_fragment, container, false);
        ImageView imageView = view.findViewById(R.id.dialog_image_view);
        Picasso.get().load(list.get(position).getHttp()).into(imageView);
        return view;
    }

}
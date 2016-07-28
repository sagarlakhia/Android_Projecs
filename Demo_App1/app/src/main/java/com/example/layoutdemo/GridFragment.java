package com.example.layoutdemo;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {

    private GridView photoGridView;
    private View gridView;
    private List<PhotoItem> photoItems;


    public GridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getActivity(),"HERE",Toast.LENGTH_SHORT);

        // Inflate the layout for this fragment
        gridView = inflater.inflate(R.layout.fragment_grid, container, false);
        photoGridView = (GridView) gridView.findViewById(R.id.gridPhotoView);

        //Getting id of the selected album
        ArrayAdapter<PhotoItem>  adapter = new CustomGridPhotoAdapter(getActivity().getBaseContext());
        photoGridView.setAdapter(adapter);

        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PhotoItem selectedPhotoItem = photoItems.get(position);
                Intent downloadIntent = new Intent(getContext(), ViewDownloadedPhotoActivity.class);
                downloadIntent.putExtra("downloadURL", selectedPhotoItem.getUrl());
                downloadIntent.putExtra("title", selectedPhotoItem.getTitle());
                startActivity(downloadIntent);
              //  Toast.makeText(getContext(), String.valueOf("Title: "+selectedPhotoItem.getTitle()), Toast.LENGTH_SHORT).show();
            }
        });

        //Setting up to preview when user does a long click
        photoGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                PhotoItem longPressedImage = photoItems.get(position);

                    loadPhoto(longPressedImage.getUrl());

                return true;
            }
        });


        return gridView;


    }

    private void loadPhoto(String url){

        //Creating alertdialog android

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.popup_image,
                (ViewGroup) gridView.findViewById(R.id.popUpWindow));
        ImageView image = (ImageView) layout.findViewById(R.id.popUp);

        //Using picasso to load images
        Picasso.with(getContext()).load(url.replace("http","https")).into(image);
        imageDialog.setView(layout);
        imageDialog.setCancelable(true);
        imageDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        imageDialog.create();
        imageDialog.show();


    }
    //Custom Adapter
    private class CustomGridPhotoAdapter extends ArrayAdapter<PhotoItem> {
        public CustomGridPhotoAdapter(Context context) {
            super(context, R.layout.photo_grid_item, photoItems);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View photoItemView = convertView;
            if (photoItemView == null) {
                photoItemView = getActivity().getLayoutInflater().inflate(R.layout.photo_grid_item, parent, false);

            }

            PhotoItem photo = photoItems.get(position);
            ImageView imgThumb = (ImageView) photoItemView.findViewById(R.id.photoGridImage);

            Picasso.with(getActivity())
                    .load(photo.getThumbnailUrl().replace("http", "https"))
                    .into(imgThumb);

            return photoItemView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return photoItems.size();
        }


    }

    public void setPhotoItems(List<PhotoItem> photoItems) {
        this.photoItems = photoItems;
    }
}

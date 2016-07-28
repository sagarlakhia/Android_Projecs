package com.example.layoutdemo;


import android.content.Context;
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
public class MultiPaneFragment extends Fragment {

    private View multiPaneView;
    private ImageView upperImageView;
    private GridView gridMultiPhotoView;
    private List<PhotoItem>photoItems;

    public MultiPaneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        multiPaneView= inflater.inflate(R.layout.fragment_multi_pane, container, false);
        gridMultiPhotoView=(GridView)multiPaneView.findViewById(R.id.gridMultiPhotoView);

        upperImageView=(ImageView)multiPaneView.findViewById(R.id.upperImageView);


        ArrayAdapter<PhotoItem>  adapter = new CustomGridPhotoAdapter(getActivity().getBaseContext());
        gridMultiPhotoView.setAdapter(adapter);

        gridMultiPhotoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PhotoItem selectedPhotoItem = photoItems.get(position);

                Picasso.with(getContext()).load(selectedPhotoItem.getUrl().replace("http","https")).into(upperImageView);
                Toast.makeText(getContext(), String.valueOf(selectedPhotoItem.getTitle()), Toast.LENGTH_SHORT).show();
            }
        });


        return multiPaneView;

    }



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

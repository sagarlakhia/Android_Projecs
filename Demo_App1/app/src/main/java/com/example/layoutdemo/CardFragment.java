package com.example.layoutdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    private View RecView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<PhotoItem> photoItems;

    public CardFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        RecView=inflater.inflate(R.layout.fragment_card, container, false);

        //Getting the id from the previous activity



        recyclerView= (RecyclerView)RecView.findViewById(R.id.recyclerView);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomRecycleAdapter(photoItems);
        recyclerView.setAdapter(adapter);

        return RecView;
    }


    private class CustomRecycleAdapter extends RecyclerView.Adapter<CustomRecycleAdapter.PhotoViewHolder>{

       List<PhotoItem> photoItems = new ArrayList<PhotoItem>();
        public CustomRecycleAdapter(List<PhotoItem> photoItems){
            this.photoItems=photoItems;
        }




        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
            PhotoViewHolder photoViewHolder = new PhotoViewHolder(view);
            return photoViewHolder;
        }

        @Override
        public void onBindViewHolder(PhotoViewHolder holder, int position) {

            PhotoItem photo = photoItems.get(position);
            Picasso.with(getContext()).load(photo.getThumbnailUrl().replace("http","https")).into(holder.photoImageView);


            holder.titleView.setText(photo.getTitle());
        }

        @Override
        public int getItemCount() {
            return photoItems.size();
        }


        public class PhotoViewHolder extends RecyclerView.ViewHolder{

            ImageView photoImageView;
            TextView titleView;
            public PhotoViewHolder(View view){


                super(view);

                photoImageView = (ImageView)view.findViewById(R.id.cardImage);
                titleView =(TextView) view.findViewById(R.id.cardTitle);

            }
        }
    }

    public void setPhotoItems(List<PhotoItem> photoItems) {
        this.photoItems = photoItems;
    }

}

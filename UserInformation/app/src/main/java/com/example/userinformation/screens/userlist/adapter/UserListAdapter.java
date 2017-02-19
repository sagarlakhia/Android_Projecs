package com.example.userinformation.screens.userlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.userinformation.R;
import com.example.userinformation.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sagar on 17-02-2017.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private ArrayList<User> users = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private itemLongClickListener itemLongClickListener;

    //==============================================================================================
    // Constructor
    //==============================================================================================

    public UserListAdapter(ArrayList<User> users, ItemClickListener itemClickListener,
                           itemLongClickListener itemLongClickListener)
    {
        this.users = users;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    //==============================================================================================
    // Adapter Methods
    //==============================================================================================

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final User user = users.get(position);
        holder.nameTextView.setText(user.name);
        holder.ageTextView.setText(String.valueOf(user.age));
        holder.emailTextView.setText(user.email);
        holder.professionTextView.setText(user.profession);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                itemClickListener.onItemClicked(user);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                itemLongClickListener.onItemLongClicked(user);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================

    public void updateUserList(List<User> users)
    {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    //==============================================================================================
    // ViewHolder
    //==============================================================================================

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.nameTextView)
        TextView nameTextView;
        @BindView(R.id.emailTextView)
        TextView emailTextView;
        @BindView(R.id.ageTextView)
        TextView ageTextView;
        @BindView(R.id.professionTextView)
        TextView professionTextView;
        View itemView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    //==============================================================================================
    // Item Click Listener
    //==============================================================================================

    public interface ItemClickListener
    {
        void onItemClicked(User user);
    }

    //==============================================================================================
    // Item Long Click Listener
    //==============================================================================================

    public interface itemLongClickListener
    {
        void onItemLongClicked(User user);
    }
}

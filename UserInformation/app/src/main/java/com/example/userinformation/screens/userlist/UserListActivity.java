package com.example.userinformation.screens.userlist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.userinformation.R;
import com.example.userinformation.models.User;
import com.example.userinformation.screens.userlist.adapter.UserListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListActivity extends AppCompatActivity implements UserListPresenter.UserListView,
        UserListAdapter.ItemClickListener,UserListAdapter.itemLongClickListener
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    @BindView(R.id.userListRecyclerView)
    RecyclerView userListRecyclerView;

    private LinearLayoutManager linearLayoutManager;
    private UserListAdapter userListAdapter;
    private UserListPresenter presenter;
    private AlertDialog alertDialog;

    //==============================================================================================
    // Static Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, UserListActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Life Cycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);

        presenter = new UserListPresenter(this);
        ArrayList<User> users = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        userListAdapter = new UserListAdapter(users, this, this);
        userListRecyclerView.setLayoutManager(linearLayoutManager);
        userListRecyclerView.setAdapter(userListAdapter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.unBind();
    }

    //==============================================================================================
    // View Implementation
    //==============================================================================================

    @Override
    public void showUsers(List<User> users)
    {
        userListAdapter.updateUserList(users);
    }

    @Override
    public void showErrorMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //==============================================================================================
    // Item Click Listener Implementation
    //==============================================================================================

    @Override
    public void onItemClicked(final User user)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message_confirmation);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                presenter.onDeleteButtonClicked(user);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                alertDialog.dismiss();
            }
        });

        alertDialog =  builder.create();
        alertDialog.show();
    }

    //==============================================================================================
    // Item Long Click Listener Implementation
    //==============================================================================================

    @Override
    public void onItemLongClicked(User user)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);

        final EditText nameEditText = (EditText)view.findViewById(R.id.nameEditText);
        final EditText ageEditText = (EditText)view.findViewById(R.id.ageEditText);
        final EditText emailEditText = (EditText)view.findViewById(R.id.emailEditText);
        final EditText professionEditText = (EditText)view.findViewById(R.id.professionEdiText);

        emailEditText.setEnabled(false);

        Button update = (Button)view.findViewById(R.id.submitButton);
        Button viewAllButton = (Button)view.findViewById(R.id.showAllUsersButton);
        viewAllButton.setVisibility(View.GONE);
        update.setText(R.string.update);

        nameEditText.setText(user.name);
        ageEditText.setText(String.valueOf(user.age));
        emailEditText.setText(user.email);
        professionEditText.setText(user.profession);

        alertDialog.setContentView(view);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = nameEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String profession = professionEditText.getText().toString();

                alertDialog.dismiss();
                presenter.onUpdateButtonClicked(name, age, email, profession);
            }
        });
    }

    //==============================================================================================
    // View Implementation
    //==============================================================================================

    @Override
    public void showUpdatedUsers()
    {
        presenter.onUserDeleted();
    }

    public void showInvalidMessage()
    {
        Toast.makeText(this, R.string.please_check_fields, Toast.LENGTH_SHORT).show();
    }
}

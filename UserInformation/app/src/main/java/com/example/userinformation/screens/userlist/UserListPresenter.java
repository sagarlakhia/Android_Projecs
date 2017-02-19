package com.example.userinformation.screens.userlist;

import com.example.userinformation.models.User;
import com.example.userinformation.net.API;
import com.example.userinformation.net.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sagar on 17-02-2017.
 */

public class UserListPresenter
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private UserListView view;

    //==============================================================================================
    // Constructor
    //==============================================================================================

    public UserListPresenter(UserListView view)
    {
        this.view = view;
        onCreated();
    }

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================

    public void onCreated()
    {
        API.userAPI().fetchUsers().enqueue(new Callback<List<User>>()
        {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response)
            {
                if(view != null)
                {
                    view.showUsers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t)
            {
                if(view != null)
                {
                    view.showErrorMessage(t.getMessage());
                }
            }
        });
    }

    public void onDataUpdated()
    {
        API.userAPI().fetchUsers().enqueue(new Callback<List<User>>()
        {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response)
            {
                if(view != null)
                {
                    view.showUsers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t)
            {
                if(view != null)
                {
                    view.showErrorMessage(t.getMessage());
                }
            }
        });
    }

    public void onUpdateButtonClicked(String name, String age, String email, String profession)
    {
        if(checkValidation(name, age, email, profession))
        {
            API.userAPI().updateUser(name, Integer.parseInt(age), email, profession).enqueue(new Callback<BaseResponse>()
            {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response)
                {
                    onDataUpdated();
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t)
                {
                    //Do nothing here
                }
            });
        }
    }

    public boolean checkValidation(String name, String age, String email, String profession)
    {
        if(name.isEmpty() || age.isEmpty() || email.isEmpty() || profession.isEmpty())
        {
            if(view != null)
            {
                view.showInvalidMessage();
                return false;
            }
        }
        return true;
    }

    public void onUserDeleted()
    {
        API.userAPI().fetchUsers().enqueue(new Callback<List<User>>()
        {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response)
            {
                if(view != null)
                {
                    view.showUsers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t)
            {
                if(view != null)
                {
                    view.showErrorMessage(t.getMessage());
                }
            }
        });
    }

    public void onDeleteButtonClicked(User user)
    {
        API.userAPI().deleteUser(user.email).enqueue(new Callback<BaseResponse>()
        {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response)
            {
                if(view != null)
                {
                    view.showUpdatedUsers();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t)
            {
                //Do nothing here
            }
        });
    }

    public void unBind()
    {
       view = null;
    }

    //==============================================================================================
    // View Interface
    //==============================================================================================

    public interface UserListView
    {
        void showUsers(List<User> users);

        void showErrorMessage(String message);

        void showUpdatedUsers();

        void showInvalidMessage();
    }
}

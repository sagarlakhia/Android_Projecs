package com.example.userinformation.screens.main;

import com.example.userinformation.models.User;
import com.example.userinformation.net.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sagar on 17-02-2017.
 */

public class MainPresenter {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    MainView view;

    //==============================================================================================
    // Constructor
    //==============================================================================================

    public MainPresenter(MainView view)
    {
        this.view = view;
    }

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================

    public void submitButtonClicked(String name, String age, String email, String profession)
    {
        if(checkValidation(name,age,email,profession))
        {
            API.userAPI().submitUserData(name,Integer.valueOf(age),email,profession).enqueue(new Callback<User>()
            {
                @Override
                public void onResponse(Call<User> call, Response<User> response)
                {
                    if(view != null)
                    {
                        view.showUsers();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t)
                {
                    //Do nothing here
                }
            });
        }
    }

    public boolean checkValidation(String name, String age, String email, String profession)
    {
        boolean isValid = true;

        if(name.isEmpty())
        {
            if(view != null)
            {
                view.showInvalidNameMessage();
                isValid = false;
            }
        }

        if(age.isEmpty())
        {
            if(view != null)
            {
                view.showInvalidAgeMessage();
                isValid = false;
            }
        }

        if(email.isEmpty())
        {
            if(view != null)
            {
                view.showInvalidEmailMessage();
                isValid = false;
            }
        }

        if(profession.isEmpty())
        {
            if(view != null)
            {
                view.showInvalidProfessionMessage();
                isValid = false;
            }
        }

        return isValid;
    }

    public void unBind()
    {
        view = null;
    }

    //==============================================================================================
    // View Interface
    //==============================================================================================

    public interface MainView
    {
        void showUsers();

        void showInvalidNameMessage();

        void showInvalidAgeMessage();

        void showInvalidEmailMessage();

        void showInvalidProfessionMessage();
    }
}

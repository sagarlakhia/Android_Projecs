package com.example.userinformation.screens.main;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.userinformation.R;
import com.example.userinformation.screens.userlist.UserListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.nameTextLayout)
    TextInputLayout nameTextLayout;
    @BindView(R.id.ageEditText)
    EditText ageEditText;
    @BindView(R.id.ageTextLayout)
    TextInputLayout ageTextLayout;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.emailTextLayout)
    TextInputLayout emailTextLayout;
    @BindView(R.id.professionEdiText)
    EditText professionEditText;
    @BindView(R.id.professionTextLayout)
    TextInputLayout professionTextLayout;

    private MainPresenter presenter;

    //==============================================================================================
    // Life Cycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
    }

    @Override
    protected void onDestroy()
    {
        presenter.unBind();
        super.onDestroy();
    }

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================

    public void clearFields()
    {
        nameEditText.setText("");
        ageEditText.setText("");
        emailEditText.setText("");
        professionEditText.setText("");

        nameTextLayout.setError(null);
        ageTextLayout.setError(null);
        emailTextLayout.setError(null);
        professionTextLayout.setError(null);
    }

    //==============================================================================================
    // On Click Methods
    //==============================================================================================

    @OnClick(R.id.submitButton)
    public void submitButtonClicked()
    {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String profession = professionEditText.getText().toString();

        presenter.submitButtonClicked(name,age,email,profession);
    }

    @OnClick(R.id.showAllUsersButton)
    public void showAllUsersButtonClicked()
    {
        UserListActivity.start(this);
        clearFields();
    }

    //==============================================================================================
    // View Implementation
    //==============================================================================================

    @Override
    public void showUsers()
    {
        UserListActivity.start(this);
        clearFields();
    }

    @Override
    public void showInvalidNameMessage()
    {
        nameTextLayout.setError(getString(R.string.invalid_name));
    }

    @Override
    public void showInvalidAgeMessage()
    {
        ageTextLayout.setError(getString(R.string.invalid_age));
    }

    @Override
    public void showInvalidEmailMessage()
    {
        emailTextLayout.setError(getString(R.string.invalid_email));
    }

    @Override
    public void showInvalidProfessionMessage()
    {
        professionTextLayout.setError(getString(R.string.invalid_profession));
    }
}

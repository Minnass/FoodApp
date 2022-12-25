package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout userNameLayout, passWordLayout, emailLayout, verifyingPasswordLayout;
    TextInputEditText userName, password, email, verifyingPassword;
    TextView signpBtn, login_navigation, error;

    FoodAppApi mFoodappApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mappingID();
        HandleLoginNavigation();
        HandleSignUpButton();
    }

    void mappingID() {
        userNameLayout = findViewById(R.id.textlayou1);
        passWordLayout = findViewById(R.id.textlayou2);
        emailLayout = findViewById(R.id.textLayout3);
        verifyingPasswordLayout = findViewById(R.id.textLayout4);
        userName = findViewById(R.id.userName_RegisterActivity);
        password = findViewById(R.id.password_RegisterActivity);
        email = findViewById(R.id.email_RegisterActivity);
        verifyingPassword = findViewById(R.id.verification_RegisterActivity);
        signpBtn = findViewById(R.id.btnSignup);
        login_navigation = findViewById(R.id.login_navigation);
        error = findViewById(R.id.error_RegisterActivity);
    }

    void HandleLoginNavigation() {
        login_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void HandleSignUpButton() {
        signpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkValidInput())
                {
                    return;
                }
                checkValidUser();
            }
        });
    }

    Boolean checkValidInput() {
        if (userName.getText().toString().equals("")) {
            userNameLayout.setHelperTextEnabled(true);
            userNameLayout.setHelperText("Required*");
            userNameLayout.setBoxBackgroundColor(Color.RED);
            return false;
        }
        if (email.getText().toString().equals("")) {
            userNameLayout.setHelperTextEnabled(true);
            userNameLayout.setHelperText("Required*");
            userNameLayout.setBoxBackgroundColor(Color.RED);
            return false;
        }
        if (password.getText().toString().equals("")) {
            passWordLayout.setHelperText("Required*");
            passWordLayout.setBoxBackgroundColor(Color.RED);
            passWordLayout.setHelperTextEnabled(true);
            return false;
        }
        if (verifyingPassword.getText().toString().equals("")) {
            passWordLayout.setHelperText("Required*");
            passWordLayout.setBoxBackgroundColor(Color.RED);
            passWordLayout.setHelperTextEnabled(true);
            return false;
        }
        if (!password.getText().toString().equals(verifyingPassword.getText().toString())) {
            error.setText("Mật khẩu nhập lại sai");
            error.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    void checkValidUser() {
        mFoodappApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        compositeDisposable.add(mFoodappApi.registerAccount("normal", userName.getText().toString(), password.getText().toString()
                        , email.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object -> {
                            if (object.isSuccess()) {
                                Intent intent = new Intent(RegisterActivity.this, InformationRegisterActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("email",email.getText().toString());
                                bundle.putString("userName",userName.getText().toString());
                                bundle.putString("passWord",password.getText().toString());
                                startActivity(intent);
                            } else {
                                error.setText(object.getMessage());
                                error.setVisibility(View.VISIBLE);
                            }
                        },
                        error -> {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

}
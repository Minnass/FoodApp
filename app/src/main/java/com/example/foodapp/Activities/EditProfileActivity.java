package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditProfileActivity extends AppCompatActivity {
    ImageView backBtn;
    ImageView avatar;
    TextView imgOptionBtn,email,savingBtn;
    TextInputLayout oldPasswordLayout,newPasswordLayout,verifyPassWordLayout;
    TextInputEditText oldPassword,newPassword,verifyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mappindID();
    }
    void mappindID()
    {
        backBtn=findViewById(R.id.back_editProfileActivity);
        avatar=findViewById(R.id.avatar_editProfileActivity);
        imgOptionBtn=findViewById(R.id.image_option);
        email=findViewById(R.id.email_profileActivity);
        oldPasswordLayout=findViewById(R.id.oldPassword_Layout);
        oldPassword=findViewById(R.id.oldPassword);
        newPasswordLayout=findViewById(R.id.newPassword_Layout);
        newPassword=findViewById(R.id.newPassword);
        verifyPassWordLayout=findViewById(R.id.VerifyPassword_Layout);
        verifyPassword=findViewById(R.id.verifyPassword);
        savingBtn=findViewById(R.id.dontButton_EditProfileActivity);
    }


}
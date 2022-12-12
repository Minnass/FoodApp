package com.example.foodapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {
    TextInputEditText userName, passWord;
    TextInputLayout userNameLayout, passWordLayout;
    TextView LoginButton, forgotPassword, signUpButton;
    LinearLayout google, gmail;


    FoodAppApi mFoodappApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ActivityResultLauncher<Intent> resultLauncher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mappingID();
        initGoogleAPI();
        navigateLoginActivity();
        HandleLoginButtonClick();
        HandleGoogleSignin();

    }

    private void mappingID() {
        userNameLayout = findViewById(R.id.userNameLayout_Login);
        passWordLayout = findViewById(R.id.passwordLayout_Login);
        userName = findViewById(R.id.userName_loginActivity);
        passWord = findViewById(R.id.password_LoginActivity);
        LoginButton = findViewById(R.id.btnLogin);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUpButton = findViewById(R.id.signuo_LoginActivity);
        google = findViewById(R.id.Google);
        gmail = findViewById(R.id.gmail);
    }

    void navigateLoginActivity() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void initGoogleAPI() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    void HandleLoginButtonClick() {
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("")) {
                    userNameLayout.setHelperTextEnabled(true);
                    userNameLayout.setHelperText("Required*");
                    userNameLayout.setBoxBackgroundColor(Color.RED);
                    return;
                }
                if (passWord.getText().toString().equals("")) {
                    passWordLayout.setHelperText("Required*");
                    passWordLayout.setBoxBackgroundColor(Color.RED);
                    passWordLayout.setHelperTextEnabled(true);

                    return;
                }
                checkValidUser();
            }
        });


    }

    void checkValidUser() {
        mFoodappApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        compositeDisposable.add(mFoodappApi.checkLogin(userName.getText().toString(), passWord.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object -> {
                            if (object.isSuccess()) {
                                Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", object.getUser());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Sai", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

    void HandleGoogleSignin() {
        resultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                            Intent data=result.getData();
                            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            task.getResult(ApiException.class);
                        } catch (ApiException e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                            handleResultFromGoolgeApiLogin(task);
                    }
                }
        );

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = gsc.getSignInIntent();
                resultLauncher.launch(intent);
            }
        });
    }


    private void handleResultFromGoolgeApiLogin(Task<GoogleSignInAccount> task) {
        task.addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
            @Override
            public void onSuccess(GoogleSignInAccount googleSignInAccount) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Log.d("Loi",e.getMessage());
            }
        });
    }
}
package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Model.LoginModel.UserModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.NotificationDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InformationRegisterActivity extends AppCompatActivity {
    ImageView backRegister;
    TextView submitBtn, goTonextBtn;
    TextInputLayout nameLayout, dateLayout, sexLayout, addressLayout;
    TextInputEditText name, dateOfbirth, sex, address;

    FoodAppApi mFoodappApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    String userName,passWord,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_register);

        mappingID();
        handleSubmitButton();
        chooseCalendar();
        backRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void getDataFromIntent()
    {
        Bundle bundle=new Bundle();
        bundle=getIntent().getExtras();
        userName=bundle.getString("userName");
        passWord=bundle.getString("passWord");
        email=bundle.getString("email");
    }
    void mappingID() {
        backRegister = findViewById(R.id.back_infoActivity);
        nameLayout = findViewById(R.id.textlayou1_infoAcitivy);
        dateLayout = findViewById(R.id.textlayou2_infoActivity);
        sexLayout = findViewById(R.id.textLayout3_infoActivity);
        addressLayout = findViewById(R.id.textLayout4_infoActivity);
        name = findViewById(R.id.name_info);
        dateOfbirth = findViewById(R.id.dateOfbirth);
        sex = findViewById(R.id.sex);
        address = findViewById(R.id.address);
        submitBtn = findViewById(R.id.SummitBtn);
        goTonextBtn = findViewById(R.id.Gotonext);
    }

    void chooseCalendar() {
        dateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int date = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(InformationRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        dateOfbirth.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });
    }

    void handleSubmitButton() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkValidInput()) {
                    return;
                }
                sendPersonalInfoToServer(email,name.getText().toString(), dateOfbirth.getText().toString(),
                        sex.getText().toString(), address.getText().toString()
                );

            }
        });
    }

    boolean checkValidInput() {
        if (name.getText().toString().equals("")) {
            nameLayout.setHelperTextEnabled(true);
            nameLayout.setHelperText("Required*");
            nameLayout.setBoxBackgroundColor(Color.RED);
            return false;
        }
        if (dateOfbirth.getText().toString().equals("")) {
            dateLayout.setHelperTextEnabled(true);
            dateLayout.setHelperText("Required*");
            dateLayout.setBoxBackgroundColor(Color.RED);
            return false;
        }
        if (sex.getText().toString().equals("")) {
            sexLayout.setHelperTextEnabled(true);
            sexLayout.setHelperText("Required*");
            sexLayout.setBoxBackgroundColor(Color.RED);
            return false;
        }
        if (address.getText().toString().equals("")) {
            addressLayout.setHelperTextEnabled(true);
            addressLayout.setHelperText("Required*");
            addressLayout.setBoxBackgroundColor(Color.RED);
            return false;
        }
        return true;
    }

    void sendPersonalInfoToServer(String email,String name, String dateOfBirth, String sex, String address) {
        Log.d("lloi",email);
        Log.d("lloi",name);
        Log.d("lloi",dateOfBirth);
        Log.d("lloi",sex);
        Log.d("lloi",address);
        compositeDisposable.add(mFoodappApi.sendPersonalInfo(email,name, dateOfBirth, sex, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object -> {
                            if (object) {
                                submitBtn.setVisibility(View.GONE);
                                goTonextBtn.setVisibility(View.VISIBLE);
                                NotificationDialog notificationDialog=new NotificationDialog(InformationRegisterActivity.this);
                                notificationDialog.setContent("Đăng kí tài khoản thành công");
                                notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                                notificationDialog.show();
                                goTonextBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        login();
                                    }
                                });
                            }
                        },
                        error -> {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

    void navigatateMainActivity(UserModel user) {
        Intent intent = new Intent(InformationRegisterActivity.this, MainHomeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    void login()
    {
        compositeDisposable.add(mFoodappApi.checkLogin(userName,passWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object -> {
                            if (object.isSuccess()) {
                                goTonextBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        navigatateMainActivity(object.getUser());
                                    }
                                });
                            }
                        },
                        error -> {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }
}
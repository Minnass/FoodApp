package com.example.foodapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.MySharedPerferences;
import com.example.foodapp.Util.NotificationDialog;
import com.example.foodapp.Util.PermissionAlertDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditProfileActivity extends AppCompatActivity {

    private final int CAMERA_PEMISSION = 1;
    ImageView backBtn;
    ImageView avatar;
    TextView imgOptionBtn, email, savingBtn, error;
    TextInputLayout oldPasswordLayout, newPasswordLayout, verifyPassWordLayout;
    TextInputEditText oldPassword, newPassword, verifyPassword;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);

    ActivityResultLauncher<String> galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mappingID();
        initView();
        handleSavingButton();
        handleSelectImageOption();
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            avatar.setImageURI(result);

        });
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                avatar.setImageBitmap(bitmap);

            }
        });
    }

    void mappingID() {
        backBtn = findViewById(R.id.back_editProfileActivity);
        avatar = findViewById(R.id.avatar_editProfileActivity);
        imgOptionBtn = findViewById(R.id.image_option);
        email = findViewById(R.id.email_profileActivity);
        oldPasswordLayout = findViewById(R.id.oldPassword_Layout);
        oldPassword = findViewById(R.id.oldPassword);
        newPasswordLayout = findViewById(R.id.newPassword_Layout);
        newPassword = findViewById(R.id.newPassword);
        verifyPassWordLayout = findViewById(R.id.VerifyPassword_Layout);
        verifyPassword = findViewById(R.id.verifyPassword);
        savingBtn = findViewById(R.id.dontButton_EditProfileActivity);
        error = findViewById(R.id.error);
    }

    void initView() {
        email.setText("Email: " + MainHomeActivity.user.getEmail());
        MySharedPerferences.getAvatar(avatar,this);
        if (MainHomeActivity.user.getLoginType().equals("google")) {
            oldPasswordLayout.setEnabled(false);
            newPasswordLayout.setEnabled(false);
            verifyPassWordLayout.setEnabled(false);
        }
    }

    void handleSavingButton() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        savingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setVisibility(View.GONE);
                if (checkValidTyping()) {
                    compositeDisposable.add(mFoodAppApi.ChangingPassword(
                                    MainHomeActivity.user.getEmail()
                                    , oldPassword.getText().toString(),
                                    newPassword.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    object -> {
                                        NotificationDialog notificationDialog = new NotificationDialog(EditProfileActivity.this);
                                        if (object.getSuccess()) {
                                            notificationDialog.setContent(object.getMesage());
                                            notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                                            notificationDialog.show();
                                        } else {
                                            notificationDialog.setContent(object.getMesage());
                                            notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_warning_24);
                                            notificationDialog.show();
                                        }
                                    },
                                    error ->
                                    {
                                        Log.d("Loi", error.getMessage());
                                    }
                            )
                    );
                }
                saveAvatarChanged();
            }
        });
    }

    Boolean checkValidTyping() {
        if (oldPassword.getText().toString().equals("")) {
            oldPasswordLayout.setHelperText("Required*");
            oldPasswordLayout.setHelperTextEnabled(true);
            return false;
        }
        if (newPassword.getText().toString().equals("")) {
            newPasswordLayout.setHelperText("Required*");
            newPasswordLayout.setHelperTextEnabled(true);
            return false;
        }
        if (verifyPassword.getText().toString().equals("")) {
            verifyPassWordLayout.setHelperText("Required*");
            verifyPassWordLayout.setHelperTextEnabled(true);
            return false;
        }
        if (!verifyPassword.getText().toString().equals(newPassword.getText().toString())) {
            error.setText("Mật khẩu không trùng khớp.");
            error.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    void handleSelectImageOption() {
        imgOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.camera_option_bottomsheet, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditProfileActivity.this);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                ImageView closeBtn = view.findViewById(R.id.close_imgOption_bottomsheet);
                ImageView camera = view.findViewById(R.id.camera);
                ImageView gallery = view.findViewById(R.id.gallery);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        checkPermission();
                        bottomSheetDialog.dismiss();
                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        galleryLauncher.launch("image/*");
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
    }

    void saveAvatarChanged()
    {
        MySharedPerferences.deleteBefore(EditProfileActivity.this);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("avatarPath", Context.MODE_PRIVATE);
        File mypath = new File(directory, "avatar.jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(mypath);
            Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 75, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert out != null;
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MySharedPerferences.setValue(EditProfileActivity.this, "imagePath", directory.getAbsolutePath());
        MySharedPerferences.setSavedBefore(EditProfileActivity.this);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    void checkPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            String[] permission = new String[]{Manifest.permission.CAMERA
            };
            requestPermissions(permission, CAMERA_PEMISSION);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                cameraLauncher.launch(intent);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PEMISSION:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        cameraLauncher.launch(intent);
                    }
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        PermissionAlertDialog.requestPermissionAgain(EditProfileActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PEMISSION);
                    } else {
                        PermissionAlertDialog.showAlerDialogWarning(EditProfileActivity.this);
                    }
                }
                break;
        }
    }

}
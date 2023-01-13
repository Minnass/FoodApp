package com.example.foodapp.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Model.UploadedImageModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.PermissionAlertDialog;
import com.example.foodapp.Util.RealPathUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodEditerAdminActitivity extends AppCompatActivity {
    ImageView back, foodImage;

    TextView savingBtn, imageButton;
    TextInputLayout foodNameLayout, priceLayout, descriptionLayout, categoryLayout, discountLayout, eaterNumberLayout, expirationTimeLayout, preparationTimeLayout, preservationGuideLayout;
    TextInputEditText foodName, price, description, category, discount, eaterNumber, expirationTime, preparationTime, preservationGuide;

    private final int CAMERA_PEMISSION = 1;
    private final int READ_EXTERNAL_FILE = 2;
    private final int WRITE_EXTERNAL_FILE = 3;
    ActivityResultLauncher<String> galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;
    String mediaPath;
    String imageName;

    FoodAppApi mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_editer_admin);
        mappingID();
        handleClick();

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            foodImage.setImageURI(result);
            foodImage.setVisibility(View.VISIBLE);
            mediaPath = RealPathUtil.getRealPath(this, result);
        });
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                foodImage.setImageBitmap(bitmap);
                foodImage.setVisibility(View.VISIBLE);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                mediaPath = RealPathUtil.getRealPath(this, tempUri);

            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    void mappingID() {
        back = findViewById(R.id.backEditFood);
        savingBtn = findViewById(R.id.saving_editorFoodAdmin);
        foodNameLayout = findViewById(R.id.foodName_layout);
        priceLayout = findViewById(R.id.price_layout);
        descriptionLayout = findViewById(R.id.description_layout);
        categoryLayout = findViewById(R.id.category_layout);
        discountLayout = findViewById(R.id.discount_layout);
        eaterNumberLayout = findViewById(R.id.eaterNumber_layout);
        expirationTimeLayout = findViewById(R.id.expirationTime_layout);
        preparationTimeLayout = findViewById(R.id.preparationTime_layout);
        preservationGuideLayout = findViewById(R.id.preservation_layout);
        foodName = findViewById(R.id.foodName_editFood);
        price = findViewById(R.id.price_editFood);
        description = findViewById(R.id.description_editFood);
        category = findViewById(R.id.category_editFood);
        discount = findViewById(R.id.discount);
        eaterNumber = findViewById(R.id.eater_editFood);
        expirationTime = findViewById(R.id.expirationTime);
        preparationTime = findViewById(R.id.preparationTime_editFood);
        preservationGuide = findViewById(R.id.preservation);
        foodImage = findViewById(R.id.image_foodEditer);
        imageButton = findViewById(R.id.imgButton);

    }

    void handleClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        savingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidTyping()) {
                    uploadMultipleFiles();
                } else {
                }
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    showBottomSheet();
                } else {
                    String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission, READ_EXTERNAL_FILE);
                }
            }

        });
    }

    private void showBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.camera_option_bottomsheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FoodEditerAdminActitivity.this);
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
                checkCameraPermission();
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

    Boolean checkValidTyping() {
        if (foodName.getText().toString().equals("")) {
            foodNameLayout.setHelperText("Required*");
            foodNameLayout.setHelperTextEnabled(true);
            return false;
        }

        if (foodImage.getVisibility() == View.GONE) {
            return false;
        }
        if (price.getText().toString().equals("")) {
            priceLayout.setHelperText("Required*");
            priceLayout.setHelperTextEnabled(true);
            return false;
        }
        if (description.getText().toString().equals("")) {
            descriptionLayout.setHelperText("Required*");
            descriptionLayout.setHelperTextEnabled(true);
            return false;
        }
        if (category.getText().toString().equals("")) {
            categoryLayout.setHelperText("Required*");
            categoryLayout.setHelperTextEnabled(true);
            return false;
        }
        if (discount.getText().toString().equals("")) {
            discountLayout.setHelperText("Required*");
            discountLayout.setHelperTextEnabled(true);
            return false;
        }
        if (eaterNumber.getText().toString().equals("")) {
            eaterNumberLayout.setHelperText("Required*");
            eaterNumberLayout.setHelperTextEnabled(true);
            return false;
        }
        if (expirationTime.getText().toString().equals("")) {
            expirationTimeLayout.setHelperText("Required*");
            expirationTimeLayout.setHelperTextEnabled(true);
            return false;
        }
        if (preparationTime.getText().toString().equals("")) {
            preparationTimeLayout.setHelperText("Required*");
            preparationTimeLayout.setHelperTextEnabled(true);
            return false;
        }
        if (preservationGuide.getText().toString().equals("")) {
            preservationGuideLayout.setHelperText("Required*");
            preservationGuideLayout.setHelperTextEnabled(true);
            return false;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void checkCameraPermission() {
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
    void addNewFood() {
        compositeDisposable.add(mFoodAppApi.addNewFood(
                                foodName.getText().toString(),
                                imageName,
                                Float.parseFloat(price.getText().toString()),
                                description.getText().toString(),
                                category.getText().toString(),
                                0,
                                Integer.parseInt(discount.getText().toString()),
                                Integer.parseInt(eaterNumber.getText().toString()),
                                expirationTime.getText().toString() + " Giờ",
                                preparationTime.getText().toString() + "phút",
                                preservationGuide.getText().toString()
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                success -> {
                                    if (success) {
                                        //Them san pham thanh cong
                                        Intent intent = new Intent();
                                        intent.putExtra("success", true);
                                        setResult(100, intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent();
                                        intent.putExtra("success", false);
                                        setResult(100, intent);
                                        finish();
                                    }
                                },
                                error ->
                                {
                                    Log.d("Loi", error.getMessage());
                                }
                        )
        );
    }

    private void uploadMultipleFiles() {
        File file = new File(mediaPath);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        Call<UploadedImageModel> call = mFoodAppApi.uploadFile(fileToUpload1);
        call.enqueue(new Callback<UploadedImageModel>() {
                         @RequiresApi(api = Build.VERSION_CODES.M)
                         @Override
                         public void onResponse(Call<UploadedImageModel> call, Response<UploadedImageModel> response) {
                             UploadedImageModel serverResponse = response.body();
                             if (serverResponse != null) {
                                 if (serverResponse.getSuccess()) {
                                     Log.d("LL", "Thanh cong");
                                     imageName = serverResponse.getImageName();
                                     addNewFood();
                                 } else {
                                     Log.d("LL", "That bai");
                                 }
                             } else {
                                 assert serverResponse != null;
                                 Log.v("Response", serverResponse.toString());
                             }
                         }

                         @Override
                         public void onFailure(Call<UploadedImageModel> call, Throwable t) {
                             Log.d("LL", t.getMessage());
                         }
                     }
        );
    }

    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
                        PermissionAlertDialog.requestPermissionAgain(FoodEditerAdminActitivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PEMISSION);
                    } else {
                        PermissionAlertDialog.showAlerDialogWarning(FoodEditerAdminActitivity.this);
                    }
                }
                break;
            case READ_EXTERNAL_FILE:
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showBottomSheet();
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        PermissionAlertDialog.requestPermissionAgain(FoodEditerAdminActitivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE  }, READ_EXTERNAL_FILE);
                    } else {
                        PermissionAlertDialog.showAlerDialogWarning(FoodEditerAdminActitivity.this);
                    }
                }
                break;
        }
    }
}
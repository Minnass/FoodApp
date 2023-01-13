package com.example.foodapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodapp.Activities.AddressActivity;
import com.example.foodapp.Activities.Admin;
import com.example.foodapp.Activities.CartActivity;
import com.example.foodapp.Activities.CartHistoryActivity;
import com.example.foodapp.Activities.ContactInformationActivity;
import com.example.foodapp.Activities.EditProfileActivity;
import com.example.foodapp.Activities.MainHomeActivity;
import com.example.foodapp.Activities.SplashScreenActivity;
import com.example.foodapp.Model.CartHistoryItemModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.MySharedPerferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private Context context;
    private MainHomeActivity mainHomeActivity;

    ImageView profileEditer, userImage;
    LinearLayout yourCart, yourAddress, cartHistory, supportCenter;
    TextView signOut, admin;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            mainHomeActivity = (MainHomeActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout view_layout_returnedFood = (LinearLayout) inflater.inflate(R.layout.fragment_profile, null);
        mappingID(view_layout_returnedFood);
        upLoadAvatar();
        hanldeClickEvent();
        handleAdminArea();
        return view_layout_returnedFood;
    }

    void mappingID(LinearLayout viewGroup) {
        profileEditer = viewGroup.findViewById(R.id.edit_profileFragment);
        userImage = viewGroup.findViewById(R.id.avatar_profileFragement);
        yourCart = viewGroup.findViewById(R.id.cart_profileFragment);
        yourAddress = viewGroup.findViewById(R.id.location_profileFragment);
        cartHistory = viewGroup.findViewById(R.id.cartHistory_profileFragment);
        supportCenter = viewGroup.findViewById(R.id.support_profileFragment);
        signOut = viewGroup.findViewById(R.id.signOut);
        admin = viewGroup.findViewById(R.id.admin_role);
    }

    void upLoadAvatar() {
        MySharedPerferences.getAvatar(userImage, context);
    }

    void handleAdminArea() {
        if (MainHomeActivity.user.getRole().equals("admin")) {
            admin.setVisibility(View.VISIBLE);
        }
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin.class);
                startActivity(intent);
            }
        });
    }

    void hanldeClickEvent() {
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainHomeActivity.user.getLoginType().equals("google")) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                    GoogleSignInClient gsc = GoogleSignIn.getClient(context, gso);
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(context, SplashScreenActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(context, SplashScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
                    startActivity(intent);
                }
            }
        });
        profileEditer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        yourCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CartActivity.class);
                startActivity(intent);
            }
        });
        yourAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "view");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        cartHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CartHistoryActivity.class);
                startActivity(intent);
            }
        });
        supportCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactInformationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        upLoadAvatar();
    }
}
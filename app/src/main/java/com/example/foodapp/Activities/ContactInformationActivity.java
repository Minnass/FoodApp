package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.foodapp.R;

public class ContactInformationActivity extends AppCompatActivity {
    private  final String FACEBOOK_URL="https://www.facebook.com/nattan1811";
    private final String INSTAGRAM_URL="https://www.instagram.com/foodstore.az/";
    private final String YUTUBE_URL="https://www.youtube.com/channel/UC6yTUazfXXMJVw-8yn4Md8Q";
    ImageView backButton;
    LinearLayout facebookContact,instaramContact,yutubeContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);
        mappingID();
        handleClick();
    }
    void mappingID()
    {
        backButton=findViewById(R.id.back_contactInfoActivity);
        facebookContact=findViewById(R.id.facebook_contact);
        instaramContact=findViewById(R.id.instagram_contact);
        yutubeContact=findViewById(R.id.youtube_channel);
    }
    void handleClick()
    {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        facebookContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURl(FACEBOOK_URL);
            }
        });
        instaramContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURl(INSTAGRAM_URL);
            }
        });
        yutubeContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURl(YUTUBE_URL);
            }
        });
    }
    void gotoURl(String url)
    {
        Uri uri=Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
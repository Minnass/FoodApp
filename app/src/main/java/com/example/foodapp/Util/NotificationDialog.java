package com.example.foodapp.Util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.foodapp.R;

public class NotificationDialog extends Dialog {
    String content;
    int dialogTypeResource;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDialogTypeResource() {
        return dialogTypeResource;
    }

    public void setDialogTypeResource(int dialogTypeResource) {
        this.dialogTypeResource = dialogTypeResource;
    }

    public NotificationDialog(@NonNull Context context) {
        super(context,true,null);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_dialog);
        TextView _content=findViewById(R.id.content_notifycation);
        ImageView _icon=findViewById(R.id.notification_icon);
        _content.setText(getContent());
        _content.setBackgroundResource(getDialogTypeResource());
    }

    @Override
    public void show() {
        super.show();
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
              dismiss();
            }
        }.start();
    }
}

package com.example.foodapp.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
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
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        setContentView(R.layout.notification_dialog);
        TextView _content = findViewById(R.id.content_notifycation);
        ImageView _icon = findViewById(R.id.notification_icon);
        _content.setText(getContent());
        _icon.setImageResource(getDialogTypeResource());

    }

    @Override
    public void show() {
        super.show();
        new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        }.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if(MotionEvent.ACTION_DOWN == ev.getAction())
        {
            Rect dialogBounds = new Rect();
            getWindow().getDecorView().getHitRect(dialogBounds);
            if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
                // You have clicked the grey area
                this.dismiss();
                return false; // stop activity closing
            }
        }

        // Touch events inside are fine.
        return super.onTouchEvent(ev);
    }

}

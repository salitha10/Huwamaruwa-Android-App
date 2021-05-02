package com.example.huwamaruwa.Progress;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.huwamaruwa.R;

public class LoadingProgress {
    Activity activity;
    AlertDialog alertDialog;

    public LoadingProgress(Activity activity) {
        this.activity = activity;
    }
    public void startProgress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_progress,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }
    public void dismissProgress(){
        alertDialog.dismiss();
    }
}

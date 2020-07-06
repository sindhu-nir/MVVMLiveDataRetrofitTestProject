package com.rescreation.btslmvvm.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rescreation.btslmvvm.R;
import com.rescreation.btslmvvm.model.retrofit.RetrofitClient;
import com.rescreation.btslmvvm.model.retrofit.RetrofitService;
import com.rescreation.btslmvvm.session.SessionManager;
import com.rescreation.btslmvvm.util.AppConstants;

public class Splash extends AppCompatActivity {

    int lang  = 1;
    SessionManager session;
    Handler handler;
    RetrofitService retrofitService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        session = new SessionManager(getApplicationContext());

        handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long cacheException = 0;

                AppConstants.setCurrentBaseURL("http://103.9.185.211/covid19/public/api/v1/");
                retrofitService = RetrofitClient.getClient("http://103.9.185.211/covid19/public/api/v1/").create(RetrofitService.class);
                session.checkLogin();
                finish();


            }
        },2000);

    }
}

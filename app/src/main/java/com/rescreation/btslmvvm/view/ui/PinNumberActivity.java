package com.rescreation.btslmvvm.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rescreation.btslmvvm.R;
import com.rescreation.btslmvvm.session.SessionManager;
import com.rescreation.btslmvvm.util.Utils;
import com.rescreation.btslmvvm.viewmodel.MainActivityViewModel;
import com.rescreation.btslmvvm.viewmodel.PinNumberViewModel;

public class PinNumberActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnStart;
    EditText etPinNumber;
    ProgressBar progressBar;
    LinearLayout mainLayout;
    String errors = "" ;
    SessionManager session;
    TextView tvPinTitle,tvPin,tvLogin;
    Context mContext;

    PinNumberViewModel pinNumberViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_number);
        mContext=PinNumberActivity.this;

        initView();
        onClickListener();

    }

    private void onClickListener() {
        btnStart.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    private void initView() {
        btnStart = (Button)findViewById(R.id.startBtnPin);
        etPinNumber = (EditText) findViewById(R.id.etPinNumber);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        tvPinTitle = (TextView)findViewById(R.id.titlePin);
        tvPin = (TextView)findViewById(R.id.tvPin);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        session = new SessionManager(getApplicationContext());

        //Context context = LocaleHelper.setLocale(this, "en");
        Resources resources = mContext.getResources();

        tvPinTitle.setText(resources.getString(R.string.pinnumber_title));
        tvPin.setText(resources.getString(R.string.your_pinnumber));
        tvLogin.setText(resources.getString(R.string.login));
        btnStart.setText(resources.getString(R.string.start_btn));

        pinNumberViewModel = ViewModelProviders.of(this).get(PinNumberViewModel.class);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBtnPin:

                sendDataToServer();



                break;

            case R.id.tvLogin:

                Intent login = new Intent(PinNumberActivity.this, LoginActivity.class);
                startActivity(login);



                break;

        }

    }

    private void sendDataToServer() {
        if(Utils.isNetworkAvailable(PinNumberActivity.this)){
            int check_condition = 1;

            if (!validPin()) { //Name validation

                check_condition = 0;
            } else {

            }

            if(check_condition == 1){
                //Intent pinIntent = new Intent(Login.this,PinNumberActivity.class);
                //startActivity(pinIntent);

                progressBar.setVisibility(View.VISIBLE);

                String uid = "";
                String mobile = "";

                if(getIntent() != null){
                    uid = getIntent().getStringExtra("UID");
                    mobile = getIntent().getStringExtra("MOBILE");

                }
                //sendData(etPinNumber.getText().toString(),uid,mobile);
                pinNumberViewModel.init(etPinNumber.getText().toString(),uid,mobile);
                pinNumberViewModel.getPinVerifyData().observe(this, pinNumberReponse -> {
                    progressBar.setVisibility(View.GONE);
                    if (pinNumberReponse!=null && pinNumberReponse.getStatus().equals(1)) {

                        session.createLoginSession();
                        Intent homeIntent = new Intent(mContext, MainActivity.class);
                        startActivity(homeIntent);

                        Utils.showSnackBar(mContext,mainLayout,pinNumberReponse.getMessage(),"OK",0);

                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Utils.showSnackBar(mContext,mainLayout,pinNumberReponse.getMessage(),"OK",0);


                    }
                });

            }
        }
    }

    private boolean validPin() {
        Context context = getApplicationContext();
        Resources resources = context.getResources();

        if (etPinNumber.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, resources.getString( R.string.required_pin), Toast.LENGTH_SHORT).show();

            return false;


        }

        return true;
    }


}

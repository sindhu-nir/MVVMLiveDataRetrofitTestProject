package com.rescreation.btslmvvm.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.rescreation.btslmvvm.R;
import com.rescreation.btslmvvm.model.modelclass.LoginData;
import com.rescreation.btslmvvm.model.response.ApiResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;
import com.rescreation.btslmvvm.session.SessionManager;
import com.rescreation.btslmvvm.util.Utils;
import com.rescreation.btslmvvm.viewmodel.LoginActivityViewModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rescreation.btslmvvm.model.response.Status.ERROR;
import static com.rescreation.btslmvvm.model.response.Status.SUCCESS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    EditText etName,etMobile,etEmail;
    Button btnStart;
    ProgressBar progressBar;
    LinearLayout mainLayout;
    String errors = "" ;
    SessionManager session;
    TextView tvTitle,tvName,tvMobile,tvEmail;
    Context mContext;
    Resources resources ;
    LoginActivityViewModel loginActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=LoginActivity.this;

        initView();
        onClickListener();


    }

    private void initView() {
        etName = (EditText) findViewById(R.id.etName);
        etMobile = (EditText)findViewById(R.id.etMobile);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnStart = (Button) findViewById(R.id.startBtn);
        tvTitle = (TextView)findViewById(R.id.titleLogin);
        tvName = (TextView)findViewById(R.id.tvName);
        tvMobile = (TextView)findViewById(R.id.tvMobile);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        session = new SessionManager(getApplicationContext());

        //Context context = LocaleHelper.setLocale(this, "bn");
        resources = mContext.getResources();

        tvTitle.setText(resources.getString(R.string.login_title));
        tvName.setText(resources.getString(R.string.your_name));
        etName.setHint(resources.getString(R.string.name_hint));
        tvMobile.setText(resources.getString(R.string.your_mobile));
        etMobile.setHint(resources.getString(R.string.mobile_hint));
        tvEmail.setText(resources.getString(R.string.your_email));
        etEmail.setHint(resources.getString(R.string.email_hint));
        btnStart.setText(resources.getString(R.string.start_btn));

        loginActivityViewModel=ViewModelProviders.of(this).get(LoginActivityViewModel.class);
        loginActivityViewModel.checkLoginRepository2().observe(this, apiResponse -> {
            System.out.println("Observer Working properly");
            consumeResponse(apiResponse);
        });

    }

    private void onClickListener() {
        btnStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBtn:

                checkLogin();

                break;

        }

    }

    private void checkLogin() {
        int check_condition = 1;

        if (!validatePhone()) { //Name validation

            check_condition = 0;
        } else {

        }


        if(!etEmail.getText().toString().trim().isEmpty()){
            if (!validateEmail()) { //Name validation

                Toast.makeText(this, resources.getString( R.string.required_valid_email), Toast.LENGTH_SHORT).show();
                check_condition = 0;
            } else {

            }
        }

        if(check_condition == 1){
            //Intent pinIntent = new Intent(Login.this,PinNumberActivity.class);
            //startActivity(pinIntent);
            loginActivityViewModel.init2(etMobile.getText().toString().trim(),etEmail.getText().toString());


//            progressBar.setVisibility(View.VISIBLE);
//            loginActivityViewModel.init(etMobile.getText().toString().trim(),etEmail.getText().toString());
//
//            loginActivityViewModel.checkLoginRepository().observe(this, loginResponse -> {
//                String uId = "";
//                String mobile = "";
//                String full_name="";
//                progressBar.setVisibility(View.GONE);
//                if (loginResponse!=null && loginResponse.getStatus().equals(1)) {
//
//                    LoginData loginData = loginResponse.getData();
//                    uId=loginData.getUserId().toString();
//                    mobile=loginData.getMobileNo();
//                    full_name=loginData.getFullName();
//                    SharedPreferences sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
//                    editor.putString("Name", full_name);
//                    editor.putString("UID", uId);
//                    editor.putString("Mobile",mobile);
//                    editor.commit();
//                    if(loginData.getIsVerified()!=null){
//                        if(loginData.getIsVerified().equals(1)){
//
//                            session.createLoginSession();
//                            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
//                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            // Add new Flag to start new Activity
//                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(homeIntent);
//                            finish();
//                        }
//                        else {
//                            Intent pinIntent = new Intent(LoginActivity.this,PinNumberActivity.class);
//                            pinIntent.putExtra("UID",uId);
//                            pinIntent.putExtra("MOBILE",mobile);
//                            startActivity(pinIntent);
//
//                        }
//
//                    }
//
//                    Utils.showSnackBar(mContext,mainLayout,loginResponse.getMessage(),"OK",0);
//
//                }
//                else{
//                    progressBar.setVisibility(View.GONE);
//                    Utils.showSnackBar(mContext,mainLayout,loginResponse.getMessage(),"OK",0);
//
//
//                }
//            });



        }
    }

    private boolean validateEmail() {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(etEmail.getText().toString());
        return matcher.matches();
    }

    private boolean validatePhone() {

        if (etMobile.getText().toString().trim().isEmpty()) {

            Toast.makeText(this,resources.getString(R.string.required_mobile) , Toast.LENGTH_SHORT).show();

            return false;


        } else if (etMobile.getText().toString().trim().length() != 11) {
            Toast.makeText(this, resources.getString(R.string.required_valid_mobile), Toast.LENGTH_SHORT).show();


            return false;

        }

        return true;

    }


    private void consumeResponse(ApiResponse apiResponse) {
        if (apiResponse==null) return;
        switch (apiResponse.status) {

            case LOADING:
                showProgressBar();
                break;


            case SUCCESS:
                hideProgressBar();
                LoginResponse response = (LoginResponse) apiResponse.data;
                if (response != null) {
                    if (response.getStatus().equals(1)) {
                        LoginData loginData = response.getData();
                        String uId=loginData.getUserId().toString();
                    } else {
                       // showErrorDialog("Login Failed", response.getMessage());
                    }
                }

                break;


            case ERROR:
                hideProgressBar();
                break;


            default:
                hideProgressBar();
                break;

        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }


    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}

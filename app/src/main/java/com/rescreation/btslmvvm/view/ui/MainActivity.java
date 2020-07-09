package com.rescreation.btslmvvm.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.rescreation.btslmvvm.R;
import com.rescreation.btslmvvm.model.modelclass.ContactData;
import com.rescreation.btslmvvm.model.modelclass.ContactListData;
import com.rescreation.btslmvvm.model.response.ApiResponse;
import com.rescreation.btslmvvm.model.response.ContactTracingDataResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;
import com.rescreation.btslmvvm.model.response.SendContactDataResponse;
import com.rescreation.btslmvvm.room.database.DatabaseInstance;
import com.rescreation.btslmvvm.room.model.ContactTracingModel;
import com.rescreation.btslmvvm.service.BluetoothBackgroundService;
import com.rescreation.btslmvvm.session.SessionManager;
import com.rescreation.btslmvvm.util.GpsUtils;
import com.rescreation.btslmvvm.util.Utils;
import com.rescreation.btslmvvm.view.adapter.ContactListAdapter;
import com.rescreation.btslmvvm.viewmodel.MainActivityViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    BluetoothBackgroundService blBackgroundService;
    Button startButton,stopButton,contactListButton;
    private static final String TAG = "StartActivity";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int Location_PERMISSION_CODE = 100;
    boolean boolean_gps = false;
    boolean boolean_permission = false;
    boolean serviceIsBound=false;
    SessionManager session;
    LinearLayout logoutLayout;
    List<ContactTracingModel> listContactTracingModel;
    boolean isServiceRunningInBackground=false;
    private DatabaseInstance databaseInstance;

    Tracker mTracker;
  //  FirebaseRemoteConfig mfirebaseRemoteConfig;
    Context mContext;
    Chronometer tvStopWatch;
    List<ContactData> contactList;
    String errors = "",uid="",serviceRunning="";
    private ContactListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    ProgressBar progressBar;
    LinearLayout backLayout;
    private RecyclerView recyclerView;
    TextView tvTitle,tvEmptyMsg;
    RelativeLayout mainLayout;
    boolean startService=false;
    CountDownTimer cTimer_1 = null;
    LinearLayout tableHeaderLayout;

    String[] permissionsRequired = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    int locationMode = -1;
    boolean isGPS;
    SharedPreferences serviceSharedPreference;
    long prevTime;
    MainActivityViewModel mainActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=MainActivity.this;

        initView();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mContext = LocaleHelper.setLocale(mContext, "en");
                if (!startService) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (boolean_permission) {

                            startFgService();

                        } else {

                            checkPermission(permissionsRequired,
                                    Location_PERMISSION_CODE);
                        }
                    } else {

                        startFgService();

                    }


                }
                else{
                    startService=false;
                    startButton.setBackgroundResource(R.drawable.start_button);
                    tvStopWatch.setBase(SystemClock.elapsedRealtime());
                    tvStopWatch.stop();

                    Intent serviceIntent = new Intent(mContext, BluetoothBackgroundService.class);
                    stopService(serviceIntent);
//                    if (serviceIsBound) {
//                        getApplicationContext().stopService(new Intent(StartActivity.this, BluetoothBackgroundService.class));
//                        getApplicationContext().unbindService(serviceConnection);
//                        serviceIsBound = false;
//                        Log.d(TAG, "Service Closed");
//
//                    }

                }
            }

        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBox();

            }
        });

    }
    public void startFgService(){

        startService = true;
        tvStopWatch.setBase(SystemClock.elapsedRealtime());
        tvStopWatch.start();
        startButton.setBackgroundResource(R.drawable.stop_button);

//        final Intent intent = new Intent(StartActivity.this, BluetoothBackgroundService.class);
//        getApplicationContext().startService(intent);
//        getApplicationContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        Intent serviceIntent = new Intent(this, BluetoothBackgroundService.class);
        ContextCompat.startForegroundService(this, serviceIntent);

        SharedPreferences.Editor editor = serviceSharedPreference.edit();
        editor.putString("prevTime", String.valueOf(System.currentTimeMillis()));
        editor.commit();
    }

    private void initView() {
      //  mContext = LocaleHelper.setLocale(mContext, "en");
        Resources resources = mContext.getResources();

        checkLocationServicesIsEnabled();

        session = new SessionManager(getApplicationContext());
        listContactTracingModel = new ArrayList<>();
        databaseInstance = DatabaseInstance.getInstance(mContext);

        startButton=findViewById(R.id.startButton);
        stopButton=findViewById(R.id.stopButton);
        contactListButton=findViewById(R.id.contactList);
        logoutLayout =(LinearLayout)findViewById(R.id.logoutll);
        tvStopWatch =findViewById(R.id.tvStopWatch);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        tvEmptyMsg = (TextView)findViewById(R.id.tvEmptyMsg);
        mainLayout = findViewById(R.id.mainLayout);
        tableHeaderLayout = findViewById(R.id.tableHeaderLayout);
        contactList = new ArrayList<>();
        tableHeaderLayout.setVisibility(View.GONE);


        SharedPreferences sharedpreferences = mContext.getSharedPreferences("MYPREF", MODE_PRIVATE);
        uid = sharedpreferences.getString("UID","");

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        setupRecyclerView();

        serviceSharedPreference = mContext.getSharedPreferences("SERVICE", MODE_PRIVATE);

        isServiceRunningInBackground=isMyServiceRunning(BluetoothBackgroundService.class);
        if (isServiceRunningInBackground){
            startButton.setBackgroundResource(R.drawable.stop_button);
            startService=true;
            prevTime = Long.parseLong(serviceSharedPreference.getString("prevTime",""));
            long difference = System.currentTimeMillis()-prevTime;
            tvStopWatch.setBase(SystemClock.elapsedRealtime()- difference);
            tvStopWatch.start();

        }


        mainActivityViewModel.checkContactData().observe(this, apiResponse -> {
            System.out.println("Observer Working properly");
            consumeResponse(apiResponse);
        });

    }
    public void setupRecyclerView(){
        adapter = new ContactListAdapter(getApplicationContext(),contactList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

    }
    public void checkLocationServicesIsEnabled() {

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission(String[] permission, int requestCode)
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions((Activity) mContext, permissionsRequired, requestCode);
        }
        else {
            boolean_permission = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int mode = Settings.Secure.getInt(getApplicationContext().getContentResolver(), Settings.Secure.LOCATION_MODE,
                        Settings.Secure.LOCATION_MODE_OFF);
                if (mode<1){
                    checkLocationServicesIsEnabled();
                    Toast.makeText(mContext, "Please Turn location On and set Device location Mode to High Accuracy"+mode, Toast.LENGTH_SHORT).show();
                }
                else{
                    startFgService();
                }
            }
            else{
                startFgService();
            }


        }
    }

    private void  ongpsPermission() {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent,6);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        String permission = permissions[0];
        if (requestCode == Location_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            /*    Toast.makeText(QuarantineSubmit.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();*/

                boolean_permission = true;
            }
            else {
                Toast.makeText(this,
                        "Location Permission Required",
                        Toast.LENGTH_SHORT)
                        .show();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    boolean showRationale = shouldShowRequestPermissionRationale( permission );

                    if (! showRationale) {

                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", getPackageName(), null)));

                    } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                        Toast.makeText(this, "Please Allow Permission", Toast.LENGTH_SHORT).show();


                    }

                }

            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            System.out.println(service.service.getClassName());
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
        super.onBackPressed();
    }
    public void showDialogBox(){
   //     Context context = LocaleHelper.setLocale(this, "en");
        Resources resources = mContext.getResources();

        // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        alertDialogBuilder.setMessage(resources.getString(R.string.logout_msg));


        alertDialogBuilder.setPositiveButton(resources.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i = new Intent(mContext, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        session.logoutUser();


                    }
                });

        alertDialogBuilder.setNegativeButton(resources.getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
    }

    public void startTimer_1(int time) {
        cTimer_1 = new CountDownTimer(time,1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {

                SendContactDataToServer();
                startTimer_1(5*60*1000);
                Log.d(TAG, "Get Data Called from Timer");


            }
        };
        cTimer_1.start();
    }

    private void SendContactDataToServer() {
        if(Utils.isNetworkAvailable(mContext)){

            checkContactTracingDB();
            getData();
        }

    }

    private void checkContactTracingDB() {

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(AddLocationActivity.this, "Testing", Toast.LENGTH_SHORT).show();

                listContactTracingModel.clear();
                listContactTracingModel.addAll(databaseInstance.contactTracingDao().getAllContactTracingData());
                System.out.println("Check Database SEND: "+listContactTracingModel.size());


                if(listContactTracingModel.size() == 0){

                }
                else {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            for(int i = listContactTracingModel.size()-1;i>=0;i--){
                                System.out.println("Check Database SEND: "+listContactTracingModel.size()+" value I= "+i);
                            //    ServerCallForContactTracing obj = new ServerCallForContactTracing(mContext);
                              //  obj.sendServer(listContactTracingModel.get(i).getMyUid(),listContactTracingModel.get(i).getContactUid(),listContactTracingModel.get(i).getSend_time(),listContactTracingModel.get(i).getRssi(),i,"Home");
                                mainActivityViewModel.sendContactDataFromServer(listContactTracingModel.get(i).getMyUid(),listContactTracingModel.get(i).getContactUid(),listContactTracingModel.get(i).getSend_time(),listContactTracingModel.get(i).getRssi(),i,"Home");
                            }



                        }
                    });
                }

            }
        });


    }
    private void getData() {

        mainActivityViewModel.getContactDataFromServer(uid);

    }

    private void consumeResponse(ApiResponse apiResponse) {
        if (apiResponse==null) return;
        switch (apiResponse.status) {

            case LOADING:
                showProgressBar();
                break;


            case SUCCESS:
                hideProgressBar();
                System.out.println("Api Response Activity : "+apiResponse.requestType);
                if (apiResponse.requestType.equals("ContactData")) {
                    ContactTracingDataResponse contactTracingDataResponse = (ContactTracingDataResponse) apiResponse.data;
                    if (contactTracingDataResponse != null) {
                        if (contactTracingDataResponse.getStatus().equals(1)) {
                            contactList.clear();
                            tableHeaderLayout.setVisibility(View.VISIBLE);
                            List<ContactData> contactDataList = contactTracingDataResponse.getData();
                            contactList.addAll(contactDataList);
                            adapter.notifyDataSetChanged();
                        } else {
                            tableHeaderLayout.setVisibility(View.GONE);
                        }
                    } else {

                    }
                }
                else if(apiResponse.requestType.equals("SendContactDataHome")){
                    SendContactDataResponse sendContactDataResponse = (SendContactDataResponse) apiResponse.data;
                    if (sendContactDataResponse != null) {
                        if (sendContactDataResponse.getStatus().equals(1)) {
                            System.out.println("Api Response Send ContactDataHome : "+sendContactDataResponse.getMessage());
                        } else {
                            tableHeaderLayout.setVisibility(View.GONE);
                        }
                    } else {

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
    public String convertDateTIme(String dateTime){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse( dateTime );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputStr = new SimpleDateFormat("d-MMM hh:mm aa").format( date );

        return outputStr;
    }
    @Override
    protected void onDestroy() {
        if(cTimer_1!=null)
            cTimer_1.cancel();
        Log.d(TAG, "OnDestroy Called");

        super.onDestroy();

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "ONPause Called");
        if(cTimer_1!=null)
            cTimer_1.cancel();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        startTimer_1(1*1000);
        Log.d(TAG, "onResume Called");
        // put your code here...

    }


    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }


    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}

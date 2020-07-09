package com.rescreation.btslmvvm.model.repository;

import android.app.Application;

import com.rescreation.btslmvvm.model.response.ApiResponse;
import com.rescreation.btslmvvm.model.response.ContactTracingDataResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;
import com.rescreation.btslmvvm.model.response.SendContactDataResponse;
import com.rescreation.btslmvvm.model.retrofit.RetrofitClient;
import com.rescreation.btslmvvm.model.retrofit.RetrofitService;
import com.rescreation.btslmvvm.room.database.DatabaseInstance;
import com.rescreation.btslmvvm.view.ui.MyApplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityRepository {

    public static MainActivityRepository mainActivityRepository;

    public static MainActivityRepository getInstance(){
        if (mainActivityRepository == null){
            mainActivityRepository = new MainActivityRepository();
        }
        return mainActivityRepository;
    }

    public RetrofitService retrofitService;

    public MainActivityRepository(){
        retrofitService = RetrofitClient.cteateService(RetrofitService.class);

    }

    MutableLiveData<ApiResponse>  getContactTracingLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResponse> getContactTracingData(String user_id){

        retrofitService.getContactTracingData(user_id).enqueue(new Callback<ContactTracingDataResponse>() {
            @Override
            public void onResponse(Call<ContactTracingDataResponse> call, Response<ContactTracingDataResponse> response) {
                if (response.isSuccessful()) {
                    getContactTracingLiveData.setValue(ApiResponse.success(response.body(), "ContactData"));

                }
            }

            @Override
            public void onFailure(Call<ContactTracingDataResponse> call, Throwable t) {
                getContactTracingLiveData.setValue(ApiResponse.error(t, null));
            }
        });
        return getContactTracingLiveData;
    }



    public LiveData<ApiResponse> checkContactTracingData() {
        return getContactTracingLiveData;
    }




    MutableLiveData<ApiResponse>  sendContactTracingLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResponse> sendContactTracingData(String myUid,  String contactWithUid,  String dateTime,
                                                                String rssi,  int listSize,  String sourcePAge){

        retrofitService.sendContactTracingData(myUid,contactWithUid,dateTime,rssi).enqueue(new Callback<SendContactDataResponse>() {
            @Override
            public void onResponse(Call<SendContactDataResponse> call, Response<SendContactDataResponse> response) {
                if (response.isSuccessful()) {
                    sendContactTracingLiveData.setValue(ApiResponse.success(response.body(), "SendContactData"+sourcePAge));
                    if(response.body()!=null && response.body().getStatus().equals(1)){
                        if (listSize==0){
                            clearDatabse();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SendContactDataResponse> call, Throwable t) {
                sendContactTracingLiveData.setValue(ApiResponse.error(t, null));
            }
        });
        return sendContactTracingLiveData;
    }

    public LiveData<ApiResponse> checkContactTracingSendData() {
        return sendContactTracingLiveData;
    }


    private void clearDatabse() {

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    DatabaseInstance databaseInstance;
                    databaseInstance = DatabaseInstance.getInstance(MyApplication.getContext());
                    databaseInstance.contactTracingDao().clear();
                    System.out.println("Clear Local Database for Contact Tracing: ");

                }
                catch (Exception e){
                }




            }
        });


    }
}

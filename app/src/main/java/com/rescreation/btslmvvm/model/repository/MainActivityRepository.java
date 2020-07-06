package com.rescreation.btslmvvm.model.repository;

import com.rescreation.btslmvvm.model.response.ContactTracingDataResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;
import com.rescreation.btslmvvm.model.retrofit.RetrofitClient;
import com.rescreation.btslmvvm.model.retrofit.RetrofitService;

import androidx.lifecycle.MutableLiveData;
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


    public MutableLiveData<ContactTracingDataResponse> getContactTracingData(String user_id){
        MutableLiveData<ContactTracingDataResponse>  mutableLiveData = new MutableLiveData<>();
        retrofitService.getContactTracingData(user_id).enqueue(new Callback<ContactTracingDataResponse>() {
            @Override
            public void onResponse(Call<ContactTracingDataResponse> call, Response<ContactTracingDataResponse> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ContactTracingDataResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}

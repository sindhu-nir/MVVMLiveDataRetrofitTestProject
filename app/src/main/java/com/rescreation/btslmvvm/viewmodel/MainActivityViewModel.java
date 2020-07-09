package com.rescreation.btslmvvm.viewmodel;

import android.app.Application;

import com.rescreation.btslmvvm.model.repository.LoginRepository;
import com.rescreation.btslmvvm.model.repository.MainActivityRepository;
import com.rescreation.btslmvvm.model.response.ApiResponse;
import com.rescreation.btslmvvm.model.response.ContactTracingDataResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mainActivityRepository = MainActivityRepository.getInstance();

    }


    private MainActivityRepository mainActivityRepository;

    public MutableLiveData<ApiResponse> getContactDataFromServer(String user_id){

        return mainActivityRepository.getContactTracingData(user_id);


    }
    public LiveData<ApiResponse> checkContactData() {
        return mainActivityRepository.checkContactTracingData();
    }


    public MutableLiveData<ApiResponse> sendContactDataFromServer(String myUid, String contactWithUid, String dateTime,
                                                                  String rssi,  int listSize,  String sourcePAge){
        return mainActivityRepository.sendContactTracingData(myUid,contactWithUid,dateTime,
         rssi,listSize,sourcePAge);


    }
    public LiveData<ApiResponse> checkContactTracingSendData() {
        return mainActivityRepository.checkContactTracingSendData();
    }
}

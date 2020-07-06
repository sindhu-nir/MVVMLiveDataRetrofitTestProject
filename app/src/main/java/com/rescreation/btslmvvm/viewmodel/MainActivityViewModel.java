package com.rescreation.btslmvvm.viewmodel;

import android.app.Application;

import com.rescreation.btslmvvm.model.repository.LoginRepository;
import com.rescreation.btslmvvm.model.repository.MainActivityRepository;
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


    private MutableLiveData<ContactTracingDataResponse> mutableLiveData=new MutableLiveData<>();
    private MainActivityRepository mainActivityRepository;

    public void init(String user_id){
//        if (mutableLiveData != null){
//            return;
//        }
        mutableLiveData = mainActivityRepository.getContactTracingData(user_id);

    }
    public LiveData<ContactTracingDataResponse> getContactData() {
        return mutableLiveData;
    }
}

package com.rescreation.btslmvvm.viewmodel;

import android.app.Application;

import com.rescreation.btslmvvm.model.repository.LoginRepository;
import com.rescreation.btslmvvm.model.repository.PinNumberRepository;
import com.rescreation.btslmvvm.model.response.LoginResponse;
import com.rescreation.btslmvvm.model.response.PinNumberReponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PinNumberViewModel extends AndroidViewModel {
    public PinNumberViewModel(@NonNull Application application) {
        super(application);
        pinNumberRepository = PinNumberRepository.getInstance();

    }

    private MutableLiveData<PinNumberReponse> mutableLiveData=new MutableLiveData<>();
    private PinNumberRepository pinNumberRepository;

    public void init(String pin,String uuid,String mobile_no){

        mutableLiveData = pinNumberRepository.verifyPinNumber(uuid,mobile_no,pin);

    }

    public LiveData<PinNumberReponse> getPinVerifyData() {
        return mutableLiveData;
    }
}

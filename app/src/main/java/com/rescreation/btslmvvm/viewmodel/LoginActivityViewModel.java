package com.rescreation.btslmvvm.viewmodel;

import android.app.Application;

import com.rescreation.btslmvvm.model.repository.LoginRepository;
import com.rescreation.btslmvvm.model.response.ApiResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginActivityViewModel extends AndroidViewModel {



    private MutableLiveData<LoginResponse> mutableLiveData=new MutableLiveData<>();
    private LoginRepository loginRepository;

    public LoginActivityViewModel(Application application) {
        super(application);
        loginRepository = LoginRepository.getInstance();
    }

//    public void init(String mobile_no,String full_name){
////        if (mutableLiveData != null){
////            return;
////        }
//
//        mutableLiveData = loginRepository.checkLoginRepo(mobile_no,full_name);
//
//    }

    public MutableLiveData<ApiResponse> init2(String mobile_no,String full_name){
        return loginRepository.checkLoginRepo2(mobile_no,full_name);

    }

//    public LiveData<LoginResponse> checkLoginRepository() {
//        return mutableLiveData;
//    }

    public LiveData<ApiResponse> checkLoginRepository2() {
        return loginRepository.checkLoginLiveData();
    }
}

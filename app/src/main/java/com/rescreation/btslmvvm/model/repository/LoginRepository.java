package com.rescreation.btslmvvm.model.repository;

import com.rescreation.btslmvvm.model.response.ApiResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;
import com.rescreation.btslmvvm.model.retrofit.RetrofitClient;
import com.rescreation.btslmvvm.model.retrofit.RetrofitService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    public static LoginRepository loginRepository;
    MutableLiveData<ApiResponse>  mutableLiveData2 ;
    public static LoginRepository getInstance(){
        if (loginRepository == null){
            loginRepository = new LoginRepository();
        }
        return loginRepository;
    }

    public RetrofitService retrofitService;

    public LoginRepository(){
        retrofitService = RetrofitClient.cteateService(RetrofitService.class);
        mutableLiveData2 = new MutableLiveData<>();

    }


    public MutableLiveData<LoginResponse> checkLoginRepo(String mobile_no,String full_name){
        MutableLiveData<LoginResponse>  mutableLiveData = null;
        retrofitService.checkLogin(mobile_no,full_name).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                    System.out.println("Login Repo Called");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<ApiResponse> getLoginDataFromServer(String mobile_no, String full_name){

        mutableLiveData2.setValue(ApiResponse.loading(null));
        retrofitService.checkLogin(mobile_no,full_name).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    mutableLiveData2.setValue(ApiResponse.success(response.body(), "Login"));
                    System.out.println("Api Response Repository : "+response);

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mutableLiveData2.setValue(ApiResponse.error(t, null));
            }
        });
        return mutableLiveData2;
    }

    public LiveData<ApiResponse> checkLoginLiveData() {
        return mutableLiveData2;
    }
}

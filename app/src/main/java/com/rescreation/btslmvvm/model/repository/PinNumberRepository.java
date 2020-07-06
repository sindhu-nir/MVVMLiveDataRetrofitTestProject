package com.rescreation.btslmvvm.model.repository;

import com.rescreation.btslmvvm.model.response.PinNumberReponse;
import com.rescreation.btslmvvm.model.retrofit.RetrofitClient;
import com.rescreation.btslmvvm.model.retrofit.RetrofitService;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinNumberRepository {

    public static PinNumberRepository pinNumberRepository;

    public static PinNumberRepository getInstance(){
        if (pinNumberRepository == null){
            pinNumberRepository = new PinNumberRepository();
        }
        return pinNumberRepository;
    }

    public RetrofitService retrofitService;

    public PinNumberRepository(){
        retrofitService = RetrofitClient.cteateService(RetrofitService.class);

    }


    public MutableLiveData<PinNumberReponse> verifyPinNumber(String user_id,String mobile_no,String pin_no){
        MutableLiveData<PinNumberReponse>  mutableLiveData = new MutableLiveData<>();
        retrofitService.varifyPinNumber(user_id,mobile_no,pin_no).enqueue(new Callback<PinNumberReponse>() {
            @Override
            public void onResponse(Call<PinNumberReponse> call, Response<PinNumberReponse> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PinNumberReponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}

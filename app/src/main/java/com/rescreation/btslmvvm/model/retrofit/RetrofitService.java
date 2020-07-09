package com.rescreation.btslmvvm.model.retrofit;

import android.database.Observable;

import com.rescreation.btslmvvm.model.response.ContactTracingDataResponse;
import com.rescreation.btslmvvm.model.response.LoginResponse;
import com.rescreation.btslmvvm.model.response.PinNumberReponse;
import com.rescreation.btslmvvm.model.response.SendContactDataResponse;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {



    @POST("login")
    Call<LoginResponse> checkLogin(@Query("mobile_no") String mobile_no, @Query("full_name") String full_name);

    @POST("get_contact_tracing_data")
    Call<ContactTracingDataResponse> getContactTracingData(@Query("user_id") String user_id);

    @POST("add_contact_tracing")
    Call<SendContactDataResponse> sendContactTracingData(@Query("user_id") String user_id,@Query("contact_with") String contact_with,
                                                         @Query("contact_time") String contact_time,@Query("strength_value") String strength_value);

    @POST("verify")
    Call<PinNumberReponse> varifyPinNumber(@Query("user_id") String user_id,@Query("mobile_no") String mobile_no,@Query("pin_code") String pin_code);


//    @GET("finsert.php")
//    Call<FkeyEntryResponse> CheckFirebaseToken(@Query("fkey") String fkey);
//
//    @GET("feedback.php")
//    Call<FeedbackResponse> SaveFeedback(@Query("name") String name, @Query("mobile") String mobile, @Query("email") String email,
//                                        @Query("msg") String msg, @Query("fkey") String fkey);
}

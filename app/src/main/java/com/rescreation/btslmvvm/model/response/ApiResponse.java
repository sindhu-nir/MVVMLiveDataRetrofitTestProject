package com.rescreation.btslmvvm.model.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.rescreation.btslmvvm.model.response.Status.ERROR;
import static com.rescreation.btslmvvm.model.response.Status.LOADING;
import static com.rescreation.btslmvvm.model.response.Status.SUCCESS;

public class ApiResponse {

    public final Status status;

    @Nullable
    public final Object data;

    @Nullable
    public final Throwable error;


    public String requestType;



    private ApiResponse(Status status, @Nullable Object data, @Nullable Throwable error, String requestType) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.requestType = requestType;
    }



    public static ApiResponse loading(String requestType) {
        return new ApiResponse(LOADING, null, null, requestType);
    }



    public static ApiResponse success(@NonNull Object data, String requestType) {
        return new ApiResponse(SUCCESS, data, null, requestType);
    }



    public static ApiResponse error(@NonNull Throwable error, String requestType) {
        return new ApiResponse(ERROR, null, error, requestType);
    }

}

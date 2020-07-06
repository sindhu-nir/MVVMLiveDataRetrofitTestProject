package com.rescreation.btslmvvm.model.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactData {

    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("total_contact")
    @Expose
    private Integer totalContact;
    @SerializedName("last_contact_time")
    @Expose
    private String lastContactTime;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getTotalContact() {
        return totalContact;
    }

    public void setTotalContact(Integer totalContact) {
        this.totalContact = totalContact;
    }

    public String getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(String lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

}

package com.rescreation.btslmvvm.model.modelclass;

public class ContactListData {

    private String fullName;
    private String mobileNo;
    private String totalContact;
    private String lastContactTime;

    public ContactListData(String fullName, String mobileNo, String totalContact,String lastContactTime) {
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.totalContact = totalContact;
        this.lastContactTime = lastContactTime;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getTotalContact() {
        return totalContact;
    }

    public String getLastContactTime() {
        return lastContactTime;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setTotalContact(String totalContact) {
        this.totalContact = totalContact;
    }

    public void setLastContactTime(String lastContactTime) {
        this.lastContactTime = lastContactTime;
    }
}

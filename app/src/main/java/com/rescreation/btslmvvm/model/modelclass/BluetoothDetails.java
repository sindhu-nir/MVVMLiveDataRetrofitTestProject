package com.rescreation.btslmvvm.model.modelclass;

public class BluetoothDetails {
    public String bluetoothName="";
    public String rssl="";


    public BluetoothDetails(String bluetoothName, String rssl) {
        this.bluetoothName = bluetoothName;
        this.rssl = rssl;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public String getRssl() {
        return rssl;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public void setRssl(String rssl) {
        this.rssl = rssl;
    }
}

package com.rescreation.btslmvvm.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rescreation.btslmvvm.R;
import com.rescreation.btslmvvm.model.modelclass.BluetoothDetails;
import com.rescreation.btslmvvm.room.database.DatabaseInstance;
import com.rescreation.btslmvvm.room.model.ContactTracingModel;
import com.rescreation.btslmvvm.util.Utils;
import com.rescreation.btslmvvm.view.ui.MyApplication;
import com.rescreation.btslmvvm.viewmodel.MainActivityViewModel;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BluetoothBackgroundService extends Service {

    private final IBinder binder = new BLServiceBinder();
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 1*60*1000;
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;

    private static final String TAG = "ServiceTag";
    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;
    Button btnStartConnection;
    Button btnSend;
    EditText etSend;
    TextView incomingMsgTextView;
    StringBuilder messages;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    BluetoothDevice mBTDevice;
    Handler handler;
    //BluetoothConnectionService mBluetoothConnectionService;
    int NOTIFICATION_ID = 200;
    private DatabaseInstance databaseInstance;
    List<BluetoothDevice> mBTDeviceList;
    List<Integer> rssiList;
    List<BluetoothDetails> mBTDeviceLists;
    Set<String> hash_Set ;
    Context mContext;
    String myUid="",myName="",myMobile="";
    private PowerManager.WakeLock wakeLock;


    public static final String ACTION_PING = BluetoothBackgroundService.class.getName() + ".PING";
    public static final String ACTION_PONG = BluetoothBackgroundService .class.getName() + ".PONG";
    SharedPreferences servicePref;

    @Override
    public void onCreate() {
        //   super.onCreate();

        mContext=BluetoothBackgroundService.this;
        SharedPreferences sharedpreferences = mContext.getSharedPreferences("MYPREF", MODE_PRIVATE);
        myUid = sharedpreferences.getString("UID","");
        myName = sharedpreferences.getString("Name","");
        myMobile = sharedpreferences.getString("Mobile","");

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock= pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BluetoothBackgroundService:lock");
        wakeLock.acquire();

        mBTDeviceList=new ArrayList<BluetoothDevice>();
        mBTDeviceLists=new ArrayList<BluetoothDetails>();
        rssiList=new ArrayList<Integer>();
        hash_Set = new HashSet<String>();
        databaseInstance = DatabaseInstance.getInstance(this);

        messages=new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,new IntentFilter("incomingMessage"));

        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToBluetoothActivity(), 1000, notify_interval);
        intent = new Intent(str_receiver);
        Log.i(TAG, "onCreate Service Called");
        startForeground(12345678, fgNotification());

    }

    public class BLServiceBinder extends Binder {
        public BluetoothBackgroundService getService() {
            return BluetoothBackgroundService.this;
        }
    }

    private class TimerTaskToBluetoothActivity extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        enableDisableBT();

                    }
                }
            });

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver1, new IntentFilter(ACTION_PING));
        super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(mBroadcastReceiver1);
        //  unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
        wakeLock.release();


//        SharedPreferences.Editor editor2 = servicePref.edit();
//        editor2.putString("serviceRunning", "0");
//        editor2.commit();
//
//        System.err.println("Service OnDestroy Called");
        Log.i(TAG, "onDestroy Service Called");
        super.onDestroy();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: enabling BT.");
            mBluetoothAdapter.enable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);

            DiscoverableForOtherDeices();

        }
        if (mBluetoothAdapter.isEnabled()) {

            mBluetoothAdapter.enable();
            Log.d(TAG, "enableDisableBT: Already Enabled.");
            String name = mBluetoothAdapter.getName();
            if (!name.contains("Tracon_")) {
                mBluetoothAdapter.setName("Tracon_"+myUid+"_"+myMobile+"_"+myName);
            }


            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);

            DiscoverableForOtherDeices();
        }

    }


    // Create a BroadcastReceiver for ACTION_FOUND
    final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        mBluetoothAdapter.setName("Bluetooth");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        mBluetoothAdapter.setName("Bluetooth");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        String name=mBluetoothAdapter.getName();
                        Log.d(TAG, "BL Name :"+name);

                        mBluetoothAdapter.setName("Tracon_"+myUid+"_"+myMobile+"_"+myName);
                        DiscoverableForOtherDeices();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;

                }
            }
        }
    };

    public static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            boolean connected = (boolean) m.invoke(device, (Object[]) null);
            return connected;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void DiscoverableForOtherDeices() {
        Method method;
        try {
            method = mBluetoothAdapter.getClass().getMethod("setScanMode", int.class, int.class);
            method.invoke(mBluetoothAdapter,BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE,0);
            Log.e("invoke","method invoke successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }

//        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//        registerReceiver(mBroadcastReceiver2,intentFilter);
        SearchForDevices();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void SearchForDevices(){
        Log.d(TAG, "btnDiscover: Looking for Contact devices.");

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
            //   Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            //    checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            Log.d(TAG, "btnDiscover: Enabling discovery.");

            IntentFilter discoverDevicesIntent = new IntentFilter();
            discoverDevicesIntent.addAction(BluetoothDevice.ACTION_FOUND);
            discoverDevicesIntent.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            discoverDevicesIntent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
            //   startTimer_1(15*1000);
        }
        if(!mBluetoothAdapter.isDiscovering()){

            //check BT permissions in manifest
            // checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter();
            discoverDevicesIntent.addAction(BluetoothDevice.ACTION_FOUND);
            discoverDevicesIntent.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            discoverDevicesIntent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
            // startTimer_1(15*1000);

        }
    }

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");

                        SearchForDevices();
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");

                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");

                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");

                        break;
                }

            }
        }
    };


    private void addTodatabseContetList(final ContactTracingModel listContentType) {


        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {


                // databaseInstance.contactTracingDao().clear();
                databaseInstance.contactTracingDao().singleInsert(listContentType);
                // setContentTypeData();

                Log.d(TAG, "Local DB Added Success:  "+listContentType);


            }
        });

    }

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                String deviceName="";boolean isAlreadyConnected=false;
                deviceName=device.getName();
                Log.d(TAG, "ACTION FOUND : Device Name : "+deviceName+" RSSI :"+rssi);
                //mBTDeviceList.add(device);

                //InsertIntoLocalDB
                if(deviceName!=null && deviceName.contains("Tracon_")){
                    //                      isAlreadyConnected=isConnected(device);
                    //            if (!isAlreadyConnected) {
                    // mBTDevice = device;
                    //  CreateServerConnection(rssi);
                    //      }

                    // Add the name and address to an array adapter
//                        if(!mBTDeviceList.contains(device)){
//                            mBTDeviceList.add(device);
//                            rssiList.add(rssi);
//                        }
                    if (!hash_Set.contains(deviceName)){
                        hash_Set.add(deviceName);
                    }

                }


            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                Log.v(TAG,"Discovery  Finished ");
                //mBluetoothAdapter.startDiscovery();
                String contactUid = "",mobile = "",name="";
                int rssi=0;
                for(String deviceName:hash_Set){
                    String[] parts = deviceName.split("_");
                    contactUid = parts[1];
                    mobile = parts[2];
                    name = parts[3];
                    sendDataAndPushNotification(contactUid,name,mobile,String.valueOf(rssi));
                }
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                //clearing any existing list data.
                hash_Set.clear();
                Log.v(TAG,"Discovery  Started ");
                mBTDeviceList.clear();
                rssiList.clear();

            }
        }
    };


    public boolean CheckDateDifference(String date1,String date2){
        boolean result=false;
        int diff=0;
        Date d1=null;
        Date d2=null;
        SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
//            d1 = sdf.parse(date1);
//            d2 = sdf.parse(date2);
//            diff=d1.compareTo(d2);
//            if(diff >0){
//                result=true;
//
//            }
            d1 = sdf.parse(date1);
            d2 = sdf.parse(date2);
            long diffMs = d1.getTime() - d2.getTime();
            long diffSec = diffMs / 1000;
            long min = diffSec / 60;
            long sec = diffSec % 60;
            System.out.println("The difference is "+min+" minutes and "+sec+" seconds.");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return  result;
    }

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    public void sendDataAndPushNotification(String contactUid,String name,String mobile,String rssi){


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        String dateTime=formatter.format(date);


        if (Utils.isNetworkAvailable(mContext)) {
//            ServerCallForContactTracing obj = new ServerCallForContactTracing(mContext);
//            obj.sendServer(myUid,contactUid, dateTime, rssi,0, "Service");

            Log.d(TAG, "Send Data to Server");

        } else {

            ContactTracingModel contactTracingModel=new ContactTracingModel(myUid,contactUid,name,mobile,dateTime,rssi);
            addTodatabseContetList(contactTracingModel);
            Log.d(TAG, "Saved Data to Local DB");

        }

        GetNotification(name);


    }
    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMsg");

            messages.append(text+"\n");
            //    incomingMsgTextView.setText(messages);

            String[] parts = text.split("_");
            String name = parts[0]; // 004
            String mobile = parts[1];
            String rssi = parts[2];
            String contactUid = parts[3];

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dateTime=formatter.format(date);


            if (Utils.isNetworkAvailable(mContext)) {
//                ServerCallForContactTracing obj = new ServerCallForContactTracing(mContext);
//                obj.sendServer(myUid,contactUid, dateTime, rssi,0, "Service");
                Log.d(TAG, "Send Data to Server");

            } else {

                ContactTracingModel contactTracingModel=new ContactTracingModel(myUid,contactUid,name,mobile,dateTime,rssi);
                addTodatabseContetList(contactTracingModel);
                Log.d(TAG, "Saved Data to Local DB");

            }


            if (!rssi.isEmpty()) {
                int rssiFound = Integer.parseInt(rssi);
                if (rssiFound>-60){


                    GetNotification(name);
                    Log.d(TAG, "Showing Notification msg");

//                        if(mBluetoothConnectionService!=null){
//                            Log.d(TAG, "Shutting down server n client");
//                            mBluetoothConnectionService.stop();
//                        }

                }
            }

//            if(mBluetoothConnectionService!=null){
//                Log.d(TAG, "Shutting down server n client");
//                mBluetoothConnectionService.stop();
//            }

        }
    };

    public void GetNotification(String msg){
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "btracsl_contact";
        String channelName = "contact";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    CHANNEL_ID, channelName, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Contact Alert");
        bigTextStyle.bigText("Alert: Please Keep Distance from "+msg+" to Stay Safe");

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.push_icon)
                .setContentTitle("Contact Alert")
                .setContentText("Alert: Please Keep Distance from "+msg+" to Stay Safe")
                .setStyle(bigTextStyle)
                .setAutoCancel(true)
                ///          .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.tracontact_icon_foreground))
                .setPriority(NotificationCompat.PRIORITY_HIGH).setSound(alarmSound);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        NOTIFICATION_ID =  (int) System.currentTimeMillis();

        notificationManager.notify(NOTIFICATION_ID /* ID of notification */, mBuilder.build());
    }


    private Notification fgNotification() {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        String CHANNEL_ID = "channel_02";
        String channelName = "tracing";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, channelName, importance);
            mNotificationManager.createNotificationChannel(channel);
        }

        // NotificationChannel channel = new NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);

        // NotificationManager notificationManager = getSystemService(NotificationManager.class);
        //notificationManager.createNotificationChannel(channel);


        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.tracontact_icon);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setAutoCancel(true);
        //      .setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }
}

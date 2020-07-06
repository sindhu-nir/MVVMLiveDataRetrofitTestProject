package com.rescreation.btslmvvm.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rescreation.btslmvvm.R;
import com.rescreation.btslmvvm.view.ui.LoginActivity;

import androidx.core.content.ContextCompat;

public class Utils {

    public static void showSnackBar(Context context, LinearLayout mainLayout, String msg, String btnText, int length){
        //-2 undefined, -1 short, 0 long

//        Context context = LocaleHelper.setLocale(Login.this, "bn");
        Resources resources = context.getResources();

        Snackbar snackbar = Snackbar
                .make(mainLayout, msg, length )
                .setAction(resources.getText(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.home_yellow));

        // Changing action button text color
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_edittext));

        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.edittext_text));
        textView.setMaxLines(5);
        snackbar.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

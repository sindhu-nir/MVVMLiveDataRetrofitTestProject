package com.rescreation.btslmvvm.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rescreation.btslmvvm.R;
import com.rescreation.btslmvvm.model.modelclass.ContactData;
import com.rescreation.btslmvvm.model.modelclass.ContactListData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private final List<ContactData> contactList;
    String refURL = "";
    private static final String TAG = "StartActivity";

    public ContactListAdapter(Context context, List<ContactData> list){
        this.context = context;
        this.contactList = list;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ContactListAdapter.ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactListAdapter.ContactListViewHolder(inflater.inflate(R.layout.tablelayout,parent,false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactListViewHolder holder, final int position) {
        ContactListViewHolder rowViewHolder = (ContactListViewHolder) holder;

        final int rowPos = rowViewHolder.getAdapterPosition();

        ContactData arrayItem = contactList.get(position);

        String convertedTime=convertDateTIme(arrayItem.getLastContactTime());

        holder.txtName.setText(arrayItem.getFullName());
        holder.txtTime.setText(convertedTime);
        // rowViewHolder.txtMobile.setText(ConvertTextToAppLanguage.convertEnglishToBangla(arrayItem.getMobileNo()));
        holder.txtMobile.setText(arrayItem.getMobileNo());
        holder.txtCount.setText(arrayItem.getTotalContact().toString());
        // rowViewHolder.txtCount.setText(ConvertTextToAppLanguage.convertEnglishToBangla(arrayItem.getTotalContact()));
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Item Clicked"+(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactListViewHolder extends RecyclerView.ViewHolder {

        TextView txtName,txtMobile,txtCount,txtTime;
        ImageView ivIcon;
        LinearLayout rowLayout;
        public ContactListViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            txtCount = (TextView) itemView.findViewById(R.id.txtCount);
            rowLayout = (LinearLayout) itemView.findViewById(R.id.rowLayout);

            int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
         //   int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            if (width>1000){
                txtName.setTextSize(10);
                txtTime.setTextSize(10);
                txtMobile.setTextSize(10);
                txtCount.setTextSize(10);
            }

        }
    }

    public String convertDateTIme(String dateTime){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse( dateTime );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputStr = new SimpleDateFormat("d-MMM hh:mm aa").format( date );

        return outputStr;
    }
}

package com.t.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    private Context context;
    OnItemClick onItemClick;
    private ArrayList<String> driverResponses;
    int count1;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    boolean isEdit,isSelectAll;
    String commaSeperated;
    public NotificationListAdapter(boolean isSelectAll, boolean isEdit, Context context, ArrayList<String> driverResponses, OnItemClick onItemClick) {
        this.isSelectAll=isSelectAll;
        this.isEdit=isEdit;
        this.context = context;
        this.driverResponses = driverResponses;
        this.onItemClick=onItemClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtMsgDesc,txtIndex,txtDateTime;
        ImageView imgDelete;
        View viewLine;
        CheckBox checkBoxItem;
        CardView cardDrive;


        public MyViewHolder(View view) {
            super(view);
//            txtDateTime = view.findViewById(R.id.txtDateTime);
            //txtIndex = view.findViewById(R.id.txtIndex);
            txtMsgDesc = view.findViewById(R.id.txtMsgDesc);
            cardDrive = view.findViewById(R.id.cardDrive);
            checkBoxItem=view.findViewById(R.id.checkboxItem);
            imgDelete=view.findViewById(R.id.imgDelete);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_list_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

       // final NotificationData allDriveList = driverResponses.get(position);
        holder.txtMsgDesc.setText(driverResponses.get(position));

        if (isEdit) {
            holder.checkBoxItem.setVisibility(View.VISIBLE);
            holder.imgDelete.setVisibility(View.VISIBLE);
        }else
        {
            holder.checkBoxItem.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
        }

        final StringBuilder builder=new StringBuilder();


        if (isSelectAll)
        {
            holder.checkBoxItem.setChecked(true);
            count1=driverResponses.size();
            onItemClick.onClickType(count1);
            ((MainActivity)context).LoadSelecteddata(driverResponses.get(position));
        }
        else {
            holder.checkBoxItem.setChecked(false);
        }


        holder.checkBoxItem.setTag(position);
        holder.checkBoxItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBoxItem.isChecked())
                {
                    count1=count1+1;
                    final int pos= Integer.parseInt(v.getTag()+"");
                    Log.e("CountCheck", String.valueOf(count1));
                    ((MainActivity)context).LoadSelecteddata(driverResponses.get(position));

                }
                if (!holder.checkBoxItem.isChecked())
                {
                    count1=count1-1;
                    Log.e("CountCheck", String.valueOf(count1));
                    final int pos= Integer.parseInt(v.getTag()+"");
                    ((MainActivity)context).UnLoadUnSelecteddata(driverResponses.get(position));
                    isSelectAll=false;
                }

                if (count1==driverResponses.size())
                    isSelectAll=true;
                onItemClick.onClickType(count1);



            }
        });




        holder.imgDelete.setTag(position);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos= Integer.parseInt(v.getTag()+"");
                driverResponses.remove(position);
                ((MainActivity)context).DeleteSinglFRomAdapter(driverResponses);
                notifyDataSetChanged();

            }
        });


    }
    public void removeAt(int position) {
        driverResponses.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, driverResponses.size());
    }

        public void isEdit(boolean isEdit)
        {
            this.isEdit=isEdit;
        }
        public void isSelectAll(boolean isSelectAll)
        {
            this.isSelectAll=isSelectAll;
        }
        public void resetCount(int count1)
        {
            this.count1=count1;
            //notifyDataSetChanged();
        }

    @Override
    public int getItemCount() {
        return driverResponses.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
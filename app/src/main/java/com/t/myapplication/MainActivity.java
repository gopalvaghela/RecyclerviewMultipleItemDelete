package com.t.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnItemClick {

    private ImageView imgProfile, imgBack, imgDelete;
    private Toolbar toolbar;
    private TextView txtTitle;
    private RecyclerView notiListDrive;
    TextView txtNoDrive, txtClearall, txtEdit, txtdelete,txtReload;
    boolean isEdit = false;
    boolean isSelectAll = false;
    int count;

    ArrayList<String> allDriveLists = new ArrayList<>();
    ArrayList<String> allDriveLists2 = new ArrayList<>();
    NotificationListAdapter applicantDriveListAdapter;
    ArrayList<Integer> deleteid = new ArrayList<>();

    
    int page = 1;

    String notiId;
    String uid;
    boolean isFromNotificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtClearall = findViewById(R.id.txtClearall);
        txtEdit = findViewById(R.id.txtEdit);
        imgDelete = findViewById(R.id.imgDelete);
        txtdelete = findViewById(R.id.txtdelete);
        txtReload=findViewById(R.id.txtRload);
        txtdelete.setText("delete");
        txtEdit.setVisibility(View.VISIBLE);

        txtReload.setVisibility(View.GONE);
        txtReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadData();
                txtReload.setVisibility(View.GONE);
                if (isEdit) {
                    txtClearall.setVisibility(View.VISIBLE);

                }
                applicantDriveListAdapter.notifyDataSetChanged();
            }
        });
        txtClearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSelectAll) {
                    isSelectAll = false;
                    txtClearall.setText("selectall");
                    txtdelete.setVisibility(View.GONE);
                } else {
                    isSelectAll = true;
                    txtdelete.setVisibility(View.VISIBLE);
                    txtClearall.setText("deselectall");
                }
                applicantDriveListAdapter.isSelectAll(isSelectAll);
                applicantDriveListAdapter.notifyDataSetChanged();


            }
        });

        txtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                txtdelete.setVisibility(View.GONE);
                txtClearall.setVisibility(View.GONE);
                if (isSelectAll && count==allDriveLists.size())
                { allDriveLists.clear();
                    applicantDriveListAdapter.notifyDataSetChanged();
                    applicantDriveListAdapter.resetCount(0);
                }else{
                    for (int i=0;i<allDriveLists.size();i++)
                    {
                        if(allDriveLists2.size()>0)
                        {
                          for (int d=0;d<allDriveLists2.size();d++)
                          {
                              if (allDriveLists.get(i).equals(allDriveLists2.get(d)))
                              {
                                  applicantDriveListAdapter.removeAt(i);

                              }
                          }
                        }

                    }
                    applicantDriveListAdapter.resetCount(0);
                    //applicantDriveListAdapter.notifyDataSetChanged();
                }
                txtdelete.setText("Delete");
                if (allDriveLists.size()>0)
                    txtReload.setVisibility(View.GONE);
                else
                    txtReload.setVisibility(View.VISIBLE);
                allDriveLists2.clear();

                count=0;


            }
        });


        notiListDrive = findViewById(R.id.notiListDrive);
        notiListDrive.setVisibility(View.VISIBLE);


        //imgProfile.setOnClickListener(this);
        initializeAdapter();
/*
        applicantDriveListAdapter = new NotificationListAdapter(isSelectAll, isEdit, this, allDriveLists, this);
        notiListDrive.setAdapter(applicantDriveListAdapter);
        notiListDrive.setLayoutManager(new LinearLayoutManager(this));*/


        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSelectAll = false;

                if (isEdit) {
                    isEdit = false;
                    txtEdit.setText("delete");
                    txtClearall.setVisibility(View.GONE);
                    txtdelete.setVisibility(View.GONE);
                    applicantDriveListAdapter.resetCount(0);

                } else {
                    isEdit = true;
                    txtEdit.setText("cancel");
                    txtClearall.setVisibility(View.VISIBLE);
                    if (isSelectAll)
                    txtClearall.setText("deselectall");
                    else
                        txtClearall.setText("selectall");
                     txtdelete.setVisibility(View.VISIBLE);
                }

                applicantDriveListAdapter.isEdit(isEdit);
                applicantDriveListAdapter.notifyDataSetChanged();
                if (allDriveLists.size()>0)
                    txtReload.setVisibility(View.GONE);
                else
                    txtReload.setVisibility(View.VISIBLE);


            }
        });


    }
    public void DeleteSinglFRomAdapter(ArrayList<String> allDriveListsupdate)
    {
        //allDriveLists.clear();
        this.allDriveLists=allDriveListsupdate;
        if (allDriveLists.size()>0)
            txtReload.setVisibility(View.GONE);
        else {
            txtReload.setVisibility(View.VISIBLE);
            txtdelete.setVisibility(View.GONE);
            txtClearall.setVisibility(View.GONE);
        }
      //  applicantDriveListAdapter.notifyDataSetChanged();
    }
    public void LoadSelecteddata(String name)
    {
        allDriveLists2.add(name);
    }
    public void UnLoadUnSelecteddata(String name)
    {
        if (allDriveLists2.size()>0)
        {
            for (int i=0;i<allDriveLists2.size();i++)
            {
                if (allDriveLists2.get(i)==name)
                {
                    allDriveLists2.remove(i);
                }
            }
        }
    }
    public void LoadData()
    {
        for (int i=0;i<10;i++)
        {
            allDriveLists.add(i,"Item: "+i);
        }

    }
    private void initializeAdapter() {
        LoadData();

        applicantDriveListAdapter = new NotificationListAdapter(isSelectAll, isEdit, this, allDriveLists, this);
        notiListDrive.setAdapter(applicantDriveListAdapter);
        notiListDrive.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClickType(int position) {

        count = position;

        if (position > 0) {
            txtdelete.setVisibility(View.VISIBLE);
            txtdelete.setText("Delete(" + count + ")");
        } else {
            txtdelete.setVisibility(View.GONE);
            txtClearall.setText("selectall");
            isSelectAll = false;
            //txtdelete.setText("Delete");

        }
        if (isEdit) {
            if (isSelectAll)
                txtClearall.setText("deselectall");
            else
                txtClearall.setText("selectall");
            txtdelete.setVisibility(View.VISIBLE);
        }
        else {
            txtdelete.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCheckIn(int position) {

    }

    @Override
    public void onFeedback(int position) {

    }

    @Override
    public void onClickTypeData(int position, String s) {

    }
}

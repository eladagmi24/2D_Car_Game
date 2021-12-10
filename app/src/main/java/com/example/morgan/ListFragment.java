package com.example.morgan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class ListFragment extends Fragment {
    private MaterialTextView list_LBL_title;
    private MyDB myDB;
    private CallBack_List callBackList;
    private MaterialTextView[] records = new MaterialTextView[10];
    private MapFragment map = new MapFragment();

    public void setCallBackList(CallBack_List callBackList) {
        this.callBackList = callBackList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initViews();
        Bundle bundle = this.getArguments();
        String fromJSON = bundle.getString("myDB");
        myDB = new Gson().fromJson(fromJSON,MyDB.class);
        for(int i = 0; i < records.length; i++)
            if (i < myDB.getRecords().size())
                records[i].setText(myDB.getRecords().get(i).toString());
        return view;
    }

    public void setTitle(String str) {
        list_LBL_title.setText(str);
    }

    private void initViews() {
        records[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               map.onClicked(myDB.getRecords().get(0));
               Log.d("myDB", " " + myDB.getRecords().get(0).getScore());
            }
        });
    }


    private void findViews(View view) {
        list_LBL_title = view.findViewById(R.id.list_LBL_title);
        records[0] = view.findViewById(R.id.list_LBL_record1);
        records[1] = view.findViewById(R.id.list_LBL_record2);
        records[2] = view.findViewById(R.id.list_LBL_record3);
        records[3] = view.findViewById(R.id.list_LBL_record4);
        records[4] = view.findViewById(R.id.list_LBL_record5);
        records[5] = view.findViewById(R.id.list_LBL_record6);
        records[6] = view.findViewById(R.id.list_LBL_record7);
        records[7] = view.findViewById(R.id.list_LBL_record8);
        records[8] = view.findViewById(R.id.list_LBL_record9);
        records[9] = view.findViewById(R.id.list_LBL_record10);
    }
}
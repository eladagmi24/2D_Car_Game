package com.example.morgan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class ListFragment extends Fragment {
    private TextInputLayout list_EDT_name;
    private MaterialTextView list_LBL_title;
    private MaterialTextView list_TXT_distance;
    private MaterialTextView list_TXT_coins;
    private MyDB myDB;
    private MaterialButton list_BTN_enter;

    private CallBack_List callBackList;

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
        list_TXT_coins.setText("Coins: " + bundle.getString("coins"));
        list_TXT_distance.setText("Distance: " + bundle.getString("distance"));
        myDB = new MyDB();
        return view;
    }

    private String getName() {
        return list_EDT_name.getEditText().getText().toString();
    }

    public void setTitle(String str) {
        list_LBL_title.setText(str);
    }

    private void initViews() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackList != null)
                    callBackList.setMainTitle(getName());
            }
        };
        list_BTN_enter.setOnClickListener(listener);
//        myDB.getRecords().add(new Record().setName(list_EDT_name.getEditText().getText().toString())
//                .setScore(list_TXT_distance)
//                .setTime()
//                .setLat()
//                .setLon());
    }

    private void findViews(View view) {
        list_LBL_title = view.findViewById(R.id.list_LBL_title);
        list_EDT_name = view.findViewById(R.id.list_EDT_name);
        list_BTN_enter = view.findViewById(R.id.list_BTN_enter);
        list_TXT_distance = view.findViewById(R.id.list_TXT_distance);
        list_TXT_coins = view.findViewById(R.id.list_TXT_coins);
    }
}
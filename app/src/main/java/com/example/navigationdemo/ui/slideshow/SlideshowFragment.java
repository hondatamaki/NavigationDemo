package com.example.navigationdemo.ui.slideshow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.navigationdemo.R;
import com.example.navigationdemo.dao.UserDao;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class SlideshowFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener, View.OnClickListener {
Switch lock;
EditText wendu,shidu,guangzhao,ranqi,yanwu,pm25,co2,hongwai;
UserDao dao;
Button bupdate;
int s;
boolean jc=false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_slideshow, container, false);
        lock=view.findViewById(R.id.lock);
        wendu=view.findViewById(R.id.wendu1);
        shidu=view.findViewById(R.id.shidu1);
        guangzhao=view.findViewById(R.id.guangzhao1);
        ranqi=view.findViewById(R.id.ranqi1);
        yanwu=view.findViewById(R.id.yanwu1);
        pm25=view.findViewById(R.id.pm251);
        co2=view.findViewById(R.id.co21);
        hongwai=view.findViewById(R.id.hongwai1);
        bupdate=view.findViewById(R.id.boradIdupdate);
        dao=new UserDao(getActivity().getApplicationContext());
        bupdate.setOnClickListener(this);
        lock.setOnCheckedChangeListener(this);
        lock.setOnFocusChangeListener(this);
        wendu.setEnabled(false);
        shidu.setEnabled(false);
        guangzhao.setEnabled(false);
        ranqi.setEnabled(false);
        yanwu.setEnabled(false);
        co2.setEnabled(false);
        pm25.setEnabled(false);
        hongwai.setEnabled(false);
        s=1;
        Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                jiance();
            }
        };
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Message msg=new Message();
                handler.sendMessage(msg);
            }
        };
        Timer timer=new Timer();
        timer.schedule(task,0,100);
        return view;
    }

    private void jiance() {
        if (wendu.isEnabled()
                ||shidu.isEnabled()
                ||guangzhao.isEnabled()
                ||yanwu.isEnabled()
                ||pm25.isEnabled()
                ||co2.isEnabled()
                ||ranqi.isEnabled()
                ||hongwai.isEnabled()){
            jc=true;
        }else {
            jc=false;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.lock:
                if (lock.isChecked()){
                    wendu.setEnabled(false);
                    shidu.setEnabled(false);
                    guangzhao.setEnabled(false);
                    ranqi.setEnabled(false);
                    yanwu.setEnabled(false);
                    co2.setEnabled(false);
                    pm25.setEnabled(false);
                    hongwai.setEnabled(false);
                    s=1;
                }else {
                    wendu.setEnabled(true);
                    shidu.setEnabled(true);
                    guangzhao.setEnabled(true);
                    ranqi.setEnabled(true);
                    yanwu.setEnabled(true);
                    co2.setEnabled(true);
                    pm25.setEnabled(true);
                    hongwai.setEnabled(true);
                    s=0;
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.lock:
                if (lock.isChecked()){
                    wendu.setEnabled(false);
                    shidu.setEnabled(false);
                    guangzhao.setEnabled(false);
                    ranqi.setEnabled(false);
                    yanwu.setEnabled(false);
                    co2.setEnabled(false);
                    pm25.setEnabled(false);
                    hongwai.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boradIdupdate:
                if (bupdate.isClickable()){
                    if (s==1){
                        Snackbar.make(view, "请取消锁定", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }else {
                        if (TextUtils.isEmpty(wendu.getText().toString())
                                || TextUtils.isEmpty(shidu.getText().toString())
                                || TextUtils.isEmpty(guangzhao.getText().toString())
                                || TextUtils.isEmpty(yanwu.getText().toString())
                                || TextUtils.isEmpty(pm25.getText().toString())
                                || TextUtils.isEmpty(co2.getText().toString())
                                || TextUtils.isEmpty(ranqi.getText().toString())
                                || TextUtils.isEmpty(hongwai.getText().toString())){
                            Snackbar.make(view, "请输入数据", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                    }
                }
                break;
        }
    }
}
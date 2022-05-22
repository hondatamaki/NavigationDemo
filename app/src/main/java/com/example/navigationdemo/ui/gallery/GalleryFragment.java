package com.example.navigationdemo.ui.gallery;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.navigationdemo.R;
import com.example.navigationdemo.databinding.FragmentGalleryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GalleryFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Vibrator mvibrator;
    String shedengbanhao,baojingdengbanhao,menjinbanhao,fengshanbanhao,chuanglianbanhao,hongwaibanhao,hongwaitongdao;
    Switch shedeng,baojingdeng,fengshan;
    EditText shedengid,baojingdengid,menjinid,fengshanid,chuanglianid,hongwaiid,ethongwaitongdao;
    ToggleButton menjin,curtainon,curtainoff,curtainstop,hongwai,shedengch1,shedengch2,shedengch3;
    FloatingActionButton change;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gallery, container, false);

        mvibrator=(Vibrator)getActivity().getSystemService(Service.VIBRATOR_SERVICE);

        shedeng=view.findViewById(R.id.shedeng);
        baojingdeng=view.findViewById(R.id.baojingdeng);
        menjin=view.findViewById(R.id.menjin);
        fengshan=view.findViewById(R.id.fengshan);
        curtainon=view.findViewById(R.id.curtainon);
        curtainoff=view.findViewById(R.id.curtainoff);
        curtainstop=view.findViewById(R.id.curtainstop);
        hongwai=view.findViewById(R.id.hongwai);
        shedengid=view.findViewById(R.id.shedeng_id);
        baojingdengid=view.findViewById(R.id.baojingdeng_id);
        menjinid=view.findViewById(R.id.menjin_id);
        fengshanid=view.findViewById(R.id.fengshan_id);
        chuanglianid=view.findViewById(R.id.chuanglian_id);
        hongwaiid=view.findViewById(R.id.hongwai_id);
        ethongwaitongdao=view.findViewById(R.id.hongwai_tongdao);
        shedengch1=view.findViewById(R.id.shedengchannel1);
        shedengch2=view.findViewById(R.id.shedengcannel2);
        shedengch3=view.findViewById(R.id.shedengchannel3);
        change=view.findViewById(R.id.change);
        /*OnClickListener*/
        menjin.setOnClickListener(this);
        hongwai.setOnClickListener(this);
        change.setOnClickListener(this);
        /*OnCheckedChangeListener*/
        shedeng.setOnCheckedChangeListener(this);
        baojingdeng.setOnCheckedChangeListener(this);
        fengshan.setOnCheckedChangeListener(this);

        curtainon.setOnCheckedChangeListener(this);
        curtainoff.setOnCheckedChangeListener(this);
        curtainstop.setOnCheckedChangeListener(this);
        shedengch1.setOnCheckedChangeListener(this);
        shedengch2.setOnCheckedChangeListener(this);
        shedengch3.setOnCheckedChangeListener(this);

        /*板号*/
        shedengbanhao=shedengid.getText().toString();
        baojingdengbanhao=baojingdengid.getText().toString();
        menjinbanhao=menjinid.getText().toString();
        fengshanbanhao=fengshanid.getText().toString();
        chuanglianbanhao=chuanglianid.getText().toString();
        hongwaibanhao=hongwaiid.getText().toString();
        /*红外通道*/
        hongwaitongdao=ethongwaitongdao.getText().toString();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change:
                if(TextUtils.isEmpty(shedengid.getText().toString())
                        || TextUtils.isEmpty(baojingdengid.getText().toString())
                        || TextUtils.isEmpty(menjinid.getText().toString())
                        || TextUtils.isEmpty(fengshanid.getText().toString())
                        || TextUtils.isEmpty(chuanglianid.getText().toString())
                        || TextUtils.isEmpty(hongwaiid.getText().toString())
                        || TextUtils.isEmpty(ethongwaitongdao.getText().toString())){
                    Snackbar.make(view, "板号及通道号不能为空", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    shedengbanhao=shedengid.getText().toString();
                    baojingdengbanhao=baojingdengid.getText().toString();
                    menjinbanhao=menjinid.getText().toString();
                    fengshanbanhao=fengshanid.getText().toString();
                    chuanglianbanhao=chuanglianid.getText().toString();
                    hongwaibanhao=hongwaiid.getText().toString();
                    hongwaitongdao=ethongwaitongdao.getText().toString();
                    Snackbar.make(view, "变更成功", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.menjin:
                ControlUtils.control(ConstantUtil.RFID_Door,menjinbanhao,ConstantUtil.CmdCode_2,ConstantUtil.Channel_1,ConstantUtil.Open);
                Snackbar.make(view, "门禁开", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.hongwai:
                ControlUtils.control(ConstantUtil.Infrared,hongwaibanhao,ConstantUtil.CmdCode_3,hongwaitongdao,ConstantUtil.Open);
                Snackbar.make(view, "红外开", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }



    @Override
    public void onCheckedChanged(CompoundButton view, boolean b) {
        switch (view.getId()){
            case R.id.shedeng:
                if (shedeng.isChecked()){
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                    shedengch1.setChecked(false);
                    shedengch2.setChecked(false);
                    shedengch3.setChecked(false);
                    Snackbar.make(view, "射灯开", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);
                }else {
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
                    Snackbar.make(view, "射灯关", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }
                break;
            case R.id.shedengchannel1:
                if (shedengch1.isChecked()){
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_1,ConstantUtil.Open);
                    shedeng.setChecked(false);
                    shedengch2.setChecked(false);
                    shedengch3.setChecked(false);
                    Snackbar.make(view, "频道一射灯开", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }else {
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_1,ConstantUtil.Close);
                    Snackbar.make(view, "频道一射灯关", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }
                break;
            case R.id.shedengcannel2:
                if (shedengch2.isChecked()){
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_2,ConstantUtil.Open);
                    shedengch1.setChecked(false);
                    shedeng.setChecked(false);
                    shedengch3.setChecked(false);
                    Snackbar.make(view, "频道二射灯开", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }else {
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_2,ConstantUtil.Close);
                    Snackbar.make(view, "频道二射灯关", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }
                break;
            case R.id.shedengchannel3:
                if (shedengch3.isChecked()){
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_3,ConstantUtil.Open);
                    shedengch1.setChecked(false);
                    shedengch2.setChecked(false);
                    shedeng.setChecked(false);
                    Snackbar.make(view, "频道三射灯开", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }else {
                    ControlUtils.control(ConstantUtil.Relay,shedengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_3,ConstantUtil.Close);
                    Snackbar.make(view, "频道三射灯关", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }
                break;
            case R.id.baojingdeng:
                if (baojingdeng.isChecked()){
                    ControlUtils.control(ConstantUtil.Relay,baojingdengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                    Snackbar.make(view, "报警灯开", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }else {
                    ControlUtils.control(ConstantUtil.Relay,baojingdengbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
                    Snackbar.make(view, "报警灯关", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }
                break;
            case R.id.fengshan:
                if (fengshan.isChecked()){
                    ControlUtils.control(ConstantUtil.Relay,fengshanbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                    Snackbar.make(view, "风扇开", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }else {
                    ControlUtils.control(ConstantUtil.Relay,fengshanbanhao,ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
                    Snackbar.make(view, "风扇关", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mvibrator.vibrate(new long[] {10,10},-1);

                }
                break;
            case R.id.curtainon:
                if (curtainon.isChecked()){
                    ControlUtils.control(ConstantUtil.Relay,chuanglianbanhao,ConstantUtil.CmdCode_3,"1",ConstantUtil.Open);
                    curtainstop.setChecked(false);
                    curtainoff.setChecked(false);
                    Snackbar.make(view, "窗帘开", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                break;
            case R.id.curtainoff:
                if (curtainoff.isChecked()) {
                    ControlUtils.control(ConstantUtil.Relay, chuanglianbanhao, ConstantUtil.CmdCode_3, "4", ConstantUtil.Open);
                    curtainstop.setChecked(false);
                    curtainon.setChecked(false);
                    Snackbar.make(view, "窗帘关", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.curtainstop:
                if (curtainstop.isChecked()) {
                    ControlUtils.control(ConstantUtil.Relay, chuanglianbanhao, ConstantUtil.CmdCode_3, "3", ConstantUtil.Open);
                    curtainon.setChecked(false);
                    curtainoff.setChecked(false);
                    Snackbar.make(view, "窗帘停", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
        }
    }
}
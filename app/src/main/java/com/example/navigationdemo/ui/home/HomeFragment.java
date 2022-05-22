package com.example.navigationdemo.ui.home;

import android.app.Activity;
import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.navigationdemo.MainActivity;
import com.example.navigationdemo.R;
import com.example.navigationdemo.databinding.FragmentHomeBinding;
import com.example.navigationdemo.ui.gallery.GalleryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public  static  String[] sensorString=new String[]{"0","0","0","0","0","0","0","0"};
    public static  String tag="CaiJiActivity";
    private Vibrator vibrator;
    String wendubanhao,shidubanhao,guangzhaobanhao,ranqibanhao,yanwubanhao,co2banhao,pm25banhao,rentihongwaibanhao;
    TextView wendu,shidu,guangzhao,ranqi,yanwu,co2,pm25,rentihongwai,banhao1,banhao2,banhao3,banhao4,banhao5,banhao6,banhao7,banhao8;
    EditText wenduid,shiduid,guangzhaoid,ranqiid,yanwuid,co2id,pm25id,rentihongwaiid;
    FloatingActionButton change;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        vibrator=(Vibrator)getActivity().getSystemService(Service.VIBRATOR_SERVICE);

        wendu=view.findViewById(R.id.wendu);
        shidu=view.findViewById(R.id.shidu);
        guangzhao=view.findViewById(R.id.guangzhao);
        ranqi=view.findViewById(R.id.ranqi);
        yanwu=view.findViewById(R.id.yanwu);
        co2=view.findViewById(R.id.co2);
        pm25=view.findViewById(R.id.pm25);
        rentihongwai=view.findViewById(R.id.rentihongwai);
        wenduid=view.findViewById(R.id.wendu_id);
        shiduid=view.findViewById(R.id.shidu_id);
        guangzhaoid=view.findViewById(R.id.guangzhao_id);
        ranqiid=view.findViewById(R.id.ranqi_id);
        co2id=view.findViewById(R.id.co2_id);
        yanwuid=view.findViewById(R.id.yanwu_id);
        pm25id=view.findViewById(R.id.pm25_id);
        rentihongwaiid=view.findViewById(R.id.rentihongwai_id);
        banhao1=view.findViewById(R.id.banhao1);
        banhao2=view.findViewById(R.id.banhao2);
        banhao3=view.findViewById(R.id.banhao3);
        banhao4=view.findViewById(R.id.banhao4);
        banhao5=view.findViewById(R.id.banhao5);
        banhao6=view.findViewById(R.id.banhao6);
        banhao7=view.findViewById(R.id.banhao7);
        banhao8=view.findViewById(R.id.banhao81);
        wendubanhao=wenduid.getText().toString();
        shidubanhao=shiduid.getText().toString();
        guangzhaobanhao=guangzhaoid.getText().toString();
        ranqibanhao=ranqiid.getText().toString();
        yanwubanhao=yanwuid.getText().toString();
        co2banhao=co2id.getText().toString();
        pm25banhao=pm25id.getText().toString();
        rentihongwaibanhao=rentihongwaiid.getText().toString();

        change=view.findViewById(R.id.change);
        change.setOnClickListener(this);



        ControlUtils.getData();
        SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {
            @Override
            public void onResult(DeviceBean deviceBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<DeviceBean.Devices> list=deviceBean.getDevice();
                            if (list.size()>0){
                                for (int i=0;i<list.size(); i++){
                                    String zhi=list.get(i).getValue();
                                    if (list.get(i).getBoardId().equals(wendubanhao)&&list.get(i).getSensorType().equals(ConstantUtil.Temperature)){
                                        sensorString[0]=zhi;
                                    }
                                    if (list.get(i).getBoardId().equals(shidubanhao)&&list.get(i).getSensorType().equals(ConstantUtil.Humidity)){
                                        sensorString[1]=zhi;
                                    }
                                    if (list.get(i).getBoardId().equals(guangzhaobanhao)&&list.get(i).getSensorType().equals(ConstantUtil.Illumination)){
                                        sensorString[2]=zhi;
                                    }
                                    if (list.get(i).getBoardId().equals(ranqibanhao)&&list.get(i).getSensorType().equals(ConstantUtil.Gas)){
                                        sensorString[3]=zhi;
                                    }
                                    if (list.get(i).getBoardId().equals(yanwubanhao)&&list.get(i).getSensorType().equals(ConstantUtil.Smoke)){
                                        sensorString[4]=zhi;
                                    }
                                    if (list.get(i).getBoardId().equals(co2banhao)&&list.get(i).getSensorType().equals(ConstantUtil.CO2)){
                                        sensorString[5]=zhi;
                                    }
                                    if (list.get(i).getBoardId().equals(pm25banhao)&&list.get(i).getSensorType().equals(ConstantUtil.PM25)){
                                        sensorString[6]=zhi;
                                    }
                                    if (list.get(i).getBoardId().equals(rentihongwaibanhao)&&list.get(i).getSensorType().equals(ConstantUtil.HumanInfrared)){
                                        sensorString[7]=zhi;
                                    }

                                }
                            }
                        }catch (Exception e){}
                    }
                });
            }
        });
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                caiji();
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
        timer.schedule(task,0,1000);
        return view;
    }

    private void caiji() {
        wendu.setText(HomeFragment.sensorString[0]);
        shidu.setText(HomeFragment.sensorString[1]);
        guangzhao.setText(HomeFragment.sensorString[2]);
        ranqi.setText(HomeFragment.sensorString[3]);
        yanwu.setText(HomeFragment.sensorString[4]);
        co2.setText(HomeFragment.sensorString[5]);
        pm25.setText(HomeFragment.sensorString[6]);
        rentihongwai.setText(HomeFragment.sensorString[7].equals("1")?"有人":"无人");
        banhao1.setText(shidubanhao);
        banhao2.setText(guangzhaobanhao);
        banhao3.setText(yanwubanhao);
        banhao4.setText(pm25banhao);
        banhao5.setText(co2banhao);
        banhao6.setText(ranqibanhao);
        banhao7.setText(wendubanhao);
        banhao8.setText(rentihongwaibanhao);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change:
                if (       TextUtils.isEmpty(wenduid.getText().toString())
                        || TextUtils.isEmpty(shiduid.getText().toString())
                        || TextUtils.isEmpty(guangzhaoid.getText().toString())
                        || TextUtils.isEmpty(ranqiid.getText().toString())
                        || TextUtils.isEmpty(yanwuid.getText().toString())
                        || TextUtils.isEmpty(co2id.getText().toString())
                        || TextUtils.isEmpty(pm25id.getText().toString())
                        || TextUtils.isEmpty(rentihongwaiid.getText().toString())) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeFragment.this).create();
//                    builder.setTitle("错误：");
//                    builder.setMessage("板号不能为空");
//                    builder.setIcon(R.drawable.icon_error3);
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.show();//                        return;
                    Snackbar.make(view, "板号不能为空", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    wendubanhao=wenduid.getText().toString();
                    shidubanhao=shiduid.getText().toString();
                    guangzhaobanhao=guangzhaoid.getText().toString();
                    ranqibanhao=ranqiid.getText().toString();
                    yanwubanhao=yanwuid.getText().toString();
                    co2banhao=co2id.getText().toString();
                    pm25banhao=pm25id.getText().toString();
                    rentihongwaibanhao=rentihongwaiid.getText().toString();
//                Toast.makeText(CaiJiActivity.this,"变更板号",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "板号设置成功", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.reset:
                wenduid.setText("");
                shiduid.setText("");
                guangzhaoid.setText("");
                ranqiid.setText("");
                yanwuid.setText("");
                co2id.setText("");
                pm25id.setText("");
                rentihongwaiid.setText("");
                Toast.makeText(getContext(),"点了菜单",Toast.LENGTH_SHORT).show();
                break;
        }

        return true;

    }
}


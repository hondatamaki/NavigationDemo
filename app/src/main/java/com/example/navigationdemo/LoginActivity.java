package com.example.navigationdemo;

//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;
import com.example.navigationdemo.dao.UserDao;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static  final int REQUEST_CODE=1024;
    EditText etip;
    Button login;
    TextInputLayout tlip;
    RelativeLayout lg;
//    UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etip=findViewById(R.id.etip);
        login=findViewById(R.id.btnlogin);
        tlip=findViewById(R.id.tlip);
        lg=findViewById(R.id.Login_ip);
        login.setOnClickListener(this);
        etip.setOnFocusChangeListener(this);
        etip.setOnClickListener(this);
        lg.setOnClickListener(this);
//        dao=new UserDao(this);
        requestPermissions(this);
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
        timer.schedule(task,0,10);
    }

    private void jiance() {
        if (!TextUtils.isEmpty(etip.getText().toString())){
            tlip.setErrorEnabled(false);
        }
    }


    private void requestPermissions(LoginActivity loginActivity) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
//            if (Environment.isExternalStorageManager()){
//                writeFile();
//            }else {
////                Intent intent=new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
////                intent.setData(Uri.parse("packge:"+ getApplicationContext().getPackageName()));
////                startActivityForResult(intent,REQUEST_CODE);
//                ActivityResultLauncher<Intent>intentActivityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                    }
//                });
//                Intent intent=new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                intent.setData(Uri.parse("packge:"+ getApplicationContext().getPackageName()));
//                intentActivityResultLauncher.launch(intent);
//            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                ActivityCompat.requestPermissions(this,new String[]{
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                },REQUEST_CODE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("提示：");
                builder.setMessage("权限申请");
//                builder.setIcon(R.drawable.icon_success);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                writeFile();
            }else {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CODE);
            }
        }else {
            writeFile();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                writeFile();
            }else {
                Toast.makeText(LoginActivity.this,"获取权限失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE&&Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            if (Environment.isExternalStorageManager()){
                writeFile();
            }else {
                Toast.makeText(LoginActivity.this,"存储权限获取失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void writeFile() {
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlogin:
                if (TextUtils.isEmpty(etip.getText().toString())){
                    tlip.setErrorEnabled(true);
                    tlip.setError("请输入IP");
                }else {
                    String ip = etip.getText().toString();
                    ControlUtils.setUser("bizideal", "123456", ip);
                    SocketClient.getInstance().creatConnect();
                    SocketClient.getInstance().login(new LoginCallback() {
                        @Override
                        public void onEvent(String s) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (s.equals(ConstantUtil.Success)) {
//                                 startActivity(new Intent(LoginActivity.this,CaiJiActivity.class));
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setTitle("错误：");
                                        builder.setMessage("登陆失败");
                                        builder.setIcon(R.drawable.icon_error_login);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.setNeutralButton("仍然进入", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        builder.show();
                                        return;
                                    }
                                }
                            });
                        }
                    });
                    break;

                }
            case R.id.Login_ip:
                if (lg.isClickable()){
                    etip.clearFocus();
                }
                break;

        }
    }
    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.etip:
                if (etip.hasFocus()){
                    etip.setHint("xxx.xxx.xxx.xxx");
                }else {
                    etip.setHint("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                break;
        }
    }
}
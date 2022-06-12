package com.example.ZhiNengJiaJuNA.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    UserHelper helper;

    public UserDao(Context context) {
        helper=new UserHelper(context);
    }
    public void insert (User user){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("insert into userTable (wendu,shidu,guangzhao,yanwu,pm25,co2,ranqi,hongwai) values('"+user.getWendu()+","+user.getShidu()+","+ user.getGuangzhao()+","+ user.getYanwu()+","+ user.getPm25()+","+ user.getCo2()+","+ user.getRanqi()+","+ user.getHongwai()+"')");
        db.close();
    }
    public void update(User user){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("update userTable set wendu='"+ user.getWendu()+"',shidu='"+ user.getShidu()+"',guangzhao='"+ user.getGuangzhao()+"',yanwu='"+ user.getYanwu()+"',pm25='"+user.getPm25()+"',co2='"+ user.getCo2()+"',ranqi='"+ user.getRanqi()+"',hongwai='"+ user.getHongwai()+"'");
        db.close();
    }
    public List<User> query(User user){
        SQLiteDatabase db=helper.getReadableDatabase();
        List<User>list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from userTable where wendu='"+ user.getWendu()+"',shidu='"+ user.getShidu()+"',guangzhao='"+ user.getGuangzhao()+"',yanwu='"+ user.getYanwu()+"',pm25='"+ user.getPm25()+"',co2='"+user.getCo2()+"',ranqi='"+ user.getRanqi()+"' and hongwai='"+ user.getHongwai()+"'",null);
        while (cursor.moveToNext()){
        String wendu=cursor.getString(0);
        String shidu=cursor.getString(1);
        String guangzhao=cursor.getString(2);
        String yanwu=cursor.getString(3);
        String pm25=cursor.getString(4);
        String co2=cursor.getString(5);
        String ranqi=cursor.getString(6);
        String hongwai=cursor.getString(7);
        User user1=new User(wendu,shidu,guangzhao,yanwu,pm25,co2,ranqi,hongwai);
        list.add(user1);
        }
        cursor.close();
        db.close();
        return list;
    }
}

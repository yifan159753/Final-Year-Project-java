package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.finalyearproject.ui.login.LoginActivity;

import java.util.HashMap;

public class Session {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE =0;

    private static final String FREENAME="LOGIN";
    //private static final String LOGIN="IS_LOGIN";
    public static final String NAME=" ";
    public static final String URL="http://192.168.31.249/";
    //public static final String URL="http://172.28.50.25/";

    public Session(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(FREENAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void create(String name){
        //editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.apply();
    }

    /*public boolean isloggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if (!this.isloggin()){
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }*/

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user=new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(URL,URL);
        return user;
    }

    public  void logout(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }



}

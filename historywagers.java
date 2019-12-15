package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class historywagers extends AppCompatActivity {

    private static String URL,url,checkurl;
    Session session;
    private String[] chips = new String[100];
    private String[] ans = new String[100];
    private String[] username = new String[100];
    private String[] time = new String[100];
    private int num;
    private TableLayout wagertable;
    private String checkname,checkmark,checklevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historywagers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        wagertable= findViewById(R.id.wagertable);

        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        url = user.get(Session.URL);
        checkname=user.get(Session.NAME);

        URL= url +"historywagers.php";
        checkurl= url +"checkid.php";

        check();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, checkurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            checkmark = jsonObject.getString("mark");
                            checklevel = jsonObject.getString("lv");

                            Snackbar.make(view, "Welcome "+checkname+",    mark:"+checkmark+",   level:"+checklevel, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();



                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(historywagers.this, "error！" + error.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("name",checkname);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(historywagers.this);
                requestQueue.add(stringRequest);

            }
        });


    }


    public void check(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    num = Integer.parseInt(jsonObject.getString("num"));
                    for (int a=0;a<num;a++){
                        chips[a]= jsonObject.getString("chips"+a);
                        ans[a] = jsonObject.getString("ans"+a);
                        username[a] = jsonObject.getString("username"+a);
                        time[a] = jsonObject.getString("time"+a);
                        if (999==Integer.parseInt(time[a])) {
                            time[a]=" - - - ";
                        }

                        TableRow newRow = new TableRow(historywagers.this);

                        TextView column0 = new TextView(historywagers.this);
                        TextView column1 = new TextView(historywagers.this);
                        TextView column2 = new TextView(historywagers.this);
                        TextView column3 = new TextView(historywagers.this);

                        column0.setText(chips[a]);
                        column0.setGravity(Gravity.CENTER);

                        column1.setText(ans[a]);
                        column1.setGravity(Gravity.CENTER);

                        column2.setText(username[a]);
                        column2.setGravity(Gravity.CENTER);

                        column3.setText(time[a]);
                        column3.setGravity(Gravity.CENTER);

                        newRow.addView(column0);
                        newRow.addView(column1);
                        newRow.addView(column2);
                        newRow.addView(column3);

                        wagertable.addView(newRow, new TableLayout.LayoutParams());
                    }


                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(historywagers.this, "error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(historywagers.this,wagers.class);
            startActivity(intent);
            return true;
        }
        return false;
    }


}

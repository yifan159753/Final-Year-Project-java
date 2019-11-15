package com.example.finalyearproject;

import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class historyrule extends AppCompatActivity {

    private static String URL,url,checkurl;
    Session session;
    private String[] banker = new String[100];
    private String[] bankersecond = new String[100];
    private String[] player = new String[100];
    private String[] playersecond = new String[100];
    private int num;
    private TableLayout ruletable;
    private String checkname,checkmark,checklevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyrule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ruletable= findViewById(R.id.ruletable);

        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        url = user.get(Session.URL);
        checkname=user.get(Session.NAME);

        URL= url +"historyrule.php";
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
                                Toast.makeText(historyrule.this, "error！" + error.toString(),
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

                RequestQueue requestQueue = Volley.newRequestQueue(historyrule.this);
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
                        banker[a]= jsonObject.getString("banker"+a);
                        bankersecond[a] = jsonObject.getString("bankersecond"+a);
                        player[a] = jsonObject.getString("player"+a);
                        playersecond[a] = jsonObject.getString("playersecond"+a);

                        TableRow newRow = new TableRow(historyrule.this);

                        TextView column0 = new TextView(historyrule.this);
                        TextView column1 = new TextView(historyrule.this);
                        TextView column2 = new TextView(historyrule.this);
                        TextView column3 = new TextView(historyrule.this);

                        column0.setText(banker[a]);
                        column0.setGravity(Gravity.CENTER);

                        column1.setText(bankersecond[a]);
                        column1.setGravity(Gravity.CENTER);

                        column2.setText(player[a]);
                        column2.setGravity(Gravity.CENTER);

                        column3.setText(playersecond[a]);
                        column3.setGravity(Gravity.CENTER);

                        newRow.addView(column0);
                        newRow.addView(column1);
                        newRow.addView(column2);
                        newRow.addView(column3);

                        ruletable.addView(newRow, new TableLayout.LayoutParams());
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
                        Toast.makeText(historyrule.this, "error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(historyrule.this,rule.class);
            startActivity(intent);
            return true;
        }
        return false;
    }



}

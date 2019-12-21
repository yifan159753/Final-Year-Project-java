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

public class MainActivity extends AppCompatActivity {

    private Button logout,rule,wagers,game;
    Session session;
    private static String checkurl,URL;
    private String checkname,checkmark,checklevel;
    private String[] username = new String[100];
    private String[] mark = new String[100];
    private String[] level = new String[100];
    private int num;
    private TableLayout t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        session=new Session(this);
        //session.checkLogin();


        logout=findViewById(R.id.logout);
        rule=findViewById(R.id.rule);
        wagers=findViewById(R.id.wagers);
        game=findViewById(R.id.game);
        t1= findViewById(R.id.table);

        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(session.NAME);
        String url = user.get(Session.URL);

        checkurl= url +"checkid.php";
        URL= url +"leaderboard.php";


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
                                Toast.makeText(MainActivity.this, "error！" + error.toString(),
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

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logout();
            }
        });

        rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,rule.class);
                startActivity(intent);
            }
        });

        wagers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,wagers.class);
                startActivity(intent);
            }
        });

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,game.class);
                startActivity(intent);
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
                        username[a]= jsonObject.getString("username"+a);
                        mark[a] = jsonObject.getString("mark"+a);
                        level[a] = jsonObject.getString("level"+a);

                        TableRow newRow = new TableRow(MainActivity.this);

                        TextView column0 = new TextView(MainActivity.this);
                        TextView column1 = new TextView(MainActivity.this);
                        TextView column2 = new TextView(MainActivity.this);
                        TextView column3 = new TextView(MainActivity.this);

                        column0.setText(a+1+"");
                        column0.setGravity(Gravity.CENTER);
                        //column0.setTextSize(20);

                        column1.setText(username[a]);
                        column1.setGravity(Gravity.CENTER);

                        column2.setText(mark[a]);
                        column2.setGravity(Gravity.CENTER);

                        column3.setText(level[a]);
                        column3.setGravity(Gravity.CENTER);

                        newRow.addView(column0);
                        newRow.addView(column1);
                        newRow.addView(column2);
                        newRow.addView(column3);

                        t1.addView(newRow, new TableLayout.LayoutParams());
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
                        Toast.makeText(MainActivity.this, "error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }



}

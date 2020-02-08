package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button logout,rule,wagers,game,leaderboard,introduction,mainquestion;
    Session session;
    private static String checkurl;
    private String checkname,checkmark,checklevel;

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
        leaderboard=findViewById(R.id.leaderboard);
        introduction=findViewById(R.id.introduction);
        mainquestion=findViewById(R.id.mainquestion);

        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(session.NAME);
        String url = user.get(Session.URL);

        checkurl= url +"checkid.php";


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

                LayoutInflater factory=LayoutInflater.from(MainActivity.this);
                final View v1=factory.inflate(R.layout.rule,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(MainActivity.this);
                inputDialog.setView(v1);

                Button btn = v1.findViewById(R.id.go_to_rule_add);
                Button btn2 = v1.findViewById(R.id.go_to_rule);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,rule_add.class);
                        startActivity(intent);


                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,rule.class);
                        startActivity(intent);

                    }
                });
                AlertDialog dialog2 = inputDialog.create();
                final Window window = dialog2.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();


            }
        });

        wagers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory2=LayoutInflater.from(MainActivity.this);
                final View v2=factory2.inflate(R.layout.wager_calculation_rules,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(MainActivity.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

                Button btn2 = v2.findViewById(R.id.wager_calculation_rules_continue);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,wagers.class);
                        startActivity(intent);

                    }
                });

            }
        });

        mainquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,question.class);
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

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,leaderboard.class);
                startActivity(intent);
            }
        });

        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,introduction.class);
                startActivity(intent);
            }
        });


    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }



}

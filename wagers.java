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

import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class wagers extends AppCompatActivity {

    Session session;
    private String checkname,checkmark,checklevel,winner;
    private static String URL,checkurl;
    private int wager,ansint=0,time,win;
    private TextView value,question,ans,textTimer,prompttext;
    private Button checkbutton,reset,prompt,chips1,chips2,chips3,chips4,chips5,chips6;
    private LinearLayout wagerstime;
    private View v1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wagers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textTimer = findViewById(R.id.timer);
        prompttext = findViewById(R.id.prompttext);
        value=findViewById(R.id.wagervalue);
        question=findViewById(R.id.wagerquestion);
        ans=findViewById(R.id.wagerans);
        checkbutton=findViewById(R.id.submit);
        reset=findViewById(R.id.reset);
        prompt=findViewById(R.id.prompt);
        chips1=findViewById(R.id.chips1);
        chips2=findViewById(R.id.chips2);
        chips3=findViewById(R.id.chips3);
        chips4=findViewById(R.id.chips4);
        chips5=findViewById(R.id.chips5);
        chips6=findViewById(R.id.chips6);

        wagerstime=findViewById(R.id.wagerstime);


        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(Session.NAME);
        String url = user.get(Session.URL);

        URL= url +"wagerswin.php";
        checkurl= url +"checkid.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, checkurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    checkmark = jsonObject.getString("mark");
                    checklevel = jsonObject.getString("lv");

                    FloatingActionButton fab = findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            Snackbar.make(view, "Welcome "+checkname+",    mark:"+checkmark+",   level:"+checklevel, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    });

                    if (Integer.parseInt(checkmark) >= 1000){

                        wagerstime.setVisibility(View.VISIBLE);

                        new CountDownTimer(60000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                time=60-(int) millisUntilFinished / 1000;
                                textTimer.setText((millisUntilFinished / 1000) + " s " );
                            }

                            public void onFinish() {
                                AlertDialog.Builder inputDialog =
                                        new AlertDialog.Builder(wagers.this);
                                inputDialog.setTitle("Sorry, time is up.");
                                inputDialog.setPositiveButton("next",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent intent = new Intent();
                                                intent.setClass(wagers.this,wagers.class);
                                                startActivity(intent);

                                            }
                                            //}).show();
                                        });
                                AlertDialog dialog = inputDialog.create();
                                dialog.setCancelable(false);
                                dialog.show();
                            }

                        }.start();

                    }


                    Random();

                    value.setText(""+wager);
                    if (win<=6){
                        winner="Blanker";
                        question.setText("Wins by betting on the Banker's hand, wagers calculation:");
                    }
                    else if (win<=8){
                        winner="Player";
                        question.setText("Wins by betting on the Player's hand, wagers calculation:");
                    }
                    else if (win==9){
                        winner="Tie";
                        question.setText("Wins by betting on the Tie, wagers calculation:");
                    }
                    else if (win==10){
                        winner="Pair";
                        question.setText("Wins by betting on the Pair, wagers calculation:");
                    }



                    if (Integer.parseInt(checkmark) < 500){

                        prompttext.setVisibility(View.VISIBLE);
                        prompt.setVisibility(View.VISIBLE);
                        prompt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (win<=6){
                                    int r= (int) (wager*0.95);
                                    prompttext.setText("Answer tips: "+r  );
                                }
                                else if (win<=8){
                                    int r= wager*1;
                                    prompttext.setText("Answer tips: "+r  );
                                }
                                else if (win==9){
                                    int r= wager*8;
                                    prompttext.setText("Answer tips: "+r  );
                                }
                                else if (win==10){
                                    int r= wager*11;
                                    prompttext.setText("Answer tips: "+r  );
                                }

                            }
                        });

                    }


                    chips1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ansint+=5;
                            ans.setText(""+ansint);
                        }
                    });

                    chips2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ansint+=15;
                            ans.setText(""+ansint);
                        }
                    });

                    chips3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ansint+=25;
                            ans.setText(""+ansint);
                        }
                    });

                    chips4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ansint+=100;
                            ans.setText(""+ansint);
                        }
                    });

                    chips5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ansint+=500;
                            ans.setText(""+ansint);
                        }
                    });

                    chips6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ansint+=1000;
                            ans.setText(""+ansint);
                        }
                    });

                    reset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ansint=0;
                            ans.setText(""+ansint);
                        }
                    });



                    checkbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v2) {

                            if((ansint==wager*0.95 && win<=6) ||
                               (ansint==wager*1 && (win==7 || win==8)) ||
                               (ansint==wager*8 && win==9) ||
                               (ansint==wager*11 && win==10)){

                                if (Integer.parseInt(checkmark) < 1000){

                                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                        }
                                    },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(wagers.this, "error！" + error.toString(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            })

                                    {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String,String> params = new HashMap<>();
                                            params.put("ansint",Integer.toString(ansint));
                                            params.put("wager",Integer.toString(wager));
                                            params.put("name",checkname);
                                            params.put("time",Integer.toString(999));
                                            params.put("winner",winner);
                                            return params;
                                        }
                                    };

                                    RequestQueue requestQueue2 = Volley.newRequestQueue(wagers.this);
                                    requestQueue2.add(stringRequest2);

                                }


                                if (Integer.parseInt(checkmark) >= 1000){

                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(wagers.this, "error！" + error.toString(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        })

                                {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,String> params = new HashMap<>();
                                        params.put("ansint",Integer.toString(ansint));
                                        params.put("wager",Integer.toString(wager));
                                        params.put("name",checkname);
                                        params.put("time",Integer.toString(time));
                                        params.put("winner",winner);
                                        return params;
                                    }
                                };

                                RequestQueue requestQueue2 = Volley.newRequestQueue(wagers.this);
                                requestQueue2.add(stringRequest2);

                                }


                                LayoutInflater factory=LayoutInflater.from(wagers.this);
                                final View win=factory.inflate(R.layout.plus20,null);

                                AlertDialog.Builder inputDialog =
                                        new AlertDialog.Builder(wagers.this);
                                inputDialog.setView(win);
                                inputDialog.setPositiveButton("next",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent intent = new Intent();
                                                intent.setClass(wagers.this,wagers.class);
                                                startActivity(intent);

                                            }
                                        });
                                AlertDialog dialog = inputDialog.create();
                                final Window window = dialog.getWindow();
                                window.setBackgroundDrawable(new ColorDrawable(0));
                                dialog.setCancelable(false);
                                dialog.show();
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);

                            }else{
                                LayoutInflater factory=LayoutInflater.from(wagers.this);
                                if (win<=6){
                                    v1=factory.inflate(R.layout.errorwagers6,null);
                                }
                                else if (win==7 || win==8){
                                    v1=factory.inflate(R.layout.errorwagers7,null);
                                }
                                else if (win==9){
                                    v1=factory.inflate(R.layout.errorwagers9,null);
                                }
                                else if (win==10){
                                    v1=factory.inflate(R.layout.errorwagers10,null);
                                }

                                AlertDialog.Builder inputDialog =
                                        new AlertDialog.Builder(wagers.this);
                                inputDialog.setView(v1);
                                inputDialog.setPositiveButton("next",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent intent = new Intent();
                                                intent.setClass(wagers.this,wagers.class);
                                                startActivity(intent);

                                            }
                                        });
                                AlertDialog dialog = inputDialog.create();
                                dialog.setCancelable(false);
                                final Window window = dialog.getWindow();
                                window.setBackgroundDrawable(new ColorDrawable(0));
                                dialog.show();
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                            }

                        }
                    });




                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(wagers.this, "error！" + error.toString(),
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

        RequestQueue requestQueue = Volley.newRequestQueue(wagers.this);
        requestQueue.add(stringRequest);





    }



    private void Random() {
        Random random=new Random();
        wager = (random.nextInt(98)+1)*100;
        win = (random.nextInt(10)+1);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //定义菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;


            case R.id.index:
                Intent intent = new Intent();
                intent.setClass(wagers.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.next:
                Intent intent2 = new Intent();
                intent2.setClass(wagers.this,wagers.class);
                startActivity(intent2);
                break;

            case R.id.dealtrule:

                LayoutInflater factory=LayoutInflater.from(wagers.this);
                final View v1=factory.inflate(R.layout.dealt_card_rules,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(wagers.this);
                inputDialog.setView(v1);
                final AlertDialog dialog = inputDialog.create();
                final Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(0));
                dialog.show();

                Button btn = v1.findViewById(R.id.dealt_card_rules_continue);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

                break;

            case R.id.wagerrule:

                LayoutInflater factory2=LayoutInflater.from(wagers.this);
                final View v2=factory2.inflate(R.layout.wager_calculation_rules,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(wagers.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

                Button btn2 = v2.findViewById(R.id.wager_calculation_rules_continue);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog2.dismiss();

                    }
                });

                break;

            case R.id.history:
                Intent intent3 = new Intent();
                intent3.setClass(wagers.this,historywagers.class);
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(wagers.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }



}

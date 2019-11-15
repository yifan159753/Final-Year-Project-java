package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class wagers extends AppCompatActivity {

    Session session;
    private String checkname,checkmark,checklevel;
    private static String URL,checkurl;
    private int wager,ansint=0,time;
    private TextView question,ans,textTimer;
    private Button checkbutton,reset,chips1,chips2,chips3,chips4,chips5,chips6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wagers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textTimer = findViewById(R.id.timer);


        question=findViewById(R.id.wagerquestion);
        ans=findViewById(R.id.wagerans);
        checkbutton=findViewById(R.id.submit);
        reset=findViewById(R.id.reset);
        chips1=findViewById(R.id.chips1);
        chips2=findViewById(R.id.chips2);
        chips3=findViewById(R.id.chips3);
        chips4=findViewById(R.id.chips4);
        chips5=findViewById(R.id.chips5);
        chips6=findViewById(R.id.chips6);



        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(Session.NAME);
        String url = user.get(Session.URL);

        URL= url +"wagerswin.php";
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
        });



        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                time=60-(int) millisUntilFinished / 1000;
                textTimer.setText("Seconds Remaining: " + (millisUntilFinished / 1000) );
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



        Random();
        question.setText(""+wager);


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
            public void onClick(View v) {

                if(ansint==wager*0.95){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(wagers.this);
                    requestQueue.add(stringRequest);


                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(wagers.this);
                    inputDialog.setTitle("Winnnn");
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

                }else{
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(wagers.this);
                    inputDialog.setTitle("Sorry, the choice is wrong.");
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

            }
        });





    }



    private void Random() {
        Random random=new Random();
        wager = (random.nextInt(98)+1)*100;

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
                inputDialog.setTitle("Dealt cards rule");
                inputDialog.setIcon(R.drawable.logo);
                inputDialog.setView(v1);
                inputDialog.setMessage("Initially, two cards are dealt for each hand. The point totals determine whether either hand gets a third card. The player hand is completed first. A total of 8 or 9 is called a \"natural,\" and the player hand gets no more cards. Player also stands on totals of 6 or 7. On any other total, zero through 5, player draws a third card, unless banker has a natural, in which case the bank hand wins with no further draw.\n\n"
                        +
                        "Banker also stands on 7, 8, or 9 and draws on 0, 1, or 2, but on other hands the banker's play is dependent on the value of the player's third card.\n\n"
                        +
                        "Specific can refer to the following figure:\n");
                inputDialog.setPositiveButton("sure",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;

            case R.id.wagerrule:

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(wagers.this);
                inputDialog2.setTitle("Wager calculation rule");
                inputDialog2.setIcon(R.drawable.logo);
                inputDialog2.setMessage("- The winner takes the betting amount.\n"+
                        "(If a participant wins by betting on the hand of the “banker”, 5% commission is deducted from the winning amount)\n\n"+
                        "- If a participant wins by betting a tie wager, he/she wins 8 times the betting amount.\n"+
                        "(Tie: when the sums of the banker’s hand and the player’s hand are the same)\n\n"+
                        "- If a participant wins by Pair Bet, he/she wins 11 times the betting amount. (Pair: when the first 2 cards are the same)\n");
                inputDialog2.setPositiveButton("sure",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
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

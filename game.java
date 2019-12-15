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

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class game extends AppCompatActivity {

    private int[] card = new int[7];
    private String[] cardcopy = new String[7];
    private int[] cardcolor = new int[7];
    private Button yes,no;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    private TextView question,chipsquestion;
    private static String URL,checkurl;
    Session session;
    private String checkname,checkmark,checklevel;
    private int win,wager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        yes = findViewById(R.id.gameyes);
        no = findViewById(R.id.gameno);
        question=findViewById(R.id.gamequestion);
        chipsquestion=findViewById(R.id.chipsquestion);
        imageView1 = findViewById(R.id.gamebanker1);
        imageView2 = findViewById(R.id.gamebanker2);
        imageView3 = findViewById(R.id.gamebanker3);
        imageView4 = findViewById(R.id.gameplayer1);
        imageView5 = findViewById(R.id.gameplayer2);
        imageView6 = findViewById(R.id.gameplayer3);

        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(Session.NAME);
        String url = user.get(Session.URL);

        URL= url +"gamewin.php";
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
                                Toast.makeText(game.this, "error！" + error.toString(),
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

                RequestQueue requestQueue = Volley.newRequestQueue(game.this);
                requestQueue.add(stringRequest);
            }
        });


        Random();


        if (win==1)
            chipsquestion.setText("player : "+wager);
        else if (win==2)
            chipsquestion.setText("banker : "+wager);


        final String[] variableValue = {"card"+cardcolor[1]+card[1],"card"+cardcolor[2]+card[2],"card"+cardcolor[3]+card[3],"card"+cardcolor[4]+card[4],"card"+cardcolor[5]+card[5],"card"+cardcolor[6]+card[6],};
        imageView4.setImageResource(getResources().getIdentifier(variableValue[0], "drawable", getPackageName()));
        imageView5.setImageResource(getResources().getIdentifier(variableValue[1], "drawable", getPackageName()));
        //imageView6.setImageResource(getResources().getIdentifier(variableValue[2], "drawable", getPackageName()));
        imageView1.setImageResource(getResources().getIdentifier(variableValue[3], "drawable", getPackageName()));
        imageView2.setImageResource(getResources().getIdentifier(variableValue[4], "drawable", getPackageName()));
        //imageView3.setImageResource(getResources().getIdentifier(variableValue[5], "drawable", getPackageName()));


        for(int i=1;i<7;i++) {

            if(card[i]==1){
                cardcopy[i]="Ace";
            }
            else if(card[i]==11){
                cardcopy[i]="Jack";
            }
            else if(card[i]==12){
                cardcopy[i]="Queen";
            }
            else if(card[i]==13){
                cardcopy[i]="King";
            }
            else{
                cardcopy[i]=Integer.toString(card[i]);
            }

            if(card[i]>9){
                card[i] = 0;
            }
        }




        if((card[1]+card[2])%10>7||(card[4]+card[5])%10>7){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    question.setText("Do banker need to draws a third card ?");
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            error();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            question.setText("Whether to payouts ?");
                            if ((card[1]+card[2])%10 > (card[4]+card[5])%10 && win==1) {
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });


                            }



                            wintwo();
                        }
                    });
                }
            });
        }



        else if((card[1]+card[2])%10==6||(card[1]+card[2])%10==7){
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    question.setText("Do banker need to draws a third card ?");
                    if((card[4]+card[5])%10<6){
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imageView3.setImageResource(getResources().getIdentifier(variableValue[5], "drawable", getPackageName()));
                                winthree();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                error();
                            }
                        });
                    }
                    if((card[4]+card[5])%10==6||(card[4]+card[5])%10==7){
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                wintwo();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                error();
                            }
                        });
                    }

                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error();
                }
            });

        }


        else if((card[1]+card[2])%10<6){
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView6.setImageResource(getResources().getIdentifier(variableValue[2], "drawable", getPackageName()));
                    question.setText("Do banker need to draws a third card ?");
                    if(     ((card[4]+card[5])%10<3)||
                            ((card[4]+card[5])%10==3&&card[3]!=8)||
                            ((card[4]+card[5])%10==4&&(card[3]!=0&&card[3]!=1&&card[3]!=8&&card[3]!=9))||
                            ((card[4]+card[5])%10==5&&(card[3]!=0&&card[3]!=1&&card[3]!=2&&card[3]!=3&&card[3]!=8&&card[3]!=9))||
                            ((card[4]+card[5])%10==6&&(card[3]==6||card[3]==7))
                    ){
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imageView3.setImageResource(getResources().getIdentifier(variableValue[5], "drawable", getPackageName()));
                                winfour();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                error();
                            }
                        });
                    }
                    else{
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                error();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                winthreetwo();
                            }
                        });
                    }


                }
            });

        }




    }



    private void Random() {
        Random random=new Random();
        for(int i=1;i<7;i++) {
            card[i] = random.nextInt(13)+1;
        }
        for(int j=1;j<7;j++) {
            cardcolor[j] = random.nextInt(4)+1;
        }
        win = (random.nextInt(2)+1);
        wager = (random.nextInt(98)+1)*100;

    }




    private void error() {
        /*@setView 装入一个EditView
         */
        //final EditText editText = new EditText(rule.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        //inputDialog.setTitle("Sorry, the choice is wrong.").setView(editText);
        inputDialog.setTitle("Sorry, the choice is wrong.");
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.setClass(game.this,game.class);
                        startActivity(intent);

                    }
                    //}).show();
                });
        AlertDialog dialog = inputDialog.create();
        //点击dialog之外的区域禁止取消dialog
        dialog.setCancelable(false);
        dialog.show();

    }

    private void wintwo() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(game.this, "error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cardcopy1",cardcopy[1]);
                params.put("cardcopy2",cardcopy[2]);
                params.put("cardcopy3","  /");
                params.put("cardcopy4",cardcopy[4]);
                params.put("cardcopy5",cardcopy[5]);
                params.put("cardcopy6","  /");
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setTitle("winnnnnn.");
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.setClass(game.this,game.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    private void winthree() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(game.this, "error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cardcopy1",cardcopy[1]);
                params.put("cardcopy2",cardcopy[2]);
                params.put("cardcopy3","  /");
                params.put("cardcopy4",cardcopy[4]);
                params.put("cardcopy5",cardcopy[5]);
                params.put("cardcopy6",cardcopy[6]);
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setTitle("winnnnnn.");
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.setClass(game.this,game.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    private void winthreetwo() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(game.this, "error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cardcopy1",cardcopy[1]);
                params.put("cardcopy2",cardcopy[2]);
                params.put("cardcopy3",cardcopy[3]);
                params.put("cardcopy4",cardcopy[4]);
                params.put("cardcopy5",cardcopy[5]);
                params.put("cardcopy6","  /");
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setTitle("winnnnnn.");
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.setClass(game.this,game.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    private void winfour() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(game.this, "error！" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cardcopy1",cardcopy[1]);
                params.put("cardcopy2",cardcopy[2]);
                params.put("cardcopy3",cardcopy[3]);
                params.put("cardcopy4",cardcopy[4]);
                params.put("cardcopy5",cardcopy[5]);
                params.put("cardcopy6",cardcopy[6]);
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setTitle("winnnnnn.");
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.setClass(game.this,game.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        dialog.setCancelable(false);
        dialog.show();
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
                intent.setClass(game.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.next:
                Intent intent2 = new Intent();
                intent2.setClass(game.this,game.class);
                startActivity(intent2);
                break;

            case R.id.dealtrule:

                LayoutInflater factory=LayoutInflater.from(game.this);
                final View v1=factory.inflate(R.layout.dealt_card_rules,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(game.this);
                inputDialog.setTitle("Dealt cards rule");
                inputDialog.setIcon(R.drawable.logo);
                inputDialog.setView(v1);
                inputDialog.setMessage("The dealt cards rule can be summarized as the following figure:\n");
                inputDialog.setPositiveButton("sure",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;

            case R.id.wagerrule:

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(game.this);
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
                intent3.setClass(game.this,historyrule.class);
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(game.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }




}

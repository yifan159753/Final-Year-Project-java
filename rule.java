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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class rule extends AppCompatActivity {

    private int[] card = new int[7];
    private String[] cardcopy = new String[7];
    private int[] cardcolor = new int[7];
    private Button yes,no;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    private TextView question;
    private static String URL,checkurl;
    Session session;
    private String checkname,checkmark,checklevel;
    private View v1;
    private int errorcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        question=findViewById(R.id.question);
        imageView1 = findViewById(R.id.banker1);
        imageView2 = findViewById(R.id.banker2);
        imageView3 = findViewById(R.id.banker3);
        imageView4 = findViewById(R.id.player1);
        imageView5 = findViewById(R.id.player2);
        imageView6 = findViewById(R.id.player3);

        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(Session.NAME);
        String url = user.get(Session.URL);

        URL= url +"rulewin.php";
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
                                Toast.makeText(rule.this, "error！" + error.toString(),
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

                RequestQueue requestQueue = Volley.newRequestQueue(rule.this);
                requestQueue.add(stringRequest);
            }
        });



        Random();


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
                    errorcode=8;error();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    question.setText("Do banker need to draws a third card ?");
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            errorcode=8;error();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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
                                errorcode=26;error();
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
                                errorcode=27;error();
                            }
                        });
                    }

                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    errorcode=261;error();
                }
            });

        }


        else if((card[1]+card[2])%10<6){
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    errorcode=21;error();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView6.setImageResource(getResources().getIdentifier(variableValue[2], "drawable", getPackageName()));
                    question.setText("Do banker need to draws a third card ?");
                    if ((card[4]+card[5])%10<3){
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
                                errorcode=12;error();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==3&&card[3]!=8){
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
                                errorcode=13;error();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==3&&card[3]==8){
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                errorcode=13;error();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                winthreetwo();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==4&&(card[3]!=0&&card[3]!=1&&card[3]!=8&&card[3]!=9)){
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
                                errorcode=14;error();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==4&&(card[3]==0||card[3]==1||card[3]==8||card[3]==9)){
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                errorcode=14;error();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                winthreetwo();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==5&&(card[3]!=0&&card[3]!=1&&card[3]!=2&&card[3]!=3&&card[3]!=8&&card[3]!=9)){
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
                                errorcode=15;error();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==5&&(card[3]!=4&&card[3]!=5&&card[3]!=6&&card[3]!=7)){
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                errorcode=15;error();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                winthreetwo();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==6&&(card[3]==6||card[3]==7)){
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
                                errorcode=16;error();
                            }
                        });
                    }
                    else if ((card[4]+card[5])%10==6&&(card[3]!=6&&card[3]!=7)){
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                errorcode=16;error();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                winthreetwo();
                            }
                        });
                    }



                    /*
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
                    }*/


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

    }



    private void error() {

        LayoutInflater factory=LayoutInflater.from(rule.this);
        if (errorcode==8){ //Natural(8 or 9)
            v1=factory.inflate(R.layout.error8,null);
        }
        else if (errorcode==261){ //Player stands on 6 or 7
            v1=factory.inflate(R.layout.error261,null);
        }
        else if (errorcode==27){ //Banker stands unless Player Draws 6 or 7
            v1=factory.inflate(R.layout.error27,null);
        }
        else if (errorcode==26){ //Banker's hand are not 7,8,9, needs a 3rd card
            v1=factory.inflate(R.layout.error26,null);
        }
        else if (errorcode==21){ //Player's hand are not 6,7,8,9, needs a 3rd card
            v1=factory.inflate(R.layout.error21,null);
        }
        else if (errorcode==12){ //Banker's hand is smaller than 3, needs a 3rd card
            v1=factory.inflate(R.layout.error12,null);
        }
        else if (errorcode==13){ //Banker stand unless Player draws 1,2,3,4,5,6,7
            v1=factory.inflate(R.layout.error13,null);
        }
        else if (errorcode==14){ //Banker stand unless Player draws 2,3,4,5,6,7
            v1=factory.inflate(R.layout.error14,null);
        }
        else if (errorcode==15){ //Banker stand unless Player draws 4,5,6,7
            v1=factory.inflate(R.layout.error15,null);
        }
        else if (errorcode==16){ //Banker stand unless Player draws 6,7
            v1=factory.inflate(R.layout.error16,null);
        }
        //@setView 装入一个EditView

        //final EditText editText = new EditText(rule.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(rule.this);
        //inputDialog.setTitle("Sorry, the choice is wrong.").setView(editText);
        //inputDialog.setTitle("Sorry, the choice is wrong.");
        inputDialog.setView(v1);
        //inputDialog.setMessage("aaaa\n\n\n\n\n\n"+errorcode);
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.setClass(rule.this,rule.class);
                        startActivity(intent);

                    }
                    //}).show();
                });
        AlertDialog dialog = inputDialog.create();
        //点击dialog之外的区域禁止取消dialog
        dialog.setCancelable(false);
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);

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
                        Toast.makeText(rule.this, "error！" + error.toString(),
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


        LayoutInflater factory=LayoutInflater.from(rule.this);
        final View win=factory.inflate(R.layout.plus20,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(rule.this);
        inputDialog.setView(win);
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(rule.this,rule.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
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
                        Toast.makeText(rule.this, "error！" + error.toString(),
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


        LayoutInflater factory=LayoutInflater.from(rule.this);
        final View win=factory.inflate(R.layout.plus20,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(rule.this);
        inputDialog.setView(win);
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(rule.this,rule.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
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
                        Toast.makeText(rule.this, "error！" + error.toString(),
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


        LayoutInflater factory=LayoutInflater.from(rule.this);
        final View win=factory.inflate(R.layout.plus20,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(rule.this);
        inputDialog.setView(win);
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(rule.this,rule.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
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
                        Toast.makeText(rule.this, "error！" + error.toString(),
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


        LayoutInflater factory=LayoutInflater.from(rule.this);
        final View win=factory.inflate(R.layout.plus20,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(rule.this);
        inputDialog.setView(win);
        inputDialog.setPositiveButton("next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(rule.this,rule.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = inputDialog.create();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
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
                intent.setClass(rule.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.next:
                Intent intent2 = new Intent();
                intent2.setClass(rule.this,rule.class);
                startActivity(intent2);
                break;

            case R.id.dealtrule:

                LayoutInflater factory=LayoutInflater.from(rule.this);
                final View v1=factory.inflate(R.layout.dealt_card_rules,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(rule.this);
                inputDialog.setTitle("Dealt cards rule");
                inputDialog.setIcon(R.drawable.logo);
                inputDialog.setView(v1);
                inputDialog.setMessage("The dealt cards rule can be summarized as the following figure:\n");
                /*inputDialog.setMessage("Initially, two cards are dealt for each hand. The point totals determine whether either hand gets a third card. The player hand is completed first. A total of 8 or 9 is called a \"natural,\" and the player hand gets no more cards. Player also stands on totals of 6 or 7. On any other total, zero through 5, player draws a third card, unless banker has a natural, in which case the bank hand wins with no further draw.\n\n"
                        +
                        "Banker also stands on 7, 8, or 9 and draws on 0, 1, or 2, but on other hands the banker's play is dependent on the value of the player's third card.\n\n"
                        +
                        "Specific can refer to the following figure:\n");*/
                inputDialog.setPositiveButton("sure",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;

            case R.id.wagerrule:

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(rule.this);
                inputDialog2.setTitle("Wager calculation rule");
                inputDialog2.setIcon(R.drawable.logo);
                inputDialog2.setMessage("- The winner takes the betting amount.\n"+
                        "(If a participant wins by betting on the hand of the “banker”, 5% commission is deducted from the winning amount)\n\n"+
                        "- If a participant wins by betting a tie wager, participant wins 8 times the betting amount.\n"+
                        "(Tie: when the sums of the banker’s hand and the player’s hand are the same)\n\n"+
                        "- If a participant wins by Pair Bet, participant wins 11 times the betting amount.\n" +
                        "(Pair: when the first 2 cards are the same)\n");
                inputDialog2.setPositiveButton("sure",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;

            case R.id.history:
                Intent intent3 = new Intent();
                intent3.setClass(rule.this,historyrule.class);
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(rule.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }





}

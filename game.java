package com.example.finalyearproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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

import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class game extends AppCompatActivity {

    private int[] card = new int[7];
    private String[] cardcopy = new String[7];
    private int[] cardcolor = new int[7];
    private Button yes,no,reset,submit,chips1,chips2,chips3,chips4,chips5,chips6;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    private TextView question,ans,chipsquestion1,chipsquestion2,chipsquestion3,chipsquestion4,chipsquestion5;
    private static String URL,checkurl;
    Session session;
    private String checkname,checkmark,checklevel;
    private int win,wager,ansint;
    private LinearLayout rulekuai1,rulekuai2,chipskuai;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        yes = findViewById(R.id.gameyes);
        no = findViewById(R.id.gameno);
        reset=findViewById(R.id.gamereset);
        submit=findViewById(R.id.gamesubmit);
        chips1=findViewById(R.id.gamechips1);
        chips2=findViewById(R.id.gamechips2);
        chips3=findViewById(R.id.gamechips3);
        chips4=findViewById(R.id.gamechips4);
        chips5=findViewById(R.id.gamechips5);
        chips6=findViewById(R.id.gamechips6);

        question=findViewById(R.id.gamequestion);
        chipsquestion1=findViewById(R.id.chipsquestion1);
        chipsquestion2=findViewById(R.id.chipsquestion2);
        chipsquestion3=findViewById(R.id.chipsquestion3);
        chipsquestion4=findViewById(R.id.chipsquestion4);
        chipsquestion5=findViewById(R.id.chipsquestion5);
        ans=findViewById(R.id.gamewagerans);

        imageView1 = findViewById(R.id.gamebanker1);
        imageView2 = findViewById(R.id.gamebanker2);
        imageView3 = findViewById(R.id.gamebanker3);
        imageView4 = findViewById(R.id.gameplayer1);
        imageView5 = findViewById(R.id.gameplayer2);
        imageView6 = findViewById(R.id.gameplayer3);

        rulekuai1 = findViewById(R.id.rulekuai1);
        rulekuai2 = findViewById(R.id.rulekuai2);
        chipskuai = findViewById(R.id.chipskuai);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(game.this, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

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


        if (win<=4){
            chipsquestion4.setText(""+wager);
            chipsquestion4.setVisibility(View.VISIBLE);
        }
        else if (win<=8){
            chipsquestion3.setText(""+wager);
            chipsquestion3.setVisibility(View.VISIBLE);
        }
        else if (win==9){
            chipsquestion2.setText(""+wager);
            chipsquestion2.setVisibility(View.VISIBLE);
        }
        else if (win==10){
            chipsquestion5.setText(""+wager);
            chipsquestion5.setVisibility(View.VISIBLE);
        }
        else if (win==11){
            chipsquestion1.setText(""+wager);
            chipsquestion1.setVisibility(View.VISIBLE);
        }



        final String[] variableValue = {"card"+cardcolor[1]+card[1],"card"+cardcolor[2]+card[2],"card"+cardcolor[3]+card[3],"card"+cardcolor[4]+card[4],"card"+cardcolor[5]+card[5],"card"+cardcolor[6]+card[6],};
        imageView4.setImageResource(getResources().getIdentifier(variableValue[0], "drawable", getPackageName()));
        imageView5.setImageResource(getResources().getIdentifier(variableValue[1], "drawable", getPackageName()));
        //imageView6.setImageResource(getResources().getIdentifier(variableValue[2], "drawable", getPackageName()));
        imageView1.setImageResource(getResources().getIdentifier(variableValue[3], "drawable", getPackageName()));
        imageView2.setImageResource(getResources().getIdentifier(variableValue[4], "drawable", getPackageName()));
        //imageView3.setImageResource(getResources().getIdentifier(variableValue[5], "drawable", getPackageName()));

        ObjectAnimator AnimatorView4 = ObjectAnimator.ofFloat(imageView4, "alpha", 0f, 1f);
        AnimatorView4.setDuration(500);//设置动画时间
        AnimatorView4.addListener(new AnimatorListenerAdapter() {//动画监听
            @Override
            public void onAnimationEnd(Animator animation) {

                imageView1.setVisibility(View.VISIBLE);
                ObjectAnimator AnimatorView5 = ObjectAnimator.ofFloat(imageView1, "alpha", 0f, 1f);
                AnimatorView5.setDuration(500);
                AnimatorView5.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        imageView5.setVisibility(View.VISIBLE);
                        ObjectAnimator AnimatorView1 = ObjectAnimator.ofFloat(imageView5, "alpha", 0f, 1f);
                        AnimatorView1.setDuration(500);
                        AnimatorView1.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {

                                imageView2.setVisibility(View.VISIBLE);
                                ObjectAnimator AnimatorView2 = ObjectAnimator.ofFloat(imageView2, "alpha", 0f, 1f);
                                AnimatorView2.setDuration(500);
                                AnimatorView2.start();

                            }
                        });
                        AnimatorView1.start();

                    }
                });
                AnimatorView5.start();

            }
        });
        AnimatorView4.start();

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
                    textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
                            textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                            if ((card[1]+card[2])%10 > (card[4]+card[5])%10 && win<=4) {
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        correct();
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(ansint==wager){
                                                    wintwoyes();
                                                }
                                                else {
                                                    error();
                                                }
                                            }
                                        });
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });
                            }
                            else if ((card[1]+card[2])%10 < (card[4]+card[5])%10 && win>4 && win<=8) {
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        correct();
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(ansint==wager*0.95){
                                                    wintwoyes();
                                                }
                                                else{
                                                    error();
                                                }
                                            }
                                        });
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });
                            }
                            else if ((card[1]+card[2])%10 == (card[4]+card[5])%10 && win==9) {
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        correct();
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(ansint==wager*8){
                                                    wintwoyes();
                                                }
                                                else{
                                                    error();
                                                }
                                            }
                                        });
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });
                            }
                            else if ((cardcopy[1].equals(cardcopy[2])) && win==10) {
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        correct();
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(ansint==wager*11){
                                                    wintwoyes();
                                                }
                                                else{
                                                    error();
                                                }
                                            }
                                        });
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });
                            }
                            else if ((cardcopy[4].equals(cardcopy[5])) && win==11) {
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        correct();
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(ansint==wager*11){
                                                    wintwoyes();
                                                }
                                                else{
                                                    error();
                                                }
                                            }
                                        });
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });
                            }
                            else {
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        error();
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        wintwono();
                                    }
                                });
                            }

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
                    textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    if((card[4]+card[5])%10<6){
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imageView3.setImageResource(getResources().getIdentifier(variableValue[5], "drawable", getPackageName()));
                                ObjectAnimator AnimatorView = ObjectAnimator.ofFloat(imageView3, "alpha", 0f, 1f);
                                AnimatorView.setDuration(500);
                                AnimatorView.start();
                                question.setText("Whether to payouts ?");
                                textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                                if ((card[1]+card[2])%10 > (card[4]+card[5]+card[6])%10 && win<=4){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager){
                                                        winthreeyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                }
                                else if ((card[1]+card[2])%10 < (card[4]+card[5]+card[6])%10 && win>4 && win<=8){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*0.95){
                                                        winthreeyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                }
                                else if ((card[1]+card[2])%10 == (card[4]+card[5]+card[6])%10 && win==9){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*8){
                                                        winthreeyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                }
                                else if ((cardcopy[1].equals(cardcopy[2])) && win==10){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        winthreeyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                }
                                else if ((cardcopy[4].equals(cardcopy[5])) && win==11){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        winthreeyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                }
                                else {
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            winthreeno();
                                        }
                                    });
                                }

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
                                question.setText("Whether to payouts ?");
                                textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                                if ((card[1]+card[2])%10 > (card[4]+card[5])%10 && win<=4){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager){
                                                        wintwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                }
                                else if ((card[1]+card[2])%10 < (card[4]+card[5])%10 && win>4 && win<=8){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*0.95){
                                                        wintwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                }
                                else if ((card[1]+card[2])%10 == (card[4]+card[5])%10 && win==9){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*8){
                                                        wintwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((cardcopy[1].equals(cardcopy[2])) && win==10){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        wintwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((cardcopy[4].equals(cardcopy[5])) && win==11){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        wintwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else {
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            winthreeno();
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
                    ObjectAnimator AnimatorView = ObjectAnimator.ofFloat(imageView6, "alpha", 0f, 1f);
                    AnimatorView.setDuration(500);
                    AnimatorView.start();
                    question.setText("Do banker need to draws a third card ?");
                    textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
                                ObjectAnimator AnimatorView = ObjectAnimator.ofFloat(imageView3, "alpha", 0f, 1f);
                                AnimatorView.setDuration(500);
                                AnimatorView.start();
                                question.setText("Whether to payouts ?");
                                textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                                if ((card[1]+card[2]+card[3])%10 > (card[4]+card[5]+card[6])%10 && win<=4){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager){
                                                        winfouryes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((card[1]+card[2]+card[3])%10 < (card[4]+card[5]+card[6])%10 && win>4 && win<=8){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*0.95){
                                                        winfouryes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((card[1]+card[2]+card[3])%10 == (card[4]+card[5]+card[6])%10 && win==9){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*8){
                                                        winfouryes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((cardcopy[1].equals(cardcopy[2])) && win==10){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        winfouryes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((cardcopy[4].equals(cardcopy[5])) && win==11){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        winfouryes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else {
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            winfourno();
                                        }
                                    });
                                }
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
                                question.setText("Whether to payouts ?");
                                textToSpeech.speak(question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                                if ((card[1]+card[2]+card[3])%10 > (card[4]+card[5])%10 && win<=4){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager){
                                                        winthreetwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((card[1]+card[2]+card[3])%10 < (card[4]+card[5])%10 && win>4 && win<=8){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*0.95){
                                                        winthreetwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((card[1]+card[2]+card[3])%10 == (card[4]+card[5])%10 && win==9){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*8){
                                                        winthreetwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((cardcopy[1].equals(cardcopy[2])) && win==10){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        winthreetwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else if ((cardcopy[4].equals(cardcopy[5])) && win==11){
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            correct();
                                            submit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(ansint==wager*11){
                                                        winthreetwoyes();
                                                    }
                                                    else {
                                                        error();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });

                                }
                                else {
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error();
                                        }
                                    });
                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            winthreetwono();
                                        }
                                    });
                                }
                            }
                        });
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


    }


    private void Random() {
        Random random=new Random();
        for(int i=1;i<7;i++) {
            card[i] = random.nextInt(13)+1;
        }
        for(int j=1;j<7;j++) {
            cardcolor[j] = random.nextInt(4)+1;
        }
        win = (random.nextInt(11)+1);
        wager = (random.nextInt(98)+1)*100;

    }


    private void correct(){
        rulekuai1.setVisibility(View.GONE);
        rulekuai2.setVisibility(View.GONE);
        chipskuai.setVisibility(View.VISIBLE);
        ans.setText(""+0);
        ansint=0;
        ans.setVisibility(View.VISIBLE);
    }


    private void error() {
        LayoutInflater factory=LayoutInflater.from(game.this);
        final View v1=factory.inflate(R.layout.wrong,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(v1);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);

    }


    private void wintwoyes(){
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
                params.put("ansint",Integer.toString(ansint));
                params.put("wager",Integer.toString(wager));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(game.this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }



    private void wintwono() {

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
                params.put("ansint",Integer.toString(0));
                params.put("wager",Integer.toString(0));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }


    private void winthreeyes() {

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
                params.put("ansint",Integer.toString(ansint));
                params.put("wager",Integer.toString(wager));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }

    private void winthreeno() {

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
                params.put("ansint",Integer.toString(0));
                params.put("wager",Integer.toString(0));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }


    private void winthreetwoyes() {

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
                params.put("ansint",Integer.toString(ansint));
                params.put("wager",Integer.toString(wager));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }

    private void winthreetwono() {

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
                params.put("ansint",Integer.toString(0));
                params.put("wager",Integer.toString(0));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }


    private void winfouryes() {

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
                params.put("ansint",Integer.toString(ansint));
                params.put("wager",Integer.toString(wager));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }

    private void winfourno() {

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
                params.put("ansint",Integer.toString(0));
                params.put("wager",Integer.toString(0));
                params.put("name",checkname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LayoutInflater factory=LayoutInflater.from(game.this);
        final View win=factory.inflate(R.layout.plus50,null);

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(game.this);
        inputDialog.setView(win);
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

                LayoutInflater factory2=LayoutInflater.from(game.this);
                final View v2=factory2.inflate(R.layout.wager_calculation_rules,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(game.this);
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
                intent3.setClass(game.this,historygame.class);
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }


    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
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

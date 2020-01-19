package com.example.finalyearproject;

import android.animation.AnimatorSet;
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

public class question extends AppCompatActivity {

    Session session;
    private String checkname,checkmark,checklevel;
    private static String URL,checkurl;
    private Button correct,incorrect,question_next;
    private TextView question_question,question_prompt,question_no;
    private ImageView correctimage,incorrectimage,plus5;
    private ObjectAnimator Animator,Animator2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        question_next = findViewById(R.id.question_next);
        question_question = findViewById(R.id.question_question);
        question_prompt = findViewById(R.id.question_prompt);
        question_no = findViewById(R.id.question_no);

        correctimage = findViewById(R.id.question_correct);
        incorrectimage = findViewById(R.id.question_incorrect);
        plus5 = findViewById(R.id.plus5);

        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(Session.NAME);
        String url = user.get(Session.URL);

        checkurl= url +"checkid.php";
        URL= url +"question.php";

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
                                Toast.makeText(question.this, "error！" + error.toString(),
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

                RequestQueue requestQueue = Volley.newRequestQueue(question.this);
                requestQueue.add(stringRequest);

            }
        });

        /*
        Animator = ObjectAnimator.ofFloat(question_next,"translationX",-50,50);
        Animator.setRepeatCount(-1);
        Animator.setDuration(2000);
        Animator.start();
        */

        question1();

        //1.At the beginning, starting from the "Banker", the cards are distributed one at a time, and each hand distributes two cards.
        //Prompt: starting from the "Player".

        //2.Banker's first two cards total 1 points, which can draw a third card.
        //Prompt: Banker draws a third card on 0, 1 or 2.

        //3.If Banker's first two card total is 4, Player's third card is K, Banker need to draw third card.
        //Prompt:  when the banker's hands is 4, banker stands unless the player's third card is 2-3-4-5-6-7.

        //4.Player's first two cards total 5 points, which can draw a third card.
        //Prompt: Player stands on totals of 6 or 7 or 8 or 9.

        //5.Banker's first two cards total 8 points, which can draw a third card.
        //Prompt:  A total of 8 or 9 is called a "natural" and no one can get more cards.

        //6.If a participant wins by betting a Banker wager, participant wins 1 times the betting amount.
        //Prompt: participant wins 0.95 times the betting amount.

        //7.J is bigger than 10 in Baccarat
        //Prompt:  J, Q, K, 10 and the total of ten points are all set to zero in Baccarat.

        //8.The Player's first card is 5 and the second card is 8, so a total of 13 points.
        //Prompt:  The total points of Player is 3.

        //9.If a participant wins by betting a TIE wager, participant wins 11 times the betting amount.
        //Prompt:  participant wins 8 times the betting amount.

        //10.If Banker's first two card total is 5, Player's third card is 5, Banker need to draw third card.
        //Prompt:  when the Banker's hands is 5, banker stands unless the Player's third card is 4-5-6-7.

    }

    private void question1() {
        question_question.setText("At the beginning, starting from the \"Banker\", the cards are distributed one at a time, and each hand distributes two cards.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: starting from the \"Player\".");
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: starting from the \"Player\".");
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question2();
            }
        });
    }

    private void question2() {
        question_no.setText("2/10");
        begin();
        question_question.setText("Banker's first two cards total 1 points, which can draw a third card.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: Banker draws a third card on 0, 1 or 2.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: Banker draws a third card on 0, 1 or 2.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question3();
            }
        });
    }


    private void question3() {
        question_no.setText("3/10");
        begin();
        question_question.setText("If Banker's first two card total is 4, Player's third card is K, Banker need to draw third card.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: when the banker's hands is 4, banker stands unless the player's third card is 2-3-4-5-6-7.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: when the banker's hands is 4, banker stands unless the player's third card is 2-3-4-5-6-7.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question4();
            }
        });
    }

    private void question4() {
        question_no.setText("4/10");
        begin();
        question_question.setText("Player's first two cards total 5 points, which can draw a third card.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: Player stands on totals of 6 or 7 or 8 or 9.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: Player stands on totals of 6 or 7 or 8 or 9.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question5();
            }
        });
    }

    private void question5() {
        question_no.setText("5/10");
        begin();
        question_question.setText("Banker's first two cards total 8 points, which can draw a third card.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt:  A total of 8 or 9 is called a \"natural\" and no one can get more cards.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: A total of 8 or 9 is called a \"natural\", no one can get more cards.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question6();
            }
        });
    }

    private void question6() {
        question_no.setText("6/10");
        begin();
        question_question.setText("If a participant wins by betting a Banker wager, participant wins 1 times the betting amount.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: participant wins 0.95 times the betting amount.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: participant wins 0.95 times the betting amount.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question7();
            }
        });
    }

    private void question7() {
        question_no.setText("7/10");
        begin();
        question_question.setText("J is bigger than 10 in Baccarat.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: J, Q, K, 10 and the total of ten points are all set to zero in Baccarat.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: J, Q, K, 10 and the total of ten points are all set to zero in Baccarat.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question8();
            }
        });
    }

    private void question8() {
        question_no.setText("8/10");
        begin();
        question_question.setText("The Player's first card is 5 and the second card is 8, so a total of 13 points.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: The total points of Player is 3.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: The total points of Player is 3.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question9();
            }
        });
    }

    private void question9() {
        question_no.setText("9/10");
        begin();
        question_question.setText("If a participant wins by betting a TIE wager, participant wins 11 times the betting amount.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: participant wins 8 times the betting amount.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: participant wins 8 times the betting amount.");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question10();
            }
        });
    }

    private void question10() {
        question_no.setText("10/10");
        begin();
        question_question.setText("If Banker's first two card total is 5, Player's third card is 5, Banker need to draw third card.");
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus();
                question_prompt.setText("Prompt: when the Banker's hands is 5, banker stands unless the Player's third card is 4-5-6-7");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct.setEnabled(false);
                incorrect.setEnabled(false);
                incorrectimage.setVisibility(View.VISIBLE);
                question_prompt.setText("Prompt: when the Banker's hands is 5, banker stands unless the Player's third card is 4-5-6-7");
                question_prompt.setVisibility(View.VISIBLE);
            }
        });
        question_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory=LayoutInflater.from(question.this);
                final View win=factory.inflate(R.layout.finish,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(question.this);
                inputDialog.setView(win);
                inputDialog.setPositiveButton("return",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(question.this,MainActivity.class);
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
        });
    }

    private void plus() {

        correct.setEnabled(false);
        incorrect.setEnabled(false);
        correctimage.setVisibility(View.VISIBLE);
        plus5.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(question.this, "error！" + error.toString(),
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void begin(){
        question_prompt.setVisibility(View.INVISIBLE);
        incorrectimage.setVisibility(View.INVISIBLE);
        correctimage.setVisibility(View.INVISIBLE);
        plus5.setVisibility(View.INVISIBLE);
        correct.setEnabled(true);
        incorrect.setEnabled(true);
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
                intent.setClass(question.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.next:
                Intent intent2 = new Intent();
                intent2.setClass(question.this,game.class);
                startActivity(intent2);
                break;

            case R.id.dealtrule:

                LayoutInflater factory=LayoutInflater.from(question.this);
                final View v1=factory.inflate(R.layout.dealt_card_rules,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(question.this);
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
                        new AlertDialog.Builder(question.this);
                inputDialog2.setTitle("Wager calculation rule");
                inputDialog2.setIcon(R.drawable.logo);
                inputDialog2.setMessage("- The winner takes the betting amount.\n"+
                        "(If a participant wins by betting on the hand of the “banker”, 5% commission is deducted from the winning amount)\n" +
                        "(If a participant wins by betting on the hand of the “player”, participant wins 1 times the betting amount)\n\n"+
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
                AlertDialog.Builder inputDialog3 =
                        new AlertDialog.Builder(question.this);
                inputDialog3.setMessage("Temporarily unavailable");
                inputDialog3.setPositiveButton("sure",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;
            default:
        }
        return true;
    }

/*
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(question.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
*/

}

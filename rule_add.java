package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class rule_add extends AppCompatActivity {

    private static String checkurl;
    Session session;
    private String checkname,checkmark,checklevel;
    private int card1,card2,cardcolor1,cardcolor2,card1copy,card2copy,a=0;
    private ImageView rule_add_card1,rule_add_card2;
    private TextView rule_add_question;
    private CheckBox box1,box2,box3,box4,box5,box6,box7,box8,box9,box10,box11,box12,box13;
    private Button rule_add_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rule_add_card1 = findViewById(R.id.rule_add_card1);
        rule_add_card2 = findViewById(R.id.rule_add_card2);
        rule_add_question = findViewById(R.id.rule_add_question);
        rule_add_submit = findViewById(R.id.rule_add_submit);

        box1 = findViewById(R.id.card1);
        box2 = findViewById(R.id.card2);
        box3 = findViewById(R.id.card3);
        box4 = findViewById(R.id.card4);
        box5 = findViewById(R.id.card5);
        box6 = findViewById(R.id.card6);
        box7 = findViewById(R.id.card7);
        box8 = findViewById(R.id.card8);
        box9 = findViewById(R.id.card9);
        box10 = findViewById(R.id.card10);
        box11 = findViewById(R.id.card11);
        box12 = findViewById(R.id.card12);
        box13 = findViewById(R.id.card13);


        session=new Session(this);
        HashMap<String,String> user=session.getUserDetail();
        checkname=user.get(Session.NAME);
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
                                Toast.makeText(rule_add.this, "error！" + error.toString(),
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

                RequestQueue requestQueue = Volley.newRequestQueue(rule_add.this);
                requestQueue.add(stringRequest);

            }
        });


        do {
            Random random=new Random();
            cardcolor1 = random.nextInt(4)+1;
            cardcolor2 = random.nextInt(4)+1;
            card1 = random.nextInt(13)+1;
            card2 = random.nextInt(13)+1;
            card1copy=card1;
            card2copy=card2;
            if (card1copy>9){
                card1copy=0;
            }
            if (card2copy>9){
                card2copy=0;
            }
        }while ( ((card1copy+card2copy)%10<3) || ((card1copy+card2copy)%10>6));

        rule_add_card1.setImageResource(getResources().getIdentifier("card"+cardcolor1+card1, "drawable", getPackageName()));
        rule_add_card2.setImageResource(getResources().getIdentifier("card"+cardcolor2+card2, "drawable", getPackageName()));
        rule_add_question.setText(""+(card1copy+card2copy)%10);


        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=1;
                else
                    a-=1;
            }
        });

        box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=2;
                else
                    a-=2;
            }
        });

        box3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=4;
                else
                    a-=4;
            }
        });

        box4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=8;
                else
                    a-=8;
            }
        });

        box5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=16;
                else
                    a-=16;
            }
        });

        box6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=32;
                else
                    a-=32;
            }
        });

        box7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=64;
                else
                    a-=64;
            }
        });

        box8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=128;
                else
                    a-=128;
            }
        });

        box9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=256;
                else
                    a-=256;
            }
        });

        box10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=512;
                else
                    a-=512;
            }
        });

        box11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=1024;
                else
                    a-=1024;
            }
        });

        box12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=2048;
                else
                    a-=2048;
            }
        });

        box13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    a+=4096;
                else
                    a-=4096;
            }
        });

        rule_add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((card1copy+card2copy)%10==3){
                    if (a==8063){
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.pass,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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
                    else{
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.rule_add_error_3,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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

                if ((card1copy+card2copy)%10==4){
                    if (a==126){
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.pass,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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
                    else{
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.rule_add_error_4,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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

                if ((card1copy+card2copy)%10==5){
                    if (a==120){
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.pass,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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
                    else{
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.rule_add_error_5,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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

                if ((card1copy+card2copy)%10==6){
                    if (a==96){
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.pass,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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
                    else{
                        LayoutInflater factory=LayoutInflater.from(rule_add.this);
                        View v1=factory.inflate(R.layout.rule_add_error_6,null);
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(rule_add.this);
                        inputDialog.setView(v1);
                        inputDialog.setPositiveButton("next",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent();
                                        intent.setClass(rule_add.this,rule_add.class);
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

            }
        });


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
                intent.setClass(rule_add.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.next:
                Intent intent2 = new Intent();
                intent2.setClass(rule_add.this,rule_add.class);
                startActivity(intent2);
                break;

            case R.id.dealtrule:

                LayoutInflater factory=LayoutInflater.from(rule_add.this);
                final View v1=factory.inflate(R.layout.dealt_card_rules,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(rule_add.this);
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
                        new AlertDialog.Builder(rule_add.this);
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
                        new AlertDialog.Builder(rule_add.this);
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


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(rule_add.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

}

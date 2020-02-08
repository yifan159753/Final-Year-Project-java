package com.example.finalyearproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

public class introduction extends AppCompatActivity {

    private Button playercard,bankercard,tie,pair,box,player,banker;
    private ObjectAnimator playercardAnimation,playercardAnimation2,bankercardAnimation,bankercardAnimation2,boxAnimation,boxAnimation2;
    private ObjectAnimator tieAnimation,tieAnimation2,pairAnimation,pairAnimation2;
    private ObjectAnimator playerAnimation,playerAnimation2,bankerAnimation,bankerAnimation2;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);


        playercard = findViewById(R.id.playercard);
        bankercard = findViewById(R.id.bankercard);
        box = findViewById(R.id.box);
        pair = findViewById(R.id.pair);
        tie = findViewById(R.id.tie);
        player = findViewById(R.id.player);
        banker = findViewById(R.id.banker);

        playercardAnimation = ObjectAnimator.ofFloat(playercard,"scaleX", 1f, 1.5f, 1f);
        playercardAnimation2 = ObjectAnimator.ofFloat(playercard,"scaleY", 1f, 1.5f, 1f);
        playercardAnimation.setRepeatCount(-1);
        playercardAnimation2.setRepeatCount(-1);


        bankercardAnimation = ObjectAnimator.ofFloat(bankercard,"scaleX", 1f, 1.5f, 1f);
        bankercardAnimation2 = ObjectAnimator.ofFloat(bankercard,"scaleY", 1f, 1.5f, 1f);
        bankercardAnimation.setRepeatCount(-1);
        bankercardAnimation2.setRepeatCount(-1);
        bankercardAnimation.setStartDelay(1000);
        bankercardAnimation2.setStartDelay(1000);

        boxAnimation = ObjectAnimator.ofFloat(box,"scaleX", 1f, 1.5f, 1f);
        boxAnimation2 = ObjectAnimator.ofFloat(box,"scaleY", 1f, 1.5f, 1f);
        boxAnimation.setRepeatCount(-1);
        boxAnimation2.setRepeatCount(-1);

        tieAnimation = ObjectAnimator.ofFloat(tie,"scaleX", 1f, 1.3f, 1f);
        tieAnimation2 = ObjectAnimator.ofFloat(tie,"scaleY", 1f, 1.3f, 1f);
        tieAnimation.setRepeatCount(-1);
        tieAnimation2.setRepeatCount(-1);

        pairAnimation = ObjectAnimator.ofFloat(pair,"scaleX", 0.8f, 1.3f, 0.8f);
        pairAnimation2 = ObjectAnimator.ofFloat(pair,"scaleY", 0.8f, 1.3f, 0.8f);
        pairAnimation.setRepeatCount(-1);
        pairAnimation2.setRepeatCount(-1);
        pairAnimation.setStartDelay(500);
        pairAnimation2.setStartDelay(500);

        playerAnimation = ObjectAnimator.ofFloat(player,"scaleX", 1f, 1.3f, 1f);
        playerAnimation2 = ObjectAnimator.ofFloat(player,"scaleY", 1f, 1.3f, 1f);
        playerAnimation.setRepeatCount(-1);
        playerAnimation2.setRepeatCount(-1);
        pairAnimation.setStartDelay(1500);
        pairAnimation2.setStartDelay(1500);

        bankerAnimation = ObjectAnimator.ofFloat(banker,"scaleX", 1f, 1.5f, 1f);
        bankerAnimation2 = ObjectAnimator.ofFloat(banker,"scaleY", 1f, 1.5f, 1f);
        bankerAnimation.setRepeatCount(-1);
        bankerAnimation2.setRepeatCount(-1);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(playercardAnimation,playercardAnimation2,bankercardAnimation, bankercardAnimation2,boxAnimation,boxAnimation2,tieAnimation,tieAnimation2,pairAnimation,pairAnimation2,playerAnimation,playerAnimation2,bankerAnimation,bankerAnimation2);
        set.setDuration(2000);
        set.start();


        playercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.introduction_playercard,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

            }
        });

        bankercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.introduction_bankercard,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

            }
        });

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.introduction_box,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

            }
        });

        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.introduction_pair,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

            }
        });

        tie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.introduction_tie,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

            }
        });

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.introduction_player,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

            }
        });

        banker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.introduction_banker,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
                inputDialog2.setView(v2);

                final AlertDialog dialog2 = inputDialog2.create();
                final Window window2 = dialog2.getWindow();
                window2.setBackgroundDrawable(new ColorDrawable(0));
                dialog2.show();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu2,menu);
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
                intent.setClass(introduction.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.next:
                Intent intent2 = new Intent();
                intent2.setClass(introduction.this,MainActivity.class);
                startActivity(intent2);
                break;

            case R.id.dealtrule:

                LayoutInflater factory=LayoutInflater.from(introduction.this);
                final View v1=factory.inflate(R.layout.dealt_card_rules,null);

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
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

                LayoutInflater factory2=LayoutInflater.from(introduction.this);
                final View v2=factory2.inflate(R.layout.wager_calculation_rules,null);

                AlertDialog.Builder inputDialog2 =
                        new AlertDialog.Builder(introduction.this);
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


            default:
        }
        return true;
    }


}

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

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
                inputDialog.setMessage("Two cards are dealt for each hand. The point totals determine whether either hand gets a third card. " +
                        "The player hand is completed first. A total of 8 or 9 is called a \"natural,\" and the player hand gets no more cards. In fact, " +
                        "unless the banker has a natural 9 or ties the natural 8, no further cards are drawn, and the naturals are automatic winners. " +
                        "Player also stands on totals of 6 or 7. On any other total, zero through 5, player draws a third card, unless banker has a natural, " +
                        "in which case the bank hand wins with no further draw.\n").show();

            }
        });

        bankercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
                inputDialog.setMessage("Banker stands on 7, 8, or 9 and draws on 0, 1, or 2, but on other hands the banker's play is dependent on the value of the player's third card:\n" +
                        "\nWhen the banker's hands is 3, banker stands unless the player's third card is not 8; \n" +
                        "When the banker's hands is 4, banker stands unless the player's third card is 2-3-4-5-6-7;\n" +
                        "When the banker's hands is 5, banker stands unless the player's third card is 4-5-6-7;\n" +
                        "When the banker's hands is 6, banker stands unless the player's third card is 6-7;\n" +
                        "If the player has one of its other two standing hands, 6 or 7, banker stands on 6 as well as 7, 8, and 9.\n" +
                        "\nNeither hand ever gets more than three cards. After the hands have been played out, the hand totaling closer to 9 wins. ").show();

            }
        });

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
                inputDialog.setMessage("Use 8 decks of 52 cards each.\n" +
                        "\nAt the beginning, dealer shuffled the cards first, then a bettor cut the cards or dealer cut the cards himself, inserted the white card above at least about twelve cards at the end, and put the entire set of shuffled cards In the distribution card box, all the cards face down.\n" +
                        "\nDealer chooses how many cards should be removes based on the point of cards on the first card, and then starting to distribute cards.\n" +
                        "\nWhen the hand is dealt to the white card, it is the last round or only one round can be played.  Taking out the white card and after the game is over, change a new set of cards.\n" +
                        "\nJ, Q, K, 10 and the total of ten points are all set to zero.  Other cards are calculated based on the points on the card, and the card with 9 points is the maximum card.\n" +
                        "\nAt the beginning, starting from the \"Player\", the cards are distributed one at a time, and each hand distributes two cards.\n").show();

            }
        });

        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
                inputDialog.setMessage("When the first 2 cards are the same and a participant wins by Pair Bet, participant wins 11 times the betting amount.\n").show();

            }
        });

        tie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
                inputDialog.setMessage("When the sums of the banker’s hand and the player’s hand are the same, If a participant wins by betting a tie wager, participant wins 8 times the betting amount. \n").show();

            }
        });

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
                inputDialog.setMessage("When the player's hand is larger than the banker's hand, If a participant wins by betting on the hand of the “player”, participant wins 1 times the betting amount.\n").show();

            }
        });

        banker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(introduction.this);
                inputDialog.setMessage("When the banker's hand is larger than the player's hand, If a participant wins by betting on the hand of the “banker”, participant wins 1 times the betting amount, but 5% commission is deducted from the winning amount.\n").show();

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
                        new AlertDialog.Builder(introduction.this);
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
                        new AlertDialog.Builder(introduction.this);
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


}

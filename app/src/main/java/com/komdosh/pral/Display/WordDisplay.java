package com.komdosh.pral.Display;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.komdosh.pral.R;
import com.komdosh.pral.System.Words.WordTask;
import com.komdosh.pral.System.Words.Words;


/**
 * Created by Komdosh on 24.09.2016.
 */

public class WordDisplay extends AppCompatActivity {
    EditText et;
    Words words;
    WordTask wt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_layout);
        et = (EditText) findViewById(R.id.editText);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(check()){
                    rightAnswer();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(check()){
                    rightAnswer();
                }

                return false;
            }
        });
        words = new Words();
        wt = words.generateTask(this);
        TextView counter = (TextView)findViewById(R.id.counterRight);
        counter.setText(""+wt.str.length);
    }

    private void rightAnswer(){
        TextView counter = (TextView)findViewById(R.id.counterRight);
        counter.setText(""+(Integer.parseInt(counter.getText().toString())-1));
        et.setText(null);
        rightPopup();
        if(Integer.parseInt(counter.getText().toString())<=0) {
            et.clearFocus();
            Toast.makeText(this, "Всё верно, приходите завтра", Toast.LENGTH_SHORT).show();
            CountDownTimer cT = new CountDownTimer(3000, 1000) {


                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    nextActivity();
                }
            }.start();
        }
    }

    public void nextActivity(){
        Intent i = new Intent(this, MathematicsDisplay.class);
        finish();
        startActivity(i);
    }

    private boolean check(){
        return wt.isContain(et.getText().toString());
    }

    public void start(View view){
        final RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);
        final Button b = (Button)findViewById(R.id.button);

        startChronometr();

        fillContent();
        rl.animate()
                .alpha(0.0f)
                .setDuration(600)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rl.setVisibility(View.INVISIBLE);
                    }
                });
        b.animate()
                .alpha(0.0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        b.setVisibility(View.INVISIBLE);
                    }
                });
    }


    private void startChronometr(){
        final Chronometer mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();

/*                if (elapsedMillis > 5000) {
                    String strElapsedMillis = "Прошло больше 5 секунд";
                    Toast.makeText(getApplicationContext(),
                            strElapsedMillis, Toast.LENGTH_SHORT)
                            .show();
                }*/
            }
        });
    }

    private void fillContent(){
        TableLayout tl = (TableLayout) findViewById(R.id.words);
        tl.setAlpha(0.0f);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT);
        int hm = wt.str.length;
        for(int i = 0; i<hm; i+=2){
            TableRow tr = new TableRow(this);
            tr.setMinimumHeight(50);
            for(int j = 0; j<2; ++j){
                if(i+j<wt.str.length){

                    TextView tv = (TextView)getLayoutInflater().inflate(R.layout.word_text_view_template, null);
                    tv.setLayoutParams(layoutParams);
                    tv.setText(wt.str[i+j]);
                    tr.addView(tv);
                }
            }
            tl.addView(tr);
        }
        tl.animate()
                .alpha(1.0f)
                .setDuration(2000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });

        final TextView t = (TextView) findViewById(R.id.timeDown);

        CountDownTimer cT = new CountDownTimer(5000, 1000) {


            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / (60 * 1000);
                long seconds = millisUntilFinished / 1000;
                t.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                t.setVisibility(View.INVISIBLE);
                TableLayout tl = (TableLayout) findViewById(R.id.words);
                tl.animate()
                        .alpha(0.0f)
                        .setDuration(1000)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                et.setAlpha(0.0f);
                                et.setVisibility(View.VISIBLE);
                                TextView counter = (TextView)findViewById(R.id.counterRight);
                                counter.setVisibility(View.VISIBLE);
                                et.animate()
                                        .alpha(1.0f)
                                        .setDuration(1000)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                            }
                                        });
                            }
                        });

            }
        }.start();
    }

    protected void rightPopup(){
        TextView rightTextView = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.setMargins(0, 150, 0, 0); // setMargins(left, top, right, bottom);
        textViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rightTextView.setLayoutParams(textViewLayoutParams);

        rightTextView.setId(R.id.rightTextView);
        rightTextView.setTextColor(0xFF22F74F);
        rightTextView.setTypeface(rightTextView.getTypeface(), Typeface.BOLD);
        rightTextView.setText("Right!");
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        rightTextView.animate()
                .translationY(-50)
                .alpha(0.0f)
                .setDuration(1500);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.activity_main);
        relativeLayout.addView(rightTextView);
    }
}

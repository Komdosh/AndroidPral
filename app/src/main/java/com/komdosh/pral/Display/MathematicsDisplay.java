package com.komdosh.pral.Display;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.komdosh.pral.R;
import com.komdosh.pral.System.Math.MathTask;
import com.komdosh.pral.System.Math.Mathematics;


/**
 * Created by Komdosh on 24.09.2016.
 */

public class MathematicsDisplay extends AppCompatActivity {
    EditText et;
    final Mathematics m = new Mathematics();
    final MathTask t = m.generateTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_layout);
        et = (EditText) findViewById(R.id.editText);
        TextInputLayout v = (TextInputLayout)findViewById(R.id.txt);
        v.setHint(getString(R.string.example, t.task[0],t.task[1],t.task[2]));
        v.setHintTextAppearance(R.style.InputLayout);

        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(check())
                    rightAnswer();
                return false;
            }
        });
        et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.KEYCODE_ENTER)
                    rightAnswer();
                return false;
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

                if(elapsedMillis>=60*1000){
                   nextActivity();
                }

/*                if (elapsedMillis > 5000) {
                    String strElapsedMillis = "Прошло больше 5 секунд";
                    Toast.makeText(getApplicationContext(),
                            strElapsedMillis, Toast.LENGTH_SHORT)
                            .show();
                }*/
            }
        });
    }

    private void nextActivity(){
        Intent i = new Intent(this, WordDisplay.class);
        finish();
        startActivity(i);
    }

    private boolean check(){
        return et.getText().toString().equals(t.answer.toString());
    }

    private void rightAnswer(){
        rightPopup();
        t.set(m.generateTask());
        et.setText("");
        TextInputLayout v = (TextInputLayout)findViewById(R.id.txt);
        v.setHint(getString(R.string.example, t.task[0],t.task[1],t.task[2]));
        TextView counter = (TextView)findViewById(R.id.counterRight);
        counter.setText(""+(Integer.parseInt(counter.getText().toString())+1));
    }

    public void start(View view){
        final RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);
        final Button b = (Button)findViewById(R.id.button);

        startChronometr();

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

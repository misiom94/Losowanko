package com.example.misio.losowanko;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Animation rotateAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rotateAnimation = AnimationUtils.loadAnimation(this,R.anim.rotation);

        Button buttonExit = (Button) findViewById(R.id.buttonWyjdz);
        final Button buttonStart = (Button) findViewById(R.id.buttonRozpocznij);
        buttonStart.setBackgroundResource(R.drawable.arrow);
        buttonExit.setBackgroundResource(R.drawable.exit);
        buttonStart.startAnimation(rotateAnimation);

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
            });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(MainActivity.this, ActivityLosowanie.class);
                startActivity(startIntent);
            }
        });



    }
}

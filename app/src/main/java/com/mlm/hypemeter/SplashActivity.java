package com.mlm.hypemeter;


import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
/**
 * Created by Martin on 2/15/2016.
 */
public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        int[] imageList = {R.drawable.hmsplashchi, R.drawable.hmsplashsf2, R.drawable.hmsplashla, R.drawable.hmsplashny, R.drawable.vegassplash, R.drawable.miamisplash};

        Random randomSplash = new Random(System.currentTimeMillis());
        int posOfImage = randomSplash.nextInt(imageList.length - 0);

        ImageView splashIcon = (ImageView) findViewById(R.id.hmsplash);
        splashIcon.setBackgroundResource(imageList[posOfImage]);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences settings=getSharedPreferences("prefs",0);
                boolean firstRun=settings.getBoolean("firstRun",false);
                if(firstRun==false) // If running for first time
                {
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putBoolean("firstRun",true);
                    editor.commit();
                    Intent i=new Intent(SplashActivity.this, EduPageActivity.class); // First time activity
                    startActivity(i);
                }
                else
                {
                    Intent a=new Intent(SplashActivity.this, LoginActivity.class); //Main Activity
                    startActivity(a);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}

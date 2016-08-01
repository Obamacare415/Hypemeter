package com.mlm.hypemeter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Martin on 2/16/2016.
 */

public class RankingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rankings_activity);

        View hotspotSmall = findViewById(R.id.hotspot_test);
        View hotspotMed = findViewById(R.id.hotspot_med);
        View hotspotLarge = findViewById(R.id.hotspot_large);

        Animation hotspot1 = AnimationUtils.loadAnimation(this, R.anim.hotspot_anim);

        Animation hotspot2 = AnimationUtils.loadAnimation(this, R.anim.hotspot_anim);
        hotspot2.setStartOffset(200);

        Animation hotspot3 = AnimationUtils.loadAnimation(this, R.anim.hotspot_anim);
        hotspot3.setStartOffset(300);

        hotspotSmall.startAnimation(hotspot1);
        hotspotMed.startAnimation(hotspot2);
        hotspotLarge.startAnimation(hotspot3);

    }
}

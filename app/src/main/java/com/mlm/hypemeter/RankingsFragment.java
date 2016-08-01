package com.mlm.hypemeter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Martin on 7/13/2016.
 */
public class RankingsFragment extends Fragment {

    private View RankingsView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RankingsView = inflater.inflate(R.layout.rankings_activity, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Power Rankings");

        View hotspotSmall = RankingsView.findViewById(R.id.hotspot_test);
        View hotspotMed = RankingsView.findViewById(R.id.hotspot_med);
        View hotspotLarge = RankingsView.findViewById(R.id.hotspot_large);

        Animation hotspot1 = AnimationUtils.loadAnimation(this.getActivity(), R.anim.hotspot_anim);

        Animation hotspot2 = AnimationUtils.loadAnimation(this.getActivity(), R.anim.hotspot_anim);
        hotspot2.setStartOffset(200);

        Animation hotspot3 = AnimationUtils.loadAnimation(this.getActivity(), R.anim.hotspot_anim);
        hotspot3.setStartOffset(300);

        hotspotSmall.startAnimation(hotspot1);
        hotspotMed.startAnimation(hotspot2);
        hotspotLarge.startAnimation(hotspot3);

        return RankingsView;
    }
}

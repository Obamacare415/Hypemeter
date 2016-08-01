package com.mlm.hypemeter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Martin on 2/15/2016.
 */
public class MainFragment extends Fragment {

    private View nearbyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        nearbyView = inflater.inflate(R.layout.content_main, container, false);

        return nearbyView;
    }
}

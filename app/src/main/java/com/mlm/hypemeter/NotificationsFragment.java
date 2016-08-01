package com.mlm.hypemeter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Martin on 3/15/2016.
 */

public class NotificationsFragment extends Fragment {

    //public static final String ARG_PAGE = "ARG_PAGE";

    /*public static NotificationsFragment newInstance(int page) {
        Bundle args = new Bundle();
        //args.putInt(ARG_PAGE, page);
        NotificationsFragment fragment = new NotificationsFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPage = getArguments().getInt(ARG_PAGE);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        return view;
    }
}

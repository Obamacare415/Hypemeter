package com.mlm.hypemeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Martin on 7/13/2016.
 */
public class ProfileFragment extends Fragment {

    private View ProfileView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProfileView = inflater.inflate(R.layout.profile_activity, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        TextView locUserEntry = (TextView) ProfileView.findViewById(R.id.prof_location);

        //Intent iString = getIntent();
        //String locUserString = iString.getStringExtra("loctext_userEntry");

        //locUserEntry.setText(locUserString);

        ImageView profEdit = (ImageView) ProfileView.findViewById(R.id.prof_edit);

        profEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(i);
            }
        });

        return ProfileView;
    }
}

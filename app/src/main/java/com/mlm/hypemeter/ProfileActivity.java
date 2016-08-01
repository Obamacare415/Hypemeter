package com.mlm.hypemeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Martin on 2/16/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        TextView locUserEntry = (TextView)findViewById(R.id.prof_location);

        Intent iString = getIntent();
        String locUserString = iString.getStringExtra("loctext_userEntry");

        locUserEntry.setText(locUserString);

        ImageView profEdit = (ImageView)findViewById(R.id.prof_edit);

        profEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });
    }
}

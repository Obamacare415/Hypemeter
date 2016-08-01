package com.mlm.hypemeter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Martin on 2/16/2016.
 */
public class EditProfileActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        ImageButton editprofCancel = (ImageButton)findViewById(R.id.editprof_cancel);

        editprofCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                finish();
            }
        });

        ImageButton editprofDone = (ImageButton)findViewById(R.id.editprof_done);

        final EditText editLoc = (EditText)findViewById(R.id.editprof_locedit);

        final String editLocText = editLoc.getText().toString();

        editprofDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent iString = new Intent(getApplicationContext(), ProfileActivity.class);
                iString.putExtra("loctext_userEntry", editLocText);

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                finish();
            }
        });
    }
}

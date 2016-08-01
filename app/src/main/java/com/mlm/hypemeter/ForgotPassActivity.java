package com.mlm.hypemeter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Martin on 2/15/2016.
 */
public class ForgotPassActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpass_activity);

        ImageButton cancelPass = (ImageButton) findViewById(R.id.cancelpass);

        cancelPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView cPassText = (TextView) findViewById(R.id.cancelpass_text);

        cPassText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton donePass = (ImageButton) findViewById(R.id.pass_done);

        donePass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView dPassText = (TextView) findViewById(R.id.done_text);

        dPassText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button sendPass = (Button) findViewById(R.id.forgotpass_button);

        sendPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText (getApplicationContext(), "A temporary password has been sent to you.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}

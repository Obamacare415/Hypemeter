package com.mlm.hypemeter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Martin on 2/15/2016.
 */
public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                finish();
            }
        });

        Button regButton = (Button) findViewById(R.id.register_button);

        regButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);

                finish();
            }
        });

        TextView forgotPass = (TextView) findViewById(R.id.login_forgotpassword);

        forgotPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ForgotPassActivity.class);
                startActivity(i);
            }
        });
    }
}

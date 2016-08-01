package com.mlm.hypemeter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Martin on 2/16/2016.
 */
public class FiltersActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_activity);

        // Get the between instance stored values
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        // Set the UI values
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiusradiogroup);

        if(preferences.getInt("radgroup", 0) != 0){

            radioGroup.check(preferences.getInt("radgroup", 0));

        }

        RadioButton radioRadHalf = (RadioButton)findViewById(R.id.radhalf_box);
        radioRadHalf.setSelected(preferences.getBoolean("radhalfbox", false));

        RadioButton radioRad1 = (RadioButton)findViewById(R.id.rad1_box);
        radioRad1.setSelected(preferences.getBoolean("rad1box", false));

        RadioButton radioRad2 = (RadioButton)findViewById(R.id.rad2_box);
        radioRad2.setSelected(preferences.getBoolean("rad2box", false));

        RadioButton radioRad5 = (RadioButton)findViewById(R.id.rad5_box);
        radioRad5.setSelected(preferences.getBoolean("rad5box", false));

        RadioGroup priceGroup = (RadioGroup)findViewById(R.id.priceradiogroup);

        if (preferences.getInt("pricegroup", 0) != 0) {

            priceGroup.check(preferences.getInt("pricegroup", 0));

        }

        RadioButton radioPrice1 = (RadioButton)findViewById(R.id.price1_radio);
        radioPrice1.setSelected(preferences.getBoolean("price1checkbox", false));

        RadioButton radioPrice2 = (RadioButton)findViewById(R.id.price2_radio);
        radioPrice2.setSelected(preferences.getBoolean("price2checkbox", false));

        RadioButton radioPrice3 = (RadioButton)findViewById(R.id.price3_radio);
        radioPrice3.setSelected(preferences.getBoolean("price3checkbox", false));

        RadioButton radioPrice4 = (RadioButton)findViewById(R.id.price4_radio);
        radioPrice4.setSelected(preferences.getBoolean("price4checkbox", false));

        CheckBox restBox = (CheckBox)findViewById(R.id.restaurants);
        restBox.setChecked(preferences.getBoolean("restbox", false));

        CheckBox barsBox = (CheckBox)findViewById(R.id.bars);
        barsBox.setChecked(preferences.getBoolean("barsbox", false));

        CheckBox nightBox = (CheckBox)findViewById(R.id.nightlife);
        nightBox.setChecked(preferences.getBoolean("nightbox", false));

        CheckBox tourBox = (CheckBox)findViewById(R.id.tourism);
        tourBox.setChecked(preferences.getBoolean("tourbox", false));

        CheckBox funBox = (CheckBox)findViewById(R.id.entertainment);
        funBox.setChecked(preferences.getBoolean("funbox", false));

        CheckBox fitBox = (CheckBox)findViewById(R.id.fitnessrec);
        fitBox.setChecked(preferences.getBoolean("fitbox", false));

        CheckBox shoppingBox = (CheckBox)findViewById(R.id.shopping);
        shoppingBox.setChecked(preferences.getBoolean("shopbox", false));
        // End UI values

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // Store values between instances here
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiusradiogroup);
        int intRadGroup = radioGroup.getCheckedRadioButtonId();

        RadioButton radioRadHalf = (RadioButton)findViewById(R.id.radhalf_box);
        boolean blnRadHalf = radioRadHalf.isChecked();

        if (radioRadHalf.isChecked()) {

            editor.putString("&radius=805", toString());
            editor.apply(); // commits string to storage

        } else {

            editor.clear(); // clears string from storage if item is not checked
        }

        RadioButton radioRad1 = (RadioButton)findViewById(R.id.rad1_box);
        boolean blnRad1 = radioRad1.isChecked();

        if (radioRad1.isChecked()) {

            editor.putString("&radius=1609", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        RadioButton radioRad2 = (RadioButton)findViewById(R.id.rad2_box);
        boolean blnRad2 = radioRad2.isChecked();

        if (radioRad2.isChecked()) {

            editor.putString("&radius=3218", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        RadioButton radioRad5 = (RadioButton)findViewById(R.id.rad5_box);
        boolean blnRad5 = radioRad5.isChecked();

        if (radioRad5.isChecked()) {

            editor.putString("&radius=8045", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        RadioGroup priceGroup = (RadioGroup)findViewById(R.id.priceradiogroup);
        int intPriceGroup = priceGroup.getCheckedRadioButtonId();

        RadioButton radioPrice1 = (RadioButton)findViewById(R.id.price1_radio);
        boolean blnPrice1 = radioPrice1.isChecked();

        if (radioPrice1.isChecked()) {

            // remember to hardcode in minimum price of "0" (free)
            editor.putString("&maxprice=1", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        RadioButton radioPrice2 = (RadioButton)findViewById(R.id.price2_radio);
        boolean blnPrice2 = radioPrice2.isChecked();

        if (radioPrice2.isChecked()) {

            editor.putString("&maxprice=2", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        RadioButton radioPrice3 = (RadioButton)findViewById(R.id.price3_radio);
        boolean blnPrice3 = radioPrice3.isChecked();

        if (radioPrice3.isChecked()) {

            editor.putString("&maxprice=3", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        RadioButton radioPrice4 = (RadioButton)findViewById(R.id.price4_radio);
        boolean blnPrice4 = radioPrice4.isChecked();

        if (radioPrice4.isChecked()) {

            editor.putString("&maxprice=4", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        CheckBox restBox = (CheckBox)findViewById(R.id.restaurants);
        boolean blnRestBox = restBox.isChecked();

        if (restBox.isChecked()) {

            editor.putString("restaurant", toString());
            editor.apply(); // commits string to storage

        } else {

            editor.clear(); // clears string from storage if item is not checked
        }

        CheckBox barsBox = (CheckBox)findViewById(R.id.bars);
        boolean blnBarsBox = barsBox.isChecked();

        if (barsBox.isChecked()) {

            editor.putString("bar", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        CheckBox nightBox = (CheckBox)findViewById(R.id.nightlife);
        boolean blnNightBox = nightBox.isChecked();

        if (nightBox.isChecked()) {

            editor.putString("night_club", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        CheckBox tourBox = (CheckBox)findViewById(R.id.tourism);
        boolean blnTourBox = tourBox.isChecked();

        if (tourBox.isChecked()) {

            editor.putString("museum", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        CheckBox funBox = (CheckBox)findViewById(R.id.entertainment);
        boolean blnFunBox = funBox.isChecked();

        if (funBox.isChecked()) {

            editor.putString("park", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        CheckBox fitBox = (CheckBox)findViewById(R.id.fitnessrec);
        boolean blnFitBox = fitBox.isChecked();

        if (fitBox.isChecked()) {

            editor.putString("gym", toString());
            editor.apply();

        } else {

            editor.clear();
        }

        CheckBox shoppingBox = (CheckBox)findViewById(R.id.shopping);
        boolean blnShopBox = shoppingBox.isChecked();

        if (shoppingBox.isChecked()) {

            editor.putString("store", toString());
            editor.apply();
        } else {

            editor.clear();
        }


        editor.putInt("radgroup", intRadGroup); // stores radius radiogroup selection
        editor.putInt("pricegroup", intPriceGroup); // stores price radiogroup selection
        editor.putBoolean("radhalfbox", blnRadHalf); // stores radio .5 mile value
        editor.putBoolean("rad1box", blnRad1); // stores radio 1 mile value
        editor.putBoolean("rad2box", blnRad2); // stores radio 2 mile value
        editor.putBoolean("rad5box", blnRad5); // stores radio 5 mile value
        editor.putBoolean("price1checkbox", blnPrice1); // stores price1 value
        editor.putBoolean("price2checkbox", blnPrice2); // stores price2 value
        editor.putBoolean("price3checkbox", blnPrice3); // stores price3 value
        editor.putBoolean("price4checkbox", blnPrice4); // stores price4 value
        editor.putBoolean("restbox", blnRestBox); // stores restaurant checkbox value
        editor.putBoolean("barsbox", blnBarsBox); // stores bars checkbox value
        editor.putBoolean("nightbox", blnNightBox); // stores nightlife checkbox value
        editor.putBoolean("tourbox", blnTourBox); // stores tourism checkbox value
        editor.putBoolean("funbox", blnFunBox); // stores entertainment checkbox value
        editor.putBoolean("fitbox", blnFitBox); // stores fitness & rec value
        editor.putBoolean("shopbox", blnShopBox); // stores shopping value

        // Commit to storage
        editor.apply();
    }
}

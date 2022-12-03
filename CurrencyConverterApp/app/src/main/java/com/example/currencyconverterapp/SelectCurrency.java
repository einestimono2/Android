package com.example.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Arrays;
import java.util.List;

public class SelectCurrency extends AppCompatActivity {
    RadioGroup rgFromLeft, rgFromRight, rgToLeft, rgToRight;

    RadioButton rbfCNY, rbfEUR, rbfJPY, rbfKRW, rbfUSD, rbfVND;
    RadioButton rbtCNY, rbtEUR, rbtJPY, rbtKRW, rbtUSD, rbtVND;

    String currentFrom, newFrom = "";
    String currentTo, newTo = "";

    List<String> currencyTypes = Arrays.asList("CNY", "EUR", "JPY", "KRW", "USD", "VND");
    double[][] mapExchangeRate = {
            {1.0,           0.13533273,     19.155123,  185.275,        0.14235674,     3453.4263},  // CNY
            {7.38980,       1.0,            141.55433,  1369.321,       1.0520535,      25522.263},  // EUR
            {0.0522066,     0.00706443,     1.0,        9.6743933,      0.0074315208,   180.52202},  // JPY
            {0.00539738,    0.000730289,    0.103318,   1.0,            0.00076823294,  18.651214},  // KRW
            {7.02461,       0.950522,       134.539,    0.00076823294,  1.0,            24301.7},    // USD
            {0.000289567,   0.0000391586,   0.00553949, 0.0535781,      0.000041160473, 1.0}         // VND
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_currency);

        // Init
        rgFromLeft = findViewById(R.id.rgFromLeft);
        rgFromLeft.setOnCheckedChangeListener(rgFromLeftListener);
        rgFromRight = findViewById(R.id.rgFromRight);
        rgFromRight.setOnCheckedChangeListener(rgFromRightListener);

        rgToLeft = findViewById(R.id.rgToLeft);
        rgToLeft.setOnCheckedChangeListener(rgToLeftListener);
        rgToRight = findViewById(R.id.rgToRight);
        rgToRight.setOnCheckedChangeListener(rgToRightListener);

        rbfCNY = findViewById(R.id.rbfCNY);
        rbfEUR = findViewById(R.id.rbfEUR);
        rbfJPY = findViewById(R.id.rbfJPY);
        rbfKRW = findViewById(R.id.rbfKRW);
        rbfUSD = findViewById(R.id.rbfUSD);
        rbfVND = findViewById(R.id.rbfVND);

        rbtCNY = findViewById(R.id.rbtCNY);
        rbtEUR = findViewById(R.id.rbtEUR);
        rbtJPY = findViewById(R.id.rbtJPY);
        rbtKRW = findViewById(R.id.rbtKRW);
        rbtUSD = findViewById(R.id.rbtUSD);
        rbtVND = findViewById(R.id.rbtVND);

        // Currrent currency
        Intent intent = getIntent();
        currentFrom = intent.getStringExtra("CURRENT_FROM");
        currentTo = intent.getStringExtra("CURRENT_TO");
        setDefaultCurrency(currentFrom, currentTo);

        findViewById(R.id.bOK).setOnClickListener(view -> {
            double exchangeRate = mapExchangeRate[currencyTypes.indexOf(newFrom)][currencyTypes.indexOf(newTo)];

            intent.putExtra("NEW_FROM", newFrom);
            intent.putExtra("NEW_TO", newTo);
            intent.putExtra("EXCHANGE_RATE", exchangeRate);

            Log.v("NEW_FROM", newFrom);
            Log.v("NEW_TO", newTo);
            Log.v("NEW_RATE", String.valueOf(exchangeRate));

            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        findViewById(R.id.bCancel).setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }

    void setDefaultCurrency(String from, String to){
        switch (from){
            case "CNY": rgFromLeft.check(R.id.rbfCNY); break;
            case "EUR": rgFromLeft.check(R.id.rbfEUR); break;
            case "JPY": rgFromLeft.check(R.id.rbfJPY); break;
            case "KRW": rgFromRight.check(R.id.rbfKRW); break;
            case "USD": rgFromRight.check(R.id.rbfUSD); break;
            case "VND": rgFromRight.check(R.id.rbfVND); break;
        }

        switch (to){
            case "CNY": rgToLeft.check(R.id.rbtCNY); break;
            case "EUR": rgToLeft.check(R.id.rbtEUR); break;
            case "JPY": rgToLeft.check(R.id.rbtJPY); break;
            case "KRW": rgToRight.check(R.id.rbtKRW); break;
            case "USD": rgToRight.check(R.id.rbtUSD); break;
            case "VND": rgToRight.check(R.id.rbtVND); break;
        }
    }

    private final RadioGroup.OnCheckedChangeListener rgFromLeftListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                newFrom = ((RadioButton) findViewById(checkedId)).getText().toString();
                rgFromRight.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                rgFromRight.clearCheck(); // clear the second RadioGroup!
                rgFromRight.setOnCheckedChangeListener(rgFromRightListener); //reset the listener
            }
        }
    };

    private final RadioGroup.OnCheckedChangeListener rgFromRightListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                newFrom = ((RadioButton) findViewById(checkedId)).getText().toString();
                rgFromLeft.setOnCheckedChangeListener(null);
                rgFromLeft.clearCheck();
                rgFromLeft.setOnCheckedChangeListener(rgFromLeftListener);
            }
        }
    };

    private final RadioGroup.OnCheckedChangeListener rgToLeftListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                newTo = ((RadioButton) findViewById(checkedId)).getText().toString();
                rgToRight.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                rgToRight.clearCheck(); // clear the second RadioGroup!
                rgToRight.setOnCheckedChangeListener(rgToRightListener); //reset the listener
            }
        }
    };

    private final RadioGroup.OnCheckedChangeListener rgToRightListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                newTo = ((RadioButton) findViewById(checkedId)).getText().toString();
                rgToLeft.setOnCheckedChangeListener(null);
                rgToLeft.clearCheck();
                rgToLeft.setOnCheckedChangeListener(rgToLeftListener);
            }
        }
    };
}
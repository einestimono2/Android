package com.example.currencyconverterapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView tvFrom, tvTo;

    EditText etFrom, etTo;

    double exchangeRate = 24301.7;
    String currentFrom = "USD";
    String currentTo = "VND";

    ActivityResultLauncher<Intent> changeCurrencyResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init
        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);

        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);

        //
        changeCurrencyResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();

                        if(intent != null){
                            exchangeRate = intent.getDoubleExtra("EXCHANGE_RATE", exchangeRate);
                            currentFrom = intent.getStringExtra("NEW_FROM");
                            currentTo = intent.getStringExtra("NEW_TO");

                            Log.v("CURRENT_FROM", currentFrom);
                            Log.v("CURRENT_TO", currentTo);
                            Log.v("CURRENT_RATE", String.valueOf(exchangeRate));

                            String txtFrom = "From: " + currentFrom;
                            tvFrom.setText(txtFrom);

                            String txtTo = "To: " + currentTo;
                            tvTo.setText(txtTo);

                            convert();
                            etFrom.requestFocus();
                        }
                    }
                });

        // Button
        findViewById(R.id.bChange).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SelectCurrency.class);
            intent.putExtra("CURRENT_FROM", currentFrom);
            intent.putExtra("CURRENT_TO", currentTo);
            changeCurrencyResult.launch(intent);
        });

        findViewById(R.id.bClear).setOnClickListener(view -> clear());

        findViewById(R.id.bConvert).setOnClickListener(view -> {
            try {
                Toast.makeText(
                        MainActivity.this,
                        "Exchange Rate: " + exchangeRate,
                        Toast.LENGTH_LONG
                ).show();

                convert();
                etFrom.clearFocus();
            }catch (Exception e){
                etTo.setText(e.toString());
            }
        });

        etFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    convert();
                }catch (Exception ignored){ }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    void convert(){
        String input = etFrom.getText().toString();
        String result = "";

        if(!input.equals("")) {
            double output = getExchange(Double.parseDouble(input));

            if((output % 1) == 0) result = new DecimalFormat("#,###").format(output);
            else result = new DecimalFormat("#,###.00").format(output);
        }

        etTo.setText(result);
    }

    void clear(){
        etFrom.setText("");
        etTo.setText("");
        etFrom.clearFocus();
    }

    double getExchange(double input){
        return input * exchangeRate;
    }
}
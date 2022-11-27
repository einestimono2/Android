package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView result;
    TextView info;

    String param1 = "";
    String operation = "";

    boolean resetInput = false;
    boolean isFirstOP = true;
    boolean calculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.textView);
        result.setAutoSizeTextTypeUniformWithConfiguration(
                30, 80, 2, TypedValue.COMPLEX_UNIT_DIP);
        info = findViewById(R.id.textViewSmall);
        info.setAutoSizeTextTypeUniformWithConfiguration(
                10, 30, 2, TypedValue.COMPLEX_UNIT_DIP);

        findViewById(R.id.buttonC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearText("C");
            }
        });

        findViewById(R.id.buttonCE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearText("CE");
            }
        });

        findViewById(R.id.buttonBS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearText("BS");
            }
        });

        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("0");
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("1");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("2");
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("3");
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("4");
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("5");
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("6");
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("7");
            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("8");
            }
        });

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText("9");
            }
        });

        findViewById(R.id.buttonPhay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calculated){
                    clearText("C");
                }

                addComma();
            }
        });

        findViewById(R.id.buttonCongTru).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calculated){
                    String opInfo = "- (" + getReformatText() + ") = ";
                    info.setText(opInfo);
                }

                addNegative();
            }
        });

        findViewById(R.id.buttonCong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOperation("+");
            }
        });

        findViewById(R.id.buttonTru).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOperation("-");
            }
        });

        findViewById(R.id.buttonNhan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOperation("x");
            }
        });

        findViewById(R.id.buttonChia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOperation("/");
            }
        });

        findViewById(R.id.buttonBang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param2 = getReformatText(true);
                String opInfo = (operation.equals("") ? "" : (param1 + " " + operation + " ")) + param2 + " = ";
                info.setText(opInfo);
                result.setText(performCalculation(param1, param2));

                resetInput = true;
                calculated = true;
            }
        });
    }

    void showText(String t){
        if(calculated){
            calculated = false;
            clearText("C");
        }

        String currentText = getReformatText();

        if(currentText.length() >= 16) return;

        if(t.equals("0") && currentText.equals("0")) return;

        if(currentText.equals("0")){
            currentText = t;
        }else{
            currentText += t;
        }

        if(resetInput){
            resetInput = false;
            currentText = t;
        }

        formatText(currentText);
    }

    void clearText(String type){
        switch (type) {
            case "CE":
                result.setText("0");
                break;
            case "C":
                result.setText("0");
                info.setText("");
                param1 = "";
                operation = "";
                resetInput = false;
                isFirstOP = true;
                calculated = false;
                break;
            case "BS":
                String text = getReformatText();

                if (text.length() == 1) text = "0";
                else text = (String) text.subSequence(0, text.length() - 1);

                if (text.equals("-")) text = "0";

                formatText(text);
                break;
            default:
                break;
        }
    }

    void addComma(){
        String currentText = (String) result.getText();

        if(currentText.contains(".")) return;

        currentText += ".";

        result.setText(currentText);
    }

    void addNegative(){
        String currentText = getReformatText();

        if(currentText.equals("0")) return;

        if(currentText.charAt(0) == '-'){
            currentText = (String) currentText.subSequence(1, currentText.length());
        }else{
            currentText = "-" + currentText;
        }

        formatText(currentText);
    }

    void addOperation(String op){
        if(calculated) calculated = false;

        if(isFirstOP){
            param1 = getReformatText(true);
            isFirstOP = false;
        }else{
            param1 = performCalculation(param1, getReformatText(true));
            result.setText(param1);
        }

        operation = op;
        resetInput = true;

        String opInfo = param1 + " " + op + " ";
        info.setText(opInfo);
    }

    String performCalculation(String param1, String param2){
        String result = "";

        if(operation.equals("")){
            result = param2;
        }else{
            try {
                double param1_double = Double.parseDouble(param1);
                double param2_double = Double.parseDouble(param2);
                double param;

                switch (operation){
                    case "+": param = param1_double + param2_double; break;
                    case "-": param = param1_double - param2_double; break;
                    case "x": param = param1_double * param2_double; break;
                    case "/": param = param1_double / param2_double; break;
                    default: return "????";
                }

                if(param % 1 == 0) result = String.valueOf(param).split("\\.")[0];
                else result = String.valueOf(param);

                param1 = "";
                resetInput = false;
                isFirstOP = true;
            }catch (Exception e){
                result = e.toString();
            }
        }

        return result;
    }

    void formatText(String text){
        String[] texts = text.split("\\.");

        try {
            texts[0] = new DecimalFormat("#,###").format(Double.parseDouble(texts[0]));
        }catch (Exception e){
            texts[0] = "0";
        }

        String currentText = (texts.length == 1) ? texts[0] : texts[0] + "." + texts[1];

        result.setText(currentText);
    }

    String getReformatText(){

        return ((String) result.getText()).replaceAll(",", "");
    }

    String getReformatText(boolean ignoreDot){
        String originText = ((String) result.getText()).replaceAll(",", "");

        if(originText.charAt(originText.length() - 1) == '.') originText = originText.substring(0, originText.length() - 1);

        return originText;
    }
}
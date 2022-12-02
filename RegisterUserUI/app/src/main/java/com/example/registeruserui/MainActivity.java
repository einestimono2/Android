package com.example.registeruserui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etFirstName, etLastName, etBirthday, etAddress, etEmail;

    RadioGroup rgGenderType;
    RadioButton rbFemale, rbMale;

    CheckBox agreeToTerms;

    boolean hasError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etBirthday = findViewById(R.id.etBirthday);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);

        rgGenderType = findViewById(R.id.rgGender);
        rbFemale = findViewById(R.id.rbFemale);
        rbMale = findViewById(R.id.rbMale);

        agreeToTerms = findViewById(R.id.cbTermsOfUse);

        findViewById(R.id.bRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = etFirstName.getText().toString();
                if(firstName.equals("")){
                    etFirstName.setBackgroundResource(R.drawable.input_error_style);
                    etFirstName.setError("Please Enter First Name!");
                    hasError = true;
                }else{
                    etFirstName.setBackgroundResource(R.drawable.input_background_style);
                    etFirstName.setError(null);
                    hasError = false;
                }

                String lastName = etLastName.getText().toString();
                if(lastName.equals("")){
                    etLastName.setBackgroundResource(R.drawable.input_error_style);
                    etLastName.setError("Please Enter Last Name!");
                    hasError = true;
                }else{
                    etLastName.setBackgroundResource(R.drawable.input_background_style);
                    etLastName.setError(null);
                    hasError = false;
                }

                int genderType = rgGenderType.getCheckedRadioButtonId();
                if(genderType == -1){
                    rbMale.setError("Select Item!");
                    rbFemale.setError("Select Item!");
                    hasError = true;
                }else{
                    rbMale.setError(null);
                    rbFemale.setError(null);
                    hasError = false;
                }

                String birthday = etBirthday.getText().toString();
                if(birthday.equals("")){
                    etBirthday.setBackgroundResource(R.drawable.input_error_style);
                    etBirthday.setError("Please Enter Birthday!");
                    hasError = true;
                }else{
                    etBirthday.setBackgroundResource(R.drawable.input_background_style);
                    etBirthday.setError(null);
                    hasError = false;
                }

                String address = etAddress.getText().toString();
                if(address.equals("")){
                    etAddress.setBackgroundResource(R.drawable.input_error_style);
                    etAddress.setError("Please Enter Address!");
                    hasError = true;
                }else{
                    etAddress.setBackgroundResource(R.drawable.input_background_style);
                    etAddress.setError(null);
                    hasError = false;
                }

                String email = etEmail.getText().toString();
                if(email.equals("")){
                    etEmail.setBackgroundResource(R.drawable.input_error_style);
                    etEmail.setError("Please Enter Email!");
                    hasError = true;
                }else{
                    etEmail.setBackgroundResource(R.drawable.input_background_style);
                    etEmail.setError(null);
                    hasError = false;
                }

                boolean isAgreeToTerms = agreeToTerms.isChecked();
                if(!isAgreeToTerms){
                    agreeToTerms.setError("You must agree to terms of use!");
                    hasError = true;
                }else{
                    agreeToTerms.setError(null);
                    hasError = false;
                }

                if(!hasError){
                    Toast.makeText(MainActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Please complete all information!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
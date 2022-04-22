package com.shrijee.rentcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.User;
import com.shrijee.rentcafe.miscellaneous.Validation;

public class MainActivity extends AppCompatActivity {

    Button loginBtnEle, registerBtnEle;
    EditText emailEle,passwordEle;
    DatabaseHelper databaseHelper;
    Validation validation = new Validation();
    public static String sharedPrefrence = "RENT_PREFRENCE";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        sharedPreferences = getSharedPreferences(sharedPrefrence,MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        setWidgets();
        checkLogin();
    }

    private void checkLogin() {
        String savedEmail = sharedPreferences.getString("email","");
        if(!savedEmail.isEmpty())
        {
            moveToHomeActivity();
            //Toast.makeText(getApplicationContext(),"Login success for " + savedEmail , Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToHomeActivity() {
        Intent homeactivityIntent = new Intent(this,HomeActivity.class);
        startActivity(homeactivityIntent);
    }

    private void setWidgets() {
        loginBtnEle = findViewById(R.id.login_btn);
        emailEle = findViewById(R.id.email_edittext);
        passwordEle = findViewById(R.id.password_edittext);
    }

    public void loginBtnClicked(View view) {
        try
        {
            String emailText = emailEle.getText().toString().trim();
            String passwordText = passwordEle.getText().toString().trim();

            if(!validation.stringValidation(emailText) || !validation.stringValidation(passwordText))
            {
                Toast.makeText(getApplicationContext(),"Please fill the email and password",Toast.LENGTH_LONG).show();
            }
            else
            {
                //validate the user
                User user = databaseHelper.loginCheck(emailText,passwordText);
                if(user!=null)
                {
                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email",emailText);
                    editor.apply();
                    moveToHomeActivity();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wrong credentials",Toast.LENGTH_SHORT).show();
                    clearDetails();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong! Please try again later!",Toast.LENGTH_LONG).show();
        }
    }

    private void clearDetails() {
        passwordEle.setText("");
        emailEle.setText("");
    }

    public void registerBtnClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
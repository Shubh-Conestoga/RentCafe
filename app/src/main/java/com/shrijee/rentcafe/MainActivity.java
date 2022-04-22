package com.shrijee.rentcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.User;
import com.shrijee.rentcafe.miscellaneous.Validation;

public class MainActivity extends AppCompatActivity {

    //widgets and data
    Button loginBtnEle, registerBtnEle;
    EditText emailEle,passwordEle;
    DatabaseHelper databaseHelper;
    Validation validation = new Validation();
    public static Drawable PHOTO_1;
    public static Drawable PHOTO_2;
    public static Drawable PHOTO_3;
    public static Drawable PHOTO_4;
    public static Drawable PHOTO_5;
    public static Drawable PHOTO_6;
    public static Drawable PHOTO_7;
    public static Drawable PHOTO_8;
    public static Drawable PHOTO_9;
    public static Drawable PHOTO_10;


    public static String sharedPrefrence = "RENT_PREFRENCE";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setting the variable so that it can easily be used anywhere in the project
        PHOTO_1 = getDrawable(R.drawable.photo_1);
        PHOTO_2 = getDrawable(R.drawable.photo_2);
        PHOTO_3 = getDrawable(R.drawable.photo_3);
        PHOTO_4 = getDrawable(R.drawable.photo_4);
        PHOTO_5 = getDrawable(R.drawable.photo_5);
        PHOTO_6 = getDrawable(R.drawable.photo_6);
        PHOTO_7 = getDrawable(R.drawable.photo_7);
        PHOTO_8 = getDrawable(R.drawable.photo_8);
        PHOTO_9 = getDrawable(R.drawable.photo_9);
        PHOTO_10 = getDrawable(R.drawable.photo_10);

        //creating an object of DatabaseHelper and getting the sharedPred
        databaseHelper = new DatabaseHelper(getApplicationContext());
        sharedPreferences = getSharedPreferences(sharedPrefrence,MODE_PRIVATE);
        setContentView(R.layout.activity_main);
//        setting the widgets
        setWidgets();
//        checking the login
        checkLogin();
    }

    private void checkLogin() {
        //gettingthe email
        String savedEmail = sharedPreferences.getString("email","");
//        ..if user email key present in sharefPref then skip the login and go to home screen
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
            //getting the email and password
            String emailText = emailEle.getText().toString().trim();
            String passwordText = passwordEle.getText().toString().trim();

            //validation
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
                    //adding the username in sharedpref
                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email",emailText);
                    editor.apply();
                    moveToHomeActivity();
                }
                else
                {
                    //toasting the wrong cred msg and clearing the fields
                    Toast.makeText(getApplicationContext(),"Wrong credentials",Toast.LENGTH_SHORT).show();
                    clearDetails();
                }
            }
        }
        //catching the exeption and toasing an error
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong! Please try again later!",Toast.LENGTH_LONG).show();
        }
    }

    private void clearDetails() {
        passwordEle.setText("");
        emailEle.setText("");
    }



// if register btn click then take user to register screen
    public void registerBtnClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
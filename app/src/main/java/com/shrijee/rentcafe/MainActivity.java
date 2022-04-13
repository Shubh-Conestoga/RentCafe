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

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    EditText email,password;
    DatabaseHelper databaseHelper;
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
            Toast.makeText(getApplicationContext(),"Login success for " + savedEmail , Toast.LENGTH_SHORT).show();
        }
    }

    private void setWidgets() {
        loginBtn = findViewById(R.id.login_btn_login);
        email = findViewById(R.id.login_edt_email);
        password = findViewById(R.id.login_edt_password);
    }

    public void loginBtnClicked(View view) {
        try
        {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if(emailText.isEmpty() || passwordText.isEmpty())
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
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wrong credentials",Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong! Please try again later!",Toast.LENGTH_LONG).show();
        }
    }

    public void registerBtnClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
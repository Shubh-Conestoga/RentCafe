package com.shrijee.rentcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.User;
import com.shrijee.rentcafe.miscellaneous.Validation;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    //Widgets and validation and data
    EditText emailEle,passwordEle,fNameEle,contactEle,lNameEle;
    Button registerBtnEle;
    ImageView backBtnEle;
    DatabaseHelper databaseHelper;
    Validation validation = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        creating an instance of DatabaseHelper
        databaseHelper = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_register);
//        setting the widgets
        setWidgets();

//        on register btn click this will called
        registerBtnEle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerBtnClicked(view);
            }
        });

        backBtnEle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLoginActivity();
            }
        });
    }


    private void setWidgets() {
        emailEle = findViewById(R.id.email_edittext);
        passwordEle = findViewById(R.id.password_edittext);
        fNameEle = findViewById(R.id.fname_edittext);
        lNameEle = findViewById(R.id.lname_edittext);
        contactEle = findViewById(R.id.contact_edittext);
        registerBtnEle = findViewById(R.id.register_btn);
        backBtnEle = findViewById(R.id.back_btn);
    }


    //on register btn click
    public void registerBtnClicked(View view) {
        try {
            //getting the data from form
            String emailText = emailEle.getText().toString().trim();
            String passwordText = passwordEle.getText().toString().trim();
            String fnameText = fNameEle.getText().toString().trim();
            String lnameText = lNameEle.getText().toString().trim();
            String contactText = contactEle.getText().toString().trim();
            String nameText = fnameText + " " + lnameText;

            String[] stringArray = {emailText, passwordText, nameText, contactText};
            //validation error if any occurs
            if(!validation.stringArrayValidation(stringArray))
            {
                Toast.makeText(getApplicationContext(),"Please enter required details to register",Toast.LENGTH_LONG).show();
            }
            else
            {
                //else creating sand saving the data in DB
                //register user
                User user = new User(nameText,emailText,passwordText,contactText);
//                saving data in DB using DatabaseHelper class
                boolean saved = databaseHelper.save(user);
//                if data saved then move user to login activity and showing the toast
                if(saved)
                {
                    Toast.makeText(getApplicationContext(),"Registered Successfully!",Toast.LENGTH_SHORT).show();
                    moveToLoginActivity();
                }
                //else showing the error
                else
                {
                    Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e)
        {
            //showing exception message and toasing the error
            Toast.makeText(getApplicationContext(),"Please try again!",Toast.LENGTH_LONG).show();
            Log.e("CATCH ERROR", "REGISTER registerBtnClicked: "+ e.getMessage() );
        }
    }

    // moving user to login activity
    private void moveToLoginActivity() {
        Intent loginactivityIntent = new Intent(this,MainActivity.class);
        startActivity(loginactivityIntent);
    }
}
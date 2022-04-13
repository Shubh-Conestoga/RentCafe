package com.shrijee.rentcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.User;

public class RegisterActivity extends AppCompatActivity {

    EditText email,password,name,contact;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_register);
        setWidgets();
    }

    private void setWidgets() {
        email = findViewById(R.id.login_edt_email);
        password = findViewById(R.id.login_edt_password);
        name = findViewById(R.id.register_edt_name);
        contact = findViewById(R.id.login_edt_mobile);
    }

    public void loginBtnClicked(View view) {
        super.onBackPressed();
    }

    public void registerBtnClicked(View view) {
        try {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            String nameText = name.getText().toString().trim();
            String contactText = contact.getText().toString().trim();

            if(emailText.isEmpty() || passwordText.isEmpty() || nameText.isEmpty() || contactText.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Please enter required details to register",Toast.LENGTH_LONG).show();
            }
            else
            {
                //register user
                User user = new User(nameText,emailText,passwordText,contactText);
                boolean saved = databaseHelper.save(user);
                if(saved)
                {
                    Toast.makeText(getApplicationContext(),"Registered Successfully!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Please try again!",Toast.LENGTH_LONG).show();
            Log.e("CATCH ERROR", "REGISTER registerBtnClicked: "+ e.getMessage() );
        }
    }
}
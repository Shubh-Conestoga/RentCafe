package com.shrijee.rentcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.Rent;
import com.shrijee.rentcafe.model.RentedProperty;

import java.util.Date;
import java.util.List;

public class RentDescriptionActivity extends AppCompatActivity {

    Rent rent;
    int userId=-1;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;
    ImageView propertyImage;
    TextView nameEle, addressEle, priceEle,bedroomEle,bathroomEle,descriptionEle,zipcodeEle,stateEle,cityEle,typeEle,waterEle,microwaveEle,heatEle,hydroEle,furnishedEle,parkingEle;
    ImageButton callBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_description);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences(MainActivity.sharedPrefrence,MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        userId = email.isEmpty() ? -1 : databaseHelper.getRenterIdByEmail(email);
        rent = (Rent)getIntent().getSerializableExtra("RENT_DATA");
        setWidgets();
        setData();
        setClickListners();
    }

    private void setClickListners() {
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = new DatabaseHelper(getApplicationContext()).getPhoneNoById(rent.getRenter());
                if(!phoneNo.isEmpty())
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","+1"+String.valueOf(phoneNo),null));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Phone Number Available!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
//        propertyImage.setImageURI(rent.getImageURL());
        nameEle.setText(rent.getRentName());
        typeEle.setText(rent.getType());
        String addressString = "";
        if(rent.getAddress()!=null)
        {
            addressString = rent.getAddress() + ", ";
        }
        addressEle.setText( addressString + rent.getCity() + ", " + rent.getState() + ", " + rent.getPincode());
        priceEle.setText(String.valueOf(rent.getPrice()));
        bathroomEle.setText(rent.getNoOfBathroom() + " Bathrooms");
        bedroomEle.setText(rent.getNoOfBedroom() + " Bedrooms");
        List<String> utilities = rent.getUtilities();
        for (String utility : utilities)
        {
            if(utility.equals("Microwave"))
                microwaveEle.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.microwave_logo,0,R.drawable.available_logo,0);
            else if(utility.equals("Heat"))
                heatEle.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.heat_logo,0,R.drawable.available_logo,0);
            else if(utility.equals("Hydro"))
                hydroEle.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.hydro_logo,0,R.drawable.available_logo,0);
            else if(utility.equals("Water"))
                waterEle.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.water_logo,0,R.drawable.available_logo,0);
        }

        descriptionEle.setText(rent.getDescription());
    }

    private void setWidgets() {
        propertyImage = findViewById(R.id.property_image);
        nameEle = findViewById(R.id.property_name);
        typeEle = findViewById(R.id.property_type);
        addressEle = findViewById(R.id.property_location);
        priceEle = findViewById(R.id.property_price);

        bathroomEle = findViewById(R.id.bathroom);
        bedroomEle = findViewById(R.id.bedroom);
        waterEle = findViewById(R.id.water);
        //waterEle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.water_logo, 0, R.drawable.available_logo, 0);
        microwaveEle = findViewById(R.id.microwave);
        heatEle = findViewById(R.id.heat);
        hydroEle = findViewById(R.id.hydro);
        parkingEle = findViewById(R.id.parking);
        furnishedEle = findViewById(R.id.furnished);
        descriptionEle = findViewById(R.id.property_description);
        callBtn = findViewById(R.id.desc_call_btn);
    }

    public void backButtonClicked(View view) {
        onBackPressed();
    }

    public void renBtnClicked(View view) {
        RentedProperty rentedProperty = new RentedProperty();
        rentedProperty.setRent(rent);
        rentedProperty.setStartingDate(new Date());
        rentedProperty.setDuration(1);
        rentedProperty.setActiveFlag(1);
        rentedProperty.setRenterId(rent.getRenter());
        rentedProperty.setRenteeId(userId);
        boolean isPropertyRented = databaseHelper.save(rentedProperty);
        if(isPropertyRented)
        {
            Toast.makeText(this,"Property Rented Success!!",Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        else
        {
            Toast.makeText(this,"Something went wrong!!",Toast.LENGTH_LONG).show();
        }
    }
}
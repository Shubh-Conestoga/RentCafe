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

    //data and widgets
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
        //dataabseHelper object creation
        databaseHelper = new DatabaseHelper(this);
        //getting sharedPref and gettign email
        sharedPreferences = getSharedPreferences(MainActivity.sharedPrefrence,MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        //getting userId from databaseHelper
        userId = email.isEmpty() ? -1 : databaseHelper.getRenterIdByEmail(email);
        //getting rent data from intent
        rent = (Rent)getIntent().getSerializableExtra("RENT_DATA");
        //setting the widgets
        setWidgets();
        //setting data to widgets
        setData();
        //setting clickListners
        setClickListners();
    }

    private void setClickListners() {
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //on click of cll button
            public void onClick(View view) {
                //getting phone number of user by renter
                String phoneNo = new DatabaseHelper(getApplicationContext()).getPhoneNoById(rent.getRenter());
                //if phoneNo is not empty then
                if(!phoneNo.isEmpty())
                {
                    //creating and implicit intent and for dialing the given phone phone no of renter and starting the activity
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","+1"+String.valueOf(phoneNo),null));
                    startActivity(intent);
                }
                else
                {
                    //otherwise showing the error
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
//        it includes all the available utilities
        List<String> utilities = rent.getUtilities();
        //setting the yes and cross symbol according to the data
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

        //setting the image
        if(rent.getImageURL().equals("1"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_1);
        else if(rent.getImageURL().equals("2"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_2);
        else if(rent.getImageURL().equals("3"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_3);
        else if(rent.getImageURL().equals("4"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_4);
        else if(rent.getImageURL().equals("5"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_5);
        else if(rent.getImageURL().equals("6"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_6);
        else if(rent.getImageURL().equals("7"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_7);
        else if(rent.getImageURL().equals("8"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_8);
        else if(rent.getImageURL().equals("9"))
            propertyImage.setImageDrawable(MainActivity.PHOTO_9);
        else
            propertyImage.setImageDrawable(MainActivity.PHOTO_10);
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

    //backbutton click then call onBackPressed funtion
    public void backButtonClicked(View view) {
        onBackPressed();
    }

    //on rent btn gets clicked
    public void renBtnClicked(View view) {
        //setting the data
        RentedProperty rentedProperty = new RentedProperty();
        rentedProperty.setRent(rent);
        rentedProperty.setStartingDate(new Date());
        rentedProperty.setDuration(1);
        rentedProperty.setActiveFlag(1);
        rentedProperty.setRenterId(rent.getRenter());
        rentedProperty.setRenteeId(userId);
        //saving the databaseHelper using save function
        boolean isPropertyRented = databaseHelper.save(rentedProperty);
        //if it returns true the showing the toast and call onBackpressed funtion
        if(isPropertyRented)
        {
            Toast.makeText(this,"Property Rented Success!!",Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        else
        {
            //showing an error
            Toast.makeText(this,"Something went wrong!!",Toast.LENGTH_LONG).show();
        }
    }
}
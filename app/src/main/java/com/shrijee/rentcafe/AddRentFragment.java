package com.shrijee.rentcafe;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.Rent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.shrijee.rentcafe.miscellaneous.Validation;



public class AddRentFragment extends Fragment {

    //Widgets
    EditText nameEle, addressEle, priceEle,bedroomEle,bathroomEle,descriptionEle,zipcodeEle,stateEle,cityEle;
    CheckBox noneEle, hydroEle,waterEle,heatEle,microwaveEle;
    RadioGroup furnishedEle,parkingEle;
    Spinner propertyTypeEle;
    Button listPropertyBtnEle;
    DatabaseHelper databaseHelper;
    //Option for spinner
    String[] propertyTypes = {"Apartment","House","Condo","Shop","Parking"};
    //for validation
    Validation validation = new Validation();

    public AddRentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //infating the view
        View view = inflater.inflate(R.layout.fragment_add_rent, container, false);
        //setting the widgets
        setWidgets(view);
        return view;
    }

    private void setWidgets(View view) {

        nameEle = view.findViewById(R.id.name_edittext);
        priceEle = view.findViewById(R.id.price_edittext);
        bedroomEle = view.findViewById(R.id.bedroom_edittext);
        bathroomEle = view.findViewById(R.id.bathroom_edittext);
        descriptionEle = view.findViewById(R.id.description_edittext);
        zipcodeEle = view.findViewById(R.id.zipcode_edittext);
        stateEle = view.findViewById(R.id.state_edittext);
        cityEle = view.findViewById(R.id.city_edittext);
        addressEle = view.findViewById(R.id.addrent_address_edittext);

        propertyTypeEle = view.findViewById(R.id.type_spinner);
        //setting the arrayadapter to Spinner
        ArrayAdapter<String> propertyTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,propertyTypes);
        propertyTypeEle.setAdapter(propertyTypeAdapter);
        //making first item default
        propertyTypeEle.setSelection(0);

        listPropertyBtnEle = view.findViewById(R.id.list_property_btn);

        furnishedEle = view.findViewById(R.id.furnished_yesno_radioGrp);
        parkingEle = view.findViewById(R.id.parking_yesno_radioGrp);

        hydroEle = view.findViewById(R.id.utilities_hydro_chkbox);
        waterEle = view.findViewById(R.id.utilities_water_chkbox);
        heatEle = view.findViewById(R.id.utilities_heat_chkbox);
        microwaveEle = view.findViewById(R.id.utilities_microwave_chkbox);

//        /
        listPropertyBtnEle.setOnClickListener(new View.OnClickListener() {
            @Override
            //called when list property button gets clicked
            public void onClick(View view) {
                boolean isValidated = true;
                //getting the values from the form
                String rentName = nameEle.getText().toString().trim();
                String rentPrice = priceEle.getText().toString().trim();
                String rentBedroom = bedroomEle.getText().toString().trim();
                String rentBathroom = bathroomEle.getText().toString().trim();
                String rentPincode = zipcodeEle.getText().toString().trim();
                String rentState = stateEle.getText().toString().trim();
                String rentAddress = addressEle.getText().toString().trim();

                String rentPropertyType = propertyTypeEle.getSelectedItem().toString().trim();

                String rentCity = cityEle.getText().toString().trim();
                String rentDescription = descriptionEle.getText().toString().trim();

                SQLiteDatabase database = databaseHelper.getReadableDatabase();
                String email = getContext().getSharedPreferences(MainActivity.sharedPrefrence, Context.MODE_PRIVATE).getString("email","");
                //getting the userId by email with help of DatabaseHelper class's function
                int userId = email.trim().isEmpty() ? -1 : databaseHelper.getRenterIdByEmail(email.trim());
                //if hydro selected then it will be appended in Comma separated String utilities otherwise empty string appended
                String hydroUtilities = hydroEle.isChecked() ? "Hydro," : "";
                String heatUtilities = heatEle.isChecked() ? "Heat," : "";
                String waterUtilities = waterEle.isChecked() ? "Water," : "";
                String microwaveUtilities = microwaveEle.isChecked() ? "Microwave," : "";
                String rentUtilities = hydroUtilities + heatUtilities + waterUtilities + microwaveUtilities;
                //removing the last character
                if(!rentUtilities.isEmpty())
                    rentUtilities = rentUtilities.substring(0,rentUtilities.length()-1);

                //making a list of utilities by spliting it on comma
                List<String> rentUtilitiesList = Arrays.asList(rentUtilities.split(","));

                //getting the data for furnished and parking. if checked then value will be 1 or 0
                int rentFurnished = furnishedEle.getCheckedRadioButtonId()==R.id.furnished_yes_radioGrp ? 1 : 0;
                int rentParking = parkingEle.getCheckedRadioButtonId()==R.id.parking_yes_radioGrp ? 1 : 0;

                //if any validation error then it will be appended in this errorMsg
                String errorMsg = "";
                if(!validation.stringValidation(rentName))
                {
                    errorMsg += "Please Enter Name of property!\n";
                }
                if(!validation.stringValidation(rentAddress))
                {
                    errorMsg += "Please Enter Name of property!\n";
                }
                if(!validation.intValidation(rentPrice))
                {
                    errorMsg += "Please Enter Price of property!\n";
                }
                if(!validation.stringValidation(rentPincode))
                {
                    errorMsg += "Please Enter Pincode of property!\n";
                }
                if(!validation.stringValidation(rentState))
                {
                    errorMsg += "Please Enter State of property!\n";
                }
                if(!validation.stringValidation(rentCity))
                {
                    errorMsg += "Please Enter City of property!\n";
                }
                if(!validation.stringValidation(rentPropertyType))
                {
                    errorMsg += "Please Choose Type of property!\n";
                }
                //if validation error then showing the error
                if(!errorMsg.isEmpty())
                {
                    Toast.makeText(getContext(),errorMsg,Toast.LENGTH_LONG).show();
                }
                else
                {
                    //otherwise
                    //getting a random id for getting any image for the property to be added in DB and it is bounded to  10 because we need in between 0 to 9
                    String rentImg = String.valueOf(new Random().nextInt(10));
                    //creating a Rent object
                    Rent rent = new Rent(rentName,rentAddress,rentPincode,rentCity,rentState,userId,Float.parseFloat(rentPrice),rentPropertyType,rentDescription,rentImg,Integer.parseInt(rentBedroom), Integer.parseInt(rentBathroom),rentUtilitiesList,rentFurnished,rentParking);
                    //saving data to DB using DatabseHelper Class's method save
                    boolean isAdded = databaseHelper.save(rent);
                    //if added
                    if(isAdded)
                    {
                        //showing the success toast and taking user to HomeActivity
                        Toast.makeText(getContext(),"Rent details saved!!",Toast.LENGTH_SHORT).show();
                        Intent homeactivityIntent = new Intent(getContext(), HomeActivity.class);
                        startActivity(homeactivityIntent);
                    }
                    else
                    {
                        //showing an Error
                        Toast.makeText(getContext(),"Something went wrong!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
//for moving user to HomeActivity
    private void moveToHomeActivity() {
        Intent homeactivityIntent = new Intent(getContext(), HomeActivity.class);
        startActivity(homeactivityIntent);
    }
}
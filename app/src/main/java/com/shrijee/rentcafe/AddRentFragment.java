package com.shrijee.rentcafe;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRentFragment} factory method to
 * create an instance of this fragment.
 */
public class AddRentFragment extends Fragment {


    EditText name,price,bedroom,bathroom,description,pincode,state,city;
    CheckBox hydro,water,heat,microwave;
    RadioGroup furnished,parking;
    Spinner propertyType;
    Button listPropertyBtn;
    DatabaseHelper databaseHelper;
    String[] propertyTypes = {"Apartment","House","Condo","Shop","Parking"};

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
        View view = inflater.inflate(R.layout.fragment_add_rent, container, false);
        setWidgets(view);
        return view;
    }

    private void setWidgets(View view) {
        name = view.findViewById(R.id.rentname_edittext);
        price = view.findViewById(R.id.price_edittext);
        bedroom = view.findViewById(R.id.bedroom_edittext);
        bathroom = view.findViewById(R.id.bathroom_edittext);
        description = view.findViewById(R.id.description_edittext);
        pincode = view.findViewById(R.id.zip_code_edittext);
        state = view.findViewById(R.id.state_edittext);
        city = view.findViewById(R.id.city_edittext);

        propertyType = view.findViewById(R.id.property_type_spinner);
        ArrayAdapter<String> propertyTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,propertyTypes);
        propertyType.setAdapter(propertyTypeAdapter);
        propertyType.setSelection(0);

        listPropertyBtn = view.findViewById(R.id.list_property_btn);

        furnished = view.findViewById(R.id.furnished_radio_grp);
        parking = view.findViewById(R.id.parking_radio_grp);

        hydro = view.findViewById(R.id.hydro_checkbox);
        water = view.findViewById(R.id.water_checkbox);
        heat = view.findViewById(R.id.heat_checkbox);
        microwave = view.findViewById(R.id.microwave_checkbox);

        listPropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidated = true;
                String rentName = name.getText().toString().trim();
                String rentPrice = price.getText().toString().trim();
                String rentBedroom = bedroom.getText().toString().trim();
                String rentBathroom = bathroom.getText().toString().trim();
                String rentPincode = pincode.getText().toString().trim();
                String rentState = state.getText().toString().trim();

                String rentPropertyType = propertyType.getSelectedItem().toString().trim();

                String rentCity = city.getText().toString().trim();
                String rentDescription = description.getText().toString().trim();

                SQLiteDatabase database = databaseHelper.getReadableDatabase();
                String email = getContext().getSharedPreferences(MainActivity.sharedPrefrence, Context.MODE_PRIVATE).getString("email","");
                int userId = email.trim().isEmpty() ? -1 : databaseHelper.getRenterIdByEmail(email.trim());

                String hydroUtilities = hydro.isChecked() ? "Hydro," : "";
                String heatUtilities = heat.isChecked() ? "Heat," : "";
                String waterUtilities = water.isChecked() ? "Water," : "";
                String microwaveUtilities = microwave.isChecked() ? "Microwave," : "";
                String rentUtilities = hydroUtilities + heatUtilities + waterUtilities + microwaveUtilities;
                if(!rentUtilities.isEmpty())
                    rentUtilities = rentUtilities.substring(0,rentUtilities.length()-1);

                List<String> rentUtilitiesList = Arrays.asList(rentUtilities.split(","));

                int rentFurnished = furnished.getCheckedRadioButtonId()==R.id.furnished_radio_btn_on ? 1 : 0;
                int rentParking = parking.getCheckedRadioButtonId()==R.id.parking_radio_btn_on ? 1 : 0;


                String errorMsg = "";
                if(rentName.isEmpty())
                {
                    errorMsg += "Please Enter Name of property!\n";
                }
                if(rentPrice.isEmpty())
                {
                    errorMsg += "Please Enter Price of property!\n";
                }
                if(rentPincode.isEmpty())
                {
                    errorMsg += "Please Enter Pincode of property!\n";
                }
                if(rentState.isEmpty())
                {
                    errorMsg += "Please Enter State of property!\n";
                }
                if(rentCity.isEmpty())
                {
                    errorMsg += "Please Enter City of property!\n";
                }
                if(rentPropertyType.isEmpty())
                {
                    errorMsg += "Please Choose Type of property!\n";
                }
                if(!errorMsg.isEmpty())
                {
                    Toast.makeText(getContext(),errorMsg,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Rent rent = new Rent(rentName,rentPincode,rentCity,rentState,userId,Float.parseFloat(rentPrice),rentPropertyType,rentDescription,"",Integer.parseInt(rentBedroom), Integer.parseInt(rentBathroom),rentUtilitiesList,rentFurnished,rentParking);
                    boolean isAdded = databaseHelper.save(rent);
                    if(isAdded)
                    {
                        Toast.makeText(getContext(),"Rent details saved!!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Something went wrong!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
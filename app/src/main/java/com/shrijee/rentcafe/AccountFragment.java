package com.shrijee.rentcafe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AccountFragment extends Fragment {


    //widgets and global data
    Switch mSwitch;
    SharedPreferences sharedPreferences;
    String userName;
    TextView nameTextView;

    //Empty Constructor
    public AccountFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getting the sharedPrefrence and getting the value of email key
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.sharedPrefrence, Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("email","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        //setting the widgets using finViewById
        setWidgets(view);
        return view;
    }

    private void setWidgets(View view) {
        mSwitch = view.findViewById(R.id.switch_enable_adding);
        nameTextView = view.findViewById(R.id.account_name_txt);
        nameTextView.setText(userName);
        //getting the shared Preference for getting the information on if user has enabled the Renter option or not
        mSwitch.setChecked(sharedPreferences.getBoolean("EnableAddingRentProperty"+"_"+userName,false));

        NavigationView nav = getActivity().findViewById(R.id.nav_layout);
        Menu menu = nav.getMenu();
        setSwitchListner(menu);
    }

    private void setSwitchListner(Menu menu) {
        //whenever user changes the switch also changing the value in sharedPreference
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //if Are you renter option is enabled then showing the renter menu group otherwise not
                menu.setGroupVisible(R.id.menu_grp_rentee,b);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // if username is shubh then key will be EnableAddingRentProperty_shubh so unique for each user
                editor.putBoolean("EnableAddingRentProperty"+"_"+userName,b);
                editor.commit();
            }
        });
    }
}
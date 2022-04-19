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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AccountFragment extends Fragment {


    Switch mSwitch;
    SharedPreferences sharedPreferences;
    String userName;

    public AccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.sharedPrefrence, Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("email","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        setWidgets(view);
        return view;
    }

    private void setWidgets(View view) {
        mSwitch = view.findViewById(R.id.switch_enable_adding);

        mSwitch.setChecked(sharedPreferences.getBoolean("EnableAddingRentProperty"+"_"+userName,false));
        NavigationView nav = getActivity().findViewById(R.id.nav_layout);
        Menu menu = nav.getMenu();
        setSwitchListner(menu);
    }

    private void setSwitchListner(Menu menu) {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                menu.setGroupVisible(R.id.menu_grp_rentee,b);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("EnableAddingRentProperty"+"_"+userName,b);
                editor.commit();
            }
        });
    }
}
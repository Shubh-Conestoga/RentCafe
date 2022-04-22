package com.shrijee.rentcafe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.dialog.RentDeleteDialog;
import com.shrijee.rentcafe.model.RentedProperty;

import java.util.List;

public class MyRentFragment extends Fragment {

    //widget and data
    RecyclerView mRecyclerView;
    SharedPreferences sharedPreferences;
    String email = "";
    RentDescriptionAdapter rentDescriptionAdapter ;
    List<RentedProperty> rentedPropertyList;

    public MyRentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rent, container, false);
        //getting email from sharedpref
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.sharedPrefrence, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        //setting the widgets
        setWidgets(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        rentDescriptionAdapter.setData(rentedPropertyList);
    }

    private void setWidgets(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        mRecyclerView = view.findViewById(R.id.recycler_view_my_rnt);
        int userId = email.isEmpty() ? -1 : databaseHelper.getRenterIdByEmail(email);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //getting rentedProperty data from databaseHelper Class
        rentedPropertyList = databaseHelper.getRentedPropertyDetails("RENTEE_ID",userId);
        //creating adapter and setting it to recyclerView
        rentDescriptionAdapter = new RentDescriptionAdapter(rentedPropertyList, new RentDescriptionAdapter.MyRentClickListner() {
            @Override
            public void onMyRentClickListner(int position) {
//                on click of item creting and  showing an alertDialog using RentDeleteDialog
                RentDeleteDialog rentDeleteDialog = new RentDeleteDialog(rentedPropertyList,position);
                rentDeleteDialog.show(getParentFragmentManager(),"Delete Fragment");
            }
        });
        mRecyclerView.setAdapter(rentDescriptionAdapter);

    }
}
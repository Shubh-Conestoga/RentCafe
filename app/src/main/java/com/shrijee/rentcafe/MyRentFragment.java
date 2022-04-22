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

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.RentedProperty;

import java.util.List;

public class MyRentFragment extends Fragment {

    RecyclerView mRecyclerView;
    SharedPreferences sharedPreferences;
    String email = "";

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
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.sharedPrefrence, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        setWidgets(view);
        return view;
    }

    private void setWidgets(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        mRecyclerView = view.findViewById(R.id.recycler_view_my_rnt);
        int userId = email.isEmpty() ? -1 : databaseHelper.getRenterIdByEmail(email);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<RentedProperty> rentedDetails = databaseHelper.getRentedPropertyDetails("RENTER_ID",userId);
        RentDescriptionAdapter rentDescriptionAdapter = new RentDescriptionAdapter(rentedDetails);
        mRecyclerView.setAdapter(rentDescriptionAdapter);

    }
}
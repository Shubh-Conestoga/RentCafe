package com.shrijee.rentcafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.Rent;

import java.util.List;

public class RentalFragment extends Fragment {

    //Creating an Adaptor
    private  RentAdapter rentAdapter;
//  RecyclerView
    private RecyclerView recyclerView;
//    data
    List<Rent> rentList;

    public RentalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rental, container, false);
        //getting the data from DatabaseHelper class using getRentDetails function
        rentList = (new DatabaseHelper(getContext())).getRentDetails();

        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //setting the adaptor in recycler view
        rentAdapter = new RentAdapter(rentList, (view1, position) -> performActivityOnClickOfRent(view1,rentList.get(position)));
        recyclerView.setAdapter(rentAdapter);
        return view;
    }

    //on resume getting data from database inshort refreshing the data
    @Override
    public void onResume() {
        super.onResume();
        rentList = (new DatabaseHelper(getContext())).getRentDetails();
        rentAdapter.setData(rentList);
    }

    //go to RentDescription activity from HomeActivity and also putting the clicked rent data using putExtra
    private void performActivityOnClickOfRent(View view1, Rent rent) {
        Intent intent = new Intent(getContext(),RentDescriptionActivity.class);
        intent.putExtra("RENT_DATA",rent);
        startActivity(intent);
    }

}
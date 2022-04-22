package com.shrijee.rentcafe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrijee.rentcafe.model.Rent;

import java.util.List;

public class RentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // data
    List<Rent> rentList = null;
    //listner
    OnRentClickListner onRentClickListner = null;

    public RentAdapter()
    {}

    //contructor
    public RentAdapter(List<Rent> rentList,OnRentClickListner onRentClickListner)
    {
        this.rentList = rentList;
        this.onRentClickListner = onRentClickListner;
    }

    //setting the data
    public void setData(List<Rent> rentList)
    {
        this.rentList = rentList;
        notifyDataSetChanged();
    }


    //holder class
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        //widget
        TextView priceView,nameView,locationView,propertyTypeView;
        ImageView propertyPhoto;
        Context context;
        public MyViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            //setting the widgets
            priceView = itemView.findViewById(R.id.price);
            nameView = itemView.findViewById(R.id.house_title);
            locationView = itemView.findViewById(R.id.location);
            propertyPhoto = itemView.findViewById(R.id.house_image);
            propertyTypeView = itemView.findViewById(R.id.house_type);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        //onClick
        @Override
        public void onClick(View view) {
            onRentClickListner.onRentClick(view,getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_row_layout,parent,false);
        return new MyViewHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //set Image

        //getting the rent
        Rent rent = rentList.get(position);
        //switch case for setting the image and other data
        switch (rent.getImageURL()) {
            case "1":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_1);
                break;
            case "2":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_2);
                break;
            case "3":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_3);
                break;
            case "4":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_4);
                break;
            case "5":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_5);
                break;
            case "6":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_6);
                break;
            case "7":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_7);
                break;
            case "8":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_8);
                break;
            case "9":
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_9);
                break;
            default:
                myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_10);
                break;
        }



        myViewHolder.locationView.setText(rent.getCity()+", "+rent.getState());
        myViewHolder.nameView.setText(rent.getRentName());
        myViewHolder.priceView.setText(String.valueOf(rent.getPrice()));
    }

    //getting the total count of data
    @Override
    public int getItemCount() {
        return rentList.size();
    }

    //interface for OnRentClickListner
    interface OnRentClickListner
    {
        void onRentClick(View view, int position);
    }

}

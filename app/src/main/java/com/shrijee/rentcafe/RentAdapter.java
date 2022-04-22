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
    List<Rent> rentList = null;
    OnRentClickListner onRentClickListner = null;

    public RentAdapter()
    {}

    public RentAdapter(List<Rent> rentList,OnRentClickListner onRentClickListner)
    {
        this.rentList = rentList;
        this.onRentClickListner = onRentClickListner;
    }

    public void setData(List<Rent> rentList)
    {
        this.rentList = rentList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView priceView,nameView,locationView,propertyTypeView;
        ImageView propertyPhoto;
        Context context;
        public MyViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            priceView = itemView.findViewById(R.id.price);
            nameView = itemView.findViewById(R.id.house_title);
            locationView = itemView.findViewById(R.id.location);
            propertyPhoto = itemView.findViewById(R.id.house_image);
            propertyTypeView = itemView.findViewById(R.id.house_type);
            this.context = context;
            itemView.setOnClickListener(this);
        }

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

        Rent rent = rentList.get(position);

        if(rent.getImageURL().equals("1"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_1);
        else if(rent.getImageURL().equals("2"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_2);
        else if(rent.getImageURL().equals("3"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_3);
        else if(rent.getImageURL().equals("4"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_4);
        else if(rent.getImageURL().equals("5"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_5);
        else if(rent.getImageURL().equals("6"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_6);
        else if(rent.getImageURL().equals("7"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_7);
        else if(rent.getImageURL().equals("8"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_8);
        else if(rent.getImageURL().equals("9"))
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_9);
        else
            myViewHolder.propertyPhoto.setImageDrawable(MainActivity.PHOTO_10);



        myViewHolder.locationView.setText(rent.getCity()+", "+rent.getState());
        myViewHolder.nameView.setText(rent.getRentName());
        myViewHolder.priceView.setText(String.valueOf(rent.getPrice()));
    }

    @Override
    public int getItemCount() {
        return rentList.size();
    }

    interface OnRentClickListner
    {
        void onRentClick(View view, int position);
    }

}

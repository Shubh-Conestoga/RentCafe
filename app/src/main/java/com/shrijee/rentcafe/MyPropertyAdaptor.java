package com.shrijee.rentcafe;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrijee.rentcafe.model.Rent;
import com.shrijee.rentcafe.model.RentedProperty;

import java.util.List;

public class MyPropertyAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<RentedProperty> rentList = null;
    RentAdapter.OnRentClickListner onRentClickListner = null;

    int greenColor = -1;
    int redColor = -1;
    public MyPropertyAdaptor()
    {}

    //getting data and implemetation of ClickListner
    public MyPropertyAdaptor(List<RentedProperty> rentList, RentAdapter.OnRentClickListner onRentClickListner)
    {
        this.rentList = rentList;
        this.onRentClickListner = onRentClickListner;
    }

    //setting data and notifying
    public void setData(List<RentedProperty> rentList)
    {
        this.rentList = rentList;
        notifyDataSetChanged();
    }


    //View holder class
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView priceView,nameView,locationView,propertyTypeView,statusView;
        ImageView propertyPhoto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            priceView = itemView.findViewById(R.id.price);
            nameView = itemView.findViewById(R.id.house_title);
            locationView = itemView.findViewById(R.id.location);
            propertyPhoto = itemView.findViewById(R.id.house_image);
            propertyTypeView = itemView.findViewById(R.id.house_type);
            statusView = itemView.findViewById(R.id.view_rental_status);
            statusView.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }
//   called whenever an item gets clicked
        @Override
        public void onClick(View view) {
//            then calling the onRentClick function
            onRentClickListner.onRentClick(view,getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_row_layout,parent,false);
//        getting the color ids
        if(greenColor==-1)
        {
            greenColor = view.getContext().getResources().getColor(R.color.green, view.getContext().getTheme());
            redColor = view.getContext().getResources().getColor(R.color.red, view.getContext().getTheme());
        }
        //returning the holder class
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //biding the data with views
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //set Image
        Rent rent = rentList.get(position).getRent();
        myViewHolder.locationView.setText(rent.getCity()+", "+rent.getState());
        myViewHolder.nameView.setText(rent.getRentName());
        myViewHolder.priceView.setText(String.valueOf(rent.getPrice()));

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

        //if rented then show green otherwise red line
        if(rentList.get(position).getActiveFlag()==1)
            myViewHolder.statusView.setBackgroundColor(greenColor);
        else
            myViewHolder.statusView.setBackgroundColor(redColor);

    }

    //total data
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

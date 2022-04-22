package com.shrijee.rentcafe;

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

public class RentDescriptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        //data
        List<RentedProperty> rentList = null;
        //click listner interface
        MyRentClickListner myRentClickListner = null;

        public RentDescriptionAdapter()
        {}

        //constructor
        public RentDescriptionAdapter(List<RentedProperty> rentList,MyRentClickListner myRentClickListner)
        {
            this.rentList = rentList;
            this.myRentClickListner = myRentClickListner;
        }

        //setting the data
        public void setData(List<RentedProperty> rentList)
        {
            this.rentList = rentList;
            notifyDataSetChanged();
        }

        //ViewHolder Class
        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {

            //Widgts and setting widgets in constructor
            TextView priceView,nameView,locationView,typeView;
            ImageView propertyPhoto;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                priceView = itemView.findViewById(R.id.price);
                nameView = itemView.findViewById(R.id.house_title);
                locationView = itemView.findViewById(R.id.location);
                propertyPhoto = itemView.findViewById(R.id.house_image);
                typeView = itemView.findViewById(R.id.house_type);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
//                onclick call onMyRentClickListner btn
                 myRentClickListner.onMyRentClickListner(getAdapterPosition());
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //getting the inflatef view and creating and returning the inflated view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_row_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            //setting the data to widgets
            MyViewHolder myViewHolder = (MyViewHolder) holder;
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
        }

        //total size of data
        @Override
        public int getItemCount() {
            return rentList.size();
        }

        //clicklistner of MyRentClickKistner
        public interface MyRentClickListner
        {
            public void onMyRentClickListner(int position);
        }

}

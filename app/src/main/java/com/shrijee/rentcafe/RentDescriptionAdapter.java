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

        List<RentedProperty> rentList = null;
        MyRentClickListner myRentClickListner = null;

        public RentDescriptionAdapter()
        {}

        public RentDescriptionAdapter(List<RentedProperty> rentList,MyRentClickListner myRentClickListner)
        {
            this.rentList = rentList;
            this.myRentClickListner = myRentClickListner;
        }

        public void setData(List<RentedProperty> rentList)
        {
            this.rentList = rentList;
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {

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
                myRentClickListner.onMyRentClickListner(getAdapterPosition());
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_row_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            //set Image
            Rent rent = rentList.get(position).getRent();
            myViewHolder.locationView.setText(rent.getCity()+", "+rent.getState());
            myViewHolder.nameView.setText(rent.getRentName());
            myViewHolder.priceView.setText(String.valueOf(rent.getPrice()));
        }

        @Override
        public int getItemCount() {
            return rentList.size();
        }

        public interface MyRentClickListner
        {
            public void onMyRentClickListner(int position);
        }

}

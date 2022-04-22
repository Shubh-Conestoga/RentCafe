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

    public MyPropertyAdaptor(List<RentedProperty> rentList, RentAdapter.OnRentClickListner onRentClickListner)
    {
        this.rentList = rentList;
        this.onRentClickListner = onRentClickListner;
    }

    public void setData(List<RentedProperty> rentList)
    {
        this.rentList = rentList;
        notifyDataSetChanged();
    }


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

        @Override
        public void onClick(View view) {
            onRentClickListner.onRentClick(view,getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_row_layout,parent,false);
        if(greenColor==-1)
        {
            greenColor = view.getContext().getResources().getColor(R.color.green, view.getContext().getTheme());
            redColor = view.getContext().getResources().getColor(R.color.red, view.getContext().getTheme());
        }
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

        if(rentList.get(position).getActiveFlag()==1)
            myViewHolder.statusView.setBackgroundColor(greenColor);
        else
            myViewHolder.statusView.setBackgroundColor(redColor);

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

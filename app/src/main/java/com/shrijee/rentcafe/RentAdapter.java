package com.shrijee.rentcafe;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrijee.rentcafe.model.Rent;

import java.util.List;

public class RentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Rent> rentList = null;

    public RentAdapter()
    {}

    public RentAdapter(List<Rent> rentList)
    {
        this.rentList = rentList;
    }

    public void setData(List<Rent> rentList)
    {
        this.rentList = rentList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView priceView,nameView,locationView;
        ImageView propertyPhoto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            priceView = itemView.findViewById(R.id.txt_price);
            nameView = itemView.findViewById(R.id.txt_rentname);
            locationView = itemView.findViewById(R.id.txt_location);
            propertyPhoto = itemView.findViewById(R.id.img_rent);
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
        Rent rent = rentList.get(position);
        myViewHolder.locationView.setText(rent.getCity()+", "+rent.getState());
        myViewHolder.nameView.setText(rent.getRentName());
        myViewHolder.priceView.setText(String.valueOf(rent.getPrice()));
    }

    @Override
    public int getItemCount() {
        return rentList.size();
    }
}

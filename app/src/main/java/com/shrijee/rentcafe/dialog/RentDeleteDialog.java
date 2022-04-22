package com.shrijee.rentcafe.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shrijee.rentcafe.MyRentFragment;
import com.shrijee.rentcafe.R;
import com.shrijee.rentcafe.RentalFragment;
import com.shrijee.rentcafe.database.DatabaseHelper;
import com.shrijee.rentcafe.model.RentedProperty;

import java.util.List;

public class RentDeleteDialog extends DialogFragment {

    //List of data and position which is selected
    List<RentedProperty> rentedPropertyList = null;
    int position = -1;

    //getting data and the position of selected item
    public RentDeleteDialog(List<RentedProperty> rentedPropertyList,int position) {
        this.rentedPropertyList = rentedPropertyList;
        this.position = position;
    }

    //this function will be called when creating an instance of this class
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //creating and alertDialog
        AlertDialog.Builder aleBuilder = new AlertDialog.Builder(getActivity());
        //setting title and message
        aleBuilder.setTitle("Leaving the property");
        aleBuilder.setMessage("Do you want to cancel the rent?");
        //setting positive buttin title and onclicklistner
        aleBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //making active_flag value to 0 for removing from the rent in DB for that using a function of DatabaseHelper class
                boolean isDeleted = new DatabaseHelper(getContext()).removeRentendProperty(rentedPropertyList.get(position).getRentedPropertyId());
                //if deletion is success then showing the success message and removing data from list and again opening the fragment
                if(isDeleted)
                {
                    Toast.makeText(getContext(),"Rent has been removed successfully!!!",Toast.LENGTH_SHORT).show();
                    rentedPropertyList.remove(position);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frm_frame_layout,new MyRentFragment());
                    transaction.commit();
                }
                //otherwise showing an error
                else
                {
                    Toast.makeText(getContext(),"Something went wrong!!",Toast.LENGTH_SHORT).show();
                }
                //dismissing the dialog in both cases
                dialogInterface.dismiss();

            }
        });
        //setting the negative button and onClickListner
        aleBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dismissing the dialog
                dialogInterface.dismiss();
            }
        });
        //returung the built alertDialog
        return aleBuilder.create();
    }
}

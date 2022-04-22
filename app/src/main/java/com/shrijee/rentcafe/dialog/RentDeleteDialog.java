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

    List<RentedProperty> rentedPropertyList = null;
    int position = -1;

    public RentDeleteDialog(List<RentedProperty> rentedPropertyList,int position) {
        this.rentedPropertyList = rentedPropertyList;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder aleBuilder = new AlertDialog.Builder(getActivity());
        aleBuilder.setTitle("Leaving the property");
        aleBuilder.setMessage("Do you want to cancel the rent?");
        aleBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean isDeleted = new DatabaseHelper(getContext()).removeRentendProperty(rentedPropertyList.get(position).getRentedPropertyId());
                if(isDeleted)
                {
                    Toast.makeText(getContext(),"Rent has been removed successfully!!!",Toast.LENGTH_SHORT).show();
                    rentedPropertyList.remove(position);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frm_frame_layout,new MyRentFragment());
                    transaction.commit();
                }
                else
                {
                    Toast.makeText(getContext(),"Something went wrong!!",Toast.LENGTH_SHORT).show();
                }
                dialogInterface.dismiss();

            }
        });
        aleBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return aleBuilder.create();
    }
}

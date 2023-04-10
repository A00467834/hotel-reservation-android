package com.example.hotelreservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ConfirmationFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.confirmation_number_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView confirmationTextView = view.findViewById(R.id.confirmation_number);
        TextView checkInDateTextView = view.findViewById(R.id.check_in_date_text);
        TextView checkOutDateTextView = view.findViewById(R.id.check_out_date_text);
        TextView hotelNameTextView = view.findViewById(R.id.hotel_name);
        TextView numberOfGuestsTextView = view.findViewById(R.id.number_of_guests_text);

        String confirmationNumber = getArguments().getString("confirmationNumber");
        String checkInDate = getArguments().getString("checkInDate");
        String checkOutDate = getArguments().getString("checkOutDate");
        String numberOfGuests = getArguments().getString("numberOfGuests");
        String hotelName = getArguments().getString("hotelName");

        confirmationTextView.setText("Confirmation Number : " + confirmationNumber);
        checkInDateTextView.setText("Check In date : " + checkInDate);
        checkOutDateTextView.setText("Check Out Date : " + checkOutDate);
        hotelNameTextView.setText("Hotel Name : " + hotelName);
        numberOfGuestsTextView.setText("Number of Guests : " + numberOfGuests);
    }
}

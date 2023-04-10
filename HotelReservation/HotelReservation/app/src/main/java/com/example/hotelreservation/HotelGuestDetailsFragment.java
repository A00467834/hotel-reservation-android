package com.example.hotelreservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelGuestDetailsFragment extends Fragment {

    View view;

    String checkInDate;

    String checkOutDate;

    int hotelId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_guest_details_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView hotelNameTextView = view.findViewById(R.id.hotel_name_text_view);
        TextView costTextView = view.findViewById(R.id.cost_text_view);
        TextView availabilityTextView = view.findViewById(R.id.availability_text_view);

        ArrayList<CustomerData> editTextDataList = new ArrayList<>();

        String hotelName = getArguments().getString("hotel name");
        String hotelPrice = getArguments().getString("hotel price");
        String hotelAvailability = getArguments().getString("hotel availability");
        String numberOfGuests = getArguments().getString("numberOfGuests");
        this.checkInDate = getArguments().getString("checkInDate");
        this.checkOutDate = getArguments().getString("checkOutDate");
        this.hotelId = Integer.parseInt(getArguments().getString("hotelId"));

        hotelNameTextView.setText("Hotel Name : " +hotelName);
        costTextView.setText("Cost : "+hotelPrice);
        availabilityTextView.setText("Availability Status : " + (Boolean.parseBoolean(hotelAvailability) ? "Available" : "Unavailable"));
        RecyclerView recyclerView = view.findViewById(R.id.guest_details_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuestDetailsAdapter guestDetailsAdapter = new GuestDetailsAdapter(getActivity(), numberOfGuests);
        recyclerView.setAdapter(guestDetailsAdapter);

        Button submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < guestDetailsAdapter.getItemCount(); i++) {
                    View itemView = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                    EditText editText = itemView.findViewById(R.id.guest_name_text);
                    String editTextData = editText.getText().toString();

                    RadioGroup genderRadioGroup = itemView.findViewById(R.id.gender_radio_group);
                    int checkedId = genderRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = genderRadioGroup.findViewById(checkedId);
                    String gender = radioButton.getText().toString();

                    editTextDataList.add(new CustomerData(editTextData, gender));
                }

                BookingDetailsData bookingData = new BookingDetailsData(checkInDate, checkOutDate);
                bookingData.setHotelId(hotelId);

                Call<BookingDetailsData> call =  Api.getClient().bookHotel(bookingData);

                call.enqueue(new Callback<BookingDetailsData>() {
                    @Override
                    public void onResponse(Call<BookingDetailsData> call, Response<BookingDetailsData> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            BookingDetailsData bookingConfirmation = response.body();

                            Bundle bundle = new Bundle();
                            bundle.putString("confirmationNumber" , Integer.toString(bookingConfirmation.getId()));
                            bundle.putString("checkInDate", checkInDate);
                            bundle.putString("checkOutDate", checkOutDate);
                            bundle.putString("numberOfGuests", numberOfGuests);
                            bundle.putString("hotelName", hotelName);

                            ConfirmationFragment confirmationFragment = new ConfirmationFragment();
                            confirmationFragment.setArguments(bundle);

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_layout, confirmationFragment);
                            fragmentTransaction.remove(HotelGuestDetailsFragment.this);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commitAllowingStateLoss();

                        } else {
                            Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingDetailsData> call, Throwable t) {
                        Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}

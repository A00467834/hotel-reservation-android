package com.example.hotelreservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HotelsListFragment extends Fragment implements ItemClickListener {

    View view;
    TextView headingTextView, checkInDateTextView, checkOutDateTextView, noOfGuestTextView;
    ProgressBar progressBar;
    List<HotelListData> userListResponseData;
    ArrayList<HotelListData> hotelListData;

    String checkInDate;
    String checkOutDate;
    String numberOfGuests;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headingTextView = view.findViewById(R.id.heading_text_view);
        checkInDateTextView = view.findViewById(R.id.check_in_date_text);
        checkOutDateTextView = view.findViewById(R.id.check_out_date_text);
        noOfGuestTextView = view.findViewById(R.id.number_of_guests);
        progressBar = view.findViewById(R.id.progress_bar);

        this.checkInDate = getArguments().getString("check in date");
        this.checkOutDate = getArguments().getString("check out date");
        this.numberOfGuests = getArguments().getString("number of guests");
        String nameOfGuest = getArguments().getString("name of guest");

        headingTextView.setText("Welcome " + nameOfGuest);
        checkInDateTextView.setText("Check In Date: " + this.checkInDate);
        checkOutDateTextView.setText("Check Out Date: " + this.checkOutDate);
        noOfGuestTextView.setText("Number of guests: " + this.numberOfGuests);

        getHotelsListsData();
    }

    public ArrayList<HotelListData> initHotelListData() {
        ArrayList<HotelListData> list = new ArrayList<>();

        list.add(new HotelListData("Halifax Regional Hotel", "2000$", "true", 1));
        list.add(new HotelListData("Hotel Pearl", "500$", "false", 2));
        list.add(new HotelListData("Hotel Amano", "800$", "true", 3));
        list.add(new HotelListData("San Jones", "250$", "false", 4));
        list.add(new HotelListData("Halifax Regional Hotel", "2000$", "true", 5));
        list.add(new HotelListData("Hotel Pearl", "500$", "false", 6));
        list.add(new HotelListData("Hotel Amano", "800$", "true", 7));
        list.add(new HotelListData("San Jones", "250$", "false", 8));

        return list;
    }

    private void getHotelsListsData() {
        progressBar.setVisibility(View.VISIBLE);

        Api.getClient().getHotels().enqueue(new Callback<List<HotelListData>>() {
            @Override
            public void onResponse(Call<List<HotelListData>> call, Response<List<HotelListData>> response) {
                if (response.isSuccessful()) {
                    userListResponseData = response.body();
                    setupRecyclerView();
                } else {
                    Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<HotelListData>> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRecyclerView() {

        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
        recyclerView.setAdapter(hotelListAdapter);
        //Bind the click listener
        hotelListAdapter.setClickListener(this);
    }


    @Override
    public void onClick(View view, int position) {
        HotelListData hotelData = userListResponseData.get(position);
        String hotelName = hotelData.getHotel_name();
        String price = hotelData.getPrice();
        String availability = hotelData.getAvailability();
        int hotelId = hotelData.getId();

        Bundle bundle = new Bundle();
        bundle.putString("hotel name", hotelName);
        bundle.putString("hotel price", price);
        bundle.putString("hotel availability", availability);
        bundle.putString("checkInDate", this.checkInDate);
        bundle.putString("checkOutDate", this.checkOutDate);
        bundle.putString("numberOfGuests", this.numberOfGuests);
        bundle.putString("hotelId", Integer.toString(hotelId));

        HotelGuestDetailsFragment hotelGuestDetailsFragment = new HotelGuestDetailsFragment();
        hotelGuestDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, hotelGuestDetailsFragment);
        fragmentTransaction.remove(HotelsListFragment.this);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }
}

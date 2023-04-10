package com.example.hotelreservation;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getHotels")
    Call<List<HotelListData>> getHotels();

    @POST("addBooking")
    Call<BookingDetailsData> bookHotel(@Body BookingDetailsData bookingObj);
}

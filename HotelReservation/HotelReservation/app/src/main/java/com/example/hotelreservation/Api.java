package com.example.hotelreservation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static ApiInterface getClient() {

        String BASE_URL = "http://192.168.4.37:8080/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of your ApiService interface
        ApiInterface apiService = retrofit.create(ApiInterface.class);

        return apiService;
    }
}




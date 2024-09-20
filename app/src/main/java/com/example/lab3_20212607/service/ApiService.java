package com.example.lab3_20212607.service;

import com.example.lab3_20212607.dto.LoginRequest;
import com.example.lab3_20212607.dto.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}



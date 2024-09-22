package com.example.lab3_20212607.service;

import com.example.lab3_20212607.dto.LoginRequest;
import com.example.lab3_20212607.dto.LoginResponse;
import com.example.lab3_20212607.dto.ToDoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("todos/user/{userId}")
    Call<ToDoResponse> getTodosByUserId(@Path("userId") int userId);

}



package com.ricardo.ricardo.service;

import com.ricardo.ricardo.models.ResponseList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpaceService {
    @GET("tech-interview/mobile-test-one.json")
    Call<List<ResponseList>> GetDayByDayClasses();
}

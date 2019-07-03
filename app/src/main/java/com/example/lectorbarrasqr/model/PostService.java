package com.example.lectorbarrasqr.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {

    String API_ROUTE = "productos";

    @GET(API_ROUTE)
    Call<List<Productos>> getPost();
}

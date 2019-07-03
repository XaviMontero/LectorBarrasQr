package com.example.lectorbarrasqr.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostService {

    String API_ROUTE = "{id}/busca";

    @GET(API_ROUTE)
    Call<List<Productos>> getPost(@Path("id") String id);
}

package com.grzegorz.ad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MagicService {
    @GET("cards")
    Call<CardChunk> listCards(
            @Query("page") int page,
            @Query("pageSize") int pageSize);

    @GET("cards/{id}")
    Call<CardDetails> cardDetails(@Path("id") String id);

}


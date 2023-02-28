package com.grzegorz.ad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MagicService {
    @GET("cards")
    Call<List<Card>> getAllCards();

    @GET("cards/{id}")
    Call<Card> getCardById(@Path("id") int id);

    @GET("pokemon-species")
    Call<CardChunk> listSpecies(
            @Query("offset") int offset,
            @Query("limit") int limit);


    @GET("pokemon-species/{name}")
    Call<CardDetails> cardDetails(@Path("name") String name);
}

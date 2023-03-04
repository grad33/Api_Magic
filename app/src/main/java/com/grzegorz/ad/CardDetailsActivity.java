package com.grzegorz.ad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardDetailsActivity extends AppCompatActivity {

    private static final String TAG = CardDetailsActivity.class.getName();
    public final static String CARD_KEY = "ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        Intent intent = getIntent();
        String cardId = intent.getStringExtra(CARD_KEY);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        OkHttpClient okhttp=new OkHttpClient().newBuilder()
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.magicthegathering.io/v1/")
                .client(okhttp)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MagicService _service = retrofit.create(MagicService.class);
        Call<CardDetails> speciesDetailsCall = _service.cardDetails(cardId);
        speciesDetailsCall.enqueue(new Callback<CardDetails>() {
            @Override
            public void onResponse(Call<CardDetails> call, Response<CardDetails> response) {
                if (response.isSuccessful()) {
                    CardDetails cardDetails = response.body();
                    TextView loading = findViewById(R.id.loading);
                    TextView cardDetailMana = findViewById(R.id.card_manaCost);
                    TextView cardDetailPower = findViewById(R.id.card_detail_power);
                    TextView cardDetailToughness = findViewById(R.id.card_detail_toughness);
                    TextView cardDetailSet = findViewById(R.id.card_detail_set);
                    TextView cardDetailName = findViewById(R.id.card_detail_name);
                    TextView cardDetailDescription = findViewById(R.id.card_detail_description);
                    ImageView cardDetailUrl=findViewById(R.id.card_img);
                    if(cardDetails.card.name!=null&&cardDetails.card.text!=null) {
                        cardDetailMana.setText(cardDetails.card.manaCost);
                        cardDetailPower.setText(cardDetails.card.power);
                        cardDetailToughness.setText(cardDetails.card.toughness);
                        cardDetailSet.setText(cardDetails.card.setName);
                        cardDetailName.setText(cardDetails.card.name);
                        cardDetailDescription.setText(cardDetails.card.text);
                        Picasso.get().load(cardDetails.card.imageUrl).into(cardDetailUrl);
                        if(cardDetailName.getText().toString()!=""){
                            loading.setText("");
                        }
                    }
                } else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<CardDetails> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
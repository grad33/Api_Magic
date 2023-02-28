package com.grzegorz.ad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    public interface OnSpeciesClickListener {
        void onSpeciesClick(Card species);
    }

    private final List<Card> _speciesList;
    private final OnSpeciesClickListener _onSpeciesClickListener;

    public CardAdapter(List<Card> speciesList, OnSpeciesClickListener onSpeciesClickListener) {

        _speciesList = speciesList;
        _onSpeciesClickListener = onSpeciesClickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_species_item, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(view, _onSpeciesClickListener);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(_speciesList.get(position));
    }

    @Override
    public int getItemCount() {
        return _speciesList.size();
    }
}
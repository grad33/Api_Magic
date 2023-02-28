package com.grzegorz.ad;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {

    private final TextView _speciesNameTextView;
    private final CardAdapter.OnSpeciesClickListener _onSpeciesClickListener;
    private Card _species;

    public CardViewHolder(@NonNull View itemView, CardAdapter.OnSpeciesClickListener _onSpeciesClickListener) {
        super(itemView);
        _speciesNameTextView = itemView.findViewById(R.id.species_name);
        this._onSpeciesClickListener = _onSpeciesClickListener;
        _speciesNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_species != null) {
                    _onSpeciesClickListener.onSpeciesClick(_species);
                }
            }
        });
    }

    public void bind(Card species) {
        _species = species;
        _speciesNameTextView.setText(species.name);
    }
}
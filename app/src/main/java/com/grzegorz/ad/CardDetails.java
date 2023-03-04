package com.grzegorz.ad;

import java.net.URI;
import java.util.List;

public class CardDetails {

    public class Card {

        public String name;
        public String imageUrl;
        public String type;
        public String text;
        public String rarity;
        public String setName;
        public String power;
        public String toughness;
        public String cmc;
        public String number;
        public String id;


        public Card(String name, String imageUrl, String type, String text, String rarity,
                           String setName, String power, String toughness, String cmc, String number, String id) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.type = type;
            this.text = text;
            this.rarity = rarity;
            this.setName = setName;
            this.power = power;
            this.toughness = toughness;
            this.cmc = cmc;
            this.number = number;
            this.id = id;
        }
    }

    //@SerializedName("flavor_text_entries") commented coz we're setting the proper FieldNamingPolicy in the builder
    public Card card;
}

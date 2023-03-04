package com.grzegorz.ad;

import java.net.URI;
import java.util.List;


public class Card{
    public String id;
    public String name;
    public String imageUrl;
    public String text;
    public String rarity;


    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", text='" + text + '\'' +
                ", rarity='" + rarity + '\'' +
                '}';
    }
}

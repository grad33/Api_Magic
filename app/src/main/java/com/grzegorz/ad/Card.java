package com.grzegorz.ad;

import java.net.URI;
import java.util.List;


public class Card{
    public String id;
    public String name;
    public String imageUrl;

    public String getId(){
        return id;
    }
    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

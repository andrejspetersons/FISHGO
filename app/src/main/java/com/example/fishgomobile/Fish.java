package com.example.fishgomobile;

import java.io.Serializable;

public class Fish implements Serializable {

    private String name;
    private int res;
    private String ierice;

    Fish(String name,int res,String ierice){

        this.name=name;
        this.res=res;
        this.ierice=ierice;

    }

    //getters
    public String getName() {
        return name;
    }

    public int getRes() {
        return res;
    }

    public String getIerice() {
        return ierice;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public void setIerice(String ierice) {
        this.ierice = ierice;
    }
}

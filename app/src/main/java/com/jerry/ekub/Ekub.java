package com.jerry.ekub;

import java.io.Serializable;

public class Ekub implements Serializable {
    private String name;
    private int amount;
    private int duration;
    private String type;
    private String date;

    public Ekub() {
    }

    public Ekub(String name, int amount, int members, String type, String date) {
        this.name = name;
        this.amount = amount;
        this.duration = members;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
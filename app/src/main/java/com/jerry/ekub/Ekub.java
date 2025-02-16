package com.jerry.ekub;

public class Ekub {
    private String name;
    private int stake;
    private int totalQuantity;
    private String type;
    private String deadline;

    public Ekub(String name, int stake, int totalQuantity, String type, String deadline) {
        this.name = name;
        this.stake = stake;
        this.totalQuantity = totalQuantity;
        this.type = type;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public int getStake() {
        return stake;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public String getType() {
        return type;
    }

    public String getDeadline() {
        return deadline;
    }
}
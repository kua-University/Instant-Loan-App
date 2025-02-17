package com.jerry.ekub;

public class EkubApplication {
    private String userId;
    private String ekubName;
    private int amount;
    private String type;


    // No-argument constructor required for Firestore
    public EkubApplication() {}

    public EkubApplication(String userId, String ekubName, int stake, int totalQty, String type, String deadline) {
        this.userId = userId;
        this.ekubName = ekubName;
        this.amount = stake;
        this.type = type;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEkubName() { return ekubName; }
    public void setEkubName(String ekubName) { this.ekubName = ekubName; }
    public int getAmount() { return amount; }
    public void setAmount(int stake) { this.amount = stake; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
package com.mobisec.DecisionMaker;

public class EventActivity {

    private String name;
    private int registered;
    private int available;

    public EventActivity(String name, int registered, int available) {
        this.name = name;
        this.registered = registered;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public int getRegistered() {
        return registered;
    }

    public int getAvailable() {
        return available;
    }

}

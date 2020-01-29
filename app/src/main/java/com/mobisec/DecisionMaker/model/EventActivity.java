package com.mobisec.DecisionMaker.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class EventActivity {

    private String id;
    private String name;
    private int available;
    private List<String> registeredUsers;

    public EventActivity() {
    }

    public EventActivity(String id, String name, int available, List<String> registeredUsers) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.registeredUsers = registeredUsers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getRegistered() {
        if (isNull(registeredUsers)) {
            return 0;
        }
        return registeredUsers.size();
    }

    public List<String> getregisteredUsers() {
        if (isNull(registeredUsers)) {
            registeredUsers = new ArrayList<>();
        }
        return registeredUsers;
    }

    public void setregisteredUsers(List<String> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }
}

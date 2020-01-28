package com.mobisec.DecisionMaker.model;

import java.util.List;

public class Event {

    private String id;
    private String name;
    private List<EventActivity> activities;
    private String adminId;
    private boolean isFinalized;

    public Event(String id, String name, List<EventActivity> activities, String adminId) {
        this.id = id;
        this.name = name;
        this.activities = activities;
        this.adminId = adminId;
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

    public List<EventActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<EventActivity> activities) {
        this.activities = activities;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean finalized) {
        isFinalized = finalized;
    }
}

package com.mobisec.DecisionMaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User {
    private String id;
    private String name;
    private Collection<String> eventID = new ArrayList<String>();

    public void setuser(String id, String name,  String eventID){       //setter
        this.id = id;
        this.name = name;
        this.eventID.add(eventID);

    }


}

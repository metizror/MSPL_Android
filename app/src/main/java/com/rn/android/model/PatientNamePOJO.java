package com.rn.android.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by abc on 10/6/2017.
 */

public class PatientNamePOJO extends RealmObject {
    //private variables
    private String name;

    @PrimaryKey
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Empty constructor
    public PatientNamePOJO() {

    }

    // constructor
    public PatientNamePOJO(String name) {
        this.name = name;
    }

    // getting ID
    public String getName() {
        return this.name;
    }

    // setting id
    public void setName(String name) {
        this.name = name;
    }
}

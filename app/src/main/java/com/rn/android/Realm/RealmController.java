package com.rn.android.Realm;

import com.rn.android.MyApplication;
import com.rn.android.model.PatientNamePOJO;

import java.util.List;

import io.realm.Realm;

import static android.R.attr.name;

/**
 * Created by abc on 10/6/2017.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(MyApplication myApplication) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(MyApplication myApplication) {

        if (instance == null) {
            instance = new RealmController(myApplication);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(PatientNamePOJO.class);
        realm.commitTransaction();
    }

    //find all objects in the PatientNamePOJO.class
    public List<PatientNamePOJO> getPatientList() {

        return (List<PatientNamePOJO>)realm.where(PatientNamePOJO.class).findAll();
    }

    //query a single item with the given id
    public PatientNamePOJO getName(String id) {

        return realm.where(PatientNamePOJO.class).equalTo("name", name).findFirst();
    }

    //check if PatientNamePOJO.class is empty
    public boolean hasPatientNamePOJO() {

        return !realm.allObjects(PatientNamePOJO.class).isEmpty();
    }

    public void addPatient(PatientNamePOJO pojo) {
        realm.beginTransaction();
        realm.copyToRealm(pojo);
        realm.commitTransaction();
    }
}

package com.mc.library.database.realm.wrapper;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmDate extends RealmObject {
    private Date value;

    public RealmDate() {
    }

    public RealmDate(Date value) {
        this.value = value;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}

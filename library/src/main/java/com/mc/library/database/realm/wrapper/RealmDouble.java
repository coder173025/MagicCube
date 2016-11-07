package com.mc.library.database.realm.wrapper;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmDouble extends RealmObject {
    private double value;

    public RealmDouble() {
    }

    public RealmDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

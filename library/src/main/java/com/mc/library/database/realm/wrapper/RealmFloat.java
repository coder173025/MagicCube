package com.mc.library.database.realm.wrapper;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmFloat extends RealmObject {
    private float value;

    public RealmFloat() {
    }

    public RealmFloat(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

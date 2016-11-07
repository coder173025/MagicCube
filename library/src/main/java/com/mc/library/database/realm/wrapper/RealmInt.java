package com.mc.library.database.realm.wrapper;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmInt extends RealmObject {
    private int value;

    public RealmInt() {
    }

    public RealmInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

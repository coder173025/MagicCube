package com.mc.library.database.realm.wrapper;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmShort extends RealmObject {
    private short value;

    public RealmShort() {
    }

    public RealmShort(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }
}

package com.mc.library.database.realm.wrapper;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmByteArray extends RealmObject {
    private byte[] value;

    public RealmByteArray() {
    }

    public RealmByteArray(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}

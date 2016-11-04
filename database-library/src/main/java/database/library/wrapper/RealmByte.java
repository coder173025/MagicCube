package database.library.wrapper;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmByte extends RealmObject {
    private byte value;

    public RealmByte() {
    }

    public RealmByte(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}

package database.library.wrapper;

import io.realm.RealmObject;

/**
 * Created by dinghui on 2016/10/24.
 */
public class RealmLong extends RealmObject {
    private long value;

    public RealmLong() {
    }

    public RealmLong(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

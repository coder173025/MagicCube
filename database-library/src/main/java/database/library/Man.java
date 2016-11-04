package database.library;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

/**
 * Created by dinghui on 2016/10/21.
 */
@RealmClass
public class Man implements RealmModel {
    public Woman wife;
    @Ignore
    public RealmList<Woman> wifes;
}

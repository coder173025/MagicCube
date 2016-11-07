package com.mc.library.database.realm;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by dinghui on 2016/10/21.
 */
@RealmClass
public class Woman implements RealmModel {
    public Man husband;
}

package com.mc.library.database.realm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmModel;

/**
 * Created by dinghui on 2016/10/20.
 */
public class DBHelper {
    public static Application sInstance;
    public static Realm sRealm;

    private DBHelper() {
        /* Cannot be instantiated! */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    /**
     * 初始化
     */
    public static void init(Context context) {
        if (sInstance == null) {
            if (context != null && context instanceof Application) {
                sInstance = (Application) context;
                /**
                 * 初始化Realm数据库
                 */
                Realm.init(context);
                Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
//                        .directory(context.getFilesDir())
//                        .name(Realm.DEFAULT_REALM_NAME)
//                        .encryptionKey(null)
                        .schemaVersion(4)
                        .migration(new RealmMigration() {
                            @Override
                            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                                Log.e("Realm: migrate", "数据库迁移@oldVersion: " + oldVersion + "@newVersion: " + newVersion);
                            }
                        })
                        .deleteRealmIfMigrationNeeded()
                        .build());
                Stetho.initialize(Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context)
//                                .withFolder(context.getCacheDir())
//                                .withEncryptionKey("encrypted.realm", "MagicCube".getBytes())
//                                .withMetaTables()
//                                .withDescendingOrder()
//                                .withLimit(1000)
//                                .databaseNamePattern(Pattern.compile(".+\\.realm"))
                                .build())
                        .build());
            } else {
                throw new IllegalArgumentException("context is null or doesn't Application Instance!");
            }
        } else {
            Log.e("DBHelper: init", "sInstance != null ==> 重复初始化！");
        }
    }

    /**
     * 增加或更新
     */
    public static <E extends RealmModel> E createOrUpdate(final E realmModel) {
        sRealm = Realm.getDefaultInstance();
        sRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                E person = sRealm.copyToRealmOrUpdate(realmModel);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.

            }
        });
        sRealm.close();
        return null;
    }

    /**
     * 查询
     */
    public static void retrieve() {

    }

    /**
     * 删除
     */
    public static void delete() {

    }
}

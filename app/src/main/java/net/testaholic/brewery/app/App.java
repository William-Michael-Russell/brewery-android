/**
 * Copyright (C) dbychkov.com.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.testaholic.brewery.app;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;

import net.testaholic.brewery.dagger.component.ApplicationComponent;
import net.testaholic.brewery.dagger.component.DaggerApplicationComponent;
import net.testaholic.brewery.dagger.module.ApplicationModule;
import net.testaholic.brewery.util.LogHelper;

/**
 * Class for maintaining global application state
 */
public class App extends Application {

    private static final String TAG = LogHelper.makeLogTag(App.class);

    private static App singleton;

    public static App getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        setUncaughtExceptionHandler();
        initApplicationComponent();
    }

    private ApplicationComponent applicationComponent;

    private void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder().applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                LogHelper.e(TAG, ex, "Exception escaped");
            }
        });
    }


    public String getDomainUrl() {
        ApplicationInfo ai = null;
        try {
            ai = singleton.getApplicationContext().getPackageManager().getApplicationInfo(singleton.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return (String) ai.metaData.get("breweryUrl");
    }

}

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

package net.testaholic.brewery.presentation;

import android.content.Context;
import android.preference.PreferenceManager;

import net.testaholic.brewery.data.LessonsImporter;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.view.SplashView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Presenter for {@link net.testaholic.brewery.activity.SplashActivity}
 */
public class SplashActivityPresenter extends PresenterBase {

    private static final String FIRST_LAUNCH_PREFERENCE_KEY = "first_launch_4";
    private static final int SPLASH_DISPLAY_LENGTH_MILLIS = 2000;

    private SplashView splashView;
    private LessonsImporter lessonsImporter;
    private Context context;

    @Inject
    public SplashActivityPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            LessonsImporter lessonImporter, Context context) {
        super(threadExecutor, postExecutionThread);
        this.lessonsImporter = lessonImporter;
        this.context = context;
    }

    public void setView(SplashView splashView) {
        this.splashView = splashView;
    }

    private boolean isFirstLaunch() {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(FIRST_LAUNCH_PREFERENCE_KEY, true);
    }

    private void setNotFirstLaunch(){
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(FIRST_LAUNCH_PREFERENCE_KEY, false)
                .commit();
    }

    public void initialize() {
        final boolean firstLaunch = isFirstLaunch();

        // Show "loading" only during lesson import
//        if (firstLaunch) {
            splashView.showLoading();
//        }

        execute(Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                    lessonsImporter.requestLessons();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        }), new DefaultSubscriber<Void>() {

            @Override
            public void onError(Throwable e) {
                splashView.renderImportError();
            }

            @Override
            public void onNext(Void v) {
                setNotFirstLaunch();
                splashView.renderSplashScreenEnded();
            }
        });

        // Fade in animation
        splashView.renderFancyAnimation();
    }

    private void sleep(){
        try {
            Thread.sleep(SPLASH_DISPLAY_LENGTH_MILLIS);
        } catch (Exception e) {
        }
    }

}

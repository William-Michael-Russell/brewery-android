/**
 * Copyright (C) dbychkov.
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

package net.testaholic.brewery.dagger.component;

import android.content.Context;

import net.testaholic.brewery.activity.BaseActivity;
import net.testaholic.brewery.bus.RxEventBus;
import net.testaholic.brewery.dagger.module.ApplicationModule;
import net.testaholic.brewery.data.LessonsImporter;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.domain.repository.LessonRepository;
import net.testaholic.brewery.navigator.Navigator;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.util.MarketService;
import net.testaholic.brewery.util.ShareService;
import net.testaholic.brewery.util.SpeechService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    Context getContext();

    LessonRepository provideLessonDataRepository();

    FlashcardRepository provideWordDataRepository();

    RxEventBus provideRxEventBus();

    MarketService provideMarketService();

    SpeechService provideSpeechService();

    ShareService provideShareService();

    LessonsImporter provideLessonsImporter();

    Navigator provideNavigator();
}

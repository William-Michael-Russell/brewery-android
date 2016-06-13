package net.testaholic.brewery.dagger.module;

import android.content.Context;

import net.testaholic.brewery.app.App;
import net.testaholic.brewery.bus.RxEventBus;
import net.testaholic.brewery.bus.RxEventBusImpl;
import net.testaholic.brewery.data.LessonsImporter;
import net.testaholic.brewery.data.LessonsImporterImpl;
import net.testaholic.brewery.data.greendao.DaoSession;
import net.testaholic.brewery.data.greendao.DataServiceFactory;
import net.testaholic.brewery.data.repository.FlashcardDataRepository;
import net.testaholic.brewery.data.repository.LessonDataRepository;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.domain.repository.LessonRepository;
import net.testaholic.brewery.navigator.Navigator;
import net.testaholic.brewery.thread.JobExecutor;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.PostExecutionUIThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.util.MarketService;
import net.testaholic.brewery.util.MarketServiceImpl;
import net.testaholic.brewery.util.ShareService;
import net.testaholic.brewery.util.ShareServiceImpl;
import net.testaholic.brewery.util.SpeechService;
import net.testaholic.brewery.util.SpeechServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(PostExecutionUIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    DaoSession providesDaoSession() {
        return DataServiceFactory.openSession(app.getApplicationContext());
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.app;
    }

    @Provides
    @Singleton
    LessonRepository provideLessonDataRepository(LessonDataRepository lessonDataRepository) {
        return lessonDataRepository;
    }

    @Provides
    @Singleton
    FlashcardRepository provideWordDataRepository(FlashcardDataRepository wordDataRepository) {
        return wordDataRepository;
    }

    @Provides
    @Singleton
    RxEventBus provideRxEventBus(RxEventBusImpl rxEventBus) {
        return rxEventBus;
    }

    @Provides
    @Singleton
    MarketService provideMarketService(MarketServiceImpl marketService) {
        return marketService;
    }

    @Provides
    @Singleton
    SpeechService provideSpeechService(SpeechServiceImpl speechService) {
        return speechService;
    }

    @Provides
    @Singleton
    ShareService provideShareService(ShareServiceImpl shareService) {
        return shareService;
    }

    @Provides
    @Singleton
    LessonsImporter provideLessonsImporter(LessonsImporterImpl lessonsImporter) {
        return lessonsImporter;
    }
}

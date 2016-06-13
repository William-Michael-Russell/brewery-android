package net.testaholic.brewery.dagger.module;

import android.app.Activity;

import net.testaholic.brewery.bus.RxEventBus;
import net.testaholic.brewery.dagger.PerActivity;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.domain.repository.LessonRepository;
import net.testaholic.brewery.presentation.BookmarkedLessonsTabFragmentPresenter;
import net.testaholic.brewery.presentation.BundledLessonsTabFragmentPresenter;
import net.testaholic.brewery.presentation.EditFlashcardsActivityPresenter;
import net.testaholic.brewery.presentation.UserLessonsTabFragmentPresenter;
import net.testaholic.brewery.presentation.ViewFlashcardsActivityPresenter;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.util.SpeechService;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return activity;
    }

    @Provides
    @PerActivity
    UserLessonsTabFragmentPresenter userLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus) {
        return new UserLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository, rxEventBus);
    }

    @Provides
    @PerActivity
    BundledLessonsTabFragmentPresenter bundledLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus) {
        return new BundledLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository,
                rxEventBus);
    }

    @Provides
    @PerActivity
    BookmarkedLessonsTabFragmentPresenter bookmarkedLessonsTabFragmentPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, LessonRepository lessonRepository, RxEventBus rxEventBus) {
        return new BookmarkedLessonsTabFragmentPresenter(threadExecutor, postExecutionThread, lessonRepository,
                rxEventBus);
    }

    @Provides
    @PerActivity
    ViewFlashcardsActivityPresenter viewFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        return new ViewFlashcardsActivityPresenter(threadExecutor, postExecutionThread, flashcardRepository,
                speechService);
    }

    @Provides
    @PerActivity
    EditFlashcardsActivityPresenter editFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        return new EditFlashcardsActivityPresenter(threadExecutor, postExecutionThread, flashcardRepository,
                speechService);
    }

}
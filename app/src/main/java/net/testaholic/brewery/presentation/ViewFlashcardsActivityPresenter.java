package net.testaholic.brewery.presentation;

import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.util.SpeechService;
import net.testaholic.brewery.view.ViewFlashcardsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter for {@link net.testaholic.brewery.activity.ViewFlashcardsActivity}
 */
public class ViewFlashcardsActivityPresenter extends FlashcardsActivityPresenter {

    private ViewFlashcardsView viewFlashcardsView;

    @Inject
    public ViewFlashcardsActivityPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            FlashcardRepository flashcardRepository, SpeechService speechService) {
        super(threadExecutor, postExecutionThread, flashcardRepository, speechService);
    }

    public void setView(ViewFlashcardsView viewFlashcardsView) {
        super.setView(viewFlashcardsView);
        this.viewFlashcardsView = viewFlashcardsView;
    }

    @Override
    public void showFlashCards(List<Ingredients> ingredientses) {
        viewFlashcardsView.renderFlashcards(ingredientses);
    }

}

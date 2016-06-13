package net.testaholic.brewery.presentation;

import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.util.SpeechService;
import net.testaholic.brewery.view.EditFlashcardsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter for {@link net.testaholic.brewery.activity.EditFlashcardsActivity}
 */
public class EditFlashcardsActivityPresenter extends FlashcardsActivityPresenter {

    private EditFlashcardsView editFlashcardsView;

    @Inject
    public EditFlashcardsActivityPresenter(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, FlashcardRepository flashcardRepository,
            SpeechService speechService) {
        super(threadExecutor, postExecutionThread, flashcardRepository, speechService);
    }

    public void setView(EditFlashcardsView editFlashcardsView) {
        super.setView(editFlashcardsView);
        this.editFlashcardsView = editFlashcardsView;
    }

    @Override
    public void showFlashCards(List<Ingredients> ingredientses) {
        editFlashcardsView.renderFlashcards(ingredientses);
    }

    public void removeFlashcard(final Ingredients ingredients, final int position) {
        execute(flashcardRepository.removeFlashcard(ingredients.getId()), new DefaultSubscriber<Void>() {

            @Override
            public void onNext(Void v) {
                editFlashcardsView.renderFlashcardRemoved(ingredients, position);
                updateProgress(ingredients);
            }
        });
    }

    private void updateProgress(final Ingredients ingredients){
        execute(flashcardRepository.getFlashcardsFromLesson(ingredients.getLessonId()),
                new DefaultSubscriber<List<Ingredients>>() {
                    @Override
                    public void onNext(List<Ingredients> ingredientses) {
                        showProgress(getProgressForWordList(ingredientses));
                    }
                });
    }

    public void onFlashcardRemoveClicked(Ingredients ingredients, int position) {
        editFlashcardsView.renderFlashcardRemovalSnackBar(ingredients, position);
    }

    public void createFlashcardsClicked(){
        execute(flashcardRepository.addFlashcard(createNewFlashcard()), new DefaultSubscriber<Ingredients>() {
            @Override
            public void onNext(Ingredients ingredients) {
                editFlashcardsView.renderCreatedFlashcard(ingredients);
                updateProgress(ingredients);
            }
        });
    }

    private Ingredients createNewFlashcard(){
        Ingredients ingredients = new Ingredients();
        ingredients.setLessonId(lessonId);
        ingredients.setIngredients("Ingredients");
        ingredients.setDefinition("Definition");
        return ingredients;
    }

    public void onFlashcardEditClicked(Ingredients ingredients, int position) {
        editFlashcardsView.renderEditFlashcardDialog(ingredients, position);
    }
}

package net.testaholic.brewery.view;

import net.testaholic.brewery.domain.Ingredients;

/**
 * View capable of rendering list of flashcards and lesson progress
 */
public interface EditFlashcardsView extends FlashcardsView {

    void renderFlashcardRemoved(Ingredients ingredients, int position);

    void renderFlashcardRemovalSnackBar(Ingredients ingredients, final int position);

    void renderEditFlashcardDialog(Ingredients ingredients, final int position);

    void renderCreatedFlashcard(Ingredients insertedIngredients);

}

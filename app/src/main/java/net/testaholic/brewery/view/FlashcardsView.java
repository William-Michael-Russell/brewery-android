package net.testaholic.brewery.view;

import net.testaholic.brewery.domain.Ingredients;

import java.util.List;

/**
 * Flashcards view
 */
public interface FlashcardsView {

    void renderFlashcards(List<Ingredients> ingredientsList);

    void renderProgress(Integer renderProgress);
}

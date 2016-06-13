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

import android.os.Handler;

import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.view.StudyFlashcardsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter for {@link net.testaholic.brewery.activity.StudyFlashcardsActivity}
 */
public class StudyFlashcardsActivityPresenter extends PresenterBase {

    public static final int DELAY_MILLIS = 1000;

    private FlashcardRepository flashcardRepository;
    private StudyFlashcardsView studyFlashcardsView;
    private Long lessonId;
    private List<Ingredients> unlearntIngredientses;
    private int currentPosition = 0;

    @Inject
    public StudyFlashcardsActivityPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            FlashcardRepository flashcardRepository) {
        super(threadExecutor, postExecutionThread);
        this.flashcardRepository = flashcardRepository;
    }

    public void setView(StudyFlashcardsView studyFlashcardsView) {
        this.studyFlashcardsView = studyFlashcardsView;
    }

    public void initialize(Long lessonId) {
        this.lessonId = lessonId;
        initWords();
    }

    private void initWords() {
        execute(flashcardRepository.getUnlearntFlashcardsFromLesson(lessonId),
                new DefaultSubscriber<List<Ingredients>>() {

                    @Override
                    public void onNext(List<Ingredients> unlearntIngredientses) {
                        StudyFlashcardsActivityPresenter.this.currentPosition = 0;
                        StudyFlashcardsActivityPresenter.this.unlearntIngredientses = unlearntIngredientses;
                        if (unlearntIngredientses.isEmpty()) {
                            showAllWordsAreLearntDialog();
                        } else {
                            showFlashcards(unlearntIngredientses);
                        }
                    }
                });
    }

    /**
     * Update "learnt" status of current flashcard and proceed to the next one
     */
    public void knowWordButtonPressed() {
        Ingredients currentIngredients = unlearntIngredientses.get(currentPosition);
        currentIngredients.setStatus(1);
        execute(flashcardRepository.updateFlashcard(currentIngredients), new DefaultSubscriber<List<Ingredients>>() {
            @Override
            public void onNext(List<Ingredients> ingredientses) {
                if (isLastFlashcard()) {
                    showLessonEndedDialog();
                } else {
                    showNextFlashcardImmediately();
                }
            }
        });
    }

    private boolean isLastFlashcard(){
        return currentPosition + 1 >= unlearntIngredientses.size();
    }

    /**
     * Show back of flashcard and proceed to the next flashcard
     */
    public void dontKnowWordButtonPressed() {
        boolean flashcardWasFlipped = studyFlashcardsView.showCardBack(currentPosition);
        boolean lastFlashcard = isLastFlashcard();
        if (flashcardWasFlipped) {
            showLessonEnded(lastFlashcard);
        } else {
            showLessonEndedDialog(lastFlashcard);
        }
    }

    private void showLessonEnded(boolean delayRequired){
        if (delayRequired) {
            showLessonEndedDialogWithDelay();
        } else {
            showNextFlashcardWithADelay();
        }
    }

    private void showLessonEndedDialog(boolean delayRequired){
        if (delayRequired) {
            showLessonEndedDialog();
        } else {
            showNextFlashcardImmediately();
        }
    }

    private void showNextFlashcardImmediately() {
        studyFlashcardsView.showFlashcard(++currentPosition);
    }

    private void showNextFlashcardWithADelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNextFlashcardImmediately();
            }
        }, DELAY_MILLIS);
    }

    private void showFlashcards(List<Ingredients> ingredientses) {
        studyFlashcardsView.renderFlashcards(ingredientses);
        studyFlashcardsView.showFlashcard(0);
    }

    private void showLessonEndedDialog() {
        studyFlashcardsView.showLessonEndedDialog();
    }

    private void showLessonEndedDialogWithDelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLessonEndedDialog();
            }
        }, DELAY_MILLIS);
    }

    private void showAllWordsAreLearntDialog() {
        studyFlashcardsView.showAllWordsLearntDialog();
    }

}

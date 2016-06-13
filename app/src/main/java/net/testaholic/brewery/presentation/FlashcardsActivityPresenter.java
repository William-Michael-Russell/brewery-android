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

import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;
import net.testaholic.brewery.util.SpeechService;
import net.testaholic.brewery.view.FlashcardsView;

import java.util.List;

/**
 * Base presenter for rendering list of flashcards for particular lesson
 */
public abstract class FlashcardsActivityPresenter extends PresenterBase {

    protected FlashcardRepository flashcardRepository;
    protected Long lessonId;
    private SpeechService speechService;
    private FlashcardsView flashcardsView;

    public FlashcardsActivityPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            FlashcardRepository flashcardRepository, SpeechService speechService) {
        super(threadExecutor, postExecutionThread);
        this.flashcardRepository = flashcardRepository;
        this.speechService = speechService;
    }

    public void initialize(Long lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public void resume() {
        initialize();
    }

    public void initialize() {
        execute(flashcardRepository.getFlashcardsFromLesson(lessonId), new DefaultSubscriber<List<Ingredients>>() {
            @Override
            public void onNext(List<Ingredients> ingredientses) {
                FlashcardsActivityPresenter.this.showFlashCards(ingredientses);
                FlashcardsActivityPresenter.this.showProgress(getProgressForWordList(ingredientses));
            }
        });
    }

    public void setView(FlashcardsView flashcardsView) {
        this.flashcardsView = flashcardsView;
    }

    public void showProgress(Integer progress) {
        flashcardsView.renderProgress(progress);
    }

    abstract void showFlashCards(List<Ingredients> ingredientses);

    public void clearProgressClicked() {
        execute(flashcardRepository.clearProgressForLesson(lessonId), new DefaultSubscriber<Void>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
                initialize();
            }
        });
    }

    protected Integer getProgressForWordList(List<Ingredients> ingredientsList) {
        int wordsLearnt = 0;
        for (Ingredients ingredients : ingredientsList) {
            wordsLearnt += ingredients.getStatus();
        }
        int totalWords = ingredientsList.size();
        return totalWords == 0 ? 0 : wordsLearnt * 100 / totalWords;
    }

    public void flashcardModified(Ingredients ingredients) {
        execute(flashcardRepository.updateFlashcard(ingredients), new DefaultSubscriber<Void>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
                initialize();
            }
        });
    }

    public void onSpeakIconClicked(String word) {
        speechService.speak(word);
    }

}

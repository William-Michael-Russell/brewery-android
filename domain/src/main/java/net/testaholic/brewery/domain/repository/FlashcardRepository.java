/*
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

package net.testaholic.brewery.domain.repository;

import net.testaholic.brewery.domain.Ingredients;

import java.util.List;

import rx.Observable;

/**
 * Repository for getting {@link Ingredients} related data
 */
public interface FlashcardRepository {

    /**
     * Get an {@link Observable} which will notify the addition of a {@link Ingredients} object
     */
    Observable<Ingredients> addFlashcard(Ingredients ingredients);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link Ingredients} object
     */
    Observable<Void> removeFlashcard(Long flashcardId);

    /**
     * Get an {@link Observable} which will notify the bulk insertion of {@link Ingredients} objects
     */
    Observable<Void> bulkInsertFlashcards(List<Ingredients> ingredientsList);

    /**
     * Get an {@link Observable} which will emit a {@link Ingredients} object by id
     */
    Observable<Ingredients> getFlashcardById(Long flashcardId);

    /**
     * Get an {@link Observable} which will notify the edition of {@link Ingredients} object
     */
    Observable<Void> updateFlashcard(Ingredients ingredients);

    /**
     * Get an {@link Observable} which will emit a List of all {@link Ingredients} objects
     */
    Observable<List<Ingredients>> getAllFlashcards();

    /**
     * Get an {@link Observable} which will emit a List of all {@link Ingredients} objects for lesson with particular id
     */
    Observable<List<Ingredients>> getFlashcardsFromLesson(Long lessonId);

    /**
     * Get an {@link Observable} which will emit a List of unlearnt {@link Ingredients} objects for lesson with particular id
     */
    Observable<List<Ingredients>> getUnlearntFlashcardsFromLesson(Long lessonId);

    /**
     * Get an {@link Observable} which will notify the removal of progress for all {@link Ingredients} objects of particular lesson
     */
    Observable<Void> clearProgressForLesson(Long lessonId);
}

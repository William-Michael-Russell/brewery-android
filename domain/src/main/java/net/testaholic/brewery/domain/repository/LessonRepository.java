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

import net.testaholic.brewery.domain.Drink;

import java.util.List;

import rx.Observable;

/**
 * Repository for getting {@link Drink} related data
 */
public interface LessonRepository {

    /**
     * Get an {@link Observable} which will emit List of all {@link Drink}
     */
    Observable<List<Drink>> getAllLessons();

    /**
     * Get an {@link Observable} which will emit List of bundled {@link Drink}
     */
    Observable<List<Drink>> getBundledLessons();

    /**
     * Get an {@link Observable} which will emit List of {@link Drink} created by user
     */
    Observable<List<Drink>> getUserLessons();

    /**
     * Get an {@link Observable} which will emit List of {@link Drink} bookmarked by user
     */
    Observable<List<Drink>> getBookmarkedLessons();

    /**
     * Get an {@link Observable} which will notify the modification of bookmarked state of {@link Drink} object
     */
    Observable<Boolean> bookmarkLesson(Long lessonId);

    /**
     * Get an {@link Observable} which will notify the addition of a {@link Drink} object
     */
    Observable<Drink> addLesson(Drink drink);

    /**
     * Get an {@link Observable} which will emit a {@link Drink} by lesson id
     */
    Observable<Drink> getLessonById(final Long lessonId);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link Drink} object by lesson id
     */
    Observable<Void> removeLessonById(final Long lessonId);

    /**
     * Get an {@link Observable} which will notify the bulk insertion of {@link Drink} objects
     */
    Observable<Void> bulkInsertLessons(final List<Drink> drinkList);

}

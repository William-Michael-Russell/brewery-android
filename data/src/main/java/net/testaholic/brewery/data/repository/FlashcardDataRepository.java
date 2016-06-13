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

package net.testaholic.brewery.data.repository;

import net.testaholic.brewery.data.datastore.FlashcardDataStore;
import net.testaholic.brewery.data.datastore.FlashcardDataStoreFactory;
import net.testaholic.brewery.data.greendao.FlashcardEntity;
import net.testaholic.brewery.data.mapper.FlashcardEntityDataMapper;
import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.repository.FlashcardRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link FlashcardRepository} for managing flashcards data
 */
@Singleton
public class FlashcardDataRepository implements FlashcardRepository {

    private final FlashcardDataStoreFactory wordDataStoreFactory;
    private final FlashcardEntityDataMapper flashcardEntityDataMapper;

    @Inject
    public FlashcardDataRepository(FlashcardDataStoreFactory wordDataStoreFactory,
                                   FlashcardEntityDataMapper flashcardEntityDataMapper) {
        this.wordDataStoreFactory = wordDataStoreFactory;
        this.flashcardEntityDataMapper = flashcardEntityDataMapper;
    }

    private FlashcardDataStore dataStore() {
        return wordDataStoreFactory.createDatabaseDataStore();
    }

    @Override
    public Observable<Ingredients> addFlashcard(Ingredients ingredients) {
        return dataStore().addFlashcard(flashcardEntityDataMapper.mapPojoToEntity(ingredients)).map(new Func1<FlashcardEntity, Ingredients>() {
            @Override
            public Ingredients call(FlashcardEntity flashcardEntity) {
                return flashcardEntityDataMapper.mapEntityToPojo(flashcardEntity);
            }
        });
    }

    @Override
    public Observable<Void> removeFlashcard(Long flashcardId) {
        return dataStore().removeFlashcard(flashcardId);
    }

    @Override
    public Observable<Void> bulkInsertFlashcards(List<Ingredients> ingredientsList) {
        return dataStore().bulkInsertFlashcards(flashcardEntityDataMapper.mapPojoListToEntityList(ingredientsList));
    }

    @Override
    public Observable<Ingredients> getFlashcardById(Long flashcardId) {
        return dataStore().getFlashcardById(flashcardId).map(new Func1<FlashcardEntity, Ingredients>() {
            @Override
            public Ingredients call(FlashcardEntity flashcardEntity) {
                return flashcardEntityDataMapper.mapEntityToPojo(flashcardEntity);
            }
        });
    }

    @Override
    public Observable<Void> updateFlashcard(Ingredients ingredients) {
        return dataStore().updateFlashcard(flashcardEntityDataMapper.mapPojoToEntity(ingredients));
    }

    @Override
    public Observable<List<Ingredients>> getAllFlashcards() {
        return dataStore().getAllFlashcards().map(new Func1<List<FlashcardEntity>, List<Ingredients>>() {
            @Override
            public List<Ingredients> call(List<FlashcardEntity> flashcardEntityList) {
                return flashcardEntityDataMapper.mapEntityListToPojoList(flashcardEntityList);
            }
        });
    }

    @Override
    public Observable<List<Ingredients>> getFlashcardsFromLesson(Long lessonId) {
        return dataStore().getFlashcardsFromLesson(lessonId).map(new Func1<List<FlashcardEntity>, List<Ingredients>>() {
            @Override
            public List<Ingredients> call(List<FlashcardEntity> flashcardEntityList) {
                return flashcardEntityDataMapper.mapEntityListToPojoList(flashcardEntityList);
            }
        });
    }

    @Override
    public Observable<List<Ingredients>> getUnlearntFlashcardsFromLesson(Long lessonId) {
        return dataStore().getUnlearntFlashcardsFromLesson(lessonId).map(new Func1<List<FlashcardEntity>, List<Ingredients>>() {
            @Override
            public List<Ingredients> call(List<FlashcardEntity> flashcardEntityList) {
                return flashcardEntityDataMapper.mapEntityListToPojoList(flashcardEntityList);
            }
        });
    }

    @Override
    public Observable<Void> clearProgressForLesson(Long lessonId) {
        return dataStore().clearProgressForLesson(lessonId);
    }
}

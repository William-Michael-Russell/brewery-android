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

import net.testaholic.brewery.data.datastore.LessonDataStore;
import net.testaholic.brewery.data.datastore.LessonDataStoreFactory;
import net.testaholic.brewery.data.greendao.LessonEntity;
import net.testaholic.brewery.data.mapper.LessonEntityDataMapper;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.domain.repository.LessonRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link LessonRepository} for managing lessons data
 */
@Singleton
public class LessonDataRepository implements LessonRepository {

    private final LessonDataStoreFactory lessonDataStoreFactory;
    private final LessonEntityDataMapper lessonEntityDataMapper;

    @Inject
    public LessonDataRepository(LessonDataStoreFactory lessonDataStoreFactory,
            LessonEntityDataMapper lessonEntityDataMapper) {
        this.lessonDataStoreFactory = lessonDataStoreFactory;
        this.lessonEntityDataMapper = lessonEntityDataMapper;
    }

    private LessonDataStore dataStore(){
        return lessonDataStoreFactory.createDatabaseDataStore();
    }

    @Override
    public Observable<List<Drink>> getAllLessons() {
        return dataStore().getAllLessons().map(new Func1<List<LessonEntity>, List<Drink>>() {
            @Override
            public List<Drink> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<List<Drink>> getBundledLessons() {
        return dataStore().getBundledLessons().map(new Func1<List<LessonEntity>, List<Drink>>() {
            @Override
            public List<Drink> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<List<Drink>> getUserLessons() {
        return dataStore().getUserLessons().map(new Func1<List<LessonEntity>, List<Drink>>() {
            @Override
            public List<Drink> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<List<Drink>> getBookmarkedLessons() {
        return dataStore().getBookmarkedLessons().map(new Func1<List<LessonEntity>, List<Drink>>() {
            @Override
            public List<Drink> call(List<LessonEntity> lessonEntityList) {
                return lessonEntityDataMapper.mapEntityListToPojoList(lessonEntityList);
            }
        });
    }

    @Override
    public Observable<Boolean> bookmarkLesson(Long lessonEntityId) {
        return dataStore().bookmarkLesson(lessonEntityId);
    }

    @Override
    public Observable<Drink> addLesson(Drink drink) {
        return dataStore().addLesson(lessonEntityDataMapper.mapPojoToEntity(drink)).map(new Func1<LessonEntity, Drink>() {
            @Override
            public Drink call(LessonEntity lessonEntity) {
                return lessonEntityDataMapper.mapEntityToPojo(lessonEntity);
            }
        });
    }

    @Override
    public Observable<Drink> getLessonById(Long lessonEntityId) {
        return dataStore().getLessonById(lessonEntityId).map(new Func1<LessonEntity, Drink>() {
            @Override
            public Drink call(LessonEntity lessonEntity) {
                return lessonEntityDataMapper.mapEntityToPojo(lessonEntity);
            }
        });
    }

    @Override
    public Observable<Void> removeLessonById(Long lessonEntityId) {
        return dataStore().removeLessonById(lessonEntityId);
    }

    @Override
    public Observable<Void> bulkInsertLessons(List<Drink> drinkEntityList) {
        return dataStore().bulkInsertLessons(lessonEntityDataMapper.mapPojoListToEntityList(drinkEntityList));
    }
}

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

package net.testaholic.brewery.data.mapper;

import net.testaholic.brewery.data.greendao.LessonEntity;
import net.testaholic.brewery.domain.Drink;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link LessonEntity} in the data layer to {@link Drink} in
 * the domain layer and vice versa
 */
@Singleton
public class LessonEntityDataMapper extends AbstractMapper<LessonEntity, Drink> {

    @Inject
    public LessonEntityDataMapper() {
    }

    @Override
    public LessonEntity mapPojoToEntity(Drink drink) {
        LessonEntity lessonEntity = null;
        if (drink != null) {
            lessonEntity = new LessonEntity();
            lessonEntity.setId(drink.getId());
            lessonEntity.setBookmarked(drink.isFavorite());
            lessonEntity.setImagePath(drink.getDrinkImageUrl());
            lessonEntity.setLessonName(drink.getDrinkName());
            lessonEntity.setUserLesson(drink.isUserLesson());
        }
        return lessonEntity;
    }

    @Override
    public Drink mapEntityToPojo(LessonEntity lessonEntity) {
        Drink drink = null;
        if (lessonEntity != null) {
            drink = new Drink();
            drink.setId(lessonEntity.getId());
            drink.setFavorite(lessonEntity.getBookmarked());
            drink.setDrinkImageUrl(lessonEntity.getImagePath());
            drink.setDrinkName(lessonEntity.getLessonName());
            drink.setUserLesson(lessonEntity.getUserLesson());
        }
        return drink;
    }
}

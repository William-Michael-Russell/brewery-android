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

import net.testaholic.brewery.data.greendao.FlashcardEntity;
import net.testaholic.brewery.domain.Ingredients;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link FlashcardEntity} in the data layer to {@link Ingredients} in
 * the domain layer and vice versa
 */
@Singleton
public class FlashcardEntityDataMapper extends AbstractMapper<FlashcardEntity, Ingredients> {

    @Inject
    public FlashcardEntityDataMapper() {
    }

    @Override
    public FlashcardEntity mapPojoToEntity(Ingredients ingredients) {
        FlashcardEntity flashcardEntity = null;
        if (ingredients != null) {
            flashcardEntity = new FlashcardEntity();
            flashcardEntity.setId(ingredients.getId());
            flashcardEntity.setDefinition(ingredients.getDefinition());
            flashcardEntity.setLessonId(ingredients.getLessonId());
            flashcardEntity.setStatus(ingredients.getStatus());
            flashcardEntity.setWord(ingredients.getIngredients());
        }
        return flashcardEntity;
    }

    @Override
    public Ingredients mapEntityToPojo(FlashcardEntity flashcardEntity) {
        Ingredients ingredients = null;
        if (flashcardEntity != null) {
            ingredients = new Ingredients();
            ingredients.setId(flashcardEntity.getId());
            ingredients.setDefinition(flashcardEntity.getDefinition());
            ingredients.setLessonId(flashcardEntity.getLessonId());
            ingredients.setStatus(flashcardEntity.getStatus());
            ingredients.setIngredients(flashcardEntity.getWord());
        }
        return ingredients;
    }
}

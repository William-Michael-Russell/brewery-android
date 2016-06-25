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

package net.testaholic.brewery.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.testaholic.brewery.activity.AbstractExpandingActivity;
import net.testaholic.brewery.app.App;
import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.domain.repository.FlashcardRepository;
import net.testaholic.brewery.domain.repository.LessonRepository;
import net.testaholic.brewery.rest.RestClient;
import net.testaholic.brewery.util.AssetUtils;
import net.testaholic.brewery.util.LogHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Importer service. Provides initial initialization of database from json file
 */
@Singleton
public class LessonsImporterImpl implements LessonsImporter {
    private static final String TAG = LogHelper.makeLogTag(LessonsImporterImpl.class);

    private final FlashcardRepository flashcardRepository;
    private final LessonRepository lessonRepository;
    private final Context context;

    @Inject
    public LessonsImporterImpl(FlashcardRepository flashcardRepository, LessonRepository lessonRepository,
                               Context context) {
        this.flashcardRepository = flashcardRepository;
        this.lessonRepository = lessonRepository;
        this.context = context;
    }

    @Override
    public void requestLessons() {
        final List<Drink> drinksForBulkInsert = new ArrayList<>();
        final List<Ingredients> wordsForBulkInsert = new ArrayList<>();
        LogHelper.d(TAG,"--- New Request ---");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Drink> drinks = null;
                try {
                    drinks = RestClient.getBreweryService().listDrinks().execute().body();
                    LogHelper.d(TAG,"Executing drink request");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (drinks != null) {
                    for (Drink drink : drinks) {

                        Drink drinkEntity = new Drink();
                        drinkEntity.setDrinkName(drink.getDrinkName());
                        drinkEntity.setUserLesson(false);
                        drinkEntity.setId(Long.valueOf(drink.getId()));
                        drinkEntity.setDrinkImageUrl(drink.getDrinkImageUrl());
                        drinkEntity.setDrinkIngredients(drink.getDrinkIngredients());
                        drinksForBulkInsert.add(drinkEntity);

                        for (String s : drink.getDrinkIngredients().split("\n")) {
                            Ingredients ingredientsEntry = new Ingredients();

                            String[] s1 = s.split("oz");
                            if (s1.length == 2) {
                                ingredientsEntry.setDefinition(s1[0]);
                                ingredientsEntry.setIngredients(s1[1] + "OZ");
                                ingredientsEntry.setDrink(drink);
                                ingredientsEntry.setLessonId(drink.getId());
                                ingredientsEntry.setStatus(0);
                                wordsForBulkInsert.add(ingredientsEntry);
                            }
                        }
                    }
                } else {
//                    Toast.makeText(App.getInstance().getApplicationContext(), "Unable to get drink list", Toast.LENGTH_SHORT).show();
                }
                //TODO implement pull to refresh with callable retrofit
            }
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lessonRepository.bulkInsertLessons(drinksForBulkInsert).subscribe();
        flashcardRepository.bulkInsertFlashcards(wordsForBulkInsert).subscribe();
    }
//
//    private static class RootInFile {
//        public List<LessonInFile> lessons;
//    }
//
//    private static class LessonInFile {
//        public int drinkId;
//        public String drinkName;
//        public String drinkImageUrl;
//        public String flashcards;
//        public String drinkIngredients;
//    }

}


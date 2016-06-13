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

package net.testaholic.brewery.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents a lesson consisting of a number of flashcards
 */
public class Drink {

    @SerializedName("id")
    private Long id;
    @SerializedName("drinkName")
    private String drinkName;
    private boolean userLesson;

    @SerializedName("drinkImageUrl")
    private String drinkImageUrl;
    private boolean favorite;

    @SerializedName("drinkIngredients")
    private String drinkIngredients;


    private List<Ingredients> ingredientsList;

    public Drink() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public boolean isUserLesson() {
        return userLesson;
    }

    public void setUserLesson(boolean userLesson) {
        this.userLesson = userLesson;
    }

    public String getDrinkIngredients() {
        return drinkIngredients;
    }

    public void setDrinkIngredients(String drinkIngredients) {
        this.drinkIngredients = drinkIngredients;
    }

    public String getDrinkImageUrl() {
        return drinkImageUrl;
    }

    public void setDrinkImageUrl(String drinkImageUrl) {
        this.drinkImageUrl = drinkImageUrl;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }


    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", drinkName='" + drinkName + '\'' +
                ", userLesson=" + userLesson +
                ", drinkImageUrl='" + drinkImageUrl + '\'' +
                ", favorite=" + favorite +
                ", drinkIngredients='" + drinkIngredients + '\'' +
                ", ingredientsList=" + ingredientsList +
                '}';
    }
}

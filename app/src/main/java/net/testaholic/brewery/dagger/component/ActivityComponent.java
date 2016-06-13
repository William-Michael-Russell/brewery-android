/**
 * Copyright (C) dbychkov.
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

package net.testaholic.brewery.dagger.component;

import android.app.Activity;

import net.testaholic.brewery.activity.EditFlashcardsActivity;
import net.testaholic.brewery.activity.DrinkCatalogActivity;
import net.testaholic.brewery.activity.SplashActivity;
import net.testaholic.brewery.activity.StudyFlashcardsActivity;
import net.testaholic.brewery.activity.ViewFlashcardsActivity;
import net.testaholic.brewery.adapter.ViewFlashcardsAdapter;
import net.testaholic.brewery.dagger.PerActivity;
import net.testaholic.brewery.dagger.module.ActivityModule;
import net.testaholic.brewery.fragment.BookmarkedLessonsTabFragment;
import net.testaholic.brewery.fragment.BundledLessonsTabFragment;
import net.testaholic.brewery.fragment.CardContainerFragment;
import net.testaholic.brewery.fragment.CardFragment;
import net.testaholic.brewery.fragment.UserLessonsTabFragment;
import net.testaholic.brewery.presentation.BookmarkedLessonsTabFragmentPresenter;
import net.testaholic.brewery.presentation.BundledLessonsTabFragmentPresenter;
import net.testaholic.brewery.presentation.EditFlashcardsActivityPresenter;
import net.testaholic.brewery.presentation.UserLessonsTabFragmentPresenter;
import net.testaholic.brewery.presentation.ViewFlashcardsActivityPresenter;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BundledLessonsTabFragment bundledLessonsTabFragment);

    void inject(BookmarkedLessonsTabFragment bookmarkedLessonsTabFragment);

    void inject(UserLessonsTabFragment userLessonsTabFragment);

    void inject(CardContainerFragment cardContainerFragment);

    void inject(CardFragment cardFragment);

    void inject(DrinkCatalogActivity drinkCatalogActivity);

    void inject(StudyFlashcardsActivity studyFlashcardsActivity);

    void inject(EditFlashcardsActivity editFlashcardsActivity);

    void inject(ViewFlashcardsActivity viewFlashcardsActivity);

    void inject(ViewFlashcardsAdapter viewFlashcardsAdapter);

    void inject(SplashActivity splashActivity);

    Activity activity();

    UserLessonsTabFragmentPresenter userLessonsTabFragmentPresenter();

    BundledLessonsTabFragmentPresenter bundledLessonsTabFragmentPresenter();

    BookmarkedLessonsTabFragmentPresenter bookmarkedLessonsTabFragmentPresenter();

    ViewFlashcardsActivityPresenter viewFlashcardsActivityPresenter();

    EditFlashcardsActivityPresenter editFlashcardsActivityPresenter();
}
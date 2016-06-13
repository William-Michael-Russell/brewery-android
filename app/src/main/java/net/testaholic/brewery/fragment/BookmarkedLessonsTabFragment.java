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

package net.testaholic.brewery.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import net.testaholic.brewery.R;
import net.testaholic.brewery.adapter.BookmarkedDrinksAdapter;
import net.testaholic.brewery.adapter.DrinksAdapter;
import net.testaholic.brewery.app.App;
import net.testaholic.brewery.dagger.component.ActivityComponent;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.presentation.BookmarkedLessonsTabFragmentPresenter;
import net.testaholic.brewery.presentation.LessonsPresenter;
import net.testaholic.brewery.view.RenderLessonsView;

import javax.inject.Inject;

import butterknife.BindString;

/**
 * Tab with bookmarked drinks
 */
public class BookmarkedLessonsTabFragment extends LessonsTabFragment implements RenderLessonsView {

    @BindString(R.string.noBookmarkedLessons) String emptyMessage;

    @Inject
    BookmarkedLessonsTabFragmentPresenter presenter;

    @Inject
    BookmarkedDrinksAdapter adapter;

    public static Fragment newInstance() {

        return new BookmarkedLessonsTabFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public DrinksAdapter getLessonsAdapter() {
        return adapter;
    }

    @Override
    public LessonsPresenter getLessonsPresenter() {
        return presenter;
    }

    @Override
    public String getEmptyMessage() {
        return emptyMessage;
    }

    @Override
    public void renderCreatedLesson(Drink drink) {
        throw new UnsupportedOperationException("Drink creation is not supported for \"Bookmarked drinks\" tab");
    }

    @Override
    public void renderLessonItemRemoved(int position) {
        throw new UnsupportedOperationException("Removing drinks is not supported for \"Bookmarked drinks\" tab");
    }

    @Override
    public void renderLessonItemBookmarked(int position, boolean bookmarked) {
        adapter.removeItem(position);
    }
}

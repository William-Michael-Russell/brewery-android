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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.testaholic.brewery.R;
import net.testaholic.brewery.adapter.DrinksAdapter;
import net.testaholic.brewery.adapter.UserDrinksAdapter;
import net.testaholic.brewery.app.App;
import net.testaholic.brewery.dagger.component.ActivityComponent;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.navigator.Navigator;
import net.testaholic.brewery.presentation.LessonsPresenter;
import net.testaholic.brewery.presentation.UserLessonsTabFragmentPresenter;
import net.testaholic.brewery.view.RenderLessonsView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;

/**
 * Tab with drinks created by user
 */
public class UserLessonsTabFragment extends LessonsTabFragment implements View.OnClickListener, RenderLessonsView {

    public static final int DELAY_MILLIS = 400;

    @Inject
    Navigator navigator;

    @Inject
    UserLessonsTabFragmentPresenter presenter;

    @Inject
    UserDrinksAdapter adapter;

    @BindString(R.string.user_lesson_name)
    String userLessonName;

    @BindString(R.string.noUserLessons)
    String emptyMessage;

    private FloatingActionButton fab;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    public static Fragment newInstance() {

        return new UserLessonsTabFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupFab();
        return view;
    }

    private void setupFab() {
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {

            try{
                FragmentManager fm = getActivity().getFragmentManager();
                EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Add New Drink");
                editNameDialogFragment.show(fm, "fragment_edit_name");

            } catch (ClassCastException e) {
                e.printStackTrace();
            }


//            presenter.createNewLessonButtonClicked();
        }
    }

    @Override
    public void renderCreatedLesson(Drink drink) {

        adapter.addFirst(drink);
        gridLayoutManager.scrollToPosition(0);
        // Wait for drink creation animation to complete, then open newly created drink
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Drink justCreatedDrink = getLessonsAdapter().getFirstItem();
                View sourceView = ((UserDrinksAdapter) getLessonsAdapter()).getFirstView();
                navigator.navigateToEditFlashcardsActivity(getActivity(), justCreatedDrink, sourceView);
                getActivity().overridePendingTransition(R.anim.appear, 0);
            }
        }, DELAY_MILLIS);
    }

    @Override
    public void renderLessonItemRemoved(int position) {
        adapter.removeItem(position);
    }

    @Override
    public void renderLessonItemBookmarked(int position, boolean bookmarked) {
        throw new UnsupportedOperationException("Bookmarking not supported for user drinks");
    }

    @Override
    public void renderLessonList(List<Drink> drinkList) {
        Collections.reverse(drinkList);
        super.renderLessonList(drinkList);
    }
}

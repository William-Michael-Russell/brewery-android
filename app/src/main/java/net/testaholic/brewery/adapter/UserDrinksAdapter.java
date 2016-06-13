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

package net.testaholic.brewery.adapter;

import android.app.Activity;
import android.view.View;

import net.testaholic.brewery.R;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.navigator.Navigator;
import net.testaholic.brewery.presentation.UserLessonsTabFragmentPresenter;
import net.testaholic.brewery.widgets.LessonItemView;

import javax.inject.Inject;

/**
 * Adapter for displaying user drinks
 */
public class UserDrinksAdapter extends DrinksAdapter {

    private View firstView;

    private UserLessonsTabFragmentPresenter presenter;

    @Inject
    public UserDrinksAdapter(Activity activity, UserLessonsTabFragmentPresenter presenter, Navigator navigator) {
        super(activity, navigator);
        this.presenter = presenter;
    }

    @Override
    protected void bind(final Drink drink, final LessonItemView view,
            final BaseListAdapter.ViewHolder<LessonItemView> holder) {
        super.bind(drink, view, holder);
        if (holder.getAdapterPosition() == 0) {
            firstView = view;
        }
        view.setRemoveIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.lessonItemClicked(drink.getId(), holder.getAdapterPosition());
            }
        });
    }

    public View getFirstView() {
        return firstView;
    }

    @Override
    public void onLessonClicked(Drink drink, LessonItemView lessonItemView) {
        navigator.navigateToEditFlashcardsActivity(context, drink, lessonItemView);
        ((Activity) context).overridePendingTransition(R.anim.appear, 0);
    }
}

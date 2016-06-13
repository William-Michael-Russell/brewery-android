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
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import net.testaholic.brewery.R;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.navigator.Navigator;
import net.testaholic.brewery.widgets.LessonItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for drinks
 */
public abstract class DrinksAdapter extends BaseListAdapter<Drink, LessonItemView> {

    private static final boolean DEFAULT_ANIMATION_ENABLED = true;

    protected List<Drink> drinks = new ArrayList<>();
    private boolean animationEnabled = DEFAULT_ANIMATION_ENABLED;
    protected Navigator navigator;

    public DrinksAdapter(Context context, Navigator navigator) {
        super(context);
        this.navigator = navigator;
    }

    public void enableAnimation() {
        animationEnabled = true;
    }

    public void disableAnimation() {
        animationEnabled = false;
    }

    @Override
    protected LessonItemView createView(Context context, ViewGroup viewGroup, int viewType) {
        return (LessonItemView) LayoutInflater.from(context)
                .inflate(R.layout.adapter_item_lesson, viewGroup, false);
    }

    @Override
    protected void bind(final Drink drink, final LessonItemView view, BaseListAdapter.ViewHolder<LessonItemView> holder) {
        if (drink != null){
            view.setLessonName(drink.getDrinkName());
            String imagePath = drink.getDrinkImageUrl();

            Picasso.with(context).load(imagePath)
                    .into(view.getLessonImageView());
            if (animationEnabled) {
                view.restartAnimation();
            }
            view.setOnLessonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLessonClicked(drink, view);
                }
            });
        }
    }

    public void onLessonClicked(Drink drink, LessonItemView lessonItemView) {
        navigator.navigateToViewFlashcardsActivity(context, drink, lessonItemView);
        ((Activity) context).overridePendingTransition(R.anim.appear, 0);
    }

}
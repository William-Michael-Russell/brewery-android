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

package net.testaholic.brewery.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.testaholic.brewery.app.App;
import net.testaholic.brewery.bus.RxEventBus;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.repository.LessonRepository;
import net.testaholic.brewery.thread.PostExecutionThread;
import net.testaholic.brewery.thread.ThreadExecutor;

import java.util.Random;

import javax.inject.Inject;

/**
 * Presenter for {@link net.testaholic.brewery.fragment.UserLessonsTabFragment}
 */
public class UserLessonsTabFragmentPresenter extends LessonsPresenter {

    private LessonRepository lessonRepository;

    @Inject
    EditFlashcardsActivityPresenter editFlashcardsActivityPresenter;

    public UserLessonsTabFragmentPresenter(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            LessonRepository lessonRepository, RxEventBus rxEventBus) {
        super(threadExecutor, postExecutionThread, lessonRepository.getUserLessons(), rxEventBus);
        this.lessonRepository = lessonRepository;
    }

    public void createNewLessonButtonClicked() {

        execute(lessonRepository.addLesson(createUserLesson()), new DefaultSubscriber<Drink>() {

            @Override
            public void onNext(Drink drink) {
                renderLessonsView.renderCreatedLesson(drink);
            }
        });


    }


    public void lessonItemClicked(Long lessonId, final int position) {
        execute(lessonRepository.removeLessonById(lessonId), new DefaultSubscriber<Void>() {

            @Override
            public void onNext(Void v) {
                renderLessonsView.renderLessonItemRemoved(position);
            }
        });
    }

    /**
     * Create user lesson
     */
    private Drink createUserLesson(){
        final Drink drink = new Drink();
        drink.setDrinkName("User drink");
        drink.setUserLesson(true);
        drink.setDrinkImageUrl(getRandomImage());
        return drink;
    }

    /**
     * Pick random image from assets
     */
    private String getRandomImage() {
        int unit = new Random().nextInt(20) + 1;
        return String.format("random/random_%s.png", unit);
    }

}

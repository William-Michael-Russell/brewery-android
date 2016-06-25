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

package net.testaholic.brewery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.testaholic.brewery.R;
import net.testaholic.brewery.adapter.EditFlashcardsAdapter;
import net.testaholic.brewery.dagger.component.ActivityComponent;
import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.presentation.EditFlashcardsActivityPresenter;
import net.testaholic.brewery.util.SpeechService;
import net.testaholic.brewery.view.EditFlashcardsView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Edit flashcards activity
 */
public class EditFlashcardsActivity extends FlashcardsActivity implements EditFlashcardsView {

    public static final int DELAY_MILLIS = 800;
    private LinearLayoutManager linearLayoutManager;

    @Inject
    EditFlashcardsAdapter editFlashcardsAdapter;

    @Inject
    EditFlashcardsActivityPresenter editFlashcardsActivityPresenter;

    @Inject
    public SpeechService speechService;

    @Override
    public void onResume() {
        super.onResume();
        editFlashcardsActivityPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        editFlashcardsActivityPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editFlashcardsActivityPresenter.destroy();
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    public void renderFlashcards(List<Ingredients> ingredientsList) {
        Collections.reverse(ingredientsList);
        editFlashcardsAdapter.setItems(ingredientsList);
        recyclerView.setAdapter(editFlashcardsAdapter);
    }

    @Override
    void clearProgressClicked() {
        editFlashcardsActivityPresenter.clearProgressClicked();
    }

    @Override
    public void onCreateExpandingActivity(Bundle savedInstanceState) {
        super.onCreateExpandingActivity(savedInstanceState);
        initRecyclerView();
        initPresenter();
        setupFab();
    }

    private void initPresenter() {
        editFlashcardsActivityPresenter.setView(this);
        editFlashcardsActivityPresenter.initialize(lessonId);
    }

    public static Intent createIntent(Context context, Drink drink, View view) {
        Intent intent = new Intent(context, EditFlashcardsActivity.class);
        intent.putExtra(EXTRA_LESSON_ID, drink.getId());
        intent.putExtra(EXTRA_LESSON_IMAGE_PATH, drink.getDrinkImageUrl());
        intent.putExtra(EXTRA_LESSON_NAME, drink.getDrinkName());
        intent.putExtra(EXTRA_EDITABLE_LESSON, true);
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        intent.putExtra(EXTRA_PROPERTY_TOP, screenLocation[1]);
        intent.putExtra(EXTRA_PROPERTY_LEFT, screenLocation[0]);
        intent.putExtra(EXTRA_PROPERTY_WIDTH, view.getWidth());
        intent.putExtra(EXTRA_PROPERTY_HEIGHT, view.getHeight());
        return intent;
    }

    public void setupFab() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFlashcardsActivityPresenter.createFlashcardsClicked();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.show();
            }
        }, DELAY_MILLIS);
    }

    private void addToTop(Ingredients ingredients) {
        editFlashcardsAdapter.addFirst(ingredients);
        recyclerView.scrollToPosition(0);
    }

    private void initRecyclerView() {
        recyclerView.setEmptyView(nestedScrollView);
        textView.setText("No user cards");
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
    }

    @Override
    public void renderFlashcardRemovalSnackBar(final Ingredients ingredients, final int position) {
        Snackbar
                .make(getRootLayout(),
                        "Remove card?",
                        Snackbar.LENGTH_LONG)
                .setAction("REMOVE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editFlashcardsActivityPresenter.removeFlashcard(ingredients, position);
                    }
                })
                .show();
    }

    @Override
    public void renderCreatedFlashcard(final Ingredients insertedIngredients) {
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrollStateChanged(RecyclerView rv, int state) {
                    if (state == RecyclerView.SCROLL_STATE_IDLE) {
                        addToTop(insertedIngredients);
                        rv.removeOnScrollListener(this);
                    }
                }
            });
            recyclerView.smoothScrollToPosition(0);
        } else {
            addToTop(insertedIngredients);
        }
    }

    @Override
    public void renderFlashcardRemoved(Ingredients ingredients, int position) {
        editFlashcardsAdapter.removeItem(position);
    }

    @Override
    public void renderEditFlashcardDialog(final Ingredients ingredients, final int position) {

//        FragmentManager fm = getSupportFragmentManager();
//        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
//        editNameDialogFragment.show(fm, "fragment_edit_name");

//        final EditText word = new EditText(this);
//        final EditText definition = new EditText(this);
//        word.setText(ingredients.getIngredients());
//        definition.setText(ingredients.getDefinition());
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//        ll.addView(word);
//        ll.addView(definition);
//        new AlertDialog.Builder(this)
//                .setTitle("Edit ingredients")
//                .setMessage("Edit ingredients word and definition")
//                .setView(ll)
//                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        String wordText = word.getText().toString();
//                        String definitionText = definition.getText().toString();
//                        ingredients.setIngredients(wordText);
//                        ingredients.setDefinition(definitionText);
//                        editFlashcardsActivityPresenter.flashcardModified(ingredients);
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                    }
//                })
//                .show();
    }
}


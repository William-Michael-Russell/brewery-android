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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.testaholic.brewery.R;
import net.testaholic.brewery.domain.Ingredients;
import net.testaholic.brewery.presentation.EditFlashcardsActivityPresenter;
import net.testaholic.brewery.widgets.EditableFlashcardView;

import javax.inject.Inject;

/**
 * Adapter for displaying list of editable flashcards
 */
public class EditFlashcardsAdapter extends BaseListAdapter<Ingredients, EditableFlashcardView> {

    private EditFlashcardsActivityPresenter presenter;

    @Inject
    public EditFlashcardsAdapter(Context context, EditFlashcardsActivityPresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected EditableFlashcardView createView(Context context, ViewGroup viewGroup, int viewType) {
        return (EditableFlashcardView) LayoutInflater.from(context)
                .inflate(R.layout.adapter_item_edit_words, viewGroup, false);
    }

    @Override
    protected void bind(final Ingredients ingredients, EditableFlashcardView view, final ViewHolder<EditableFlashcardView> holder) {
        if (ingredients != null) {
            view.setFlashcardWord(ingredients.getIngredients());
            view.setFlashcardDefinition(ingredients.getDefinition());


//
//            view.setOnWordSavedListener(new SwitchingEditText.OnItemSavedListener() {
//                @Override
//                public void onItemSaved(String wordFromView) {
//                    ingredients.setIngredients(wordFromView);
//                    presenter.flashcardModified(ingredients);
//                }
//            });
//            view.setOnDefinitionSavedListener(new SwitchingEditText.OnItemSavedListener() {
//                @Override
//                public void onItemSaved(String definitionFromView) {
//                    ingredients.setDefinition(definitionFromView);
//                    presenter.flashcardModified(ingredients);
//                }
//            });
            view.setLearnt(ingredients.isLearnt());
            view.setSpeakerIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onSpeakIconClicked(ingredients.getIngredients());
                }
            });
            view.setRemoveListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    presenter.onFlashcardRemoveClicked(ingredients, holder.getAdapterPosition());
                }
            });
            view.setEditListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    presenter.onFlashcardEditClicked(ingredients, holder.getAdapterPosition());

                }
            });
        }
    }


}

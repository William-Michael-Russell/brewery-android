package net.testaholic.brewery.fragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.testaholic.brewery.R;
import net.testaholic.brewery.app.App;
import net.testaholic.brewery.domain.Drink;
import net.testaholic.brewery.rest.RestClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by williamrussell on 6/12/16.
 */

public class EditNameDialogFragment extends DialogFragment {

    private EditText mEditText;

    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_name, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        mEditText = (EditText) view.findViewById(R.id.txt_add_drink_name);

        Button cancel = (Button) view.findViewById(R.id.btn_cancel_drink);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button save = (Button) view.findViewById(R.id.btn_save_drink);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText drinkIngredients = (EditText) view.findViewById(R.id.txt_add_drink_ingredients);
                String drinkIngredientsText = drinkIngredients.getText().toString();
                EditText drinkImageUrl = (EditText) view.findViewById(R.id.txt_add_drink_image);
                EditText drinkNameET = (EditText) view.findViewById(R.id.txt_add_drink_name);
                String drinkNameTxt = drinkNameET.getText().toString();
                String drinkImageUrlText = drinkImageUrl.getText().toString();

                final Drink newDrink = new Drink();
                newDrink.setDrinkImageUrl(drinkImageUrlText);
                newDrink.setDrinkName(drinkNameTxt);
                newDrink.setDrinkIngredients(drinkIngredientsText);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RestClient.getBreweryService().postDrink(newDrink).execute();

                            getDialog().dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                });
            }
        });


        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}

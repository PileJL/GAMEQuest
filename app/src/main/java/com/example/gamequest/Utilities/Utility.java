package com.example.gamequest.Utilities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.gamequest.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Utility {

    public static void navigateToActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void scaleViews(ArrayList<View> views, float scaleX, float scaleY) {
        for (View view: views) {
            scaleView(view, scaleX, scaleY);
        }
    }

    public static void scaleView(View view, float scaleX, float scaleY) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", scaleX);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", scaleY);

        scaleXAnimator.setDuration(100); // Duration of animation in milliseconds
        scaleYAnimator.setDuration(100);

        scaleXAnimator.start();
        scaleYAnimator.start();
    }

    public static void emptyFields(ArrayList<TextInputEditText> fields) {
        for (TextInputEditText field: fields) {
            field.setText("");
        }
    }

    public static void emptyEditTexts(ArrayList<EditText> fields) {
        for (EditText field: fields) {
            field.setText("");
        }
    }

    public static boolean hasEmptyFields(ArrayList<EditText> fields) {
        for (EditText field : fields) {
            // return true is has an empty field
            if (field.getText().toString().trim().isBlank()) {
                return true;
            }
        }
        return false;
    }

    public static String getTrimmedText(EditText field) {
        return field.getText().toString().trim();
    }

    public static void unSelectRadioButtons(ArrayList<RadioButton> radioButtons, RadioButton exeption) {
        for (RadioButton radioButton: radioButtons) {
            if (radioButton != exeption) {
                radioButton.setChecked(false);
                continue;
            }
            radioButton.setChecked(true);
        }
    }
}

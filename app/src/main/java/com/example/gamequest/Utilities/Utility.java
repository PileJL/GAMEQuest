package com.example.gamequest.Utilities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

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
}

package com.example.gamequest.Utilities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

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
    public static void resetRadioButtons(ArrayList<RadioButton> radioButtons) {
        for (RadioButton radioButton: radioButtons) {
            radioButton.setChecked(false);
        }
    }
    // Static method to add a row to the TableLayout
    public static void addTableRow(Context context, TableLayout tableLayout, String name, String score) {
        // Create a new TableRow
        TableRow tableRow = new TableRow(context);

        // Create and configure the first TextView (Name)
        TextView nameTextView = new TextView(context);
        nameTextView.setText(name); // Set the text
        nameTextView.setGravity(android.view.Gravity.CENTER); // Center text
        nameTextView.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set text color
        nameTextView.setTextSize(15); // Set text size
        nameTextView.setPadding(20, 10, 0, 10); // Set padding
        Typeface nameFont = ResourcesCompat.getFont(context, R.font.fredoka_regular);
        nameTextView.setTypeface(nameFont); // Set font

        // Create and configure the second TextView (Score)
        TextView scoreTextView = new TextView(context);
        scoreTextView.setText(score); // Set the text
        scoreTextView.setGravity(android.view.Gravity.CENTER); // Center text
        scoreTextView.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set text color
        scoreTextView.setTextSize(15); // Set text size
        scoreTextView.setPadding(0, 10, 0, 10); // Set padding
        Typeface scoreFont = ResourcesCompat.getFont(context, R.font.fredoka_regular);
        scoreTextView.setTypeface(scoreFont); // Set font

        // Add the TextViews to the TableRow
        tableRow.addView(nameTextView);
        tableRow.addView(scoreTextView);

        // Add the TableRow to the TableLayout
        tableLayout.addView(tableRow);
    }

    public static void playBGMusic(MediaPlayer mediaPlayer) {
        mediaPlayer.setLooping(true);
        Utility.playAudio(mediaPlayer, 1f);
    }

    public static int playAudio(MediaPlayer mediaPlayer,  float playbackSpeed) {
        if (mediaPlayer != null) {
            mediaPlayer.setPlaybackParams(new PlaybackParams().setSpeed(playbackSpeed));
            mediaPlayer.start();
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public static void cutAudio(ArrayList<MediaPlayer> mediaPlayers) {
        for (MediaPlayer mediaPlayer: mediaPlayers) {
            if (mediaPlayer != null || mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    }

    public static void animateHeart(Context context, ArrayList<ImageView> hearts, int lives) {
        Animation fadeOutShrink = AnimationUtils.loadAnimation(context, R.anim.fade_out_shrink);
        hearts.get(lives).startAnimation(fadeOutShrink);
        fadeOutShrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {            }
            @Override
            public void onAnimationEnd(Animation animation) {
                // Change the image resource to an empty heart
                hearts.get(lives).setImageResource(R.drawable.heart_no_fill);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}

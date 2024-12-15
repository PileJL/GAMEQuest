package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamequest.Teacher.Module.ModuleAdapter;
import com.example.gamequest.Teacher.Module.ModuleItem;
import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.QuizPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuizPage extends AppCompatActivity {

    QuizPageBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<RadioButton> radioButtons = new ArrayList<>();
    String questionAnswer = "";

    String lessonId = ModuleListPage.lessonId;
    String lessonTitle = ModuleListPage.lessonTitle;
    ArrayList<Map<String, Object>> quizQuestions = new ArrayList<>();
    int answeredQuestions = 0;
    public static int userScore = 0;
    public static int quizTotalItem;

    Map<String, Object> assessmentLog = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = QuizPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // This callback will intercept the back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                whenBackIsPressed();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
        // initializations
        radioButtons = new ArrayList<>(Arrays.asList(binding.radioButton1, binding.radioButton2, binding.radioButton3, binding.radioButton4));

        // back button onclick
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());

        // set quiz topic
        binding.quizTopic.setText(lessonTitle);

        // get quiz details
        db.collection("quizzes")
                .whereEqualTo("lessonId", lessonId) // Filter by lessonId
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String quizId = document.getId();
                                String instructions = document.getString("instructions");
                                binding.quizInstructions.setText(instructions);
                                // get quiz questions
                                getQuizQuestions(quizId);
                                return;
                            }
                        } else {
                            Toast.makeText(QuizPage.this, "No documents found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("FirestoreError", "Error: ", task.getException());
                        Toast.makeText(QuizPage.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                    }
                });

        // radiobuttons onclick
        binding.radioButton1.setOnClickListener(this::radioButtonOnClick);
        binding.radioButton2.setOnClickListener(this::radioButtonOnClick);
        binding.radioButton3.setOnClickListener(this::radioButtonOnClick);
        binding.radioButton4.setOnClickListener(this::radioButtonOnClick);

        // next question onclick
        binding.nextQuestionBttn.setOnClickListener(v -> checkAnswer());

    }

    private void checkAnswer() {
        // check if user selected an answer
        if (questionAnswer.isBlank()) {
            Toast.makeText(this, "Please select your answer.", Toast.LENGTH_SHORT).show();
        }
        else {

            // check if answer is correct
            if (questionAnswer.equalsIgnoreCase(quizQuestions.get(answeredQuestions).get("answer").toString())) {
                userScore++;
//                Toast.makeText(this, "tama", Toast.LENGTH_SHORT).show();
            }
            else {
//                Toast.makeText(this, "mali", Toast.LENGTH_SHORT).show();
            }

            // empty questionAnswer
            questionAnswer = "";
            // increment answered questions
            answeredQuestions++;
            // rest radio buttons
            Utility.resetRadioButtons(radioButtons);
            // set next question
            setQuestion();
        }
    }

    private void getQuizQuestions(String quizId) {
        // get quiz questions
        db.collection("quizQuestions")
                .whereEqualTo("quizId", quizId) // Filter by quizId
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Use the document ID as the key and the document data as the value
                                quizQuestions.add(document.getData());
                            }

                            // Log the results for debugging
                            Log.d("QuizQuestions", "Quiz Questions Map: " + quizQuestions.toString());
                            // set question label
                            quizTotalItem = quizQuestions.size();
                            binding.questionLabel.setText("Question No. 1 / " + quizTotalItem);

                            // set first question
                            setQuestion();
                        } else {
                            Toast.makeText(QuizPage.this, "No documents found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("FirestoreError", "Error: ", task.getException());
                        Toast.makeText(QuizPage.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setQuestion() {
        // check if all questions are answered
        if (answeredQuestions == quizTotalItem) {
            // add user score to database
            addUserScoreToDB();
            return;
        }
        // set question label
        binding.questionLabel.setText(String.format("Question No. %d / %d", answeredQuestions + 1, quizTotalItem));
        // set question
        binding.quizQuestion.setText(quizQuestions.get(answeredQuestions).get("question").toString());
        // set choices
        ArrayList<String> choices = (ArrayList<String>) quizQuestions.get(answeredQuestions).get("choices");
        binding.option1.setText(choices.get(0));
        binding.option2.setText(choices.get(1));
        binding.option3.setText(choices.get(2));
        binding.option4.setText(choices.get(3));
    }

    private void addUserScoreToDB() {
        // add user score to assessmentLog
        assessmentLog.put("lessonId", lessonId);
        assessmentLog.put("userName", SignInPage.userName);
        assessmentLog.put("userScore", String.format("%d / %d", userScore, quizTotalItem));
        Toast.makeText(this, String.format("%d / %d", userScore, quizTotalItem), Toast.LENGTH_SHORT).show();

        // else, add the account to database
        db.collection("assessmentLog")
                .add(assessmentLog)
                .addOnSuccessListener(documentReference -> {
                    // go to quiz score page
                    Utility.navigateToActivity(this, new Intent(this, QuizScorePage.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(QuizPage.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, ModulePage.class));
        finish();
    }

    private void radioButtonOnClick(View v) {
        Utility.unSelectRadioButtons(radioButtons, (RadioButton) v);
        questionAnswer = v.getTag().toString();
    }
}
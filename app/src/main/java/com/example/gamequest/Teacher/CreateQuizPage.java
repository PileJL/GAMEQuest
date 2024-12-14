package com.example.gamequest.Teacher;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.gamequest.R;
import com.example.gamequest.databinding.CreateQuizPageBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.QuizTypeSelectionPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.gamequest.SignInPage;

public class CreateQuizPage extends AppCompatActivity {

    CreateQuizPageBinding binding;
    String lessonTitle, lessonDescription, gradingPeriod;
    Map<String, Object> question = new HashMap<>();
    ArrayList<RadioButton> radioButtons = new ArrayList<>();
    String questionAnswer = "";
    int questionCount = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> lesson = new HashMap<>();
    Map<String, Object> quiz = new HashMap<>();
    ArrayList<Map<String, Object>> quizQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = CreateQuizPageBinding.inflate(getLayoutInflater());
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

        binding.backButton.setOnClickListener(v -> whenBackIsPressed());
        // initializations
        radioButtons = new ArrayList<>(Arrays.asList(binding.radioButton1, binding.radioButton2, binding.radioButton3, binding.radioButton4));

        // get lesson details from previous page
        Intent intent = getIntent();
        lessonTitle = intent.getStringExtra("lessonTitle");
        lessonDescription = intent.getStringExtra("lessonDescription");
        gradingPeriod = intent.getStringExtra("gradingPeriod");

        // lesson details
        lesson = new HashMap<>() {{
            put("title", lessonTitle);
            put("description", lessonDescription);
            put("gradingPeriod", gradingPeriod);
            put("userId", SignInPage.userID);
            put("date", new Timestamp(new Date()));
        }};


        // set quiz topic to lesson title
        binding.quizTopic.setText(lessonTitle);

        // addQuestion button onclick
        binding.addQuestionBttn.setOnClickListener(v -> addQuestion());

        // radiobuttons onclick
        binding.radioButton1.setOnClickListener(this::radioButtonOnClick);
        binding.radioButton2.setOnClickListener(this::radioButtonOnClick);
        binding.radioButton3.setOnClickListener(this::radioButtonOnClick);
        binding.radioButton4.setOnClickListener(this::radioButtonOnClick);

        // create button onclick
        binding.createQuizButton.setOnClickListener(v -> createQuiz());
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, CreateLessonPage.class));
        finish();
    }

    private void createQuiz() {
        // check if all fields are filled out
        if (Utility.hasEmptyFields(new ArrayList<>(List.of(binding.quizInstructions))) || quizQuestions.isEmpty()) {
            Toast.makeText(CreateQuizPage.this, "Please provide all necessary details.", Toast.LENGTH_SHORT).show();
        }
        else {
            // add the lesson to the database
            db.collection("lessons")
                    .add(lesson)
                    .addOnSuccessListener(documentReference -> {
                        // add the lesson to the database
                        addQuiz(documentReference.getId());
                    })
                    .addOnFailureListener(e -> Toast.makeText(CreateQuizPage.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void addQuiz(String lessonId) {
        // add quiz details
        quiz = new HashMap<>() {{
            put("instructions", Utility.getTrimmedText(binding.quizInstructions));
            put("lessonId", lessonId);
        }};
        // quiz to database
        db.collection("quizzes")
                .add(quiz)
                .addOnSuccessListener(documentReference -> {
                    // add quiz questions to database
                    addQuizQuestions(documentReference.getId());
                })
                .addOnFailureListener(e -> Toast.makeText(CreateQuizPage.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void addQuizQuestions(String quizId) {
        for (Map<String, Object> question : quizQuestions) {
            question.put("quizId", quizId);
            db.collection("quizQuestions")
                    .add(question)
                    .addOnFailureListener(e -> Toast.makeText(CreateQuizPage.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
        Utility.navigateToActivity(this, new Intent(this, QuizAddedPage.class));
        finish();
    }

    private void radioButtonOnClick(View v) {
        Utility.unSelectRadioButtons(radioButtons, (RadioButton) v);
        questionAnswer = v.getTag().toString();
    }

    private void addQuestion() {
        // check if details not are complete
        if (Utility.hasEmptyFields(new ArrayList<>(Arrays.asList(binding.quizQuestion, binding.option1, binding.option2,
                binding.option3, binding.option4))) || questionAnswer.isBlank()) {
            Toast.makeText(CreateQuizPage.this, "Please complete the question's details", Toast.LENGTH_SHORT).show();
        }
        else {
            // else, add the questions to the array
            questionCount++;
            Toast.makeText(CreateQuizPage.this, "Question no. " + questionCount + " successfully added.", Toast.LENGTH_SHORT).show();
            String questionNumberLabel = "Question no. " + (questionCount + 1);
            // add the question to the array
            quizQuestions.add(
                    new HashMap<>() {{
                        put("question", Utility.getTrimmedText(binding.quizQuestion));
                        put("choices", Arrays.asList(Utility.getTrimmedText(binding.option1), Utility.getTrimmedText(binding.option2),
                            Utility.getTrimmedText(binding.option3), Utility.getTrimmedText(binding.option4)));
                        put("answer", questionAnswer);
            }});
            // clear fields
            Utility.emptyEditTexts(new ArrayList<>(Arrays.asList(binding.quizQuestion, binding.option1, binding.option2,
                    binding.option3, binding.option4)));
            // set form label to new question number
            binding.questionLabel.setText(questionNumberLabel);
        }
    }
}
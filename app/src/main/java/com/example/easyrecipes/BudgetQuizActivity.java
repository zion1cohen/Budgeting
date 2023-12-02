package com.example.easyrecipes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BudgetQuizActivity extends AppCompatActivity {

    private TextView question1TextView, question2TextView, question3TextView, resultTextView;
    private RadioGroup answer1RadioGroup, answer2RadioGroup, answer3RadioGroup;
    private Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_quiz);

        // Initialize views
        question1TextView = findViewById(R.id.question1TextView);
        answer1RadioGroup = findViewById(R.id.answer1RadioGroup);

        question2TextView = findViewById(R.id.question2TextView);
        answer2RadioGroup = findViewById(R.id.answer2RadioGroup);

        question3TextView = findViewById(R.id.question3TextView);
        answer3RadioGroup = findViewById(R.id.answer3RadioGroup);

        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Set questions and options
        question1TextView.setText("Question 1: What is the first tip for budgeting?");
        setOptions(answer1RadioGroup, "A. Save money regularly", "B. Spend without tracking", "C. Ignore income", "D. Avoid savings");

        question2TextView.setText("Question 2: What is the key to avoiding debt?");
        setOptions(answer2RadioGroup, "A. Accumulate credit card debt", "B. Budgeting wisely", "C. Ignoring bills", "D. Spending impulsively");

        question3TextView.setText("Question 3: How can you increase savings?");
        setOptions(answer3RadioGroup, "A. Save less each month", "B. Avoid emergency fund", "C. Increase income", "D. Spend more than you earn");

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
    }

    private void setOptions(RadioGroup radioGroup, String optionA, String optionB, String optionC, String optionD) {
        ((RadioButton) radioGroup.getChildAt(0)).setText(optionA);
        ((RadioButton) radioGroup.getChildAt(1)).setText(optionB);
        ((RadioButton) radioGroup.getChildAt(2)).setText(optionC);
        ((RadioButton) radioGroup.getChildAt(3)).setText(optionD);
    }

    private void checkAnswers() {
        int correctCount = 0;

        // Check answer for Question 1
        if (answer1RadioGroup.getCheckedRadioButtonId() == R.id.answer1OptionA) {
            correctCount++;
        }

        // Check answer for Question 2
        if (answer2RadioGroup.getCheckedRadioButtonId() == R.id.answer2OptionB) {
            correctCount++;
        }

        // Check answer for Question 3
        if (answer3RadioGroup.getCheckedRadioButtonId() == R.id.answer3OptionC) {
            correctCount++;
        }

        // Display the result
        String resultMessage = "You got " + correctCount + " out of 3 questions correct!";
        resultTextView.setText(resultMessage);
        resultTextView.setVisibility(View.VISIBLE);
    }
}

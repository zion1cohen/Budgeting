package com.example.easyrecipes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BudgetBreakdownActivity extends AppCompatActivity {

    private TextView textViewTotalIncome;
    private TextView textViewTotalExpenses;
    private TextView textViewIncomeMinusExpenses;
    private ImageView imageViewFeedback;
    private Button buttonViewBudgetTips;
    private MediaPlayer cheerSound;
    private MediaPlayer awwSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_breakdown);

        textViewTotalIncome = findViewById(R.id.textViewTotalIncome);
        textViewTotalExpenses = findViewById(R.id.textViewTotalExpenses);
        textViewIncomeMinusExpenses = findViewById(R.id.textViewIncomeMinusExpenses);
        imageViewFeedback = findViewById(R.id.imageViewFeedback);
        buttonViewBudgetTips = findViewById(R.id.buttonViewBudgetTips);
        cheerSound = MediaPlayer.create(this, R.raw.cheer);
        awwSound = MediaPlayer.create(this, R.raw.aww);
        // Retrieve data from TrackTransactionActivity
        double totalIncome = getIntent().getDoubleExtra("TOTAL_INCOME", 0.0);
        double totalExpenses = getIntent().getDoubleExtra("TOTAL_EXPENSES", 0.0);
        double incomeMinusExpenses = getIntent().getDoubleExtra("INCOME_MINUS_EXPENSES", 0.0);

        // Display data in TextViews
        textViewTotalIncome.setText("Total Income: " + totalIncome);
        textViewTotalExpenses.setText("Total Expenses: " + totalExpenses);
        textViewIncomeMinusExpenses.setText("Income - Expenses: " + incomeMinusExpenses);

        // Display thumbs up or down based on financial situation
        if (incomeMinusExpenses >= 0) {
            imageViewFeedback.setImageResource(R.drawable.thumbsup);
            playCheerSound();
        } else {
            imageViewFeedback.setImageResource(R.drawable.thumbs_down);
            playAwwSound();
        }

        // Set click listener for the "View Budget Tips" button
        buttonViewBudgetTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the WebsiteGridActivity
                Intent intent = new Intent(BudgetBreakdownActivity.this, WebsiteGridActivity.class);
                startActivity(intent);
            }
        });
    }
    private void playCheerSound() {
        if (cheerSound != null && !cheerSound.isPlaying()) {
            cheerSound.start();
        }
    }
    private void playAwwSound() {
        if (awwSound != null && !awwSound.isPlaying()) {
            awwSound.start();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (cheerSound != null) {
            cheerSound.release();
        }
        if (awwSound != null) {
            awwSound.release();
        }
    }
}

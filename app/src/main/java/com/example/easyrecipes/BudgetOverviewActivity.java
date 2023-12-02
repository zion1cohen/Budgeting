package com.example.easyrecipes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BudgetOverviewActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_UPDATE_BUDGET = 1;

    private TextView textViewIncome;
    private TextView textViewExpenses;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_overview);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Now you have the incomeValue and expensesValue, you can update your UI accordingly.
        // For example, set them to TextViews.
        textViewIncome = findViewById(R.id.textViewIncome);
        textViewExpenses = findViewById(R.id.textViewExpenses);

        // Update TextViews with the initial values
        updateTextViews();



        // Set click listener for the "Track Transactions" button
        Button buttonTrackTransactions = findViewById(R.id.buttonTrackTransactions);
        buttonTrackTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTrackTransactionActivity();
            }
        });

        // ... (other existing code)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_BUDGET && resultCode == RESULT_OK) {
            // BudgetOverviewActivity has finished, update UI or perform any necessary actions
            updateTextViews();  // Update the TextViews with new totals
        }
    }

    private double getDoubleFromPrefs(String key, double defaultValue) {
        return sharedPreferences.getFloat(key, (float) defaultValue);
    }
    private void openTrackTransactionActivity() {
        Intent intent = new Intent(BudgetOverviewActivity.this, TrackTransactionActivity.class);

        // Pass initial income and expense values to TrackTransactionActivity
        double initialIncomeValue = getDoubleFromPrefs("INCOME_VALUE", 0.0);
        double initialExpensesValue = getDoubleFromPrefs("EXPENSES_VALUE", 0.0);
        intent.putExtra("INITIAL_INCOME", initialIncomeValue);
        intent.putExtra("INITIAL_EXPENSES", initialExpensesValue);

        startActivityForResult(intent, REQUEST_CODE_UPDATE_BUDGET);
    }
    // Define the updateTextViews method to update TextViews based on the latest values
    private void updateTextViews() {
        double incomeValue = getDoubleFromPrefs("INCOME_VALUE", 0.0);
        double expensesValue = getDoubleFromPrefs("EXPENSES_VALUE", 0.0);

        // Update TextViews with the new totals
        textViewIncome.setText("Income: $" + String.format("%.2f", incomeValue));
        textViewExpenses.setText("Expenses: $" + String.format("%.2f", expensesValue));
    }
}

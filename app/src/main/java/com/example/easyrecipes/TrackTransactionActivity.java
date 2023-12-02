package com.example.easyrecipes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TrackTransactionActivity extends AppCompatActivity implements TransactionAdapter.TransactionClickListener {
    private MediaPlayer cashSound;
    private MediaPlayer registerSound;
    private EditText editTextAmount;
    private EditText editTextDescription;
    private Spinner spinnerTransactionType;
    private Button buttonAddTransaction;
    private ListView listViewTransactions;

    private Button buttonBudget;
    private List<Transaction> transactions;
    private TransactionAdapter transactionAdapter;

    private double initialIncomeValue;
    private double initialExpensesValue;

    private TextView textViewTotalIncome;
    private TextView textViewTotalExpenses;
    private TextView textViewIncomeMinusExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_transaction);

        // Initialize UI components
        cashSound = MediaPlayer.create(this, R.raw.cash);
        registerSound = MediaPlayer.create(this, R.raw.register);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerTransactionType = findViewById(R.id.spinnerTransactionType);
        buttonAddTransaction = findViewById(R.id.buttonAddTransaction);
        transactions = new ArrayList<>();
        transactionAdapter = new TransactionAdapter(this, transactions, this);
        listViewTransactions = findViewById(R.id.listViewTransactions);

        textViewTotalIncome = findViewById(R.id.textViewTotalIncome);
        textViewTotalExpenses = findViewById(R.id.textViewTotalExpenses);
        textViewIncomeMinusExpenses = findViewById(R.id.textViewIncomeMinusExpenses);
        buttonBudget = findViewById(R.id.buttonBudget);

        listViewTransactions.setAdapter(transactionAdapter);
// Retrieve initial values from the intent
        initialIncomeValue = getIntent().getDoubleExtra("INITIAL_INCOME", 0.0);
        initialExpensesValue = getIntent().getDoubleExtra("INITIAL_EXPENSES", 0.0);

        // Display initial values in TextViews
        textViewTotalIncome.setText("Total Income: " + initialIncomeValue);
        textViewTotalExpenses.setText("Total Expenses: " + initialExpensesValue);
        // Set click listener for the "Add Transaction" button
        buttonAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTransaction();
            }
        });

        // Set item click listener for the ListView
        listViewTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Transaction selectedTransaction = transactions.get(position);
                Toast.makeText(TrackTransactionActivity.this, selectedTransaction.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Set click listener for the "Budget Breakdown" button
        buttonBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start BudgetBreakdownActivity with updated values
                Intent intent = new Intent(TrackTransactionActivity.this, BudgetBreakdownActivity.class);
                intent.putExtra("TOTAL_INCOME", calculateTotalIncome());
                intent.putExtra("TOTAL_EXPENSES", calculateTotalExpenses());
                intent.putExtra("INITIAL_INCOME", initialIncomeValue);
                intent.putExtra("INITIAL_EXPENSES", initialExpensesValue);
                intent.putExtra("INCOME_MINUS_EXPENSES", calculateIncomeMinusExpenses());

                startActivity(intent);
            }
        });

    }

    @Override
    public void onTransactionClick(Transaction transaction) {
        transactions.remove(transaction);
        transactionAdapter.notifyDataSetChanged();
        updateUI();
        Toast.makeText(this, "Transaction removed: " + transaction.toString(), Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        double totalIncome = calculateTotalIncome();
        double totalExpenses = calculateTotalExpenses();
        double incomeMinusExpenses = calculateIncomeMinusExpenses();

        // Update the TextView elements
        textViewTotalIncome.setText("Total Income: " + totalIncome);
        textViewTotalExpenses.setText("Total Expenses: " + totalExpenses);
        textViewIncomeMinusExpenses.setText("Income - Expenses: " + incomeMinusExpenses);

        // Set the result before finishing the activity
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
    }

    private void addTransaction() {
        try {
            String amountString = editTextAmount.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            String selectedTransactionType = spinnerTransactionType.getSelectedItem().toString();
            boolean isIncome = selectedTransactionType.equals("Income");

            if (!amountString.isEmpty() && !description.isEmpty()) {
                double amount = Double.parseDouble(amountString);
                Transaction newTransaction = new Transaction(amount, description, isIncome);

                transactions.add(newTransaction);
                transactionAdapter.notifyDataSetChanged();

                editTextAmount.getText().clear();
                editTextDescription.getText().clear();

                updateUI();

                // Play sound based on transaction type
                if (isIncome) {
                    playCashSound();
                } else {
                    playRegisterSound();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred. Check logs for details.", Toast.LENGTH_SHORT).show();
        }
    }

    private void playCashSound() {
        if (cashSound != null && !cashSound.isPlaying()) {
            cashSound.start();
        }
    }

    private void playRegisterSound() {
        if (registerSound != null && !registerSound.isPlaying()) {
            registerSound.start();
        }
    }

    // Other methods...

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (cashSound != null) {
            cashSound.release();
        }
        if (registerSound != null) {
            registerSound.release();
        }
    }


    private double calculateIncomeMinusExpenses() {
        double income = calculateTotalIncome();
        double expenses = calculateTotalExpenses();
        return income - expenses;
    }

    private double calculateTotalIncome() {
        double totalIncome = initialIncomeValue;
        for (Transaction transaction : transactions) {
            if (transaction.isIncome()) {
                totalIncome += transaction.getAmount();
            }
        }
        return totalIncome;
    }

    private double calculateTotalExpenses() {
        double totalExpenses = initialExpensesValue;
        for (Transaction transaction : transactions) {
            if (!transaction.isIncome()) {
                totalExpenses += transaction.getAmount();
            }
        }
        return totalExpenses;
    }

}

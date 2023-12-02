package com.example.easyrecipes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText incomeEditText = findViewById(R.id.IncomeValue);
        final EditText expensesEditText = findViewById(R.id.ExpensesValue);
        final RadioGroup radioGroupExpenseType = findViewById(R.id.radioGroupExpenseType);

        final Button buttonSubmitInformation = findViewById(R.id.buttonSubmitInformation);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        buttonSubmitInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from UI components
                double incomeValue = Double.parseDouble(incomeEditText.getText().toString());
                double expensesValue = Double.parseDouble(expensesEditText.getText().toString());
                String expenseType = getSelectedExpenseType(radioGroupExpenseType);

                // Save data to SharedPreferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("INCOME_VALUE", (float) incomeValue);
                editor.putFloat("EXPENSES_VALUE", (float) expensesValue);
                editor.putString("EXPENSE_TYPE", expenseType);
                editor.apply();

                // Start BudgetOverviewActivity
                startActivity(new Intent(MainActivity.this, BudgetOverviewActivity.class));
            }
        });
    }

    private String getSelectedExpenseType(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }
}

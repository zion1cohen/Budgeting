package com.example.easyrecipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebsiteGridActivity extends AppCompatActivity {

    private GridView gridView;
    private LinearLayout topLinkLayout; // New layout to hold the link at the top
    private ImageView imageViewTopLink;
    private TextView textViewTopLink;
    private TextView textViewTitle;
    private Button btnBudgetQuiz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_grid);

        gridView = findViewById(R.id.gridView);
        topLinkLayout = findViewById(R.id.topLinkLayout);
        imageViewTopLink = findViewById(R.id.imageViewTopLink);
        textViewTopLink = findViewById(R.id.textViewTopLink);
        textViewTitle = findViewById(R.id.textViewTitle);
// Get a reference to the button
        btnBudgetQuiz = findViewById(R.id.btnBudgetQuiz);
        // Set the title text
        textViewTitle.setText("Budgeting Tips");
        btnBudgetQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the BudgetQuizActivity
                Intent intent = new Intent(WebsiteGridActivity.this, BudgetQuizActivity.class);
                startActivity(intent);
            }
        });
        // Set the top link
        String topLink = "https://www.nelnetbank.com/learning-center/10-best-budget-planning-tips-for-college-students/";
        textViewTopLink.setText("Learn More");
        topLinkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite(topLink);
            }
        });

        // Array of image resources and corresponding names and descriptions
        final int[] imageResources = {
                R.drawable.budget1,
                R.drawable.budget2,
                R.drawable.budget3,
                R.drawable.budget4
        };

        final String[] names = {
                "Budget 1",
                "Budget 2",
                "Budget 3",
                "Budget 4"
        };

        final String[] descriptions = {
                "Budget 1 - Smart Spending:\n" +
                        "Learn how to prioritize your spending by distinguishing between needs and wants. Create a monthly budget that allocates funds for essentials like tuition, rent, and groceries while allowing some room for leisure activities.",
                "Budget 2 - Emergency Fund Building:\n" +
                        "Establish the habit of setting aside a small portion of your income for emergencies. Having a financial safety net can alleviate stress during unexpected situations, such as medical expenses or sudden car repairs.",
                "Budget 3 - Meal Planning for Savings:\n" +
                        "Discover the art of meal planning to save money on food expenses. Preparing meals at home and buying groceries in bulk can significantly reduce your monthly food costs, leaving you with more funds for other necessities.",
                "Budget 4 - Side Hustle Strategies:\n" +
                        "Explore opportunities for part-time work or freelancing to supplement your income. Having a side hustle not only provides extra cash but also valuable experience that can enhance your resume while you study."
        };

        // Set up the adapter for the GridView with custom layout
        CustomGridAdapter adapter = new CustomGridAdapter(this, imageResources, names, descriptions);
        gridView.setAdapter(adapter);

        // Set item click listener to show description
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = names[position];
                String selectedDescription = descriptions[position];
                Toast.makeText(WebsiteGridActivity.this, selectedName + ": " + selectedDescription, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to open a website link
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}

package com.example.calculator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView historyTextView = findViewById(R.id.history_text_view);

        // Get the history data from the Intent
        ArrayList<String> history = getIntent().getStringArrayListExtra("history");

        // Display the history
        StringBuilder historyText = new StringBuilder();
        if (history != null) {
            for (String record : history) {
                historyText.append(record).append("\n");
            }
        }
        historyTextView.setText(historyText.toString());
    }
}

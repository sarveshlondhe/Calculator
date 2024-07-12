package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTV, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAc, buttonDot, buttonHistory;
    List<String> calculationHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Setting the content view

        resultTV = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC, R.id.button_c);
        assignId(buttonBrackOpen, R.id.button_open_bracket);
        assignId(buttonBrackClose, R.id.button_close_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_add);  // Corrected the typo to buttonPlus
        assignId(buttonMinus, R.id.button_subtract);  // Corrected the typo to buttonMinus
        assignId(buttonEquals, R.id.button_equal);
        assignId(buttonDot, R.id.button_dot);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAc, R.id.button_ac);
        assignId(buttonHistory, R.id.button_history);  // Add history button
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTV.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalculate);
            if (!finalResult.equals("Error")) {
                resultTV.setText(finalResult);
                addToHistory(dataToCalculate + " = " + finalResult);
            }
            return;
        }

        if (buttonText.equals("C")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else if (buttonText.equals("HISTORY")) {
            openHistoryActivity();
            return;
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);
    }

    String getResult(String data) {
        try {

            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String rawResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();
            context.exit();

            // Convert the result to BigDecimal for better precision and rounding
            BigDecimal result = new BigDecimal(rawResult);
            result = result.setScale(6, RoundingMode.HALF_UP);  // Set scale to 6 decimal places

            // Remove trailing zeros and decimal point if the result is an integer
            String finalResult = result.stripTrailingZeros().toPlainString();

            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }

    void addToHistory(String calculation) {
        calculationHistory.add(calculation);
    }

    private void openHistoryActivity() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        intent.putStringArrayListExtra("history", (ArrayList<String>) calculationHistory);
        startActivity(intent);
    }
}

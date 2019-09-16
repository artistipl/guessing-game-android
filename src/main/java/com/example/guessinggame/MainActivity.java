package com.example.guessinggame;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private Button btnGuess;
    private TextView txtInfo;
    private Button btnPlayAgain;
    private int theNumber;
    private int counter = 0;
    private boolean activeEnter;

    public void checkGuess() {
        String guessText = txtGuess.getText().toString();
        String message ="";
        counter++;
        try {
            int guessInt = Integer.parseInt(guessText);
            if (guessInt > theNumber) {
                activeEnter = true;
                message = "Liczba " + guessInt + " jest wyższa od mojej.";
            } else if (guessInt < theNumber) {
                activeEnter = true;
                message = "Liczba " + guessInt + " jest niższa od mojej.";
            } else {
                activeEnter = false;
                message = "Brawo! " + guessInt + " to liczba, którą miałem na myśli! Wystarczyło Ci " + counter + " prób.";
                btnPlayAgain.setVisibility(View.VISIBLE);
                btnGuess.setEnabled(false);
                closeKeyboard();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

        } catch (NumberFormatException e) {
            message = "Wprowadż poprawną liczbę od 1 do 100!";
        } finally {
            txtInfo.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void newGame() {
        counter = 0;
        theNumber = (int) (Math.random() * 100 + 1);
        btnPlayAgain.setVisibility(View.INVISIBLE);
        btnGuess.setEnabled(true);
        txtInfo.setText(getString(R.string.type_number));
        txtGuess.setText("");
        activeEnter = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGuess = findViewById(R.id.txtGuess);
        btnGuess = findViewById(R.id.btnGuess);
        txtInfo = findViewById(R.id.txtInfo);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        newGame();
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEnter = false;
                checkGuess();
            }
        });
        txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (activeEnter == true) {
                    checkGuess();
                    return true;
                } else {
                    return false;
                }
            }
        });
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.lab1_20206456;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private String[] words;
    private Random random;
    private String currWord;
    private TextView[] charViews;
    private LinearLayout wordLayout;
    private LetterAdapter adapter;
    private GridView gridView;
    private int numCorr;
    private int numChars;
    private ImageView[] parts;
    private int sizeParts=6;
    private int currPart;
    private long startTime;
    private ArrayList<String> gameRes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_game));

        ImageView backButton = findViewById(R.id.imageView);
        ImageView statsButton = findViewById(R.id.imageView2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, StatisticsActivity.class);
                intent.putStringArrayListExtra("gameResults", gameRes);
                intent.putExtra("playerName", getIntent().getStringExtra("playerName"));
                startActivity(intent);
            }
        });

        words = getResources().getStringArray(R.array.words);
        wordLayout = findViewById(R.id.words);
        gridView = findViewById(R.id.letters);
        random=new Random();

        parts = new ImageView[sizeParts];
        parts[0] = findViewById(R.id.head);
        parts[1] = findViewById(R.id.body);
        parts[2] = findViewById(R.id.armLeft);
        parts[3] = findViewById(R.id.armRight);
        parts[4] = findViewById(R.id.legLeft);
        parts[5] = findViewById(R.id.legRight);

        playGame();
    }


    private void playGame(){
        String newWord = words[random.nextInt(words.length)]; // Sirve para que cada inicio del juego se presenten las palabras en un orden random

        while(newWord.equals(currWord)){  // Para que las palabras no se repitan
            newWord=words[random.nextInt(words.length)];
        }

        startTime = System.currentTimeMillis();

        currWord = newWord.toUpperCase();

        wordLayout.removeAllViews();

        charViews = new TextView[currWord.length()];
        for(int i=0; i<currWord.length();i++){
            charViews[i]=new TextView(this);
            charViews[i].setText(""+currWord.charAt(i));
            charViews[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            charViews[i].setGravity(Gravity.CENTER);
            charViews[i].setTextColor(Color.WHITE);
            charViews[i].setBackgroundResource(R.drawable.letter_bg);
            wordLayout.addView(charViews[i]);
        }

        adapter=new LetterAdapter(this);
        gridView.setAdapter(adapter);
        numCorr = 0;
        currPart = 0;
        numChars = currWord.length();

        for (ImageView part : parts) {
            part.setVisibility(View.INVISIBLE);
        }
    }

    public void letterPressed(View view){
        String letter=((TextView) view).getText().toString().toUpperCase();
        char letterChar = letter.charAt(0);

        view.setEnabled(false);
        boolean correct = false;

        for(int i = 0; i<currWord.length();i++){
            if(currWord.charAt(i) == letterChar){
                correct = true;
                numCorr++;
                charViews[i].setTextColor(Color.BLACK);
                charViews[i].setText("" + currWord.charAt(i));
            }
        }

        TextView gameResultTextView = findViewById(R.id.gameResult);

        if(correct){
            if(numCorr == numChars){
                disabledButtons();

                long endTime = System.currentTimeMillis();
                long timeTaken = (endTime - startTime) / 1000;
                isGameResult("Terminó en " + timeTaken + "s");
                gameResultTextView.setText("Ganó / Terminó en " + timeTaken + "s");
            }

        }else if(currPart<sizeParts) {
            parts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        }else{
            disabledButtons();
            isGameResult("Perdió");
            gameResultTextView.setText("Perdió. La palabra era: " + currWord);
        }

    }

    public void disabledButtons(){
        for(int i=0; i<gridView.getChildCount();i++){
            gridView.getChildAt(i).setEnabled(false);
        }
    }

    public void startNewGame(View view) {
        playGame();

        TextView gameResultTextView = findViewById(R.id.gameResult);
        isGameResult("Canceló");
        gameResultTextView.setText("");
    }

    private void isGameResult(String result) {
        gameRes.add("Juego " + (gameRes.size() + 1) + ": " + result);
    }



}

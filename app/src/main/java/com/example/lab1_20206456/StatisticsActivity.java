package com.example.lab1_20206456;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_statistics));

        ImageView backButton = findViewById(R.id.imageView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tvPlayerName = findViewById(R.id.playerNameText3);

        // Para mostrar el nombre
        String playerName = getIntent().getStringExtra("playerName");

        if (playerName != null) {
            tvPlayerName.setText(playerName);
        }

        ArrayList<String> gameRes = getIntent().getStringArrayListExtra("gameResults");

        TextView statisticsTextView = findViewById(R.id.statisticsTextView);
        for (int i = 0; i < gameRes.size(); i++) {
            statisticsTextView.append(gameRes.get(i) + "\n");
        }

        Button btnNewGame = findViewById(R.id.btnNewGame);
        // Volver a jugar
        btnNewGame.setOnClickListener(v -> {
            Intent intent = new Intent(StatisticsActivity.this, GameActivity.class);
            startActivity(intent);
        });


    }
}

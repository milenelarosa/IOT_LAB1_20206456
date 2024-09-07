package com.example.lab1_20206456;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonPlay;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextName = findViewById(R.id.name_input);
        buttonPlay = findViewById(R.id.play_button);
        tvTitle = findViewById(R.id.title);

        // Habilitar el botón si el nombre está ingresado
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonPlay.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Registrar context menu para el título
        registerForContextMenu(tvTitle);

        // Acción del botón jugar
        buttonPlay.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("playerName", name);
            startActivity(intent);
        });

    }

    // Crear el menú contextual para cambiar el color del título
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.title) {
            menu.add(0, v.getId(), 0, "Verde");
            menu.add(0, v.getId(), 1, "Rojo");
            menu.add(0, v.getId(), 2, "Morado");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Verde":
                tvTitle.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                return true;
            case "Rojo":
                tvTitle.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                return true;
            case "Morado":
                tvTitle.setTextColor(getResources().getColor(android.R.color.holo_purple));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
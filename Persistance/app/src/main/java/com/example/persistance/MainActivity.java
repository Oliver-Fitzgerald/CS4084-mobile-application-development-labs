package com.example.persistance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBNameStore nameStore;
    private String input;

    private void displayNames() {
        ArrayList<Name> names = nameStore.getNames(MainActivity.this);
        Toast.makeText(MainActivity.this, names.get(0).getFirst(), Toast.LENGTH_SHORT).show();
        String displayNames = "";
        for (Name name: names) {
            Toast.makeText(MainActivity.this, name.getFirst() + " " + name.getLast(), Toast.LENGTH_SHORT).show();
            displayNames += name.getFirst() + " " + name.getLast() + "\n";
        }

        TextView text_display = findViewById(R.id.displayed_text);
        text_display.setText(displayNames);
    }

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

        nameStore = new DBNameStore(this.getApplicationContext());
        TextInputEditText input_component = findViewById(R.id.names_input);
        Button button = findViewById(R.id.enter_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input = input_component.getText().toString();
                ArrayList<Name> names = nameStore.splitIntoNames(input);
                System.out.println(names.get(0).getFirst());

                if (!names.isEmpty()) {
                    nameStore.writeNames(MainActivity.this, names);
                    displayNames();
                }
            }
        });

    }
}
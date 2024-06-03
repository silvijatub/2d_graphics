package com.example.a2d_graphics;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> radius = new ArrayList<Integer>();
    ArrayList<Integer> border = new ArrayList<Integer>();
    ArrayList<Integer> fill = new ArrayList<Integer>();
    ArrayList<Integer> stroke = new ArrayList<Integer>();
    ArrayList<Integer> xCoord = new ArrayList<Integer>();
    ArrayList<Integer> yCoord = new ArrayList<Integer>();
    DrawView canvas;
    private int numberOfCircles = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editTextText);
        Button drawButton = findViewById(R.id.button);
        Button saveButton = findViewById(R.id.buttonSave);
        canvas = findViewById(R.id.draw_view);

        loadDrawingParameters();

        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredText = editText.getText().toString();

                // Remove non-alphabetic characters
                enteredText = enteredText.replaceAll("[^a-zA-Z]", "");
                int numberOfLetters = enteredText.length();
                Toast.makeText(MainActivity.this, "Number of letters: " +
                        numberOfLetters, Toast.LENGTH_SHORT).show();
                numberOfCircles = numberOfLetters;

                canvas.setNumberOfCircles(numberOfCircles);

                // Clear previous circles
                //canvas.clearCircles();

                radius.clear();
                border.clear();
                fill.clear();
                stroke.clear();
                xCoord.clear();
                yCoord.clear();

                // Draw new circles with random parameters and locations
                Random rand = new Random();
                int canvasWidth = canvas.getWidth();
                int canvasHeight = canvas.getHeight();

                for (int i = 0; i < numberOfLetters; i++) {
                    // Generate random parameters for each circle
                    int circleRadius = rand.nextInt(canvas.getWidth() / 4);
                    radius.add(circleRadius);
                    int borderWidth = rand.nextInt(10) + 1;
                    border.add(borderWidth);
                    int fillColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                    fill.add(fillColor);
                    int strokeColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                    stroke.add(strokeColor);
                    // Generate random coordinates within the canvas bounds
                    int centerX = rand.nextInt(canvasWidth);
                    xCoord.add(centerX);
                    int centerY = rand.nextInt(canvasHeight);
                    yCoord.add(centerY);


                        canvas.setCircleParams(i, centerX, centerY, circleRadius, borderWidth, fillColor, strokeColor);
                                        // Set parameters in DrawView for each circle

                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDrawingParameters(radius, border, fill, stroke, xCoord, yCoord);
            }
        });
    }

    private void saveDrawingParameters(ArrayList<Integer> radius, ArrayList<Integer> border,
                                       ArrayList<Integer> fill, ArrayList<Integer> stroke,
                                       ArrayList<Integer> xCoord, ArrayList<Integer> yCoord) {
        File file = new File(getExternalFilesDir(null), "drawing_parameters.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < radius.size(); i++) {
                writer.write(radius.get(i) + "," + border.get(i) + "," + fill.get(i) + "," +
                        stroke.get(i) + "," + xCoord.get(i) + "," + yCoord.get(i));
                writer.newLine();
            }
            writer.close();
            Toast.makeText(this, "Drawing parameters saved.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save drawing parameters.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDrawingParameters() {
        File file = new File(getExternalFilesDir(null), "drawing_parameters.txt");
        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] params = line.split(",");
                radius.add(Integer.parseInt(params[0]));
                border.add(Integer.parseInt(params[1]));
                fill.add(Integer.parseInt(params[2]));
                stroke.add(Integer.parseInt(params[3]));
                xCoord.add(Integer.parseInt(params[4]));
                yCoord.add(Integer.parseInt(params[5]));
            }
            reader.close();

            // Set the number of circles
            numberOfCircles = radius.size();
            canvas.setNumberOfCircles(numberOfCircles);

            // Clear previous circles
           // canvas.clearCircles();

            // Set parameters in DrawView for each circle
            for (int i = 0; i < numberOfCircles; i++) {
                canvas.setCircleParams(i, xCoord.get(i), yCoord.get(i), radius.get(i), border.get(i), fill.get(i), stroke.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load drawing parameters.", Toast.LENGTH_SHORT).show();
        }
    }

}
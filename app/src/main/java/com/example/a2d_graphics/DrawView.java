package com.example.a2d_graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class DrawView extends View {
    private ArrayList<Circle> circles = new ArrayList<>();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNumberOfCircles(int number) {
        if (circles.size() < number) {
            for (int i = circles.size(); i < number; i++) {
                circles.add(new Circle());
            }
        } else if (circles.size() > number) {
            for (int i = circles.size() - 1; i >= number; i--) {
                circles.remove(i);
            }
        }
    }

    public void setCircleParams(int index, int x, int y, int radius, int borderWidth, int fillColor, int strokeColor) {
        if (index >= circles.size()) return;
        Circle circle = circles.get(index);
        circle.x = x;
        circle.y = y;
        circle.radius = radius;
        circle.borderWidth = borderWidth;
        circle.fillColor = fillColor;
        circle.strokeColor = strokeColor;
        invalidate();
    }

    public void clearCircles() {
        circles.clear();
        invalidate();
    }

    // Add methods to get parameters of all circles
    public ArrayList<Integer> getRadius() {
        ArrayList<Integer> radiusList = new ArrayList<>();
        for (Circle circle : circles) {
            radiusList.add(circle.radius);
        }
        return radiusList;
    }

    public ArrayList<Integer> getBorder() {
        ArrayList<Integer> borderList = new ArrayList<>();
        for (Circle circle : circles) {
            borderList.add(circle.borderWidth);
        }
        return borderList;
    }

    public ArrayList<Integer> getFill() {
        ArrayList<Integer> fillList = new ArrayList<>();
        for (Circle circle : circles) {
            fillList.add(circle.fillColor);
        }
        return fillList;
    }

    public ArrayList<Integer> getStroke() {
        ArrayList<Integer> strokeList = new ArrayList<>();
        for (Circle circle : circles) {
            strokeList.add(circle.strokeColor);
        }
        return strokeList;
    }

    public ArrayList<Integer> getXCoord() {
        ArrayList<Integer> xCoordList = new ArrayList<>();
        for (Circle circle : circles) {
            xCoordList.add(circle.x);
        }
        return xCoordList;
    }

    public ArrayList<Integer> getYCoord() {
        ArrayList<Integer> yCoordList = new ArrayList<>();
        for (Circle circle : circles) {
            yCoordList.add(circle.y);
        }
        return yCoordList;
    }

    // Circle class definition
    private class Circle {
        int x, y, radius, borderWidth, fillColor, strokeColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Circle circle : circles) {
            Paint paint = new Paint();
            paint.setColor(circle.fillColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(circle.x, circle.y, circle.radius, paint);

            paint.setColor(circle.strokeColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(circle.borderWidth);
            canvas.drawCircle(circle.x, circle.y, circle.radius, paint);
        }
    }
}

